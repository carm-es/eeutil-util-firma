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
import java.util.UUID;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import es.mpt.dsic.inside.aop.AuditEntryPointAnnotation;
import es.mpt.dsic.inside.reflection.MapUtil;
import es.mpt.dsic.inside.reflection.UtilReflection;
import es.mpt.dsic.inside.security.model.ApplicationLogin;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.ws.service.EeUtilService;
import es.mpt.dsic.inside.ws.service.exception.InSideException;
import es.mpt.dsic.inside.ws.service.model.CSVInfo;
import es.mpt.dsic.inside.ws.service.model.CSVInfoAmbito;
import es.mpt.dsic.inside.ws.service.model.CopiaInfo;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoExtended;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirma;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirmaSalida;
import es.mpt.dsic.inside.ws.service.model.EstadoInfo;
import es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo;

@Service("eeUtilService")
@WebService(endpointInterface = "es.mpt.dsic.inside.ws.service.EeUtilService")
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
public class EeUtilServiceImpl implements EeUtilService {

  private static final String EXTRA_PARA_M = "ExtraParaM";

  private static final String COPIA_PARAM = "copia";

  private static final String SIMPLE_VAL = "simple";

  private static final String ERROR = "ERROR";

  @Autowired
  EeUtilFirmaServiceBusiness eUtilFirmaServiceBusiness;

  protected static final Log logger = LogFactory.getLog(EeUtilServiceImpl.class);

  @Secured("ROLE_TRAMITAR")
  public CopiaInfo comprobarAplicacion(ApplicationLogin info, CopiaInfo copia)
      throws InSideException {
    return generarCopia(info, copia, false);
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
  public CopiaInfo generarCopia(ApplicationLogin info, CopiaInfo copia, boolean simple)
      throws InSideException {



    try {
      return eUtilFirmaServiceBusiness.generarCopiaFirmaComun(info.getIdApplicacion(),
          info.getPassword(), copia, null, simple);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generaCopiaMDC(copia, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generaCopiaMDC(copia, simple);
      logger.error(e.getMessage(), e);
      EstadoInfo estado = new EstadoInfo(ERROR, ERROR, e.getMessage());
      throw new InSideException(e.getMessage(), estado, e);
    }

  }

  /**
   * @param copia
   * @param simple
   */
  private void generaCopiaMDC(CopiaInfo copia, boolean simple) {
    try {
      Object[] objs = new Object[2];
      String[] strP = new String[] {COPIA_PARAM, SIMPLE_VAL};
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
  public String generarCSV(ApplicationLogin info, CSVInfo copia) throws InSideException {



    logger.debug("Generar CSV: " + info.getIdApplicacion());
    try {
      return eUtilFirmaServiceBusiness.generarCSV(info.getIdApplicacion(), copia);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarCSVMDC(copia);
      logger.error(e.getMessage(), e);

      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarCSVMDC(copia);
      logger.error(e.getMessage(), e);
      EstadoInfo estado = new EstadoInfo(ERROR, ERROR, e.getMessage());

      throw new InSideException(e.getMessage(), estado, e);
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
  public String generarCSVAmbito(ApplicationLogin info, CSVInfoAmbito copia)
      throws InSideException {

    try {
      return eUtilFirmaServiceBusiness.generarCSVAmbito(info.getIdApplicacion(), copia);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarCSVAmbitoMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarCSVAmbitoMDC(copia);
      logger.error(e.getMessage(), e);
      EstadoInfo estado = new EstadoInfo(ERROR, ERROR, e.getMessage());

      throw new InSideException(e.getMessage(), estado, e);
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
  public CopiaInfo generarCopiaFirma(ApplicationLogin login, CopiaInfo copia, ListaFirmaInfo firmas,
      boolean simple) throws InSideException {



    logger.debug("generarCopiaFirma: " + login.getIdApplicacion());
    try {
      return eUtilFirmaServiceBusiness.generarCopiaFirmaComun(login.getIdApplicacion(),
          login.getPassword(), copia, firmas, simple);

    } catch (EeutilException e) {
      ingresarMDCAppUUID(login.getIdApplicacion());
      generarCopiaFirmaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(login.getIdApplicacion());
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
      String[] strP = new String[] {COPIA_PARAM, "firmas", SIMPLE_VAL};
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
  public CopiaInfo generarCopiaFirmaNormalizada(ApplicationLogin info, CopiaInfo copia,
      ListaFirmaInfo firmas, boolean simple) throws InSideException {
    try {
      return eUtilFirmaServiceBusiness.generarCopiaFirmaNormalizada(info.getIdApplicacion(),
          info.getPassword(), copia, firmas, simple);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarCopiaFirmaNormalizadaMDC(copia, firmas, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
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
      String[] strP = new String[] {COPIA_PARAM, "firmas", SIMPLE_VAL};
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
  public CopiaInfoFirmaSalida generarJustificanteFirma(ApplicationLogin info,
      CopiaInfoFirma copiaInfoFirma, boolean simple) throws InSideException {

    try {
      return eUtilFirmaServiceBusiness.generarJustificanteFirma(info.getIdApplicacion(),
          info.getPassword(), copiaInfoFirma, simple);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
      generarJustificanteFirmaMDC(copiaInfoFirma, simple);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(info.getIdApplicacion());
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
      String[] strP = new String[] {"copiaInfoFirma", SIMPLE_VAL};
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
  public CopiaInfo generarInforme(ApplicationLogin login, CopiaInfoExtended copia)
      throws InSideException {
    try {
      return eUtilFirmaServiceBusiness.generarInforme(login.getIdApplicacion(), login.getPassword(),
          copia);
    } catch (EeutilException e) {
      ingresarMDCAppUUID(login.getIdApplicacion());
      generarInformeMDC(copia);
      logger.error(e.getMessage(), e);
      throw new InSideException(e.getMessage(), e);
    } catch (Exception e) {
      ingresarMDCAppUUID(login.getIdApplicacion());
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


  private void ingresarMDCAppUUID(String idApp) {
    MDC.put("idApli", idApp);
    MDC.put("uUId", UUID.randomUUID().toString());
  }


}
