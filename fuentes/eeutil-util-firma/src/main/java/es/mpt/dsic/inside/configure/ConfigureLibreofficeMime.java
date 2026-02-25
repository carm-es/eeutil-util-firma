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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import es.mpt.dsic.inside.config.EeutilApplicationDataConfig;
import es.mpt.dsic.inside.utils.exception.EeutilException;

@Component
public class ConfigureLibreofficeMime {


  private static final String ACTIVE_CONST = "active";

  protected static final Log logger = LogFactory.getLog(ConfigureLibreofficeMime.class);

  private static Map<String, Boolean> mLibreofficeFormats = new HashMap<>();

  private static Map<String, Boolean> mLibreofficeProhibitedFormats = new HashMap<>();

  public static boolean getObjLibreofficeFormats(String key) {
    if (mLibreofficeFormats.get(key) != null && mLibreofficeFormats.get(key).booleanValue())
      return true;
    else
      return false;
  }


  public static boolean getObjLibreofficeProhibitedFormats(String key) {
    if (mLibreofficeProhibitedFormats.get(key) != null
        && mLibreofficeProhibitedFormats.get(key).booleanValue())
      return true;
    else
      return false;
  }



  @PostConstruct
  public void cargarMimes() throws EeutilException {

    // obtenemos el path de las propiedades externas.
    String path = EeutilApplicationDataConfig.CONFIG_PATH;

    JSONParser parser = new JSONParser();
    try (FileReader fReader = new FileReader(path + "/" + "libreoffice-formatos.json");) {
      Object obj = parser.parse(fReader);
      JSONObject jsonObject = (JSONObject) obj;

      // A JSON array. JSONObject supports java.util.List interface.
      JSONArray jsonArrayFormList = (JSONArray) jsonObject.get("Libreoffice-Formats");

      Iterator<JSONObject> iterator = jsonArrayFormList.iterator();
      while (iterator.hasNext()) {
        JSONObject jsonO = iterator.next();
        mLibreofficeFormats.put(jsonO.get("mime").toString(),
            Boolean.parseBoolean(jsonO.get(ACTIVE_CONST).toString()));

      }

      // A JSON array. JSONObject supports java.util.List interface.
      JSONArray jsonArrayFormProhibitedList =
          (JSONArray) jsonObject.get("Libreoffice-Prohibited-Formats");

      Iterator<JSONObject> iteratorProhibited = jsonArrayFormProhibitedList.iterator();
      while (iteratorProhibited.hasNext()) {
        JSONObject jsonO = iteratorProhibited.next();
        mLibreofficeProhibitedFormats.put(jsonO.get("mime").toString(),
            Boolean.parseBoolean(jsonO.get(ACTIVE_CONST).toString()));
      }



    } catch (IOException | ParseException e) {
      logger.error(e.getMessage(), e);
      throw new EeutilException(e.getMessage(), e);
    }

  }


  /*
   * public static void main(String args[]) {
   * 
   * try { Date ini=new Date();
   * System.out.println(ConfigureLibreofficeMime.getMimeType(Files.readAllBytes(Paths.
   * get("E:\\Downloads\\408-Article Text-2147-1-10-20130307.pdf")))); Date fin=new Date();
   * System.out.println(fin.getTime()-ini.getTime());
   * 
   * ini=new Date();
   * System.out.println(ConfigureLibreofficeMime.getMimeType(Files.readAllBytes(Paths.
   * get("E:\\Downloads\\Manual integracion.pdf")))); fin=new Date();
   * System.out.println(fin.getTime()-ini.getTime());
   * 
   * } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
   * 
   * 
   * public static String getMimeType(byte[] bytesFichero) { try { Tika tika = new Tika();
   * TikaConfig config = TikaConfig.getDefaultConfig(); MimeType mimeType =
   * config.getMimeRepository().forName(tika.detect(bytesFichero));
   * 
   * return mimeType.getName(); } catch (MimeTypeException e) { return null; } }
   */

  public static void main(String[] args) throws IOException, ParseException {

    JSONParser parser = new JSONParser();
    FileReader fReader = null;
    fReader = new FileReader(
        "C:/d/proyectos/EEUTIL/java/workspace_EEUtils_repo_git/config_jetty/local/vis-docexp/"
            + "libreoffice-formatos.json");
    Object obj = parser.parse(fReader);
    JSONObject jsonObject = (JSONObject) obj;

    JSONArray jsonArrayFormList = (JSONArray) jsonObject.get("Libreoffice-Formats");

    Iterator<JSONObject> iterator = jsonArrayFormList.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonO = iterator.next();
      mLibreofficeFormats.put(jsonO.get("mime").toString(),
          Boolean.parseBoolean(jsonO.get(ACTIVE_CONST).toString()));

    }


    JSONArray jsonArrayFormProhibitedList =
        (JSONArray) jsonObject.get("Libreoffice-Prohibited-Formats");

    Iterator<JSONObject> iteratorProhibited = jsonArrayFormProhibitedList.iterator();
    while (iteratorProhibited.hasNext()) {
      JSONObject jsonO = iteratorProhibited.next();
      mLibreofficeProhibitedFormats.put(jsonO.get("mime").toString(),
          Boolean.parseBoolean(jsonO.get(ACTIVE_CONST).toString()));
    }
  }


}
