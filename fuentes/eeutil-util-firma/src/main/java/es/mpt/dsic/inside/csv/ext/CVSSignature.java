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

package es.mpt.dsic.inside.csv.ext;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.gob.afirma.core.AOInvalidFormatException;
import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.core.util.tree.AOTreeModel;
import es.gob.afirma.core.util.tree.AOTreeNode;
import es.mpt.dsic.inside.csv.CSVUtil;
import es.mpt.dsic.inside.utils.exception.EeutilException;

// import es.gob.afirma.exceptions.AOException;
// import es.gob.afirma.misc.AOUtil;
// import es.gob.afirma.viewer.processor.SignatureProcessor;

/**
 * Clase que obtiene el CVS de una firma. Por defecto, el CVS consiste en el hash MD5 del resultado
 * binario de la operaci&oacute;n XOR realizada entre todos los PKCS#1 del documento de firma
 * proporcionado. El CVS puede configurarse este valor mediante un patr&oacute;n.
 */
public class CVSSignature {

  private static final String NO_SE_PUEDE_GENERAR_LA_HUELLA_PARA_EL_DOCUMENTO_FIRMADO =
      "No se puede generar la huella para el documento firmado: ";

  protected final static Log logger = LogFactory.getLog(CVSSignature.class);

  /** Cadena que delimita un patr&oacute;n. */
  private final static String PATTERN_DELIMITATOR = "$";

  /**
   * Cadena que separa el identificador de un patr&oacute;n de su configuraci&oacute;n en donde en
   * el utilimo caracter es la configuraci&oacute;n.
   */
  private final static String PARAM_DELIMITATOR = "-";

  /** Objeto procesador de la firma de la cual obtener el CVS. */
  private SignatureProcessor signProcessor = null;

  /**
   * Elementos del patr&oacute;n del CVS. Esta compruesto por cadenas de texto y elementos y objetos
   * predefinidos.
   */
  private PatternElement[] patternElements = null;

  private Vector<Boolean> processedElementsBinary = null;
  private Vector<byte[]> processedElements = null;

  /**
   * Contruye un objeto para el an&aacute;lisis de la firma indicada.
   * 
   * @param signData Documento de firma de la que obtener el CVS.
   */
  public CVSSignature(SignatureProcessor processor) {
    this.signProcessor = processor;
  }

  /**
   * Establece el patr&oacute;n que debe seguir el CVS de la firma. Si se indica {@code null} se
   * utilizar&aacute; el patr&oacute;n por defecto.
   * 
   * @param pattern Patr&oacute;n de la firma.
   */
  public void setPattern(String pattern) {
    this.patternElements = parsePattern(pattern);
    this.processedElements = null;
    this.processedElementsBinary = null;
  }

  /**
   * Obtiene el CVS de la firma. Si no se indic&oacute; un patr&oacute;n se usar&aacute;n el por
   * defecto. Si se produce alg&uacute;n error durante el c&aacute;lculo del CVS se devolver&aacute;
   * {@code null}.
   * 
   * @return CVS de la firma.
   * @see #setPattern(String)
   */
  /*
   * public byte[] getCVS() {
   * 
   * byte [] salida= null; try(ByteArrayOutputStream buffer = new ByteArrayOutputStream();) { if
   * (this.processedElements == null) { this.processAllElements(); }
   * 
   * for (byte[] element : this.processedElements) { buffer.write(element); }
   * 
   * 
   * //salida = Arrays.copyOf(buffer.toByteArray(),buffer.toByteArray().length); salida =
   * buffer.toByteArray();
   * 
   * } catch (Exception e) { Logger.getLogger("es.gob.afirma").
   * warning("Error al calcular el CVS de la firma: " + e); return null; }
   * 
   * return salida; }
   */

