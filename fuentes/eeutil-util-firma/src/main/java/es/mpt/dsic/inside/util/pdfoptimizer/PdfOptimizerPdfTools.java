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

package es.mpt.dsic.inside.util.pdfoptimizer;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Component;

import es.mpt.dsic.inside.pdf.exception.PdfConversionException;
import es.mpt.dsic.inside.utils.file.FileUtil;

@Component
public class PdfOptimizerPdfTools {

  protected final static Log logger = LogFactory.getLog(PdfOptimizerPdfTools.class);

  public File optimizePdf(String inputPathFile, int optimizationLevel)
      throws PdfConversionException {


    String outputPathFile = inputPathFile;

    try {
      byte[] input = IOUtils.toByteArray(Files.newInputStream(Paths.get(inputPathFile)));
      Boolean isPDFA = UtilsPdfA.validateSimple(input);

      if (!isPDFA) {
        logger.debug("El documento no es PDF/A, iniciando conversión...");

        byte[] contenido = UtilsPdfA.convertToPDFA(input, PdfCompliance.DEFAULT_FORMAT);
        Path tempFile = Files.createTempFile("pdftools-Optimize", ".pdf");
        // Escribir el array de bytes en el archivo temporal
        Files.write(tempFile, contenido);

        outputPathFile = String.valueOf(tempFile.toAbsolutePath());
      }

    } catch (Exception x) {
      logger.debug("Error al optimizar el documento '" + inputPathFile + "'", x);
    }
    return new File(outputPathFile);
  }

}
