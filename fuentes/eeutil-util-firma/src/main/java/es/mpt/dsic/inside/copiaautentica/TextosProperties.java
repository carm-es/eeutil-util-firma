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

package es.mpt.dsic.inside.copiaautentica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import es.mpt.dsic.inside.config.EeutilApplicationDataConfig;
import es.mpt.dsic.inside.config.EeutilConfigPath;

public class TextosProperties {


  private static TextosProperties instance = null;
  private static Properties propiedades = null;

  public static TextosProperties getInstance() throws IOException {
    if (instance == null) {
      instance = new TextosProperties();
    }
    return instance;
  }

  private TextosProperties() throws IOException {
    FileInputStream fin = null;
    try {
      propiedades = new Properties();
      // String ruta = System.getProperty("config.path") + "/textos.properties";
      Properties props = EeutilApplicationDataConfig.loadProperties("application.properties");
      String ruta = System.getProperty(
          props.getProperty("application.name") + "." + EeutilConfigPath.CONFIG_PATH_VAR)
          + "/textos.properties";
      fin = new FileInputStream(ruta);
      propiedades.load(fin);
    } catch (IOException e) {
      throw e;
    } finally {
      if (fin != null) {
        fin.close();
      }
    }
  }

  public String getProperty(String key) {
    return propiedades.getProperty(key);
  }

}
