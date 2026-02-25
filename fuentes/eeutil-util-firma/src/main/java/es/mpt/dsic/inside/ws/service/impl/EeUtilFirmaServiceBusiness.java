/*
 * Copyright (C) 2025, Gobierno de España This program is licensed and may be used, modified and
 * redistributed under the terms of the European Public License (EUPL), either version 1.1 or (at
 * your option) any later version as soon as they are approved by the European Commission. Unless
 * required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and more details. You
 * should have received a copy of the EUPL1.1 license along with this program; if not, you may find
 * it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.mpt.dsic.inside.ws.service.impl;

import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.xml.xmp.DublinCoreSchema;
import com.lowagie.text.xml.xmp.PdfSchema;
import com.lowagie.text.xml.xmp.XmpArray;
import com.lowagie.text.xml.xmp.XmpSchema;
import com.lowagie.text.xml.xmp.XmpWriter;

import es.gob.afirma.core.AOUnsupportedSignFormatException;
import es.mpt.dsic.inside.configure.ConfigureLibreofficeMime;
import es.mpt.dsic.inside.configure.ConfigureRestInfo;
import es.mpt.dsic.inside.copiaautentica.ImageProperties;
import es.mpt.dsic.inside.copiaautentica.PdfCopiaAutenticaUtils;
import es.mpt.dsic.inside.copiaautentica.PdfCopiaNormalizadaUtils;
import es.mpt.dsic.inside.csv.CSVUtil;
import es.mpt.dsic.inside.csv.ext.CVSSignature;
import es.mpt.dsic.inside.csv.ext.SignatureProcessor;
import es.mpt.dsic.inside.exception.AfirmaException;
import es.mpt.dsic.inside.model.FirmaInfoAfirma;
import es.mpt.dsic.inside.model.InformacionFirmaAfirma;
import es.mpt.dsic.inside.model.ResultadoValidacionInfoAfirma;
import es.mpt.dsic.inside.pdf.NewPdfConversion;
import es.mpt.dsic.inside.pdf.converter.PdfConverter;
import es.mpt.dsic.inside.pdf.file.StamperWrapper;
import es.mpt.dsic.inside.security.context.AplicacionContext;
import es.mpt.dsic.inside.security.model.AppInfo;
import es.mpt.dsic.inside.services.AfirmaService;
import es.mpt.dsic.inside.util.CodigosError;
import es.mpt.dsic.inside.util.SignedData;
import es.mpt.dsic.inside.util.SignedDataExtractor;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.utils.file.FileUtil;
import es.mpt.dsic.inside.utils.io.IOUtil;
import es.mpt.dsic.inside.utils.mime.MimeUtil;
import es.mpt.dsic.inside.utils.pdf.PdfEncr;
import es.mpt.dsic.inside.utils.xml.XMLUtil;
import es.mpt.dsic.inside.ws.service.exception.InSideException;
import es.mpt.dsic.inside.ws.service.model.CSVInfo;
import es.mpt.dsic.inside.ws.service.model.CSVInfoAmbito;
import es.mpt.dsic.inside.ws.service.model.ContenidoInfo;
import es.mpt.dsic.inside.ws.service.model.CopiaInfo;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoExtended;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirma;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirmaSalida;
import es.mpt.dsic.inside.ws.service.model.EstadoInfo;
import es.mpt.dsic.inside.ws.service.model.FirmaInfo;
import es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@Component
public class EeUtilFirmaServiceBusiness {

  private static final String ERROR_AL_ACCEDER_AL_SERVICIO_DE_LIBREOFFICE =
      "Error al acceder al servicio de libreoffice.";

  private static final String ERROR_AL_GENERAR_CONST = "Error al generar ";

  private static final String APPLICATION_PDF_MIME = "application/pdf";

  private static final String LANG_PARAM = "/Lang";

  private static final String TITLE_PARAM = "/Title";

  protected static final Log logger = LogFactory.getLog(EeUtilFirmaServiceBusiness.class);

  private static final String ERROR = "ERROR";

  private static final String ERROR_AL_ELIMINAR_FICHERO = "Error al eliminar fichero:";

  @Autowired(required = false)
  private AplicacionContext aplicacionContext;

  @Autowired
  private PdfCopiaNormalizadaUtils pdfCopiaNormalizadaUtils;

  @Autowired
  PdfConverter pdfConverter;

  @Autowired
  private AfirmaService afirmaService;

  @Autowired
  SignatureProcessor processor;

  @Autowired
  private NewPdfConversion newPdfConversion;

  @Autowired
  private PdfCopiaAutenticaUtils pdfCopiaAutenticaUtils;

  /*
   * constantes para calcular el hash
   */
  private static final String ALGORITMO_SHA256 = "SHA256";
  private static final String ALGORITMO_SHA384 = "SHA384";
  private static final String ALGORITMO_SHA512 = "SHA512";

  private static final int LONGITUD_HASHHEX_SHA256 = 64;
  private static final int LONGITUD_HASHHEX_SHA384 = 96;
  private static final int LONGITUD_HASHHEX_SHA512 = 128;

  private static final String TEXT_TCN_MIME = "text/tcn";


  public CopiaInfo generarCopiaFirmaNormalizada(String idApp, String passw, CopiaInfo copia,
      ListaFirmaInfo firmas, boolean simple) throws EeutilException {

    Object[] aObj = null;
    File entrada = null;
    File entradaSinSeguridad = null;
    File pdf = null;
    File fileFixedRotations = null;
    File pdfReduced = null;
    File pdfResult = null;
    File pdfBlank = null;
    String outputResultado = null;
    List<Integer> listaOrientacionPaginas = null;
    StamperWrapper stamperWrapper = null;

    boolean bTieneRotaciones = false;

    try {
      logger.debug("generarCopiaFirma");

      if (StringUtils.isEmpty(copia.getIdAplicacion())
          && !CSVUtil.hasScope(copia.getIdAplicacion())) {
        throw new InSideException("No se ha especificado el �mbito", new EstadoInfo());
      }
      AppInfo info = aplicacionContext.getAplicacionInfo();
      String rutaLogo = info.getPropiedades().get("rutaLogo");


      String[] aResultado = workFlowMimeLibreoffice(copia, "copiaFirmaNormalizada");

      entrada = new File(aResultado[0]);

      // String mimeFinal = aResultado[1];

      // si el mime es de pdf le quitamos la seguridad al fichero.
      /*
       * if(mimeFinal.equals("application/pdf")) { //String rutaSinSeguridad =
       * FileUtil.createFilePath("sinSeguridad") + ".pdf";
       * 
       * //quitamos seguridad al pdf original //entradaSinSeguridad =
       * copiarPdfEliminandoSeguridad(entrada.toPath(), rutaSinSeguridad);
       * entradaSinSeguridad=entrada; } //si no es pdf no se hace el proceso de quitar la seguridad
       * puesto que la conversion no lleva seguridad ya. else { entradaSinSeguridad = entrada; }
       */

      entradaSinSeguridad = entrada;


      // Se convierte a PDF
      pdf = convertir(idApp, passw, entradaSinSeguridad, aResultado[1]);

      // Se convierte a un PDF con las paginas del anterior pdf
      // estampadas a tamano reducido

      // fileFixedRotations= newPdfConversion.fixedFilesWithRotations(pdf);

      fileFixedRotations = pdf;

      stamperWrapper = new StamperWrapper();
      String fileOut = stamperWrapper.createFilePrefix(NewPdfConversion.REDUCED_PREFIX, ".pdf");

      stamperWrapper.createStamperWrapper(fileFixedRotations, fileOut);

      // Se convierte a un PDF con las paginas del anterior pdf
      // estampadas a tamano reducido
      aObj = newPdfConversion.copiaPDFReducidoStamper(stamperWrapper);
      pdfReduced = new File(stamperWrapper.getNameFileFileOutPrefix());
      listaOrientacionPaginas = (List<Integer>) aObj[1];

      stamperWrapper.closeAll();

      String resultPathBlank = FileUtil.createFilePath("blankCabeceraPie") + ".pdf";

      pdfBlank = crearPaginasVaciasTodasPaginas(listaOrientacionPaginas);

      String resultPath = FileUtil.createFilePath("copiaNormalizada") + ".pdf";

      stamperWrapper.createStamperWrapper(pdfBlank, resultPathBlank);

      PdfCopiaAutenticaUtils copiaAutenticaUtils = new PdfCopiaAutenticaUtils();

      int numPaginas = stamperWrapper.getReader().getNumberOfPages();


      // Se calcula el número de paginas en blanco a meter al final, si
      // hay muchos firmantes.
      int pagsBlancas = pdfCopiaNormalizadaUtils.numeroPaginasEnBlanco(firmas, simple);

      if (pagsBlancas > 0) {
        // Se meten las paginas en blanco
        pdfCopiaNormalizadaUtils.insertarPaginasEnBlanco(stamperWrapper, pagsBlancas);
      }
      // Se estampa la informacion
      pdfResult = pdfCopiaNormalizadaUtils.copiaAutenticaNormalizadaOptimized(
          /* (PdfReader)aObj[5], (FileOutputStream) aObj[6], (PdfStamper)aObj[7], */stamperWrapper,
          copia, rutaLogo, simple, firmas, pagsBlancas, listaOrientacionPaginas);

      stamperWrapper.closeAll();

      HashMap<String, String> mDatosAccesibilidad = new HashMap<>();
      mDatosAccesibilidad.put(TITLE_PARAM, aObj[3].toString());
      mDatosAccesibilidad.put(LANG_PARAM, aObj[2].toString());

      bTieneRotaciones = (boolean) aObj[5];

      if (!bTieneRotaciones) {
        outputResultado = stampReducidoYInfoAccesibilidad(pdfResult, pdfReduced,
            mDatosAccesibilidad, (List) aObj[4], bTieneRotaciones);
      } else {
        outputResultado = stampReducidoYInfoAccesibilidad(pdfReduced, pdfResult,
            mDatosAccesibilidad, (List) aObj[4], bTieneRotaciones);
        // outputResultado =
        // stampReducidoYInfoAccesibilidad(pdfResult,pdfReduced,mDatosAccesibilidad,(List)aObj[4]);
      }

      copia.getContenido().setContenido(IOUtil.getBytesFromObject(new File(outputResultado)));
      copia.getContenido().setTipoMIME(APPLICATION_PDF_MIME);

    } catch (EeutilException e) {
      throw new EeutilException("Error en generarCopiaFirmaNormalizada: " + e.getMessage(), e);
    } catch (Exception t) {
      throw new EeutilException("Error en generarCopiaFirmaNormalizada: " + t.getMessage(), t);
    } finally {
      try {
        if (entrada != null && entrada.exists()) {
          FileUtils.forceDelete(entrada);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + entrada.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + entrada.getAbsolutePath(), e);
      }
      try {
        if (entradaSinSeguridad != null && entradaSinSeguridad.exists()) {
          FileUtils.forceDelete(entradaSinSeguridad);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + entrada.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + entradaSinSeguridad.getAbsolutePath(),
            e);
      }
      try {
        if (pdf != null && pdf.exists()) {
          FileUtils.forceDelete(pdf);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdf.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdf.getAbsolutePath(), e);
      }
      try {
        if (pdfReduced != null && pdfReduced.exists()) {
          FileUtils.forceDelete(pdfReduced);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdfReduced.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfReduced.getAbsolutePath(), e);
      }
      try {
        if (pdfResult != null && pdfResult.exists()) {
          FileUtils.forceDelete(pdfResult);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdfResult.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfResult.getAbsolutePath(), e);
      }
      try {
        if (fileFixedRotations != null && fileFixedRotations.exists()) {
          FileUtils.forceDelete(fileFixedRotations);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdfResult.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + fileFixedRotations.getAbsolutePath(),
            e);
      }
      if (outputResultado != null) {
        File f = new File(outputResultado);
        try {
          if (f != null && f.exists()) {
            FileUtils.forceDelete(f);
          }
        } catch (IOException e) {
          throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + f.getAbsolutePath(), e);
        }
      }
      try {
        if (pdfBlank != null && pdfBlank.exists()) {
          FileUtils.forceDelete(pdfBlank);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdfResult.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfBlank.getAbsolutePath(), e);
      }
    }

    return copia;
  }


  /**
   * Convierte a PDF.
   * 
   * @throws es.mpt.dsic.eeutil.misc.consumer.model.InSideException
   */
  private File convertir(String idApp, String passw, File contenido, String mimeType)
      throws EeutilException {

    String ipOO = aplicacionContext.getAplicacionInfo().getPropiedades().get("ip.openoffice");
    String portOO = aplicacionContext.getAplicacionInfo().getPropiedades().get("port.openoffice");


    return pdfConverter.convertir(ipOO, portOO, contenido, mimeType, idApp,
        ConfigureRestInfo.getBaseUrlRestIgae(), ConfigureRestInfo.getTokenNameRestIgae(),
        ConfigureRestInfo.getTokenValueRestIgae());
  }


  public ListaFirmaInfo obtenerFirmantes(String aplicacion, byte[] firma) throws InSideException {
    InformacionFirmaAfirma info;
    try {
      info = afirmaService.obtenerInformacionFirma(aplicacion, firma, true, false, false, null);
    } catch (EeutilException e) {
      // logger.error(e.getMessage(), e);

      throw new InSideException(e.getMessage(), e);
    }
    return listaFirmaInfoAfirmaToListaFirmaInfo(info.getFirmantes());
  }


  private ListaFirmaInfo listaFirmaInfoAfirmaToListaFirmaInfo(List<FirmaInfoAfirma> listaAfirma) {
    if (listaAfirma == null) {
      return null;
    }

    ListaFirmaInfo lista = new ListaFirmaInfo();
    lista.setInformacionFirmas(new ArrayList<FirmaInfo>());

    for (FirmaInfoAfirma firmaInfoAfirma : listaAfirma) {
      lista.getInformacionFirmas().add(firmaInfoAfirmaToFirmaInfo(firmaInfoAfirma));
    }
    return lista;

  }


  private FirmaInfo firmaInfoAfirmaToFirmaInfo(FirmaInfoAfirma firmaInfoAfirma) {
    if (firmaInfoAfirma == null) {
      return null;
    }

    FirmaInfo firmaInfo = new FirmaInfo();
    firmaInfo.setNombre(firmaInfoAfirma.getNombre());
    firmaInfo.setApellido1(firmaInfoAfirma.getApellido1());
    firmaInfo.setApellido2(firmaInfoAfirma.getApellido2());
    firmaInfo.setNifcif(firmaInfoAfirma.getNifcif());
    firmaInfo.setFecha(firmaInfoAfirma.getFecha());
    firmaInfo.setExtras(firmaInfoAfirma.getExtras());

    return firmaInfo;
  }


  private ImageProperties getLogoProperties() {
    String rutaLogo = aplicacionContext.getAplicacionInfo().getPropiedades().get("rutaLogo");
    float escalaLogoX = 17.5f;
    float escalaLogoY = 17.5f;
    if (StringUtils
        .isNotBlank(aplicacionContext.getAplicacionInfo().getPropiedades().get("escalaLogoX"))) {
      escalaLogoX =
          Float.valueOf(aplicacionContext.getAplicacionInfo().getPropiedades().get("escalaLogoX"));
    }
    if (StringUtils
        .isNotBlank(aplicacionContext.getAplicacionInfo().getPropiedades().get("escalaLogoY"))) {
      escalaLogoY =
          Float.valueOf(aplicacionContext.getAplicacionInfo().getPropiedades().get("escalaLogoY"));
    }
    return new ImageProperties(rutaLogo, escalaLogoX, escalaLogoY);
  }

  public String obtenerCSV(byte[] firma, byte[] documentoFirmado, String idAplicacion)
      throws EeutilException {
    try {
      processor.processSign(firma, documentoFirmado, idAplicacion);

      CVSSignature signature = new CVSSignature(processor);
      // String cvsText = signature.getCVSText();
      return signature.getCVSTextOM(firma, documentoFirmado, idAplicacion);
    } catch (AOUnsupportedSignFormatException e) {
      // en este punto retornamos un csv a partir del hash de documento, el documento no lleva
      // ninguna firma
      return DigestUtils.md5Hex(firma);
    } catch (EeutilException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }


  public String generarHash(DataHandler data, String algoritmo) throws EeutilException {
    String hashHEX = null;

    EstadoInfo error = new EstadoInfo();
    byte[] byteArrayFichero;
    InputStream in = null;

    try {
      in = data.getInputStream();
      byteArrayFichero = org.apache.commons.io.IOUtils.toByteArray(in);
    } catch (IOException e) {
      error.setDescripcion(e.getMessage());
      error.setCodigo(ERROR);
      throw new EeutilException("Error al generarHash" + error.getCodigo() + error.getDescripcion(),
          e);
    } finally {
      if (in != null)
        try {
          in.close();
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
        }
    }

    if (algoritmo == null || algoritmo.trim().equals("")) {

      error.setCodigo(ERROR);
      error.setEstado("ERROR en el algoritmo");
      error.setDescripcion("El algoritmo debe ser o SHA256 o SHA384 o SHA512.");
      throw new EeutilException(error.getCodigo() + error.getEstado() + error.getDescripcion());
    } else if (algoritmo.trim().equalsIgnoreCase(ALGORITMO_SHA256)
        || algoritmo.trim().contains(String.valueOf(LONGITUD_HASHHEX_SHA256 * 4))) {
      hashHEX =
          org.apache.commons.codec.digest.DigestUtils.sha256Hex(byteArrayFichero).toUpperCase();
    } else if (algoritmo.trim().equalsIgnoreCase(ALGORITMO_SHA384)
        || algoritmo.trim().contains(String.valueOf(LONGITUD_HASHHEX_SHA384 * 4))) {
      hashHEX =
          org.apache.commons.codec.digest.DigestUtils.sha384Hex(byteArrayFichero).toUpperCase();
    } else if (algoritmo.trim().equalsIgnoreCase(ALGORITMO_SHA512)
        || algoritmo.trim().contains(String.valueOf(LONGITUD_HASHHEX_SHA512 * 4))) {
      hashHEX =
          org.apache.commons.codec.digest.DigestUtils.sha512Hex(byteArrayFichero).toUpperCase();
    } else {
      error.setCodigo(ERROR);
      error.setEstado("ERROR en el algoritmo");
      error.setDescripcion("El algoritmo debe ser o SHA256 o SHA384 o SHA512.");
      throw new EeutilException(error.getCodigo() + error.getEstado() + error.getDescripcion());
    }

    return hashHEX.toUpperCase();

  }


  public boolean validarHash(DataHandler fichero, String hashHEX) throws EeutilException {
    String hashCalculado = null;

    if ((hashHEX == null || "".equals(hashHEX.trim()))
        || (hashHEX.trim().length() != LONGITUD_HASHHEX_SHA256
            && hashHEX.trim().length() != LONGITUD_HASHHEX_SHA384
            && hashHEX.trim().length() != LONGITUD_HASHHEX_SHA512)) {
      EstadoInfo error = new EstadoInfo();
      error.setCodigo(ERROR);
      error.setEstado("ERROR en el hash");
      error.setDescripcion(
          "El hash informado no est� generado con algoritmo ni SHA256 ni SHA384 ni SHA512.");
      throw new EeutilException(error.getCodigo() + error.getEstado() + error.getDescripcion());
    } else if (hashHEX.trim().length() == LONGITUD_HASHHEX_SHA256) {
      hashCalculado = this.generarHash(fichero, ALGORITMO_SHA256);
    } else if (hashHEX.trim().length() == LONGITUD_HASHHEX_SHA384) {
      hashCalculado = this.generarHash(fichero, ALGORITMO_SHA384);
    } else if (hashHEX.trim().length() == LONGITUD_HASHHEX_SHA512) {
      hashCalculado = this.generarHash(fichero, ALGORITMO_SHA512);
    } else {
      // si no hemos conseguido meter hashCalculado lanzamos una exception
      throw new EeutilException("El valor de hashCalculado no puede ser null");
    }

    return hashCalculado.equalsIgnoreCase(hashHEX);

  }


  public CopiaInfo generarCopiaFirmaComun(String idApp, String passw, CopiaInfo copia,
      ListaFirmaInfo firmas, boolean simple) throws EeutilException {

    File pdf = null;
    File fileFixedRotations = null;
    File entrada = null;
    File entradaSinSeguridad = null;
    Object[] aObj = null;
    File pdfReduced = null;
    StamperWrapper stamperWrapper;
    File pdfResult = null;
    File pdfBlank = null;
    String outputResultado = null;
    File conPaginasEnBlanco = null;
    List<Integer> listaOrientacionPaginas = null;
    boolean bTieneRotaciones = false;
    try {

      // Si llega el CSV, se obliga a que tenga �mbito
      if (!StringUtils.isEmpty(copia.getCsv())) {
        if (StringUtils.isEmpty(copia.getIdAplicacion())
            && !CSVUtil.hasScope(copia.getIdAplicacion())) {
          throw new EeutilException("No se ha especificado el ambito", null);
        }
      }

      ImageProperties logoProperties = getLogoProperties();

      String[] aResultado = workFlowMimeLibreoffice(copia, "copiaFirma");

      entrada = new File(aResultado[0]);

      // String mimeFinal = aResultado[1];

      // si el mime es de pdf le quitamos la seguridad al fichero.
      /*
       * if(mimeFinal.equals("application/pdf")) { //String rutaSinSeguridad =
       * FileUtil.createFilePath("sinSeguridad") + ".pdf";
       * 
       * //quitamos seguridad al pdf original // entradaSinSeguridad =
       * copiarPdfEliminandoSeguridad(entrada.toPath(),rutaSinSeguridad); entradaSinSeguridad =
       * entrada; } //si no es pdf no se hace el proceso de quitar la seguridad puesto que la
       * conversion no lleva seguridad ya. else { entradaSinSeguridad = entrada; }
       */
      entradaSinSeguridad = entrada;

      // Se convierte a PDF
      pdf = convertir(idApp, passw, entradaSinSeguridad, aResultado[1]);

      // Se convierte a un PDF con las paginas del anterior pdf
      // estampadas a tamano reducido

      // fileFixedRotations= newPdfConversion.fixedFilesWithRotations(pdf);

      fileFixedRotations = pdf;



      stamperWrapper = new StamperWrapper();
      String fileOut = stamperWrapper.createFilePrefix(NewPdfConversion.REDUCED_PREFIX, ".pdf");

      stamperWrapper.createStamperWrapper(fileFixedRotations, fileOut);

      aObj = newPdfConversion.copiaPDFReducidoStamper(stamperWrapper);

      stamperWrapper = (StamperWrapper) aObj[0];

      pdfReduced = new File(stamperWrapper.getNameFileFileOutPrefix());

      // pdfReduced=(File) aObj[0];
      listaOrientacionPaginas = (List<Integer>) aObj[1];

      // Se calcula el número de paginas en blanco a meter al final, si
      // hay muchos firmantes.

      String resultPath = FileUtil.createFilePath("copiaSimpleFirma") + ".pdf";
      String resultPathBlank = FileUtil.createFilePath("blankCabeceraPie") + ".pdf";

      stamperWrapper.closeAll();

      pdfBlank = crearPaginasVaciasTodasPaginas(listaOrientacionPaginas);

      stamperWrapper.createStamperWrapper(pdfBlank, resultPathBlank);

      PdfCopiaAutenticaUtils copiaAutenticaUtils = new PdfCopiaAutenticaUtils();

      int pagsBlancas = pdfCopiaAutenticaUtils.numeroPaginasEnBlanco(firmas, simple);



      if (pagsBlancas > 0) {
        // Se meten las paginas en blanco
        /* conPaginasEnBlanco = */
        pdfCopiaAutenticaUtils.insertarPaginasEnBlanco(
            stamperWrapper/* (PdfReader)aObj[5], (FileOutputStream) aObj[6],(PdfStamper)aObj[7] */,
            pagsBlancas);

        // Se estampa la informacion
        // pdfResult = pdfCopiaAutenticaUtils.copiaAutenticaSimpleFirma(conPaginasEnBlanco, copia,
        // simple, firmas,
        // pagsBlancas, logoProperties);

        pdfResult = pdfCopiaAutenticaUtils.copiaAutenticaSimpleFirmaOptimized(
            stamperWrapper/* (PdfReader)aObj[5], (FileOutputStream) aObj[6],(PdfStamper)aObj[7] */,
            copia, simple, firmas, pagsBlancas, logoProperties);


      } else {
        // Se estampa la informacion
        // pdfResult = pdfCopiaAutenticaUtils.copiaAutenticaSimpleFirma(pdfBlank, copia, simple,
        // firmas,
        // pagsBlancas, logoProperties);

        pdfResult = pdfCopiaAutenticaUtils.copiaAutenticaSimpleFirmaOptimized(stamperWrapper,
            /* (PdfReader)aObj[5], (FileOutputStream) aObj[6],(PdfStamper)aObj[7], */ copia, simple,
            firmas, pagsBlancas, logoProperties);


      }

      stamperWrapper.closeAll();



      HashMap<String, String> mDatosAccesibilidad = new HashMap<>();
      mDatosAccesibilidad.put(TITLE_PARAM, aObj[3].toString());
      mDatosAccesibilidad.put(LANG_PARAM, aObj[2].toString());

      bTieneRotaciones = (boolean) aObj[5];


      if (!bTieneRotaciones) {
        outputResultado = stampReducidoYInfoAccesibilidad(pdfResult, pdfReduced,
            mDatosAccesibilidad, (List) aObj[4], bTieneRotaciones);
      } else {
        outputResultado = stampReducidoYInfoAccesibilidad(pdfReduced, pdfResult,
            mDatosAccesibilidad, (List) aObj[4], bTieneRotaciones);
        // outputResultado =
        // stampReducidoYInfoAccesibilidad(pdfResult,pdfReduced,mDatosAccesibilidad,(List)aObj[4]);
      }



      copia.getContenido().setContenido(IOUtil.getBytesFromObject(new File(outputResultado)));
      copia.getContenido().setTipoMIME(APPLICATION_PDF_MIME);

    } catch (EeutilException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (Exception t) {
      throw new EeutilException(t.getMessage(), t);
    } finally {
      try {
        if (pdf != null && pdf.exists()) {
          FileUtils.forceDelete(pdf);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdf.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdf.getAbsolutePath(), e);
      }
      try {
        if (fileFixedRotations != null && fileFixedRotations.exists()) {
          FileUtils.forceDelete(fileFixedRotations);
        }
      } catch (IOException e) {
        // logger.error(ERROR_AL_ELIMINAR_FICHERO + pdf.getAbsolutePath(),e);
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + fileFixedRotations.getAbsolutePath(),
            e);
      }



      try {
        if (entrada != null && entrada.exists()) {
          FileUtils.forceDelete(entrada);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + entrada.getAbsolutePath(), e);
      }
      try {
        if (entradaSinSeguridad != null && entradaSinSeguridad.exists()) {
          FileUtils.forceDelete(entradaSinSeguridad);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + entradaSinSeguridad.getAbsolutePath(),
            e);
      }
      try {
        if (pdfReduced != null && pdfReduced.exists()) {
          FileUtils.forceDelete(pdfReduced);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfReduced.getAbsolutePath(), e);
      }
      try {
        if (pdfResult != null && pdfResult.exists()) {
          FileUtils.forceDelete(pdfResult);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfResult.getAbsolutePath(), e);
      }
      try {
        if (pdfBlank != null && pdfBlank.exists()) {
          FileUtils.forceDelete(pdfBlank);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + pdfBlank.getAbsolutePath(), e);
      }

      if (outputResultado != null) {
        File f = new File(outputResultado);
        try {
          if (f != null && f.exists()) {
            FileUtils.forceDelete(f);
          }
        } catch (IOException e) {
          throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + f.getAbsolutePath(), e);
        }
      }

      try {
        if (conPaginasEnBlanco != null && conPaginasEnBlanco.exists()) {
          FileUtils.forceDelete(conPaginasEnBlanco);
        }
      } catch (IOException e) {
        throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + conPaginasEnBlanco, e);
      }

    }

    return copia;
  }

  /**
   * @param listaOrientacionPaginas
   * @return
   * @throws IOException
   */
  private File crearPaginasVaciasTodasPaginas(List listaOrientacionPaginas) throws IOException {
    File pdfBlank;
    // Creating PDF document object
    try (PDDocument document = new PDDocument();) {

      for (int i = 0; i < listaOrientacionPaginas.size(); i++) {

        // Add an empty page to it
        document.addPage(new PDPage(PDRectangle.A4));

        if (listaOrientacionPaginas.get(i).equals(PageFormat.LANDSCAPE)) {
          document.getPage(i).setRotation(90);
        }

      }
      String inputPathBlank = FileUtil.createFilePath("blank");

      // creamos un fichero en blanco
      document.save(inputPathBlank);
      pdfBlank = new File(inputPathBlank);
    }
    return pdfBlank;
  }

  /**
   * @param pdfReduced
   * @param pdfResult
   * @return
   * @throws IOException
   * @throws DocumentException
   * @throws FileNotFoundException
   */
  private String stampReducidoYInfoAccesibilidad(File pdfReduced, File pdfResult,
      HashMap<String, String> mAccesibilidad, List aBookmarks, boolean bTieneRotaciones)
      throws EeutilException {
    String outputResultado = FileUtil.createFilePath("resultado");

    /*
     * try { reader = new PdfReader(pdfReduced.getAbsolutePath()); reader2 = new
     * PdfReader(pdfResult.getAbsolutePath());
     * 
     * // Create a stamper stamper = new PdfStamper(reader, new FileOutputStream(outputResultado));
     * 
     * 
     * 
     * for (int i = 1; i <= reader.getNumberOfPages(); i++) {
     * 
     * if(i==23) continue;
     * 
     * // Create an imported page to be inserted PdfImportedPage page =null;
     * 
     * 
     * page = stamper.getImportedPage(reader2, i);
     * 
     * 
     * a(reader2,i,page,stamper);
     * 
     * 
     * }
     * 
     * 
     * 
     * }catch(Exception e) { throw new EeutilException(e.getMessage(),e); } finally {
     * if(stamper!=null) try { stamper.close(); } catch (DocumentException | IOException e) { throw
     * new EeutilException(e.getMessage(), e); } if(reader!=null)reader.close(); if(reader2!=null)
     * reader2.close(); }
     * 
     * 
     */
    /* PDDocument pdDoc2= null; */
    try {
      fusionarDocumentoBlancoReducido(pdfResult.getAbsolutePath(), pdfReduced.getAbsolutePath(),
          outputResultado, mAccesibilidad, aBookmarks, bTieneRotaciones);
    } catch (FileNotFoundException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (IOException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (DocumentException e) {
      throw new EeutilException(e.getMessage(), e);
    }
    /*
     * finally { if(pdDoc2!=null) { try { pdDoc2.close(); } catch (IOException e) { throw new
     * EeutilException(e.getMessage(), e); } } }
     */

    return outputResultado;
  }


  /**
   * @param copia
   * @return
   * @throws InSideException
   * @throws AfirmaException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private String[] verificarFirmaYPDFEncriptado(String mime, String prefijoFile, CopiaInfo copia)
      throws EeutilException {

    String[] aResultado = new String[2];

    String inputPathFile = null;
    String mimeOriginalFinal = null;

    try {

      // esto tiene que ir siempre antes de AOSignerFactory.getSigner
      if (APPLICATION_PDF_MIME.equalsIgnoreCase(mime)
          && PdfEncr.isProtectedPdf(copia.getContenido().getContenido())) {
        throw new EeutilException(
            "Error al generarCopiaFirmaComun. El fichero pdf tiene contrase�a y no se puede procesar");
      }
      // comprobamos si el contenido es una firma o no. Esto es nuevo y habra que validarlo.
      // AOSigner aoSigner=new
      // AOSignerWrapperEeutils().wrapperGetSigner(copia.getContenido().getContenido(),copia.getContenido().getTipoMIME());


      ResultadoValidacionInfoAfirma validacionFirmaInfo = null;
      boolean esFirma = false;

      try {
        // Si no es ninguno de estos formatos no puede ser una firma.
        if (!IOUtil.esPosibleFormatoFirmaCadesXadesPadesExtendedAnalyzeBytes(
            copia.getContenido().getContenido())) {
          esFirma = false;
        }

        else {
          validacionFirmaInfo = afirmaService.validarFirma(copia.getIdAplicacion(),
              Base64.encodeBase64String(copia.getContenido().getContenido()), null, null, null,
              null);
          esFirma = validacionFirmaInfo.isEstado();
          // no es una firma y se procesa como no firma
          if (!esFirma && validacionFirmaInfo.getDetalle() != null
              && validacionFirmaInfo.getDetalle().contains("El formato de la firma no es")) {
            esFirma = false;
          }
          // en cualquier otro caso se cuenta como una firma.
          else {
            esFirma = true;
          }
        }
      }
      // si lanza una excepcion
      catch (EeutilException t) {
        // es un formato no valido de firma (no se reconoce como firma)
        // Error al realizar la peticion a DSSAfirmaVerify HTTP response '413: Request Entity Too
        // Large' when communicating with
        // https://des-afirma.redsara.es/afirmaws/services/DSSAfirmaVerify
        // hemos hecho una personalizacion de excepciones en EEutilException para las excepciones de
        // tipo WebServiceException
        if ((t.getCOD_AFIRMA() != null && t.getCOD_AFIRMA().equals(CodigosError.COD_0003))) {
          esFirma = false;
        } else if ((t.getMSG_AFIRMA() != null
            ? t.getMSG_AFIRMA().indexOf("Request Entity Too Large") != -1
            : false)) {
          // evaluamos si es una posible firma mirando los primeros bytes
          if (IOUtil
              .esPosibleFormatoFirmaCadesXadesAnalyzeBytes(copia.getContenido().getContenido())) {
            // ERROR es una posible firma
            throw t;
          } else {
            esFirma = false;
          }

        } else {
          esFirma = true;
        }
      }



      // validamos si es firma correcta
      if (esFirma) {
        // le pasamos el contenido de una firma
        InformacionFirmaAfirma infoAfirma = afirmaService.obtenerInformacionFirma(
            copia.getIdAplicacion(), copia.getContenido().getContenido(), false, true, true, null);

        // se comprueba si es tcn
        boolean esTcnFirmaXades = XMLUtil.comprobarFirmaXadesEsTcn(
            copia.getContenido().getContenido(), infoAfirma.getTipoDeFirma().getTipoFirma());

        if (esTcnFirmaXades) {
          // cambiamos el mime a tcn para preparar la llamada a convertir
          copia.getContenido().setTipoMIME(TEXT_TCN_MIME);
          mimeOriginalFinal = TEXT_TCN_MIME;
        } else {
          // do nothing
          if (!TEXT_TCN_MIME.equals(copia.getContenido().getTipoMIME())) {
            copia.getContenido().setTipoMIME(infoAfirma.getDocumentoFirmado().getTipoMIME());
            // si es una firma copiamos el mime de la firma y devolvemos en return
            mimeOriginalFinal = infoAfirma.getDocumentoFirmado().getTipoMIME();
          }
        }


        inputPathFile =
            FileUtil.createFilePath(prefijoFile, infoAfirma.getDocumentoFirmado().getContenido());
      }

      else {
        inputPathFile = FileUtil.createFilePath(prefijoFile, copia.getContenido().getContenido());
        // si no es firma se mantiene el mime
        mimeOriginalFinal = mime;
      }


    } catch (EeutilException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (ParserConfigurationException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (SAXException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (IOException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

    aResultado[0] = inputPathFile;
    aResultado[1] = mimeOriginalFinal;

    return aResultado;
  }


  public CopiaInfo generarInforme(String idApp, String passw, CopiaInfoExtended copia)
      throws EeutilException {
    try {

      // Si no llega �mbito, se comprueba si viene incluido en el CSV:
      // AMB-CSVCSVCSVCSVCSVCSVCSVCSVCSVCSVCS
      if (StringUtils.isBlank(copia.getIdAplicacion())) {
        StringTokenizer stk = new StringTokenizer(copia.getIdAplicacion(), "-");
        if (stk.countTokens() != 2) {
          throw new InSideException("No se ha recibido el �mbito del CSV.", new EstadoInfo());
        }
      }

      if (StringUtils.isEmpty(copia.getCsv())) {
        StringTokenizer stk = new StringTokenizer(copia.getIdAplicacion(), "-");
        StringBuilder csv = new StringBuilder(stk.nextToken());
        csv.append(CSVUtil.CSV_SEPARATOR);
        csv.append(obtenerCSV(copia.getFirma(), copia.getContenido().getContenido(),
            copia.getIdAplicacion()));
        copia.setCsv(csv.toString());
      }

      ListaFirmaInfo firmantes = obtenerFirmantes(idApp, copia.getFirma());
      ponerNifsVacios(firmantes);
      return this.generarCopiaFirmaComun(idApp, passw, copia, firmantes, copia.isSimple());
    } catch (EeutilException e) {
      // logger.error("Error en generarInforme: "+e.getMessage(), e);
      throw e;
    } catch (Exception t) {

      // logger.error("Error en generarInforme: "+t.getMessage(), t);
      throw new EeutilException("No se puede obtener el informe de la firma " + t.getMessage(), t);
    }
  }


  public CopiaInfoFirmaSalida generarJustificanteFirma(String idApp, String passw,
      CopiaInfoFirma copiaInfoFirma, boolean simple) throws EeutilException {

    byte[] bytesDocumento = null;
    String mimeDocumento = null;
    CopiaInfoFirmaSalida salida = new CopiaInfoFirmaSalida();

    try {
      // Obtencion del documento firmado y del mime de este.
      if (copiaInfoFirma.isDocumentoEnFirma()) {
        logger.debug("El documento SI esta en la firma");
        SignedData signedData = SignedDataExtractor.getDataFromSign(copiaInfoFirma.getFirma());
        bytesDocumento = signedData.getContenido();
        mimeDocumento = signedData.getTipoMime();
      } else {
        logger.debug("El documento NO esta en la firma");

        bytesDocumento = copiaInfoFirma.getDocumento();
        mimeDocumento = MimeUtil.getMimeNotNull(bytesDocumento);

      }

      String csv = "";
      String tituloCSV = "EMPTY";
      if (copiaInfoFirma.isIncluirCSV()) {
        csv = obtenerCSV(copiaInfoFirma.getFirma(), copiaInfoFirma.getDocumento(), idApp);
        tituloCSV = copiaInfoFirma.getTituloCSV();
        if (csv == null) {
          EstadoInfo estadoInfo = new EstadoInfo();
          throw new InSideException(
              "No se puede generar el justificante de la firma porque no se ha podido obtener el CSV",
              estadoInfo);
        }
      }

      if (copiaInfoFirma.isIncluirCSVEnRespuesta() != null
          && copiaInfoFirma.isIncluirCSVEnRespuesta()) {
        salida.setCsv(csv);
      }

      ListaFirmaInfo firmantes = null;
      if (copiaInfoFirma.isIncluirFirmantes()) {
        firmantes = obtenerFirmantes(idApp, copiaInfoFirma.getFirma());
        if (!copiaInfoFirma.isIncluirNifFirmantes()) {
          ponerNifsVacios(firmantes);
        }
      }

      CopiaInfo copiaInfo = new CopiaInfo();
      copiaInfo.setIdAplicacion(copiaInfoFirma.getIdAplicacion());
      copiaInfo.setLateral(copiaInfoFirma.getLateral());
      copiaInfo.setEstamparLogo(copiaInfoFirma.isEstamparLogo());
      copiaInfo.setCsv(csv);
      copiaInfo.setExpediente(copiaInfoFirma.getExpediente());
      copiaInfo.setFecha(copiaInfoFirma.getFecha());
      copiaInfo.setNif(copiaInfoFirma.getNif());
      if (copiaInfoFirma.getUrlSede() != null) {
        copiaInfo.setUrlSede(copiaInfoFirma.getUrlSede());
      } else {
        copiaInfo.setUrlSede("https://sede.administracionespublicas.gob.es/valida");
      }
      ContenidoInfo cInfo = new ContenidoInfo();
      cInfo.setContenido(bytesDocumento);
      cInfo.setTipoMIME(mimeDocumento);
      copiaInfo.setContenido(cInfo);

      copiaInfo.setTituloAplicacion(copiaInfoFirma.getTituloAplicacion());
      copiaInfo.setTituloCSV(tituloCSV);
      copiaInfo.setTituloFecha(copiaInfoFirma.getTituloFecha());
      copiaInfo.setTituloExpediente(copiaInfoFirma.getTituloExpediente());
      copiaInfo.setTituloNif(copiaInfoFirma.getTituloNif());
      copiaInfo.setTituloURL(copiaInfoFirma.getTituloURL());

      copiaInfo.setOpcionesPagina(copiaInfoFirma.getOpcionesPagina());

      copiaInfo = this.generarCopiaFirmaComun(idApp, passw, copiaInfo, firmantes, simple);

      salida.setContenido(copiaInfo.getContenido().getContenido());
      salida.setTipoMime(copiaInfo.getContenido().getTipoMIME());

    } catch (InSideException e) {
      // logger.error("Error en generarJustificanteFirma: "+e.getMessage(), e);
      throw new EeutilException(e.getMessage(), e);
    } catch (Exception t) {
      // logger.error("Error en generarJustificanteFirma: "+t.getMessage(), t);
      throw new EeutilException(
          "No se puede obtener el justificante de la firma " + idApp + t.getMessage(), t);
    }

    return salida;

  }


  public String generarCSV(String idApp, CSVInfo copia) throws EeutilException {
    logger.debug("Generar CSV: " + idApp);
    byte[] contenido = copia.getContenido();
    byte[] documentoFirmado = copia.getContenidoFirmado();
    String cvsText;

    try {
      cvsText = obtenerCSV(contenido, documentoFirmado, idApp);

      if (cvsText == null) {
        // logger.error("EL CSV OBTENIDO ES NULO");
        throw new EeutilException("EL CSV OBTENIDO ES NULO");
      }
    } catch (Exception t) {
      // logger.error("Error en generarCSV: "+t.getMessage(), t);
      throw new EeutilException("No se puede obtener el csv de la firma " + t.getMessage(), t);
    }
    return cvsText;
  }


  public String generarCSVAmbito(String idApp, CSVInfoAmbito copia) throws EeutilException {
    final byte[] contenido = copia.getContenido();
    final byte[] documentoFirmado = copia.getContenidoFirmado();
    final String cvsAmbito = copia.getAmbito();
    final String cvsText;

    try {
      cvsText = obtenerCSV(contenido, documentoFirmado, idApp);

      if (cvsText == null) {
        // logger.error("EL CSV OBTENIDO ES NULO");
        throw new EeutilException("EL CSV OBTENIDO ES NULO. ");
      }
    } catch (Exception t) {
      // logger.error("Error en generarCSVAmbito: "+t.getMessage(), t);
      throw new EeutilException("No se puede obtener el csv de la firma: " + t.getMessage(), t);
    }
    return StringUtils.isNotBlank(cvsAmbito) ? cvsAmbito + CSVUtil.CSV_SEPARATOR + cvsText
        : cvsText;
  }


  private void ponerNifsVacios(ListaFirmaInfo firmantes) {
    List<FirmaInfo> lista = firmantes.getInformacionFirmas();

    for (FirmaInfo firmante : lista) {
      firmante.setNifcif("");
    }
  }


  // public static void main(String args[]) throws IOException, DocumentException
  // {
  // new
  // EeUtilFirmaServiceBusiness().fusionarDocumentoBlancoReducido("c:/FORMULARIO/copiaSimpleFirma160399365910416.pdf","c:/FORMULARIO/newreduced160399365889716.pdf","c:/FORMULARIO/resultado.pdf",null,
  // null);
  // //generarENMAIN("c:/GEISER/");
  // //generarENMAIN("c:/DOC_SOLICITUD/");
  // //generarENMAIN("c:/ALBARAN/");
  // //generarENMAIN("c:/35_PAG/");
  // //generarENMAIN("c:/SENADO/");
  // //generarENMAIN("c:/AYER_NORMAL/");
  // //generarENMAIN("c:/AYER_ROTADO/");
  //
  //
  //
  // }

  /**
   * @throws IOException
   * @throws DocumentException
   * @throws FileNotFoundException
   */
  private void fusionarDocumentoBlancoReducido(String rutaOrigen, String rutaDestino,
      String rutaResultado, HashMap<String, String> mAccesibilidad, List aBookmarks,
      boolean bTieneRotaciones) throws IOException, DocumentException, FileNotFoundException {

    // PdfReader reader=null;
    // PdfReader reader2=null;
    FileOutputStream fos = null;
    PdfStamper stamper = null;

    try (PdfReader reader = new PdfReader(rutaOrigen);
        PdfReader reader2 = new PdfReader(rutaDestino);) {

      // reader = new PdfReader(rutaOrigen);
      // reader2 = new PdfReader(rutaDestino);



      boolean ponerXMas = false;
      int numeroPagAdicionales = reader2.getNumberOfPages() - reader.getNumberOfPages();
      if (numeroPagAdicionales != 0) {

        numeroPagAdicionales = Math.abs(numeroPagAdicionales);
        ponerXMas = true;
      }

      int numReferenciaPaginas =
          reader2.getNumberOfPages() >= reader.getNumberOfPages() ? reader.getNumberOfPages()
              : reader2.getNumberOfPages();

      // PdfReader reader2 = new PdfReader(formulario+"newreduced.pdf");
      // PdfReader reader = new PdfReader(formulario+"copiaSimpleFirma.pdf");
      // Create a stamper
      fos = new FileOutputStream(rutaResultado);
      stamper = new PdfStamper(reader, fos, PdfWriter.VERSION_1_7);
      stamper.setViewerPreferences(PdfWriter.DisplayDocTitle);


      // A�adimos al stamp los metadatos de title y lang
      // si el titulo del pdf origen existe se anade al diccionario
      if (mAccesibilidad.get(TITLE_PARAM) != null) {


        addTitleXMPMetadata(mAccesibilidad, stamper);


      }

      if (mAccesibilidad.get(LANG_PARAM) != null && stamper.getReader().getCatalog() != null) {
        stamper.getReader().getCatalog().put(PdfName.LANG,
            new PdfString(mAccesibilidad.get(LANG_PARAM)));
      }

      if (aBookmarks != null && !aBookmarks.isEmpty()) {
        stamper.getWriter().setOutlines(aBookmarks);
      }

      // Create an imported page to be inserted
      PdfImportedPage page = null;

      for (int i = 1; i <= numReferenciaPaginas; i++) {



        // pasamos de ella por que es la pagina de firma
        // if(reader.getNumberOfPages()==i && ponerUnaMas)
        // {
        // continue;
        // }

        page = stamper.getImportedPage(reader2, i);
        /*
         * int orientacion=new EeUtilFirmaServiceBusiness().obtenerOrientacionPagina(reader2,i);
         * System.out.println("pagina"+i+" "+ orientacion);
         * //stamper.getUnderContent(i).addTemplate(page, 0, 0);
         * 
         * if(orientacion==PageFormat.PORTRAIT) { stamper.getUnderContent(i).addTemplate(page, 0,
         * 0); } else { Rectangle pageSize = reader2.getPageSize(i);
         * stamper.getUnderContent(i).addTemplate(page, 0, 1, -1, 0,
         * pageSize.getHeight(),pageSize.getWidth()); }
         * 
         * 
         * 
         * 
         * }catch(Exception e) {
         * 
         * }
         */


        fusionarPaginasBlancasReducidas(reader2, i, page, stamper);



      }

      // pasamos de ella por que es la pagina de firma
      if (ponerXMas && !bTieneRotaciones) {

        int numPaginaReducida = numReferenciaPaginas;


        for (int ipag = 0; ipag < numeroPagAdicionales; ipag++) {
          page = stamper.getImportedPage(reader2, numPaginaReducida + ipag + 1);
          stamper.insertPage(numPaginaReducida + ipag + 1,
              reader2.getPageSize(numPaginaReducida + ipag + 1));
          fusionarPaginasBlancasReducidas(reader2, numPaginaReducida + ipag + 1, page, stamper);
        }
        // stamper.getUnderContent(reader2.getNumberOfPages()).addTemplate(page, 0, 0);

      }



    } finally {
      if (stamper != null)
        stamper.close();
      if (fos != null)
        fos.close();
      /*
       * if(reader!=null) reader.close(); if(reader2!=null) reader2.close();
       */ }


  }

  private void addTitleXMPMetadata(HashMap<String, String> mAccesibilidad, PdfStamper stamper)
      throws IOException {
    XmpWriter xmp = null;

    try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
      // os = new ByteArrayOutputStream();

      xmp = new XmpWriter(os);
      XmpSchema dc = new DublinCoreSchema();
      XmpArray array = new XmpArray(XmpArray.ALTERNATIVE);
      array.add(mAccesibilidad.get(TITLE_PARAM));
      // SetProperty(TITLE, array);
      dc.setProperty(DublinCoreSchema.TITLE, mAccesibilidad.get(TITLE_PARAM));
      // XmpArray subject = new XmpArray(XmpArray.UNORDERED);
      // subject.add("Hello World");
      // subject.add("XMP & Metadata");
      // subject.add("Metadata");
      // dc.setProperty(DublinCoreSchema.SUBJECT, subject);
      xmp.addRdfDescription(dc);
      PdfSchema pdf = new PdfSchema();
      // pdf.setProperty(PdfSchema.KEYWORDS, "Hello World, XMP, Metadata");
      pdf.setProperty(PdfSchema.VERSION, "1.7");
      xmp.addRdfDescription(pdf);
      if (xmp != null)
        xmp.close();
      stamper.setXmpMetadata(os.toByteArray());
    }
  }


  public void fusionarPaginasBlancasReducidas(PdfReader pdfReader, int i, PdfImportedPage page,
      PdfStamper stamper) {

    Rectangle pagesize = pdfReader.getPageSizeWithRotation(i);
    float oWidth = pagesize.getWidth();
    float oHeight = pagesize.getHeight();
    int rotation = pagesize.getRotation();
    // float scale = getScale(oWidth, oHeight);
    // la escala siempre es 1.0f ya que siempre estamos usando paginas de tama�o 210x297mm o
    // 297x210mm (595x842 o 842x595)
    float scale = 1.0f;
    float scaledWidth = oWidth * scale;
    float scaledHeight = oHeight * scale;


    AffineTransform transform = new AffineTransform(scale, 0, 0, scale, 0, 0);
    switch (rotation) {
      case 0:
        stamper.getUnderContent(i).addTemplate(page, scale, 0, 0, scale, 0, 0);
        break;
      case 90:
        AffineTransform rotate90 = new AffineTransform(0, -1f, 1f, 0, 0, scaledHeight);
        rotate90.concatenate(transform);
        stamper.getUnderContent(i).addTemplate(page, 0, -1f, 1f, 0, 0, scaledHeight);
        break;
      case 180:
        AffineTransform rotate180 = new AffineTransform(-1f, 0, 0, -1f, scaledWidth, scaledHeight);
        rotate180.concatenate(transform);
        stamper.getUnderContent(i).addTemplate(page, -1f, 0, 0, -1f, scaledWidth, scaledHeight);
        break;
      case 270:
        AffineTransform rotate270 = new AffineTransform(0, 1f, -1f, 0, scaledWidth, 0);
        rotate270.concatenate(transform);
        stamper.getUnderContent(i).addTemplate(page, 0, 1f, -1f, 0, scaledWidth, 0);
        break;
      default:
        stamper.getUnderContent(i).addTemplate(page, scale, 0, 0, scale, 0, 0);
    }
  }


  /*
   * private static float getScale(float width, float height) { float scaleX =
   * PageSize.A4.getWidth() / width; float scaleY = PageSize.A4.getHeight() / height; return
   * Math.min(scaleX, scaleY); }
   */


  /**
   * Workflow para sacar el tipo de mime y guardarlo en CopiaInfo y la ruta del fichero temporal
   * como return
   * 
   * @param copia
   * @param mime
   * @param prefijoFile
   * @return inputPathFile
   * @throws EeutilException
   */
  private String[] workFlowMimeLibreoffice(CopiaInfo copia, String prefijoFile)
      throws EeutilException {
    String[] aResultado = new String[2];

    String inputPathFile = null;
    String mimeVerificarFirma = null;

    String mimeResultado = null;


    String mimeTika = null;


    try {
      byte[] bContenidoDocumento = copia.getContenido().getContenido();
      // obtenemos el mime para tika
      mimeTika = MimeUtil.getMimeType(bContenidoDocumento);

      if (validarSiMimeTablaProhibidos(mimeTika)) {
        throw new EeutilException(
            "El fichero incluido en la peticion no es un formato valido para la operacion que intenta realizar, mas informacion en https://administracionelectronica.gob.es/ctt/inside ."
                + ERROR_AL_GENERAR_CONST + prefijoFile + "., el mime obtenido no es procesable: "
                + mimeTika);
      }
      // vemos si el posible mime es una posible firma
      if (MimeUtil.esMimeTikaPosibleFirma(mimeTika)) {
        aResultado = verificarFirmaYPDFEncriptado(mimeTika, prefijoFile, copia);
        inputPathFile = aResultado[0];
        mimeVerificarFirma = aResultado[1];
      }
      // si no es una posible firma si es pdf verificamos que no esta protegido y si esta generamos
      // el inputPathFile
      else {
        mimeVerificarFirma = mimeTika;
        if (APPLICATION_PDF_MIME.equals(mimeVerificarFirma)) {
          if (PdfEncr.isProtectedPdf(bContenidoDocumento)) {
            throw new EeutilException(ERROR_AL_GENERAR_CONST + prefijoFile
                + ". El fichero pdf tiene contrase�a y no se puede procesar");
          }
        }

        inputPathFile = FileUtil.createFilePath(prefijoFile, bContenidoDocumento);
      }

      mimeResultado =
          obtenerMimeSobreMimeParamMimeTika(mimeVerificarFirma, copia.getContenido().getTipoMIME());
      boolean esMimePermitido = validarSiMimeTablaPermitidos(mimeResultado);

      // verificamos si el mime esta en nuestra tabla sino lo desechamos y lanzamos excepcion.
      if (!esMimePermitido) {
        throw new EeutilException(
            "El fichero incluido en la peticion no es un formato valido para la operacion que intenta realizar, mas informacion en https://administracionelectronica.gob.es/ctt/inside ."
                + ERROR_AL_GENERAR_CONST + prefijoFile
                + ".Mime no permitido para la visualizacion de documentos: MIME: " + mimeResultado);
      }
      // ponemos como mime definitivo en el parametro el del resultado
      copia.getContenido().setTipoMIME(mimeResultado.toLowerCase());
    } catch (Exception e) {

      // solo si hay excepcion se borra
      if (inputPathFile != null && new File(inputPathFile).exists()) {
        try {
          FileUtils.forceDelete(new File(inputPathFile));
        } catch (IOException t) {
          // logger.error(ERROR_AL_ELIMINAR_FICHERO + entrada.getAbsolutePath(),e);
          throw new EeutilException(ERROR_AL_ELIMINAR_FICHERO + inputPathFile, t);
        }
      }



      throw new EeutilException(e.getMessage(), e);



    }


    aResultado[0] = inputPathFile;
    aResultado[1] = mimeResultado;

    return aResultado;
  }



  private String obtenerMimeSobreMimeParamMimeTika(String mimeTika, String mimeParam)
      throws EeutilException {

    String mimeResultado = null;

    // si el mime es tcn prevalece sobre todo
    if (TEXT_TCN_MIME.equals(mimeParam)) {
      mimeResultado = mimeParam;
    } else {
      if (mimeTika == null || "".equals(mimeTika)) {
        // Error
        if (mimeParam == null || "".equals(mimeParam)) {
          throw new EeutilException("Error, no es posible obtener mime del contenido");
        }
        // sacamos el mime param
        else {
          mimeResultado = mimeParam;
        }

      }
      // si tika es distinto de null
      else {
        if (mimeTika.equals(mimeParam)) {
          mimeResultado = mimeTika;
        } else {
          // si el mimeparam es null
          if (mimeParam == null || "".equals(mimeParam)) {
            mimeResultado = mimeTika;
          }
          // si el mimeparam no es null prevalece el parametro
          else {
            mimeResultado = mimeTika.toLowerCase();
            // si el mime de tika no es valido nos quedamos con el del param
            if (!validarSiMimeTablaPermitidos(mimeResultado)) {
              mimeResultado = mimeParam;
              if (!validarSiMimeTablaPermitidos(mimeResultado)) {
                throw new EeutilException("Error, el mime no es imprimible por libreoffice: "
                    + mimeResultado + " tika: " + mimeTika + " param " + mimeParam);
              }


            }

          }
        }

      }
    }

    if (!validarSiMimeTablaPermitidos(mimeResultado)) {
      throw new EeutilException("Error, el mime no es imprimible por libreoffice: " + mimeResultado
          + " tika: " + mimeTika + " param " + mimeParam);
    }

    return mimeResultado;

  }

  public boolean validarSiMimeTablaPermitidos(String mimeResultado) {
    if (ConfigureLibreofficeMime.getObjLibreofficeFormats(mimeResultado)) {
      return true;
    } else {
      return false;
    }
  }


  public boolean validarSiMimeTablaProhibidos(String mimeResultado) {
    if (ConfigureLibreofficeMime.getObjLibreofficeProhibitedFormats(mimeResultado)) {
      return true;
    } else {
      return false;
    }
  }


  public static void main(String args[]) throws EeutilException, IOException {


    File file = new File("d:/Descargas/generarCopiaSinPermisos.pdf");
    PDDocument document = PDDocument.load(file);


    AccessPermission accessPermission = new AccessPermission();
    accessPermission.setCanPrint(true);
    accessPermission.setCanModify(true);
    accessPermission.setCanFillInForm(true);
    accessPermission.setCanExtractContent(true);
    accessPermission.setCanExtractForAccessibility(true);
    accessPermission.setCanAssembleDocument(true);

    StandardProtectionPolicy standardProtectionPolicy =
        new StandardProtectionPolicy(null, null, accessPermission);
    document.protect(standardProtectionPolicy);
    document.save("d:/Descargas/pdfBoxEncryption.pdf");
    document.close();

    // StamperWrapper stamperWrapper = null;
    // try {

    // String ruta= "D:/descargas/1.pdf";
    // File file = new File(ruta);
    //
    // File fileIntermedio =new NewPdfConversion().fixedFilesWithRotations(file);
    //
    // stamperWrapper = new StamperWrapper();
    // String fileOut = "D:/descargas/temporal2.pdf";
    //
    // stamperWrapper.createStamperWrapper(fileIntermedio, fileOut);
    //
    // new NewPdfConversion().copiaPDFReducidoStamper(stamperWrapper);
    //
    // stamperWrapper.closeAll();
    //
    // String fileDefinitivo= "D:/descargas/definitivo3.pdf";
    //
    // stamperWrapper.createStamperWrapper(new File(fileOut), fileDefinitivo);
    //
    // PdfCopiaAutenticaUtils copiaAutenticaUtils=new PdfCopiaAutenticaUtils();
    //
    // int numPaginas= stamperWrapper.getReader().getNumberOfPages();
    //
    // copiaAutenticaUtils.insertarPaginasEnBlanco(stamperWrapper, 1);
    //
    // int i=1;
    //
    // while(i<= numPaginas)
    // {
    //
    // PdfContentByte over = stamperWrapper.getStamper().getOverContent(i++);
    //
    // new EeUtilFirmaServiceBusiness().pintarQR("http://google.com", over);
    //
    // }



    PdfReader reader = new PdfReader("D:/descargas/problema.pdf");

    Document pdfDocument = new Document();
    PdfWriter writer = null;
    FileOutputStream fos = null;
    // try {


    fos = new FileOutputStream("D:/descargas/solucion.pdf");
    writer = PdfWriter.getInstance(pdfDocument, fos);
    writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
    writer.setEncryption(null, null, PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_ASSEMBLY
        | PdfWriter.ALLOW_COPY /*
                                * | PdfWriter.ALLOW_DEGRADED_PRINTING | PdfWriter.ALLOW_FILL_IN |
                                * PdfWriter.ALLOW_MODIFY_ANNOTATIONS |
                                * PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_SCREENREADERS
                                */, 0);
    writer.open();
    pdfDocument.open();
    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
      PdfImportedPage importPage = writer.getImportedPage(reader, i);

      Rectangle rectangle = reader.getPageSizeWithRotation(i);

      if (rectangle.getHeight() >= rectangle.getWidth()) {
        pdfDocument.setPageSize(PageSize.A4);
      } else {
        pdfDocument.setPageSize(PageSize.A4.rotate());
      }



      Image img = Image.getInstance(importPage);


      if (reader.getPageSizeWithRotation(i).getRotation() != 0) {
        img.setRotationDegrees(360f - reader.getPageSizeWithRotation(i).getRotation());
      }

      if (rectangle.getHeight() >= rectangle.getWidth())
        img.scaleToFit(525, 770);
      else
        img.scaleToFit(770, 525);



      pdfDocument.add(img);


    }

    pdfDocument.close();
    writer.close();



    // }
    // finally
    // {
    //
    // stamperWrapper.closeAll();
    //
    // }

  }


  private void pintarQR(String url, PdfContentByte pdfContentByte) throws EeutilException {

    PdfDocument pdfDocument = null;
    try {
      byte[] qr = createQR(url, 8, 8);
      float percent = 65;

      // PdfDocument pdfDocument = stamp.getOverContent(numPag).getPdfDocument();
      pdfDocument = pdfContentByte.getPdfDocument();

      // Si lo queremos mas a la derecha disminuimos el segundo n�mero.
      float pos_x = 5.0f;
      // Si lo queremos mas abajo aumentamos el segundo n�mero.
      float pos_y = 18.0f;


      Image imagenQr = getImagenQr(qr, percent, pos_x, pos_y);

      // stamp.getOverContent(numPag).addImage(imagenLogo);
      pdfContentByte.addImage(imagenQr);

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

  }


  private Image getImagenQr(byte[] qr, float percent, float pos_x, float pos_y)
      throws IOException, BadElementException {
    Image img = Image.getInstance(qr);
    img.scalePercent(percent);
    img.setAbsolutePosition(pos_x, pos_y);
    return img;
  }

  private byte[] createQR(String url, int width, int height) throws EeutilException {
    // String sUrl = "https://portafirma.seap.minhap.es";

    byte[] salida = null;
    try (ByteArrayOutputStream out =
        QRCode.from(url).to(ImageType.JPG).withSize(width, height).stream();) {
      // salida= Arrays.copyOf(out.toByteArray(),out.toByteArray().length);
      salida = out.toByteArray();
    } catch (IOException e) {
      // logger.error("Error al crear el QR. URL:" + url);
      throw new EeutilException("Error al crear el QR. URL:" + url + " " + e.getMessage(), e);
    }
    return salida;
  }



}