  /**
   * Obtiene el CVS de la firma. Si no se indic&oacute; un patr&oacute;n se usar&aacute;n el por
   * defecto. Si se produce alg&uacute;n error durante el c&aacute;lculo del CVS se devolver&aacute;
   * {@code null}.
   * 
   * @return CVS de la firma.
   * @see #setPattern(String)
   */
  /*
   * public String getCVSText() {
   * 
   * StringBuilder buffer = new StringBuilder(); try { if (this.processedElements == null) {
   * this.processAllElements(); }
   * 
   * for (int i = 0; i < this.processedElementsBinary.size(); i++) { if
   * (this.processedElementsBinary.get(i) == Boolean.TRUE) {
   * buffer.append(transcodeBinaryToText(this.processedElements.get(i))); } else { buffer.append(new
   * String(this.processedElements.get(i), XMLUtil.UTF8_CHARSET)); } } } catch (Exception e) {
   * Logger.getLogger("es.gob.afirma"). warning("Error al calcular el CVS de la firma: " + e);
   * return null; } return buffer.toString(); }
   */
  /**
   * Obtiene el CVS de la firma. Si no se indic&oacute; un patr&oacute;n se usar&aacute;n el por
   * defecto. Si se produce alg&uacute;n error durante el c&aacute;lculo del CVS se devolver&aacute;
   * {@code null}.
   * 
   * @return CVS de la firma.
   * @throws IOException
   * @throws AOInvalidFormatException
   * @see #setPattern(String)
   */
  public String getCVSTextOM(byte[] signature, byte[] signedDocument, String idAplicacion)
      throws EeutilException {

    try {
      logger.debug("getCVSTextOM INIT");
      // Árbol de firmas
      AOTreeModel arbolFirmas = null;

      logger.debug("getCVSTextOM, generando arbol de firmas");

      arbolFirmas = this.signProcessor.generateCertificatesTree(signature);

      // Array con el pkcs1 de las firmas.
      Vector<byte[]> pkcsList = new Vector<byte[]>();
      logger.debug("getCVSTextOM, obteniendo PKCS1 del arbol");
      this.signProcessor.getPKCS1Values(pkcsList, (AOTreeNode) arbolFirmas.getRoot());

      // Generacion de un número aleatorio de 256 bits
      logger.debug("getCVSTextOM, generando Random de 32 bits");
      byte[] randomBytes = CSVUtil.generateRandomNumber(32);

      // Obtencion del sha-256 del documento firmado.
      // Documento firmado.
      /*
       * byte[] signedData = null; try { signedData = getSignedData (); } catch (Exception e) {
       * throw new CSVCantBeGeneratedException ("No se puede extraer el contenido firmado" , e); }
       */
      // sha256 del documento firmado
      byte[] sha256DocumentoFirmado = null;
      try {
        logger.debug("getCVSTextOM, obteniendo sha-256 del documento firmado");
        // calculamos el documento procesado con la funcion de signproccessor
        // obtenerDocumentoFirmado
        byte[] signedDocumentProcesado =
            signProcessor.obtenerDocumentoFirmado(signature, signedDocument, idAplicacion);
        sha256DocumentoFirmado = CSVUtil.generateDigest(signedDocumentProcesado, "SHA-256");
      } catch (NoSuchAlgorithmException e) {
        throw new EeutilException(
            NO_SE_PUEDE_GENERAR_LA_HUELLA_PARA_EL_DOCUMENTO_FIRMADO + e.getMessage(), e);
      } catch (Exception e) {
        throw new EeutilException(
            NO_SE_PUEDE_GENERAR_LA_HUELLA_PARA_EL_DOCUMENTO_FIRMADO + e.getMessage(), e);

      }

      // Generacion de los sha256 de los pkcs
      Vector<byte[]> sha256Pkcs = null;
      try {
        logger.debug("getCVSTextOM, obteniendo sha-256 de los pkcs");
        sha256Pkcs = CSVUtil.generateDigest(pkcsList, "SHA-256");
      } catch (NoSuchAlgorithmException e) {
        throw new EeutilException(
            "No se puede generar la huella para los pkcs de la firma: " + e.getMessage(), e);
      }

      // Vector de los array de bytes sobre los que queremos calcular el XOR
      Vector<byte[]> xorFactors = new Vector<byte[]>();
      xorFactors.addAll(sha256Pkcs);
      xorFactors.add(randomBytes);
      xorFactors.add(sha256DocumentoFirmado);
      logger.debug("getCVSTextOM, calculando xor");
      byte[] xor = MathUtils.xorArrays(xorFactors);

      // Se pasa a binario este XOR
      logger.debug("getCVSTextOM, pasando xor a binario");
      String binario = CSVUtil.toBinaryString(xor);

      // Tomamos los caracteres pares
      logger.debug("getCVSTextOM, obteniendo caracteres pares");
      String binarioPares = CSVUtil.caracteresPares(binario);

      // Pasamos a hexadecimal
      logger.debug("getCVSTextOM, pasando a hexadecimal");
      String hexadecimal = CSVUtil.bigBinaryToHex(binarioPares);

      // Rellenamos con ceros hasta rellenar los 32 caract
      logger.debug("getCVSTextOM, rellenando con ceros");
      String hexadecimal32Caracteres = CSVUtil.rellenarCeros(hexadecimal, 32);

      logger.debug("getCVSTextOM END");
      return hexadecimal32Caracteres;

    } catch (EeutilException e1) {
      throw new EeutilException(
          NO_SE_PUEDE_GENERAR_LA_HUELLA_PARA_EL_DOCUMENTO_FIRMADO + e1.getMessage(), e1);
    } catch (Exception e1) {
      throw new EeutilException(
          NO_SE_PUEDE_GENERAR_LA_HUELLA_PARA_EL_DOCUMENTO_FIRMADO + e1.getMessage(), e1);
    }
  }

