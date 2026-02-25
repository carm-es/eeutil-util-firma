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

package es.mpt.dsic.inside.rest.run;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import es.mpt.dsic.inside.utils.exception.EeutilException;

/**
 * Esta clase guardara el token unico que gestionara la aplicacion, ese token se sobreescribira
 * cuando al menos queden 5 minutos para la expiracion de este
 * 
 * @author mamoralf
 *
 */
public class JwtTokenRunAlmacenador {

  private String jwtRun;

  private static JwtTokenRunAlmacenador laInstancia;


  public static JwtTokenRunAlmacenador getInstance() {
    if (laInstancia == null) {
      laInstancia = new JwtTokenRunAlmacenador();
    }
    return laInstancia;
  }



  public String getJwtRun() {
    return jwtRun;
  }



  public void setJwtRun(String jwtRun) {
    this.jwtRun = jwtRun;
  }

}
