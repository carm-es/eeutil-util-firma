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

package es.mpt.dsic.inside.configure;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import es.mpt.dsic.inside.config.EeutilApplicationDataConfig;

@Component
public class ConfigurePdfOptimizerPdfTools {

  protected final static Log logger = LogFactory.getLog(ConfigurePdfOptimizerPdfTools.class);

  @PostConstruct
  public void configure() {
    try {
      String libName = null;
      if (SystemUtils.IS_OS_WINDOWS) {
        libName = "PdfOptimizeAPI.dll";
      } else {
        libName = "libPdfOptimizeAPI.so";
      }

      // converter
      logger.warn("Configurando PDF Optimizer, cargando libreria:"
          + EeutilApplicationDataConfig.CONFIG_PATH + File.separator + libName);
      System.load(EeutilApplicationDataConfig.CONFIG_PATH + File.separator + libName);
    } catch (UnsatisfiedLinkError e) {
      logger.error("Error cargando libreria.\n" + e);
    }
  }

}
