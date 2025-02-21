/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.mpt.dsic.inside.ws.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.mpt.dsic.inside.ws.service.exception.InSideException;
import es.mpt.dsic.inside.ws.service.model.CSVInfo;
import es.mpt.dsic.inside.ws.service.model.CSVInfoAmbito;
import es.mpt.dsic.inside.ws.service.model.CopiaInfo;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoExtended;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirma;
import es.mpt.dsic.inside.ws.service.model.CopiaInfoFirmaSalida;
import es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo;

@WebService
public interface EeUtilUtilFirmaUserNameTokenService {

  @WebMethod(operationName = "generarCopia", action = "urn:GenerarCopia")
  @WebResult(name = "estadoInfo", partName = "estadoInfo")
  public CopiaInfo generarCopia(
      @WebParam(name = "copiaInfo") @XmlElement(required = true,
          name = "copiaInfo") CopiaInfo copia,
      @WebParam(name = "simpleFormat") @XmlElement(required = true,
          name = "simpleFormat") boolean simple)
      throws InSideException;

  @WebMethod(operationName = "generarCopiaFirma", action = "urn:GenerarCopiaFirma")
  @WebResult(name = "estadoInfoFirma", partName = "estadoInfoFirma")
  public CopiaInfo generarCopiaFirma(
      @WebParam(name = "copiaInfo") @XmlElement(required = true,
          name = "copiaInfo") CopiaInfo copia,
      @WebParam(name = "firmaInfo") @XmlElement(required = true,
          name = "firmaInfo") ListaFirmaInfo firmas,
      @WebParam(name = "simpleFormat") @XmlElement(required = true,
          name = "simpleFormat") boolean simple)
      throws InSideException;

  @WebMethod(operationName = "generarCopiaFirmaNormalizada",
      action = "urn:GenerarCopiaFirmaNormalizada")
  @WebResult(name = "estadoInfoFirma", partName = "estadoInfoFirma")
  public CopiaInfo generarCopiaFirmaNormalizada(
      @WebParam(name = "copiaInfo") @XmlElement(required = true,
          name = "copiaInfo") CopiaInfo copia,
      @WebParam(name = "firmaInfo") @XmlElement(required = true,
          name = "firmaInfo") ListaFirmaInfo firmas,
      @WebParam(name = "simpleFormat") @XmlElement(required = true,
          name = "simpleFormat") boolean simple)
      throws InSideException;

  @WebMethod(operationName = "generarCSV", action = "urn:GenerarCSV")
  @WebResult(name = "CSVResult", partName = "CSVResult")
  public String generarCSV(
      @WebParam(name = "CSVInfo") @XmlElement(required = true, name = "CSVInfo") CSVInfo copia)
      throws InSideException;

  @WebMethod(operationName = "generarCSVAmbito", action = "urn:generarCSVAmbito")
  @WebResult(name = "CSVResult", partName = "CSVResult")
  public String generarCSVAmbito(@WebParam(name = "CSVInfoAmbito") @XmlElement(required = true,
      name = "CSVInfoAmbito") CSVInfoAmbito copia) throws InSideException;

  @WebMethod(operationName = "generarJustificanteFirma", action = "urn:generarJustificanteFirma")
  @WebResult(name = "infoFirma", partName = "infoFirma")
  public CopiaInfoFirmaSalida generarJustificanteFirma(
      @WebParam(name = "copiaInfoFirma") @XmlElement(required = true,
          name = "copiaInfoFirma") CopiaInfoFirma copiaInfoFirma,
      @WebParam(name = "simpleFormat") @XmlElement(required = true,
          name = "simpleFormat") boolean simple)
      throws InSideException;

  @WebMethod(operationName = "generarInforme", action = "urn:generarInforme")
  @WebResult(name = "estadoInfoFirma", partName = "estadoInfoFirma")
  public CopiaInfo generarInforme(@WebParam(name = "copiaInfoExtended") @XmlElement(required = true,
      name = "copiaInfoExtended") CopiaInfoExtended copia) throws InSideException;

  @WebMethod(operationName = "generarHash", action = "urn:generarHash")
  @WebResult(name = "hash", partName = "hash")
  public String generarHash(@XmlElement(required = true, name = "fichero") DataHandler fichero,
      @XmlElement(required = true, name = "algoritmo") String algoritmo) throws InSideException;

  @WebMethod(operationName = "validarHash", action = "urn:validarHash")
  @WebResult(name = "resultado", partName = "resultado")
  public boolean validarHash(@XmlElement(required = true, name = "fichero") DataHandler fichero,
      @XmlElement(required = true, name = "hashHEX") String hashHEX) throws InSideException;

}
