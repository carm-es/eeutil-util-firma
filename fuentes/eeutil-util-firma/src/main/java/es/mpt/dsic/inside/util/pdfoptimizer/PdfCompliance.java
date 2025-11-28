package es.mpt.dsic.inside.util.pdfoptimizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.DomXmpParser;

import java.io.IOException;
import java.io.InputStream;

public enum PdfCompliance {

  UNKNOWN(0x0000) {
    @Override
    public boolean isPdfA() {
      return false;
    }
  },

  OTHERS(0x0900), // Formatos no controlados como PDF/A-4E y PDF/A-4F (ISO 19005-4:2020)

  PDFA_1B(0x1401) { // Conformidad básica (ISO 19005-1:2005)
    @Override
    public int getPart() {
      return 1;
    }

    @Override
    public String getConformance() {
      return "B";
    }
  },

  PDFA_1A(0x1402) { // Conformidad accesible (ISO 19005-1:2005)
    @Override
    public int getPart() {
      return 1;
    }

    @Override
    public String getConformance() {
      return "A";
    }
  },

  PDFA_2B(0x1701) { // Conformidad básica (ISO 19005-2:2011)
    @Override
    public int getPart() {
      return 2;
    }

    @Override
    public String getConformance() {
      return "B";
    }
  },

  PDFA_2U(0x1702) { // Conformidad unicode (ISO 19005-2:2011)
    @Override
    public int getPart() {
      return 2;
    }

    @Override
    public String getConformance() {
      return "U";
    }
  },

  PDFA_2A(0x1703) { // Conformidad accesible (ISO 19005-2:2011)
    @Override
    public int getPart() {
      return 2;
    }

    @Override
    public String getConformance() {
      return "A";
    }
  },

  PDFA_3B(0x1711) { // Conformidad básica con archivos adjuntos (ISO 19005-3:2012)
    @Override
    public int getPart() {
      return 3;
    }

    @Override
    public String getConformance() {
      return "B";
    }
  },

  PDFA_3U(0x1712) { // Conformidad unicode con archivos adjuntos (ISO 19005-3:2012)
    @Override
    public int getPart() {
      return 3;
    }

    @Override
    public String getConformance() {
      return "U";
    }
  },

  PDFA_3A(0x1713) { // Conformidad accesible con archivos adjuntos (ISO 19005-3:2012)
    @Override
    public int getPart() {
      return 3;
    }

    @Override
    public String getConformance() {
      return "A";
    }
  };

  protected final static Log logger = LogFactory.getLog(PdfCompliance.class);//

  public static final PdfCompliance DEFAULT_FORMAT = PDFA_2B;
  private static final PdfCompliance[] allFormats =
      {PDFA_1B, PDFA_1A, PDFA_2B, PDFA_2U, PDFA_2A, PDFA_3B, PDFA_3U, PDFA_3A};

  private int level = 0;

  private PdfCompliance(int lvl) {
    this.level = lvl;
  }

  private int getLevel() {
    return this.level;
  }

  public boolean isPdfA() {
    return true;
  }

  public int getPart() {
    return 0;
  }

  public String getConformance() {
    return null;
  }

  public static PdfCompliance getCompliance(Integer number) {
    if (null == number) {
      return PdfCompliance.DEFAULT_FORMAT;
    }
    for (int i = 0; i < allFormats.length; i++) {
      if (allFormats[i].getLevel() == number) {
        return allFormats[i];
      }
    }
    return PdfCompliance.DEFAULT_FORMAT;
  }

  public static PdfCompliance getCompliance(String format) {
    if (null == format) {
      return PdfCompliance.DEFAULT_FORMAT;
    }
    if (format.toUpperCase().contains("1")) {
      if (format.toUpperCase().contains("B")) {
        return PDFA_1B;
      } else if (format.toUpperCase().contains("A")) {
        return PDFA_1A;
      }
    } else if (format.toUpperCase().contains("2")) {
      if (format.toUpperCase().contains("B")) {
        return PDFA_2B;
      } else if (format.toUpperCase().contains("A")) {
        return PDFA_2A;
      } else if (format.toUpperCase().contains("U")) {
        return PDFA_2U;
      }
    } else if (format.toUpperCase().contains("3")) {
      if (format.toUpperCase().contains("B")) {
        return PDFA_3B;
      } else if (format.toUpperCase().contains("A")) {
        return PDFA_3A;
      } else if (format.toUpperCase().contains("U")) {
        return PDFA_3U;
      }
    } else if (format.toUpperCase().contains("4")) {
      return OTHERS;
    }
    return PdfCompliance.DEFAULT_FORMAT;
  }

  public static PdfCompliance builder(int part, String conformance) {
    switch (part) {
      case 1:
        if ("B".equalsIgnoreCase(conformance)) {
          return PDFA_1B;
        } else if ("A".equalsIgnoreCase(conformance)) {
          return PDFA_1A;
        }
        break;
      case 2:
        if ("B".equalsIgnoreCase(conformance)) {
          return PDFA_2B;
        } else if ("U".equalsIgnoreCase(conformance)) {
          return PDFA_2U;
        } else if ("A".equalsIgnoreCase(conformance)) {
          return PDFA_2A;
        }
        break;
      case 3:
        if ("B".equalsIgnoreCase(conformance)) {
          return PDFA_3B;
        } else if ("U".equalsIgnoreCase(conformance)) {
          return PDFA_3U;
        } else if ("A".equalsIgnoreCase(conformance)) {
          return PDFA_3A;
        }
        break;
      case 4:
        if ("E".equalsIgnoreCase(conformance)) {
          return OTHERS;
        } else if ("F".equalsIgnoreCase(conformance)) {
          return OTHERS;
        }
        break;
    }
    return PdfCompliance.UNKNOWN;
  }

  public static PdfCompliance detectPDFAType(byte[] pdfFile) {
    PdfCompliance retVal = PdfCompliance.UNKNOWN;
    PDDocument document = null;

    try {
      document = PDDocument.load(pdfFile);
      retVal = detectPDFAType(document);
      document.close();
    } catch (Exception x) {
      logger.error("Error detecting PDF/A type", x);
    }
    return retVal;
  }

  private static PdfCompliance detectPDFAType(PDDocument document) throws IOException {
    try {
      // Obtener el catálogo del documento
      PDDocumentCatalog catalog = document.getDocumentCatalog();

      // Obtener los metadatos XMP
      PDMetadata metadata = catalog.getMetadata();

      if (metadata == null) {
        return PdfCompliance.UNKNOWN;
      }

      // Parsear los metadatos XMP
      InputStream xmpInputStream = metadata.createInputStream();
      DomXmpParser xmpParser = new DomXmpParser();
      XMPMetadata xmpMetadata = xmpParser.parse(xmpInputStream);
      xmpInputStream.close();

      // Buscar el schema PDF/A Identification
      PDFAIdentificationSchema pdfaid = xmpMetadata.getPDFIdentificationSchema();

      if (pdfaid == null) {
        return PdfCompliance.UNKNOWN;
      }

      // Extraer parte y conformidad
      Integer part = pdfaid.getPart();
      String conformance = pdfaid.getConformance();

      if (part == null || conformance == null || conformance.isEmpty()) {
        return PdfCompliance.UNKNOWN;
      }
      return PdfCompliance.builder(part, conformance);

    } catch (Exception e) {
      logger.error("Error detecting PDF/A type", e);
    }
    return PdfCompliance.UNKNOWN;
  }

}

