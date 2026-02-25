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

package es.mpt.dsic.inside.configure;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import es.mpt.dsic.inside.config.EeutilApplicationDataConfig;
import es.mpt.dsic.inside.utils.exception.EeutilException;

@Component
public class ConfigureRestInfo {

  /*** INICIO REST-IGAE ***/

  private static String baseUrlRestIgae;

  private static String tokenNameRestIgae;
  private static String tokenValueRestIgae;

  /*** FIN REST-IGAE ***/

  /*** INICIO RUN ***/

  private static String baseUrlSecureRun;
  private static boolean activeRun;
  private static Integer activeRunSize;
  private static Integer activeUrlNoQrSize;
  private static String baseUrlOriginalSecureRun;
  private static String runUsername;
  private static String runPassword;

  private static Integer numberCharacteresKeyRun;

  /*** FIN RUN ****/

  /*** INICIO LIBREOFFICE ****/

  private static String baseUrlSecureLibreoffice;
  private static boolean activeLibreoffice;

  private static String tokenNameLibreoffice;
  private static String tokenValueLibreoffice;

  /*** FIN LIBREOFFICE ***/


  public static Integer getActiveRunSize() {
    return activeRunSize;
  }



  public static String getBaseUrlRestIgae() {
    return baseUrlRestIgae;
  }



  public static String getTokenNameRestIgae() {
    return tokenNameRestIgae;
  }



  public static String getTokenValueRestIgae() {
    return tokenValueRestIgae;
  }



  public static Integer getActiveUrlNoQrSize() {
    return activeUrlNoQrSize;
  }



  public static String getBaseUrlSecureRun() {
    return baseUrlSecureRun;
  }



  public static boolean isActiveRun() {
    return activeRun;
  }


  public static String getBaseUrlOriginalSecureRun() {
    return baseUrlOriginalSecureRun;
  }


  public static String getRunUsername() {
    return runUsername;
  }


  public static String getRunPassword() {
    return runPassword;
  }


  public static Integer getNumberCharacteresKeyRun() {
    return numberCharacteresKeyRun;
  }


  public static String getBaseUrlSecureLibreoffice() {
    return baseUrlSecureLibreoffice;
  }



  public static Boolean isActiveLibreoffice() {
    return activeLibreoffice;
  }



  public static String getTokenNameLibreoffice() {
    return tokenNameLibreoffice;
  }



  public static String getTokenValueLibreoffice() {
    return tokenValueLibreoffice;
  }



  @PostConstruct
  public static void cargarRutasRelativas() throws EeutilException, IOException {

    // obtenemos el path de las propiedades externas.
    String path = EeutilApplicationDataConfig.CONFIG_PATH;

    try (FileReader fReader = new FileReader(path + "/" + "eeutil.properties");) {
      Properties prop = new Properties();
      prop.load(fReader);

      baseUrlRestIgae = prop.getProperty("eeutil.igae.rs.base.url");
      tokenNameRestIgae = prop.getProperty("eeutil.igae.token.name");
      tokenValueRestIgae = prop.getProperty("eeutil.igae.token.value");

      baseUrlSecureRun = prop.getProperty("run.alta.url");
      activeRun = Boolean.parseBoolean(prop.getProperty("run.active"));
      activeRunSize = Integer.parseInt(prop.getProperty("run.active.size"));
      activeUrlNoQrSize = Integer.parseInt(prop.getProperty("run.active.urlnoqr.size"));
      baseUrlOriginalSecureRun = prop.getProperty("run.original.url");
      runUsername = prop.getProperty("run.username");
      runPassword = prop.getProperty("run.password");
      numberCharacteresKeyRun = Integer.parseInt(prop.getProperty("run.alta.numbercharacterskey"));

      baseUrlSecureLibreoffice = prop.getProperty("eeutil.libreoffice.rs.base.url");
      tokenNameLibreoffice = prop.getProperty("eeutil.libreoffice.token.name");
      tokenValueLibreoffice = prop.getProperty("eeutil.libreoffice.token.value");
      activeLibreoffice = Boolean.parseBoolean(prop.getProperty("libreoffice.active"));

    }

  }

}
