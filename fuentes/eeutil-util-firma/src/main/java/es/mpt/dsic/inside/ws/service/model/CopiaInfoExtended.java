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

package es.mpt.dsic.inside.ws.service.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.mpt.dsic.inside.exception.interfaz.IMDCAble;
import es.mpt.dsic.inside.reflection.MapUtil;
import es.mpt.dsic.inside.reflection.UtilReflection;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopiaInfoExtended")
public class CopiaInfoExtended extends CopiaInfo implements IMDCAble {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @XmlElement(required = true, name = "firma")
  private byte[] firma;

  @XmlElement(required = false, name = "simple")
  private boolean simple;

  public byte[] getFirma() {
    return firma;
  }

  public void setFirma(byte[] firma) {
    this.firma = firma;
  }

  public boolean isSimple() {
    return simple;
  }

  public void setSimple(boolean simple) {
    this.simple = simple;
  }

  // no se puede heredar de dos asi que lo planchamos
  public String printData() {
    String resultado = null;
    try {
      Map<String, String> mParametros =
          UtilReflection.getInstance().extractDataPermitted(this, null);
      resultado = MapUtil.mapToString(mParametros);
    } catch (Throwable e) {
      logger
          .error("Error al sacar los extraParams" + e.getMessage() + ".Seguimos con la ejecucion");
    }
    return resultado;
  }

}