  /**
   * Parsea el patron para obtener todos los elementos que lo componen.
   * 
   * @param pattern Patr&oacute;n a parsear.
   * @return Listado de elementos que componen el patr&oacute;n.
   */
  private PatternElement[] parsePattern(String pattern) {

    Vector<PatternElement> patternElements = new Vector<PatternElement>();
    String[] elementsText = AOUtil.split(pattern, CVSSignature.PATTERN_DELIMITATOR);

    // Comprobamos si habia algun delimitador protegido
    for (int i = 0; i < elementsText.length; i++) {
      String element = elementsText[i];
      if (element.length() > 0 && element.substring(element.length() - 1).equals("\\")) {
        elementsText[i] = element.substring(0, element.length() - 1) + PATTERN_DELIMITATOR;
      }
    }

    // Parseamos cada elemento
    for (String element : elementsText) {
      patternElements.add(parsePatternElement(element));
    }

    return patternElements.toArray(new PatternElement[patternElements.size()]);
  }

  /**
   * Parsea un elemento del patr&oacute;n.
   * 
   * @param element Elemento a parsear.
   * @return Elemento del patr&oacute;n ya configurado.
   */
  private PatternElement parsePatternElement(String element) {

    for (PatternElementType type : PatternElementType.values()) {
      if (element.startsWith(type.pattern)) {
        if (element.startsWith(type.pattern + PARAM_DELIMITATOR)) {
          return new PatternElement(type, extractConfig(element, type.pattern));
        } else {
          return new PatternElement(type, type.defaultConfig);
        }
      }
    }
    return new PatternElement(PatternElementType.TEXT, element);
  }

  private String extractConfig(String element, String prefix) {
    return element.substring((prefix + PARAM_DELIMITATOR).length(), element.length());
  }

  /**
   * Funci&oacute;n para la representaci&oacute;n textual de los elementos binarios del CVS.
   * 
   * @param binary Datos que se desean representar.
   * @return Representaci&oacute;n textual de los binarios.
   */
  private String transcodeBinaryToText(byte[] binary) {
    return AOUtil.hexify(binary, false);
  }

  /** Elemento del patr&oacute;n. */
  private class PatternElement {

    /** Tipo de elemento. */
    PatternElementType type;

    /** Configuraci&oacute;n del elemento. */
    String config = null;

    /**
     * Crea un elemento para el patr&oacute;n con una configuraci&oacute;n concreta.
     * 
     * @param patternType Tipo de elemento.
     * @param param Configuraci&oacute;n del elemento del patr&oacute;n.
     * @see PatternElementType
     */
    PatternElement(PatternElementType patternType, String param) {
      type = patternType;
      config = (param != null) ? param : type.defaultConfig;
    }
  }

  /** Tipo de elemento del patr&oacute;n. */
  private enum PatternElementType {
    /** Hash del XOR de los PKCS#1 de las firmas. */
    PKCS1XORHASH("PKCS1", "MD5"),
    /** Hash del documento de firma. */
    SIGNHASH("HASH", "MD5"),
    /** Constante textual. */
    TEXT("TEXT", null);

    /** Patr&oacute;n del tipo de elemento. */
    private String pattern;

    /** Configuraci&oacute;n por defecto del tipo de elemento. */
    private String defaultConfig;

    /**
     * Contruye el tipo de elemento.
     * 
     * @param pattern Patr&oacute;n que debe encontrarse para identificar el elemento.
     * @param config Configurac&oacute;n por defecto para el elemento.
     */
    private PatternElementType(String pattern, String config) {
      this.pattern = pattern;
      this.defaultConfig = config;
    }
  }
}
