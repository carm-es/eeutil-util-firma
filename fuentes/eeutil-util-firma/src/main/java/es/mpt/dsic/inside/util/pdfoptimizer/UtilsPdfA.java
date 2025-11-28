package es.mpt.dsic.inside.util.pdfoptimizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.schema.XMPBasicSchema;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.xml.XmpSerializer;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;


public class UtilsPdfA {
  protected final static Log logger = LogFactory.getLog(UtilsPdfA.class);

  private UtilsPdfA() {}

  public static byte[] convertToPDFA(byte[] inputFile, PdfCompliance compliance)
      throws IOException {
    PDDocument document = null;
    InputStream colorProfile = null;

    try {
      // Cargar el documento PDF
      document = PDDocument.load(inputFile);

      // Crear el catálogo del documento
      PDDocumentCatalog catalog = document.getDocumentCatalog();

      // Agregar metadatos XMP para PDF/A-3
      addXMPMetadata(document, compliance);

      // Agregar perfil de color ICC (sRGB)
      UtilsPdfA dummy = new UtilsPdfA();
      colorProfile = dummy.getClass()
          .getResourceAsStream("/org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc");
      if (colorProfile == null) {
        // Intenta cargar desde el classpath alternativo
        colorProfile = PDDocument.class
            .getResourceAsStream("/org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc");
      }

      if (colorProfile != null) {
        PDOutputIntent outputIntent = new PDOutputIntent(document, colorProfile);
        outputIntent.setInfo("sRGB IEC61966-2.1");
        outputIntent.setOutputCondition("sRGB IEC61966-2.1");
        outputIntent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
        outputIntent.setRegistryName("http://www.color.org");
        catalog.addOutputIntent(outputIntent);
      }

      // Marcar como PDF/A-3
      catalog.setVersion("1.7");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      document.save(baos);
      return baos.toByteArray();

    } catch (BadFieldValueException | TransformerException e) {
      throw new IOException("Error al crear metadatos XMP", e);
    } finally {
      if (document != null) {
        document.close();
      }
      if (colorProfile != null) {
        colorProfile.close();
      }
    }
  }

  private static void addXMPMetadata(PDDocument document, PdfCompliance compliance)
      throws BadFieldValueException, TransformerException, IOException {

    XMPMetadata xmp = XMPMetadata.createXMPMetadata();

    // Schema PDF/A Identification
    PDFAIdentificationSchema pdfaid = xmp.createAndAddPDFAIdentificationSchema();
    pdfaid.setPart(compliance.getPart());
    pdfaid.setConformance(compliance.getConformance());

    // Schema Dublin Core
    DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
    dc.setTitle("Conversión EEUTILS");
    dc.addCreator("EEUTILS - CARM");
    dc.setDescription("Documento convertido a PDF/A (" + compliance + ")");

    // Schema XMP Basic
    XMPBasicSchema xmpBasic = xmp.createAndAddXMPBasicSchema();
    xmpBasic.setCreateDate(GregorianCalendar.getInstance());
    xmpBasic.setModifyDate(GregorianCalendar.getInstance());
    xmpBasic.setMetadataDate(GregorianCalendar.getInstance());
    xmpBasic.setCreatorTool("Apache PDFBox");

    // Serializar XMP a XML
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    XmpSerializer serializer = new XmpSerializer();
    serializer.serialize(xmp, baos, true);

    // Crear metadata stream
    PDMetadata metadata = new PDMetadata(document);
    metadata.importXMPMetadata(baos.toByteArray());

    // Agregar metadata al catálogo
    document.getDocumentCatalog().setMetadata(metadata);
  }

  public static boolean validateSimple(byte[] data) {
    if (null == data) {
      return false;
    }
    return PdfCompliance.detectPDFAType(data).isPdfA();
  }
}
