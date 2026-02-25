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



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import es.gob.afirma.core.AOException;
import es.gob.afirma.core.AOInvalidFormatException;
import es.gob.afirma.core.AOUnsupportedSignFormatException;
import es.gob.afirma.core.misc.MimeHelper;
import es.gob.afirma.core.signers.AOSignInfo;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.core.signers.AOSimpleSignInfo;
import es.gob.afirma.core.util.tree.AOTreeModel;
import es.gob.afirma.core.util.tree.AOTreeNode;
import es.gob.afirma.signers.xades.AOXAdESSigner;
import es.gob.eeutils.compatibilility.afirma.xades.XAdESSigner165Utils;
import es.mpt.dsic.inside.exception.AfirmaException;
import es.mpt.dsic.inside.model.InformacionFirmaAfirma;
import es.mpt.dsic.inside.services.AfirmaService;
import es.mpt.dsic.inside.util.SignedData;
import es.mpt.dsic.inside.util.SignedDataExtractor;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.utils.pdf.PdfEncr;
import es.mpt.dsic.inside.wrapper.AOSignerWrapperEeutils;

@Service
public class SignatureProcessor {

  protected final static Log logger = LogFactory.getLog(SignatureProcessor.class);

  /** Firma que deseamos visualizar. */
  // private byte[] signatureData = null;

  /** Manejador de firma compatible con el objeto de firma analizado. */
  // private AOSigner signer = null;

  /** Informaci&oacute;n general obtenida de la firma. */
  // private AOSignInfo signInfo = null;

  /** Informaci&oacute;n de los datos contenidos en la firma. */
  // private AOSignedDataInfo dataInfo = null;

  /** &Aacute;rbol de firmas. */
  // private AOTreeModel signTree = null;

  /* Documento firmado */
  // private byte[] signedDocument = null;

  @Autowired
  private AfirmaService afirmaService;

