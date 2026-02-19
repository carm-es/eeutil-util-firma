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

package es.mpt.dsic.inside.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SLF4JLocationAwareLog;

import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.mpt.dsic.inside.utils.test.IPruebaTraza;

/**
 * @author miguel.moral
 *
 */
public class UtilidadesTestEeutilUtilFirma implements IPruebaTraza {
  protected static final Log logger = LogFactory.getLog(UtilidadesTestEeutilUtilFirma.class);


  public String testTrazaImp() {
    boolean bImpSLF4JLocationAwareLog = false;

    if (logger instanceof SLF4JLocationAwareLog) {
      bImpSLF4JLocationAwareLog = true;
    }
    logger.error("Is implementation SLF4JLocationAwareLog " + bImpSLF4JLocationAwareLog
        + " Is error enabled: " + logger.isErrorEnabled()
        + " Verificacion de que la traza en eeutil-util-firma es correcta");

    return "Is implementation SLF4JLocationAwareLog " + bImpSLF4JLocationAwareLog
        + " Is error enabled: " + logger.isErrorEnabled()
        + " Verificacion de que la traza en eeutil-util-firma es correcta";
  }

  public static void main(String args[]) throws IOException {
    FileInputStream fis = new FileInputStream("c:/d/encriptado.pdf");
    FileInputStream fis2 = new FileInputStream("c:/d/encriptado.pdf");
    FileInputStream fis3 = new FileInputStream("c:/d/encriptado.pdf");
    String outputFile = "c:/d/decryptado.pdf";
    String outputFile2 = "c:/d/decryptado_clonando_permisos.pdf";

    PdfReader reader = new PdfReader(fis, "password".getBytes());
    PdfEncryptor.encrypt(reader, new FileOutputStream(outputFile), null, null,
        PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_DEGRADED_PRINTING
            | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_MODIFY_ANNOTATIONS
            | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_PRINTING
            | PdfWriter.ALLOW_SCREENREADERS,
        true);
    reader.close();
    fis.close();
    PdfReader reader2 = new PdfReader(fis2, "password".getBytes());
    PdfEncryptor.encrypt(reader2, new FileOutputStream(outputFile2), null, null,
        reader2.getPermissions(), true);
    reader2.close();
    fis2.close();
    System.out.println("HOLA MUNDO");

    boolean isEncrypted = true;
    try (PdfReader reader3 = new PdfReader(fis3, "password3".getBytes());) {
      isEncrypted = reader.isEncrypted();

    }

    System.out.println(isEncrypted);


  }
}
