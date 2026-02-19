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

import java.io.IOException;
import java.util.Map;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.aowagie.text.DocumentException;
import com.aowagie.text.pdf.PRIndirectReference;
import com.aowagie.text.pdf.PdfDictionary;
import com.aowagie.text.pdf.PdfName;
import com.aowagie.text.pdf.PdfObject;
import com.aowagie.text.pdf.PdfReader;

import es.mpt.dsic.inside.aop.AuditEntryPointAnnotation;
import es.mpt.dsic.inside.reflection.MapUtil;
import es.mpt.dsic.inside.reflection.UtilReflection;
import es.mpt.dsic.inside.security.model.ApplicationLogin;
import es.mpt.dsic.inside.security.wss4j.CredentialUtil;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.ws.service.EeUtilUtilFirmaUserNameTokenService;
import es.mpt.dsic.inside.ws.service.exception.InSideException;
import es.mpt.dsic.inside.ws.service.model.CSVInfo;
import es.mpt.dsic.inside.ws.service.model.CSVInfoAmbito;
import es.mpt.dsic.inside.ws.service.model.CopiaInfo;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoExtended;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirma;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirmaSalida;
import es.mpt.dsic.inside.ws.service.model.EstadoInfo;
import es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo;

@Service("eeUtilUtilFirmaUserNameTokenService")
@WebService(endpointInterface = "es.mpt.dsic.inside.ws.service.EeUtilUtilFirmaUserNameTokenService")
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
public class EeUtilUtilFirmaUserNameTokenServiceImpl
    implements EeUtilUtilFirmaUserNameTokenService {

  private static final String EXTRA_PARA_M = "ExtraParaM";

  private static final String COPIA_PARAM = "copia";

  private static final String SIMPLE_VALUE = "simple";

  protected static final Log logger =
      LogFactory.getLog(EeUtilUtilFirmaUserNameTokenServiceImpl.class);

  private static final String ERROR = "ERROR";

  @Resource
  private WebServiceContext wsContext;

  @Autowired
  CredentialUtil credentialUtil;

  @Autowired
  EeUtilFirmaServiceBusiness eUtilFirmaServiceBusiness;

  @Secured("ROLE_TRAMITAR")
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfo comprobarAplicacion(CopiaInfo copia) throws InSideException {
    return generarCopia(copia, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.mpt.dsic.inside.ws.service.EeUtilService#generarCopia(es.mpt.dsic.
   * inside.security.model.ApplicationLogin, es.mpt.dsic.inside.ws.service.model.CopiaInfo, boolean)
   * 
   * Genera el justificante de firma a partir de la firma, CSV pero sin la lista de firmantes. Los
   * firmantes no se calculan.
   */
  @Override
  @Secured("ROLE_TRAMITAR")
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfo generarCopia(CopiaInfo copia, boolean simple) throws InSideException {

    ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);
    try {
      return eUtilFirmaServiceBusiness.generarCopiaFirmaComun(login.getIdApplicacion(),
          login.getPassword(), copia, null, simple);
    } catch (EeutilException e) {
      generaCopiaMDC(copia, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generaCopiaMDC(copia, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    }
  }

  /**
   * @param copia
   * @param simple
   */
  private void generaCopiaMDC(CopiaInfo copia, boolean simple) {
    try {
      Object[] objs = new Object[2];
      String[] strP = new String[] {COPIA_PARAM, SIMPLE_VALUE};
      objs[0] = copia;
      objs[1] = simple;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public String generarCSV(CSVInfo copia) throws InSideException {
    try {
      logger.debug("Generar CSV");
      ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);
      return eUtilFirmaServiceBusiness.generarCSV(login.getIdApplicacion(), copia);

    } catch (EeutilException e) {
      generarCSVMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarCSVMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }
  }


  /**
   * @param copia
   */
  private void generarCSVMDC(CSVInfo copia) {
    try {
      Object[] objs = new Object[1];
      String[] strP = new String[] {COPIA_PARAM};
      objs[0] = copia;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public String generarCSVAmbito(CSVInfoAmbito copia) throws InSideException {

    try {
      ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);
      return eUtilFirmaServiceBusiness.generarCSVAmbito(login.getIdApplicacion(), copia);
    } catch (EeutilException e) {
      generarCSVAmbitoMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarCSVAmbitoMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }

  }

  /**
   * @param copia
   */
  private void generarCSVAmbitoMDC(CSVInfoAmbito copia) {
    try {
      Object[] objs = new Object[1];
      String[] strP = new String[] {COPIA_PARAM};
      objs[0] = copia;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.mpt.dsic.inside.ws.service.EeUtilService#generarCopiaFirma(es.mpt.
   * dsic.inside.security.model.ApplicationLogin, es.mpt.dsic.inside.ws.service.model.CopiaInfo,
   * es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo, boolean)
   * 
   * Genera el justificante de firma a partir de la firma, CSV y lista de firmantes
   */
  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfo generarCopiaFirma(CopiaInfo copia, ListaFirmaInfo firmas, boolean simple)
      throws InSideException {

    try {
      logger.debug("generarCopiaFirma");

      ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);

      return eUtilFirmaServiceBusiness.generarCopiaFirmaComun(login.getIdApplicacion(),
          login.getPassword(), copia, firmas, simple);
    } catch (EeutilException e) {
      generarCopiaFirmaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarCopiaFirmaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }

  }


  /**
   * @param copia
   * @param firmas
   * @param simple
   */
  private void generarCopiaFirmaMDC(CopiaInfo copia, ListaFirmaInfo firmas, boolean simple) {
    try {
      Object[] objs = new Object[3];
      String[] strP = new String[] {COPIA_PARAM, "firmas", SIMPLE_VALUE};
      objs[0] = copia;
      objs[1] = firmas;
      objs[2] = simple;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.mpt.dsic.inside.ws.service.EeUtilService#generarCopiaFirmaNormalizada
   * (es.mpt.dsic.inside.security.model.ApplicationLogin,
   * es.mpt.dsic.inside.ws.service.model.CopiaInfo,
   * es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo, boolean)
   * 
   * Genera el justificante de firma en formato normalizado a partir de la firma, CSV y lista de
   * firmantes
   */
  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfo generarCopiaFirmaNormalizada(CopiaInfo copia, ListaFirmaInfo firmas,
      boolean simple) throws InSideException {

    ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);
    try {
      return eUtilFirmaServiceBusiness.generarCopiaFirmaNormalizada(login.getIdApplicacion(),
          login.getPassword(), copia, firmas, simple);
    } catch (EeutilException e) {
      generarCopiaFirmaNormalizadaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarCopiaFirmaNormalizadaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }

  }

  /**
   * @param copia
   * @param firmas
   * @param simple
   */
  private void generarCopiaFirmaNormalizadaMDC(CopiaInfo copia, ListaFirmaInfo firmas,
      boolean simple) {
    try {
      Object[] objs = new Object[3];
      String[] strP = new String[] {COPIA_PARAM, "firmas", SIMPLE_VALUE};
      objs[0] = copia;
      objs[1] = firmas;
      objs[2] = simple;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.mpt.dsic.inside.ws.service.EeUtilService#generarJustificanteFirma(
   * es.mpt.dsic.inside.security.model.ApplicationLogin,
   * es.mpt.dsic.inside.ws.service.model.CopiaInfoFirma, boolean)
   * 
   * Genera el justificante de firma a partir del documento original y la firma. El CSV y la lista
   * de firmantes se calcula dinamicamente.
   */
  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfoFirmaSalida generarJustificanteFirma(CopiaInfoFirma copiaInfoFirma,
      boolean simple) throws InSideException {
    try {
      ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);

      return eUtilFirmaServiceBusiness.generarJustificanteFirma(login.getIdApplicacion(),
          login.getPassword(), copiaInfoFirma, simple);
    } catch (EeutilException e) {
      generarJustificanteFirmaMDC(copiaInfoFirma, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarJustificanteFirmaMDC(copiaInfoFirma, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }

  }


  /**
   * @param copiaInfoFirma
   * @param simple
   */
  private void generarJustificanteFirmaMDC(CopiaInfoFirma copiaInfoFirma, boolean simple) {
    try {
      Object[] objs = new Object[2];
      String[] strP = new String[] {"copiaInfoFirma", SIMPLE_VALUE};
      objs[0] = copiaInfoFirma;
      objs[1] = simple;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.mpt.dsic.inside.ws.service.EeUtilService#generarInforme(es.mpt.dsic
   * .inside.security.model.ApplicationLogin, es.mpt.dsic.inside.ws.service.model.CopiaInfoExtended)
   * 
   * Genera el justificante de firma a partir del documento original, la firma y el CSV. La lista de
   * firmantes se calcula dinamicamente a partir de la firma.
   */
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public CopiaInfo generarInforme(CopiaInfoExtended copia) throws InSideException {
    try {
      ApplicationLogin login = credentialUtil.getCredentialEeutilUserToken(wsContext);

      return eUtilFirmaServiceBusiness.generarInforme(login.getIdApplicacion(), login.getPassword(),
          copia);

    } catch (EeutilException e) {
      generarInformeMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarInformeMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }
  }


  /**
   * @param copia
   */
  private void generarInformeMDC(CopiaInfoExtended copia) {
    try {
      Object[] objs = new Object[1];
      String[] strP = new String[] {COPIA_PARAM};
      objs[0] = copia;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }


  // obligado el parametro algoritmo a ser asi: SHA256 , SHA384 , SHA512
  // SHA256("") e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855 longitud=64
  // caracteres hexadecimales
  // SHA384("")
  // 38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b
  // longitud=96 caracteres hexadecimales
  // SHA512("")
  // cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e
  // longitud=128 caracteres hexadecimales

  @Override
  public String generarHash(@XmlMimeType("application/octet-stream") DataHandler data,
      String algoritmo) throws InSideException {



    try {
      return eUtilFirmaServiceBusiness.generarHash(data, algoritmo);
    } catch (EeutilException e) {
      generarHashMDC(data, algoritmo);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      generarHashMDC(data, algoritmo);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }
  }

  /**
   * @param data
   * @param algoritmo
   */
  private void generarHashMDC(DataHandler data, String algoritmo) {
    try {
      Object[] objs = new Object[2];
      String[] strP = new String[] {"data", "algoritmo"};
      objs[0] = data;
      objs[1] = algoritmo;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }

  @Override
  @AuditEntryPointAnnotation(nombreApp = "EEUTIL-UTIL-FIRMA")
  public boolean validarHash(@XmlMimeType("application/octet-stream") DataHandler fichero,
      String hashHEX) throws InSideException {



    try {
      return eUtilFirmaServiceBusiness.validarHash(fichero, hashHEX);
    } catch (EeutilException e) {
      validarHashMDC(fichero, hashHEX);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      validarHashMDC(fichero, hashHEX);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), new EstadoInfo(ERROR, ERROR, e.getMessage()), e);
    }
  }

  /**
   * @param fichero
   * @param hashHEX
   */
  private void validarHashMDC(DataHandler fichero, String hashHEX) {
    try {
      Object[] objs = new Object[2];
      String[] strP = new String[] {"fichero", "hashHEX"};
      objs[0] = fichero;
      objs[1] = hashHEX;

      Map<String, String> mParametros =
          UtilReflection.getInstance().extractMultipleDataPermitted(null, objs, strP);
      String resultado = MapUtil.mapToString(mParametros);
      MDC.put(EXTRA_PARA_M, resultado);

    } catch (IOException e1) {

      // si falla palante

    }
  }


  /*
   * public static void main (String args[]) throws DocumentException, IOException {
   */


  /*
   * FileOutputStream outputStream = null; Document document = new Document(); outputStream = new
   * FileOutputStream("c:/d/sample6.pdf"); PdfWriter writer=PdfWriter.getInstance(document,
   * outputStream); try { document.open(); document.add(new Paragraph("sdfdsfeasebs  " ));
   * document.add(new LineSeparator(0.5f, 100, null, 0, -5));
   * 
   * PdfDictionary dictionary1 = new PdfDictionary(PdfName.PARENT); PdfDictionary dictionary2 = new
   * PdfDictionary(PdfName.PARENT); dictionary2.put(PdfName.PARENT, dictionary1);
   * dictionary1.put(PdfName.PARENT, dictionary2);
   * 
   * writer.getExtraCatalog().put(PdfName.PARENT, dictionary1);
   * writer.getExtraCatalog().put(PdfName.PARENT, dictionary2);
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary1);
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary2);
   * 
   * getParentReference2(writer.getExtraCatalog());
   * 
   * 
   * document.close(); outputStream.close(); writer.close(); } catch(Throwable e) {
   * document.close(); outputStream.close(); writer.close(); }
   * 
   * com.aowagie.text.pdf.PdfReader reader2 = new
   * com.aowagie.text.pdf.PdfReader("c:/d/sample4.pdf"); com.aowagie.text.pdf.PdfReader reader = new
   * com.aowagie.text.pdf.PdfReader("c:/d/sampleStackOverflowError2.pdf");
   * getParentReference2(reader.getCatalog());
   * 
   */
  /********************************************************************/

  /*
   * FileOutputStream outputStream2 = null; Document document2 = new Document(); outputStream2 = new
   * FileOutputStream("c:/d/sample_n_level.pdf"); PdfWriter writer2=PdfWriter.getInstance(document2,
   * outputStream2); try { document2.open(); document2.add(new Paragraph("sdfdsfeasebs  " ));
   * document2.add(new LineSeparator(0.5f, 100, null, 0, -5));
   * 
   * 
   * PdfDictionary base = new PdfDictionary(PdfName.PARENT);
   * 
   * 
   * 
   * 
   * for(int i= 0 ; i< 2700; i++) { PdfDictionary agregador = buscarDiccionarioFinal(base);
   * PdfDictionary agregado = new PdfDictionary(PdfName.PARENT);
   * 
   * agregador.put(PdfName.PARENT, agregado); }
   * 
   * 
   * 
   * writer2.getExtraCatalog().put(PdfName.PARENT, base);
   * 
   * 
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary1);
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary2);
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary1);
   * //writer.getExtraCatalog().put(PdfName.PARENT, dictionary2);
   * 
   * getParentReference2(writer2.getExtraCatalog());
   * 
   * 
   * document2.close(); outputStream2.close(); writer2.close(); } catch(Throwable e) { throw e;
   * //document2.close(); //outputStream2.close(); //writer2.close(); }
   * 
   * PdfReader reader2 = new PdfReader("c:/d/sample_n_level.pdf");
   * getParentReference2(reader2.getCatalog());
   */


  /******************************************************/


  /*
   * PdfDictionary dict1 = new PdfDictionary(PdfName.PARENT); PdfDictionary dict2 = new
   * PdfDictionary(PdfName.PARENT); PdfDictionary dict3 = new PdfDictionary(PdfName.PARENT);
   * dict2.put(PdfName.PARENT, dict3); dict1.put(PdfName.PARENT, dict2);
   * 
   * PdfDictionary base = new PdfDictionary(PdfName.PARENT);
   * 
   * 
   * 
   * 
   * for(int i= 0 ; i< 1000; i++) { PdfDictionary agregador = buscarDiccionarioFinal(base);
   * PdfDictionary agregado = new PdfDictionary(PdfName.PARENT);
   * 
   * agregador.put(PdfName.PARENT, agregado); }
   * 
   * 
   * 
   * buscarDiccionarioFinal(base);
   * 
   */



  /*****************************************************************/


  /*
   * PdfReader reader6 = new PdfReader("c:/d/sample_100_level.pdf"); PdfStamper stamper = new
   * PdfStamper(reader6, new FileOutputStream("c:/d/sample_100_level_stamper.pdf"));
   * 
   * 
   * PdfDictionary dictionary1 = new PdfDictionary(PdfName.PARENT); PdfDictionary dictionary2 = new
   * PdfDictionary(PdfName.PARENT); dictionary2.put(PdfName.PARENT, dictionary1);
   * dictionary1.put(PdfName.PARENT, dictionary2);
   * 
   * stamper.getReader().getCatalog().put(PdfName.PARENT, dictionary1);
   * stamper.getReader().getCatalog().put(PdfName.PARENT, dictionary2);
   * 
   * stamper.close();
   * 
   */
  /*
   * PdfReader reader6 = new PdfReader("c:/d/REDUNDANCIA_CICLICA_ANDRES.pdf");
   * 
   * getParentReference2(reader6.getCatalog());
   * 
   * 
   * 
   * }
   * 
   * 
   * public static PRIndirectReference getParentReference2(PdfDictionary dict) { PdfDictionary
   * parentDict = dict; PRIndirectReference parentRef = null; int iteraciones=0; do {
   * 
   * System.out.println("ITERACIONES"+iteraciones++);; PdfObject parentObj =
   * parentDict.get(com.aowagie.text.pdf.PdfName.PARENT); if (parentObj != null && parentObj
   * instanceof PRIndirectReference) parentRef = (PRIndirectReference)parentObj; parentDict =
   * parentDict.getAsDict(com.aowagie.text.pdf.PdfName.PARENT); } while (parentDict != null); return
   * parentRef; }
   * 
   * 
   * public static PdfDictionary buscarDiccionarioFinal(PdfDictionary pdfDict) {
   * 
   * PdfDictionary dict = null; int iteraciones = 0;
   * 
   * do { dict = pdfDict.getAsDict(PdfName.PARENT);
   * 
   * if(dict==null) { System.out.println("ITERACIONES"+ iteraciones); return pdfDict; } else {
   * pdfDict = dict; }
   * 
   * iteraciones++;
   * 
   * }while(dict != null);
   * 
   * return null; }
   * 
   */

}
