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

package es.mpt.dsic.inside.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import es.gob.afirma.core.AOException;
import es.gob.afirma.core.misc.MimeHelper;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.core.signers.AOSignerFactory;
import es.gob.afirma.signers.xades.AOXAdESSigner;
import es.gob.eeutils.compatibilility.afirma.xades.XAdESSigner165Utils;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.utils.pdf.PdfEncr;
import es.mpt.dsic.inside.utils.xml.XMLUtil;
import es.mpt.dsic.inside.utils.xmlsecurity.XMLSeguridadFactoria;
import es.mpt.dsic.inside.wrapper.AOSignerWrapperEeutils;

public class SignedDataExtractor {

  private static final String NO_SE_ENCUENTRA_EL_DOCUMENTO_IMPLICITO_EN_LA_FIRMA =
      "No se encuentra el documento Implicito en la firma";
  private static final String NO_SE_PUEDE_PARSEAR_LA_FIRMA = "No se puede parsear la firma ";

  protected final static Log logger = LogFactory.getLog(SignedDataExtractor.class);

  private SignedDataExtractor() {

  }

  /**
   * Obtiene un objeto SignData a partir de una firma.
   * 
   * @param sign firma.
   * @return un objeto que contiene el documento que se ha firmado y el mime de ese documento.
   * @throws Exception cuando no se puedan obtener los datod e la firma.
   */
  public static SignedData getDataFromSign(byte[] sign) throws EeutilException {

    byte[] datosFirma = null;

    try {
      datosFirma = getDataSignedFromSignXML(sign);

    } catch (Exception e) {
      AOSigner signer;
      try {
        signer = obtenerSigner(sign);
        datosFirma = getDataSignedFromSignerGenerico(signer, sign);
      } catch (EeutilException e1) {
        throw new EeutilException(e.getMessage(), e);
      }

    }

    // String mime = MimeUtil.getMimeNotNull(datosFirma);

    // forma alternativa de calcular mimetipe. otra alternativa seria desdoblar este
    // metodo para cuando venimos por el proceso de generarcsv
    // porque no lo necesita para nada y evitamos el error de
    // ava.util.ConcurrentModificationException
    MimeHelper helperMime = new MimeHelper(datosFirma);
    String mime = null;
    try {
      mime = helperMime.getMimeType();
    } catch (IOException e) {
      throw new EeutilException(e.getMessage(), e);
    }

    SignedData signData = new SignedData(datosFirma, mime);

    return signData;

  }

  /**
   * Obtiene los datos firmados de una firma XML implicita.
   * 
   * @param sign bytes de la firma
   * @return bytes de los datos firmados de una firma XML
   * @throws Exception si no se pueden obtener los datos firmados.
   */
  private static byte[] getDataSignedFromSignXML(byte[] sign) throws Exception {
    byte[] datosFirma = null;

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    // dbf.setFeature(XMLSeguridadFactoria.SAX_FEATURES_EXTERNAL_GENERAL_ENTITIES, false);
    // dbf.setFeature(XMLSeguridadFactoria.SAX_FEATURES_EXTERNAL_PARAMETER_ENTITIES, false);
    XMLSeguridadFactoria.setPreventAttackDocumentBuilderFactoryExternalStatic(dbf);
    DocumentBuilder db = null;
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      // traza basura, borrarla
      // logger.error(NO_SE_PUEDE_PARSEAR_LA_FIRMA + e.getMessage(), e);
      throw new Exception(NO_SE_PUEDE_PARSEAR_LA_FIRMA + e.getMessage(), e);
    }
    Document dom = null;
    try (ByteArrayInputStream bArrayIn = new ByteArrayInputStream(sign)) {
      dom = db.parse(bArrayIn);
      datosFirma = getContent(dom);
      return datosFirma;

    } catch (SAXException | IOException e) {
      // traza basura, borrarla
      // logger.error(NO_SE_PUEDE_PARSEAR_LA_FIRMA + e.getMessage(), e);
      throw new Exception(NO_SE_PUEDE_PARSEAR_LA_FIRMA + e.getMessage(), e);
    }
  }

  /**
   * Obtiene el contenido del nodo hijo del nodo padre del documento. Este nodo debe contener los
   * atributos "id" y "encoding".
   * 
   * @param dom Árbol XML del documento
   * @return el contenido del nodo hijo del nodo padre del documento.
   * @throws Exception si no se encuentra el nodo que cumpla estas condiciones.
   */
  private static byte[] getContent(Document dom) throws EeutilException {
    Element elementRoot = dom.getDocumentElement();

    Node child = elementRoot.getFirstChild();
    boolean lastChild = false;
    boolean encontrado = false;
    while (!lastChild && !encontrado) {

      encontrado = XMLUtil.contieneIdEncoding(child);

      if (child == elementRoot.getLastChild()) {
        lastChild = true;
      } else if (!encontrado) {
        child = child.getNextSibling();
      }
    }

    String b64Content = null;
    byte[] document = null;

    if (encontrado) {
      b64Content = child.getFirstChild().getNodeValue();
      document = Base64.decodeBase64(b64Content);
    } else {
      // traza basura
      // logger.error(NO_SE_ENCUENTRA_EL_DOCUMENTO_IMPLICITO_EN_LA_FIRMA);
      throw new EeutilException(NO_SE_ENCUENTRA_EL_DOCUMENTO_IMPLICITO_EN_LA_FIRMA);
    }

    return document;

  }

  /**
   * Se obtiene un objeto para manipular la firma.
   * 
   * @param bytes de la firma
   * @return Instancia de un objeto para manipular la firma.
   * @throws IOException
   */
  private static AOSigner obtenerSigner(byte[] bytes) throws EeutilException {
    AOSigner signer = null;

    // esto tiene que ir siempre antes de AOSignerFactory.getSigner
    if (PdfEncr.isProtectedPdf(bytes)) {
      throw new EeutilException(
          "Error al obtenerSigner. El fichero pdf tiene contrase�a y no se puede procesar");
    }

    signer = new AOSignerWrapperEeutils().wrapperGetSigner(bytes);
    return signer;
  }

  private static byte[] getDataSignedFromSignerGenerico(AOSigner signer, byte[] bytesFirma)
      throws EeutilException {
    byte[] bytesDatosFirmados = null;

    if (signer == null) {
      // logger.error(NO_SE_ENCUENTRA_EL_DOCUMENTO_IMPLICITO_EN_LA_FIRMA);
      throw new EeutilException(NO_SE_ENCUENTRA_EL_DOCUMENTO_IMPLICITO_EN_LA_FIRMA);
    }

    try {

      if (signer instanceof AOXAdESSigner) {
        bytesDatosFirmados = XAdESSigner165Utils.getData(bytesFirma);
      } else {
        bytesDatosFirmados = signer.getData(bytesFirma);
      }
    } catch (AOException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (IOException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (Throwable e) {
      throw new EeutilException(e.getMessage(), e);
    }

    return bytesDatosFirmados;
  }

  public static void main(String args[]) throws IOException, AOException {
    byte[] bytesFirma = Files.readAllBytes(Paths.get("e:/Downloads/firmaxadestf07.xsig"));

    AOSigner signer = AOSignerFactory.getSigner(bytesFirma);
    // AOXAdESSigner
    byte[] bytesDatosFirmados = signer.getData(bytesFirma);

    System.out.println(
        "Tama�o bytes firma: " + ((bytesDatosFirmados == null) ? 0 : bytesDatosFirmados.length));

  }

}