  /**
   * Procesa un objeto de firma electr&oacute;nica en uno de los formatos de firma soportados para
   * extraer sus caracter&iacute;sticas y los datos firmados.
   * 
   * @param signature Firma que se desea analizar.
   * @throws AOUnsupportedSignFormatException Cuando el formato de firma no est&aacute; soportado.
   * @throws AOException Cuando ocurre un error al analizar el objeto de firma.
   */
  public void processSign(final byte[] signature, byte[] signedDocument, String idAplicacion)
      throws AOUnsupportedSignFormatException, EeutilException {

    try {

      AOSigner signer = null;
      AOSignInfo signInfo = null;
      AOSignedDataInfo dataInfo = null;

      // Comprobaciones de seguridad
      if (signature == null || signature.length == 0) {
        throw new NullPointerException("Signature vacio.No se han introducido datos de firma");
      }

      // esto tiene que ir siempre antes de AOSignerFactory.getSigner
      if (PdfEncr.isProtectedPdf(signature)) {
        throw new EeutilException(
            "Error al processSign. El fichero pdf tiene contrase�a y no se puede procesar");
      }

      // Obtenemos un manejador de firma compatible
      signer = new AOSignerWrapperEeutils().wrapperGetSigner(signature);

      if (signer == null) {
        // traza basura, borrarla
        // logger.error("La firma proporcionada no se corresponde con ningun formato reconocido");
        throw new AOUnsupportedSignFormatException(
            "La firma proporcionada no se corresponde con ningun formato reconocido. Se recogera la excepcion mas adelante");
      }

      signedDocument = obtenerDocumentoFirmado(signature, signedDocument, idAplicacion);

      // Obtenemos los datos generales de la firma y los datos que se firmaron
      byte[] signedData = null;
      try {
        // signInfo = signer.getSignInfo(signature);

        if (signer instanceof AOXAdESSigner) {
          signedData = XAdESSigner165Utils.getData(signature);
        } else {
          signedData = signer.getData(signature);
        }



      } catch (Exception e) {
        // logger.error("Ocurrio un problema al analizar el objeto de firma", e);
        throw new AOException(
            "Ocurrio un problema al analizar el objeto de firma: " + e.getMessage(), e);
      }

      // Analizamos los datos firmados
      if (signedData != null) {
        dataInfo = new AOSignedDataInfo();
        // this.dataInfo.setDataMimeType(signer.getDataMimeType(signedData));
        MimeHelper mimeHelper = new MimeHelper(signature);
        dataInfo.setDataMimeType(mimeHelper.getMimeType());
        dataInfo.setData(signedData);
        // this.dataInfo = ViewerUtils.analizeData(signedData);
      }
    } catch (AOUnsupportedSignFormatException e) {
      throw new AOUnsupportedSignFormatException(e.getMessage());
    } catch (AOException e) {
      throw new EeutilException(e.getMessage(), e);
    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

  }

  /**
   * Si el documento de la firma es nulo lo calcula sino lo devuelve como tal, es un calculado que
   * se ha extraido a una funcion con visibilidad package para que CVSSignature lo pueda ver.
   * 
   * @param signature firma
   * @param signedDocument documento de la firma.
   * @param idAplicacion
   * @throws Exception
   * @throws AfirmaException
   */
  byte[] obtenerDocumentoFirmado(final byte[] signature, byte[] signedDocument, String idAplicacion)
      throws AfirmaException, EeutilException {
    // Si el documento firmado es nulo, lo obtenemos de la firma
    if (signedDocument == null) {
      signedDocument = obtainSignedDocument(signature);

      // obtenemos el documento de la informacion de la firma
      if (signedDocument == null) {
        InformacionFirmaAfirma infoAfirma = afirmaService.obtenerInformacionFirma(idAplicacion,
            signature, false, true, false, null);
        signedDocument = infoAfirma.getDocumentoFirmado().getContenido();
      }
    }

    return signedDocument;
  }

  private byte[] obtainSignedDocument(byte[] signature) throws EeutilException {
    SignedData signedData = SignedDataExtractor.getDataFromSign(signature);
    return signedData.getContenido();
  }

  /**
   * Genera un &aacute;rbol con la misma estructura que el &aacute;rbol de firmas de la firma
   * procesada. Cada nodo distinto del ra&iacute;z contendr&aacute; la informaci&oacute;n de una
   * firma simple de la firma electr&oacute;nica manteniendo la misma estructura conceptual que
   * esta.
   * 
   * @return &Aacute;rbol de firmas.
   * @throws IOException
   * @throws AOInvalidFormatException
   */
  public AOTreeModel generateCertificatesTree(byte[] signature) throws EeutilException {



    AOTreeModel signTree = null;

    // esto tiene que ir siempre antes de AOSignerFactory.getSigner
    if (PdfEncr.isProtectedPdf(signature)) {
      throw new EeutilException(
          "Error en generateCertificatesTree. El fichero pdf tiene contrase�a y no se puede procesar");
    }

    AOSigner signer = new AOSignerWrapperEeutils().wrapperGetSigner(signature);

    try {
      signTree = signer.getSignersStructure(signature, true);
    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }


    return signTree;
  }

  /**
   * Recupera un array formado por el XOR de los PKCS1 de las distinas firmas simples de la firma
   * electr&oacute;nica.
   * 
   * @return Array resultante.
   * @throws IOException
   * @throws AOInvalidFormatException
   */
  public byte[] signatureValue(byte[] signature) throws EeutilException {


    AOTreeModel signTree = generateCertificatesTree(signature);

    Vector<byte[]> result = new Vector<byte[]>();
    getPKCS1Values(result, (AOTreeNode) signTree.getRoot());

    if (result.size() == 0)
      return null;
    else if (result.size() == 1)
      return result.elementAt(0);
    else
      return MathUtils.xorArrays(result);
  }

  /**
   * Recupera un listado con los PKCS#1 de las firmas simples de una firma electr&oacute;nica.
   */
  public void getPKCS1Values(Vector<byte[]> values, AOTreeNode node) {

    // Object object = ((DefaultMutableTreeNode)node).getUserObject();
    Object object = ((AOTreeNode) node).getUserObject();
    if (object instanceof AOSimpleSignInfo) {

      // Comprobamos si ya tenemos este certificado
      byte[] pkcs1 = ((AOSimpleSignInfo) object).getPkcs1();
      if (pkcs1 != null)
        values.add(pkcs1);
    }

    // Procesamos los nodos hijos
    for (int i = 0; i < node.getChildCount(); i++) {
      getPKCS1Values(values, (AOTreeNode) node.getChildAt(i));
    }
  }



  public byte[] generateB64VerificationCode(byte[] signature) throws EeutilException {
    final Vector<byte[]> vector = new Vector<byte[]>();
    final AOTreeNode root = (AOTreeNode) generateCertificatesTree(signature).getRoot();
    generateB64VerificationCode(root, vector);
    return MathUtils.xorArrays(vector);
  }

  private void generateB64VerificationCode(AOTreeNode root, Vector<byte[]> vector) {
    for (int i = 0; i < root.getChildCount(); i++) {
      getPKCS1Values(vector, (AOTreeNode) root.getChildAt(i));
    }
  }

  public static InputStream getInputStream(byte[] ba) throws EeutilException {

    try {

      return new ByteArrayInputStream(ba);

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }

  public byte[] fromBase64StringToByteArray(String s) {

    return Base64.decodeBase64(s);
  }

  public String fromByteArrayToBase64(byte[] s) {

    return Base64.encodeBase64String(s);
  }

  public byte[] fromInputStreamToByteArray(InputStream is) throws EeutilException {

    byte[] copyToByteArray = null;
    try {
      if (is.markSupported())
        is.reset();
      copyToByteArray = FileCopyUtils.copyToByteArray(is);
    } catch (IOException e) {
      // logger.error(e.getMessage(),e);
      throw new EeutilException(e.getMessage(), e);
    }

    return copyToByteArray;
  }



  public String fromInputStreamToBase64String(InputStream is) throws EeutilException {
    byte[] fromInputStreamToByteArray = fromInputStreamToByteArray(is);
    return fromByteArrayToBase64(fromInputStreamToByteArray);
  }

}
