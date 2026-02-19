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

package es.mpt.dsic.inside.copiaautentica;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
// import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.mpt.dsic.inside.csv.CSVUtil;
import es.mpt.dsic.inside.pdf.PdfOptions;
import es.mpt.dsic.inside.pdf.file.StamperWrapper;
import es.mpt.dsic.inside.rest.run.ClientSecureRestRun;
import es.mpt.dsic.inside.security.context.AplicacionContext;
import es.mpt.dsic.inside.security.util.AplicationContextUtils;
import es.mpt.dsic.inside.utils.exception.EeutilException;
import es.mpt.dsic.inside.ws.service.model.CopiaInfo;
import es.mpt.dsic.inside.ws.service.model.FirmaInfo;
import es.mpt.dsic.inside.ws.service.model.ListaFirmaInfo;
import es.mpt.dsic.inside.ws.service.model.OpcionesPagina;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@Service("pdfCopiaAutenticaUtils")
public class PdfCopiaAutenticaUtils {

  private static final String ISO_8859_1 = "ISO-8859-1";
  public static int FILAS_SIMPLE = 1;
  public static int FILAS_FIRMA = 6;
  public static int FILAS_COMPLEJO = 3;

  public static float[] RELATIVE_WIDTHS_COMPLEJO = new float[] {0.25f, 0.4f, 0.35f};

  public static final String EMPTY = "EMPTY";

  public static int NUM_FIRMANTES_ABAJO_MAX = 3;
  public static int NUM_FIRMANTES_PAGINA_SIMPLE = 50;
  public static int NUM_FIRMANTES_PAGINA_COMPLEJO = 25;

  protected static final Log logger = LogFactory.getLog(PdfCopiaAutenticaUtils.class);

  private static final String RUTA_LOGO_PARAM = "rutaLogo";

  @Autowired(required = false)
  private AplicacionContext aplicacionContext;

  @Autowired
  private AplicationContextUtils aplicationContextUtils;

  @Autowired
  private ClientSecureRestRun clientSecureRestRun;

  public int numeroPaginasEnBlanco(ListaFirmaInfo firmantes, boolean simple) {
    int paginas = 0;
    int numMax = NUM_FIRMANTES_PAGINA_COMPLEJO;
    if (simple) {
      numMax = NUM_FIRMANTES_PAGINA_SIMPLE;
    }
    if (firmantes != null && firmantes.getInformacionFirmas().size() > NUM_FIRMANTES_ABAJO_MAX) {
      paginas = firmantes.getInformacionFirmas().size() / numMax;
      int modulo = firmantes.getInformacionFirmas().size() % numMax;
      if (modulo > 0) {
        paginas++;
      }
    }
    return paginas;
  }

  public PdfOptions createPdfOptions(OpcionesPagina opcionesPagina) {
    PdfOptions pdfOptions = PdfOptions.createDefault();
    pdfOptions.setPrintPageNumbers(false);
    if (opcionesPagina != null) {
      if (opcionesPagina.getPorcentajeDocumento() != null) {
        pdfOptions.setPagePercent(opcionesPagina.getPorcentajeDocumento());
      }
      if (opcionesPagina.getSeparacionX() != null) {
        pdfOptions.setPagePositionX(opcionesPagina.getSeparacionX());
      }
      if (opcionesPagina.getSeparacionY() != null) {
        pdfOptions.setPagePositionY(opcionesPagina.getSeparacionY());
      }
    }
    return pdfOptions;
  }

  public PdfOptions createPdfOptionsWithOutPercent(OpcionesPagina opcionesPagina) {
    PdfOptions pdfOptions = PdfOptions.createDefault();
    pdfOptions.setPagePercent(0);
    pdfOptions.setPrintPageNumbers(false);
    if (opcionesPagina != null) {
      if (opcionesPagina.getPorcentajeDocumento() != null) {
        pdfOptions.setPagePercent(opcionesPagina.getPorcentajeDocumento());
      }
      if (opcionesPagina.getSeparacionX() != null) {
        pdfOptions.setPagePositionX(opcionesPagina.getSeparacionX());
      }
      if (opcionesPagina.getSeparacionY() != null) {
        pdfOptions.setPagePositionY(opcionesPagina.getSeparacionY());
      }
    }
    return pdfOptions;
  }

  public void insertarPaginasEnBlanco(
      /* PdfReader reader, FileOutputStream fos, PdfStamper stamper, */StamperWrapper stamperWrapper,
      int numeroPaginas) throws EeutilException {

    String resultPath = null;



    try {
      // resultPath = FileUtil.createFilePath("pagBlanco");
      // fis = new FileInputStream(ficheroPDF);
      // fos = new FileOutputStream(resultPath);

      // try(FileInputStream fis = new FileInputStream (ficheroPDF);FileOutputStream
      // fos = new FileOutputStream (resultPath);) {
      // pdfReaderUnlocker=new PdfReader(fis);
      // reader = PdfUtils.unlockPdf(pdfReaderUnlocker);

      // stamper = new PdfStamper(reader, fos, PdfWriter.VERSION_1_7);
      // stamper.setFullCompression();

      for (int i = 1; i <= numeroPaginas; i++) {
        stamperWrapper.getStamper().insertPage(stamperWrapper.getReader().getNumberOfPages() + i,
            PageSize.A4);
      }

    } catch (Exception t) {
      // logger.error("No se pueden insertar paginas en blanco. Nombre de fichero:" +
      // ficheroPDF.getName()
      // + "Numero de paginas:" + numeroPaginas, t);
      throw new EeutilException("No se pueden insertar paginas en blanco. Nombre de fichero:"
          /* + ficheroPDF.getName() */ + "Numero de paginas:" + numeroPaginas + " "
          + t.getMessage(), t);
    } finally {

    }
  }


  /*
   * public InputStream copiaAutenticaSimpleFirma (InputStream ficheroPDF, CopiaInfo copia, String
   * rutaLogo, boolean simple, ListaFirmaInfo listaFirma, int pagsBlanco) throws Exception {
   */
  public File copiaAutenticaSimpleFirmaOptimized(StamperWrapper stamperWrapper,
      /* PdfReader reader, FileOutputStream fOut, PdfStamper stamp, */ CopiaInfo copia,
      boolean simple, ListaFirmaInfo listaFirma, int pagsBlanco, ImageProperties logoProperties)
      throws EeutilException {
    Document overDocument = null;

    File fResul = null;

    // intentamos acortar la url de sede si no es vacia
    generateURLRunSede(copia);

    copia.setCsv(CSVUtil.unificarAmbitoCSV(copia.getIdAplicacion(), copia.getCsv()));

    // obtenemos la propiedad "pintarFechaFirma"
    boolean pintarFechaFirma = StringUtils
        .isEmpty(aplicacionContext.getAplicacionInfo().getPropiedades().get("pintarFechaFirma"))
            ? true
            : "N".equals(
                aplicacionContext.getAplicacionInfo().getPropiedades().get("pintarFechaFirma"))
                    ? false
                    : true;



    try {
      // fos = new FileOutputStream(resultPath);
      // stamp2 = new PdfStamper(reader2, fos, PdfWriter.VERSION_1_7);
      // stamp2.setFullCompression();


      String rutaLogoOriginal =
          aplicacionContext.getAplicacionInfo().getPropiedades().get(RUTA_LOGO_PARAM);


      // stamp2.setFormFlattening(false);
      int numPags = stamperWrapper.getReader().getNumberOfPages();
      int primeraPaginaBlanco = numPags - pagsBlanco + 1;

      int pagsDocumentoOriginal = numPags - pagsBlanco;

      simple = simple || (pagsDocumentoOriginal == 1 && listaFirma != null);

      int i = 1;

      Image imgQR = null;

      while (i <= numPags) {

        if (i >= primeraPaginaBlanco) {
          simple = false;
        }

        PdfContentByte over = stamperWrapper.getStamper().getOverContent(i);


        overDocument = over.getPdfDocument();
        PdfPTable footer = null;

        // float table_width = reader.getPageSize(i).getWidth() - 50;
        PdfWriter writer = stamperWrapper.getStamper().getWriter();
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        float table_width = calculeWidth(stamperWrapper.getReader(), i, writer);
        // pintarLineaTMP(over, table_width);
        // SIMPLE

        if (simple) {

          footer = createWideTable(FILAS_SIMPLE, table_width);
          // Se a�ade informacion a la tabla footer
          tratarTablaInfo(copia, true, footer);

          // SIMPLE - Hay firmantes
          if (listaFirma != null) {

            // SIMPLE - Hay Firmantes - Pocos firmantes
            if (listaFirma.getInformacionFirmas().size() <= NUM_FIRMANTES_ABAJO_MAX) {
              // Se a�ade info de los firmantes a la tabla footer
              tratarTablaFirmantes(listaFirma.getInformacionFirmas(), 0,
                  listaFirma.getInformacionFirmas().size(), copia, true, footer, pintarFechaFirma);

              // SIMPLE - Hay Firmantes - Muchos firmantes - No se meten salvo que sean las
              // paginas en blanco.
            } else {
              // SIMPLE - Hay Firmantes - Muchos firmantes - es hoja en blanco
              if (i >= primeraPaginaBlanco) {
                int numPaginaBlanco = i - primeraPaginaBlanco;
                int iInicial = numPaginaBlanco * NUM_FIRMANTES_PAGINA_SIMPLE;
                int iFinal = iInicial + NUM_FIRMANTES_PAGINA_SIMPLE;

                if (iFinal > listaFirma.getInformacionFirmas().size()) {
                  iFinal = listaFirma.getInformacionFirmas().size();
                }

                PdfPTable tablaFirmantes = createWideTable(FILAS_SIMPLE, table_width);
                tratarTablaFirmantes(listaFirma.getInformacionFirmas(), iInicial, iFinal, copia,
                    true, tablaFirmantes, pintarFechaFirma);

                // escribir tabla Firmantes
                pintarTablaFirmantesEnHojaBlanca(tablaFirmantes, overDocument, over, table_width,
                    stamperWrapper.getReader().getPageSize(i).getHeight());
              }

            }

          }

          // COMPLEJO
        } else {
          // Complejo - hay firmantes
          if (listaFirma != null) {

            // Complejo - hay firmantes- Pocos firmantes
            if (listaFirma.getInformacionFirmas().size() <= NUM_FIRMANTES_ABAJO_MAX) {

              // Complejo - hay firmantes- Pocos firmantes- No es ultima pagina
              if (i != numPags) {

                // footer = createWideTable(FILAS_COMPLEJO, reader.getPageSize(i).getWidth());
                footer = createWideTableRelativeWidths(RELATIVE_WIDTHS_COMPLEJO, table_width);
                tratarTablaInfo(copia, false, footer);

                // CREAR E INSERTAR CODIGO DE BARRAS AL FOOTER
                if (!("").equalsIgnoreCase(copia.getCsv())) {
                  PdfPCell cell2 = createCodeBarCell(
                      createImageCodeBar(CSVUtil.formatCSV(copia.getCsv()), over));
                  footer.addCell(cell2);
                }

                // Complejo - hay firmantes- Pocos firmantes- Ultima pagina
              } else {

                int columnas = FILAS_FIRMA;
                if (!algunNif(listaFirma.getInformacionFirmas())) {
                  columnas = FILAS_FIRMA - 1;
                }

                footer = createWideTable(columnas, table_width);
                tratarTablaFirmantes(listaFirma.getInformacionFirmas(), 0,
                    listaFirma.getInformacionFirmas().size(), copia, false, footer,
                    pintarFechaFirma);
              }

              // Complejo - hay firmantes - Muchos firmantes
            } else {

              // footer = createWideTable(FILAS_COMPLEJO, reader.getPageSize(i).getWidth());
              footer = createWideTableRelativeWidths(RELATIVE_WIDTHS_COMPLEJO, table_width);
              tratarTablaInfo(copia, false, footer);

              // CREAR E INSERTAR CODIGO DE BARRAS AL FOOTER
              if (!("").equalsIgnoreCase(copia.getCsv())) {
                PdfPCell cell2 =
                    createCodeBarCell(createImageCodeBar(CSVUtil.formatCSV(copia.getCsv()), over));
                footer.addCell(cell2);
              }

              // Complejo - hay firmantes - Muchos firmantes - Hoja blanca
              if (i >= primeraPaginaBlanco) {

                int numPaginaBlanco = i - primeraPaginaBlanco;
                int iInicial = numPaginaBlanco * NUM_FIRMANTES_PAGINA_COMPLEJO;
                int iFinal = iInicial + NUM_FIRMANTES_PAGINA_COMPLEJO;

                if (iFinal > listaFirma.getInformacionFirmas().size()) {
                  iFinal = listaFirma.getInformacionFirmas().size();
                }

                int columnas = FILAS_FIRMA;
                if (!algunNif(listaFirma.getInformacionFirmas())) {
                  columnas = FILAS_FIRMA - 1;
                }

                PdfPTable tablaFirmantes = createWideTableRelativeWidths(
                    new float[] {0.15f, 0.25f, 0.2f, 0.15f, 0.25f}, table_width);
                tratarTablaFirmantes(listaFirma.getInformacionFirmas(), iInicial, iFinal, copia,
                    false, tablaFirmantes, pintarFechaFirma);

                // escribir tabla Firmantes
                pintarTablaFirmantesEnHojaBlanca(tablaFirmantes, overDocument, over, table_width,
                    stamperWrapper.getReader().getPageSize(i).getHeight());

              }

            }

            // Complejo - no hay firmantes
          } else {

            // footer = createWideTable(FILAS_COMPLEJO, reader.getPageSize(i).getWidth());
            footer = createWideTableRelativeWidths(RELATIVE_WIDTHS_COMPLEJO, table_width);
            tratarTablaInfo(copia, false, footer);

            // CREAR E INSERTAR CODIGO DE BARRAS AL FOOTER
            if (!("").equalsIgnoreCase(copia.getCsv())) {
              PdfPCell cell2 =
                  createCodeBarCell(createImageCodeBar(CSVUtil.formatCSV(copia.getCsv()), over));
              footer.addCell(cell2);
            }

          }

        }

        if (copia.getEstamparLogo()) {
          pintarLogo(logoProperties, over,
              stamperWrapper.getReader().getPageSizeWithRotation(i).getHeight(),
              stamperWrapper.getReader().getPageSizeWithRotation(i).getWidth(), rutaLogoOriginal);
        }

        pintarLateral(copia, stamperWrapper.getReader().getPageSizeWithRotation(i).getHeight(),
            over, stamperWrapper.getReader().getPageSizeWithRotation(i).getWidth());

        pintarLineaHorizontal(over);

        pintarPie(footer, overDocument, over);

        if (copia.getUrlQR() != null && !copia.getUrlQR().contentEquals("")) {


          if (imgQR == null) {
            imgQR = pintarQR(copia.getUrlQR(), copia);
          }
          over.addImage(imgQR);



        }

        i++;
      }

      fResul = new File(stamperWrapper.getNameFileFileOutPrefix());

    } catch (NoClassDefFoundError t) {

      if (t.getMessage().contains("bouncycastle")) {
        throw new EeutilException(
            "El fichero esta protegido y no se puede acceder:" + " " + t.getMessage(), t);
      } else {
        // logger.error(t.getMessage());
        throw new EeutilException(
            "No se pueden insertar paginas en blanco. Nombre de fichero:" + t.getMessage(), t);
      }

    } catch (Exception t) {
      // logger.error("Error en copiaAutenticaSimpleFirma. No se puede generar la copia autentica.
      // Nombre de fichero"
      // + ficheroPDF.getName() + " " + t.getMessage());
      throw new EeutilException(
          "Error en copiaAutenticaSimpleFirma. No se puede generar la copia autentica."
              + t.getMessage(),
          t);
    }
    return fResul;
  }

  private void pintarLineaHorizontal(PdfContentByte over) {
    // PINTAR LINEA HORIZONTAL
    float x = 50.0f;
    float y = 81.0f;
    float tamanolinea = 525.0f;
    over.moveTo(x, y);
    over.lineTo(x + tamanolinea, y);
    over.stroke();
  }

  // Funcion utilizada para depurar. Pinta una linea que en produccion estar
  // oculta
  private void pintarLineaTMP(PdfContentByte over, float table_width) {
    // PINTAR LINEA HORIZONTAL
    float x = table_width;
    float y = 80f;
    over.moveTo(x, y);
    over.lineTo(x, y - 20);
    over.stroke();
  }

  public void pintarPie(PdfPTable footer, Document document, PdfContentByte over) {
    footer.writeSelectedRows(0, -1, 50.0f, 80.0f, over);
  }

  public void pintarTablaFirmantesEnHojaBlanca(PdfPTable tabla, Document document,
      PdfContentByte over, float width, float hight) {
    tabla.writeSelectedRows(0, -1, (document.right() - document.left() - 400) / 2, hight - 85,
        over);

  }

  private Image getImagenLogo(ImageProperties logoProperties, float pos_x, float pos_y)
      throws EeutilException {
    try {
      Image img = null;
      if (StringUtils.isNotEmpty(logoProperties.getRuta())) {
        File f = new File(logoProperties.getRuta());
        if (f.exists()) {
          img = Image.getInstance(logoProperties.getRuta());
          img.scalePercent(logoProperties.getEscalaX(), logoProperties.getEscalaY());
          img.setAbsolutePosition(pos_x, pos_y);
        }
      }
      return img;

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }

  private void pintarLogo(ImageProperties logoProperties, PdfContentByte pdfContentByte, float alto,
      float ancho, String rutaLogoOriginal) throws EeutilException {

    try {

      // se obtiene el logo o el logo rotado si corresponde
      if (ancho > alto) {
        logoProperties
            .setRuta(aplicationContextUtils.getrutaLogoByOrientation(rutaLogoOriginal, false));
      }
      // si tiene la palabra rotado se elimina, si no la tiene no se hace nada
      else {
        logoProperties
            .setRuta(aplicationContextUtils.getrutaLogoByOrientation(rutaLogoOriginal, true));
      }


      float posx = 10.0f;

      // PdfDocument pdfDocument = stamp.getOverContent(numPag).getPdfDocument();
      // PdfDocument pdfDocument = pdfContentByte.getPdfDocument();

      float posy = alto - 45f;

      // establecemos el porcentage reduccioen funcion del tamano del logo
      Image imagenLogo = getImagenLogo(logoProperties, posx, posy);

      // stamp.getOverContent(numPag).addImage(imagenLogo);
      pdfContentByte.addImage(imagenLogo);

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }

  private void pintarLateral(CopiaInfo copia, float height, PdfContentByte over, float width)
      throws EeutilException {

    try {

      String lineaLateral = "";
      TextosProperties tprop = null;

      // Si el lateral no esta vacio, lo escribimos en el lateral.
      if (copia.getLateral() != null) {
        lineaLateral = copia.getLateral();
      } else {
        tprop = TextosProperties.getInstance();

        // String textoAmbito = trataCabecera (copia.getTituloAplicacion(),
        // tprop.getProperty("simple.cabecera.aplicacion"));
        // lineaLateral = tratarParCabeceraValor (textoAmbito, copia.getIdAplicacion());

        if (!("").contentEquals(copia.getCsv())) {
          lineaLateral += tprop.getProperty("lateral.texto1") + tprop.getProperty("lateral.texto15")
              + " : " + CSVUtil.formatCSV(copia.getCsv());
        }

        if (!("EMPTY").contentEquals(copia.getUrlSede())) {
          lineaLateral += " | " + tprop.getProperty("lateral.texto2")/* +" "+ copia.getUrlSede() */;
        }
      }

      if (width < height) {

        lineaLateral = rellenarStringConBlancos(lineaLateral, 285);

        // si el tamano de la linea lateral es muy grande (mas de 220 caracteres, se trunca
        if (lineaLateral != null && lineaLateral.length() >= 286) {
          lineaLateral = lineaLateral.substring(0, 285) + "...";
        }
      } else {

        lineaLateral = rellenarStringConBlancos(lineaLateral, 222);

        // si el tamano de la linea lateral es muy grande (mas de 207 caracteres, se trunca
        if (lineaLateral != null && lineaLateral.length() >= 223) {
          lineaLateral = lineaLateral.substring(0, 222) + "...";
        }


      }

      // si el campo lateral existe al rellenar con blancos se marca con | el final para que se
      // escriban los espacios en blanco
      // que hay en la linea.
      if (copia.getLateral() != null) {
        lineaLateral += "|";
      }



      escribirLateral(lineaLateral, height, over, width, copia, tprop);

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

  }

  private void escribirLateral(String lateral, float height, PdfContentByte over, float width,
      CopiaInfo copia, TextosProperties tprop) {
    Font fontLateral = null;
    Font fontLateralRoja = null;

    if (height < width) {
      fontLateral = FontFactory.getFont(FontFactory.TIMES, ISO_8859_1, 5, Font.NORMAL, Color.BLACK);
      fontLateralRoja =
          FontFactory.getFont(FontFactory.TIMES, ISO_8859_1, 5, Font.NORMAL, Color.RED);
    } else {
      fontLateral = FontFactory.getFont(FontFactory.TIMES, ISO_8859_1, 6, Font.NORMAL, Color.BLACK);
      fontLateralRoja =
          FontFactory.getFont(FontFactory.TIMES, ISO_8859_1, 6, Font.NORMAL, Color.RED);
    }

    PdfPTable header = new PdfPTable(1);

    header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    // header.getDefaultCell().setRotation(90);
    header.getDefaultCell().setBorder(0);
    header.setTotalWidth(30);


    // Paragraph p = new Paragraph (lateral, fontLateral);
    // p.setAlignment(Element.ALIGN_CENTER);
    PdfPCell cell = null;


    int indiceRojo = -1;
    // si esto es distinto de nulo es que NO existe un lateral como parametro.
    if (tprop != null) {
      indiceRojo = tprop.getProperty("lateral.texto1").length();
    }
    // indiceRojo = lateral.indexOf("documento original");
    String textoRojo = null;
    String textoNegro = null;
    if (indiceRojo != -1) {
      textoRojo = lateral.substring(0, indiceRojo/* +"documento original".length() */);
      textoNegro =
          lateral.substring(indiceRojo/* +"documento original".length() */, lateral.length());
    }

    if (textoRojo != null) {
      Paragraph paragraph = new Paragraph(textoRojo, fontLateralRoja);
      paragraph.add(new Paragraph(textoNegro, fontLateral));
      cell = new PdfPCell(paragraph);
    } else {
      cell = new PdfPCell(new Paragraph(lateral, fontLateral));
    }
    // cell = new PdfPCell(p);
    cell.setRotation(90);
    cell.setBorder(0);
    // cell.setBorder(Rectangle.BOX);
    // cell.setBorder(FILAS_COMPLEJO);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);

    if (height < width) {

      // cell.setFixedHeight(400f);
      // cell.setMinimumHeight(400f);
      header.setTotalWidth(30f);
      header.setLockedWidth(true);

    }
    // cell.setUseBorderPadding(true);
    // cell.setBorderWidth(5f);


    if (!("EMPTY").contentEquals(copia.getUrlSede()) && copia.getLateral() == null) {


      if (width < height) {
        PdfPCell cell2 = null;
        cell2 = new PdfPCell();
        Phrase c = new Phrase(copia.getUrlSede(),
            FontFactory.getFont(FontFactory.TIMES, "ISO-8859-1", 6, Font.NORMAL));
        cell2.setRotation(90);
        cell2.addElement(c);
        cell2.setBorder(0);
        cell2.setPadding(0);
        // cell2.setBorder(Rectangle.BOX);
        header.addCell(cell2);
      } else {
        PdfPCell cell2 = null;
        cell2 = new PdfPCell();
        Phrase c = new Phrase(copia.getUrlSede(),
            FontFactory.getFont(FontFactory.TIMES, "ISO-8859-1", 5, Font.NORMAL));
        cell2.setRotation(90);
        cell2.addElement(c);
        cell2.setBorder(0);
        cell2.setPadding(0);
        // cell2.setBorder(Rectangle.BOX);
        header.addCell(cell2);
      }

    }
    header.addCell(cell);


    // header.writeSelectedRows(0,
    // -1,15,(reader.getPageSizeWithRotation(1).getHeight()/2)+300, over);
    float posy = 0;

    if (height < width) {

      posy = (height / 2) + lateral.length() + 26;
      header.writeSelectedRows(0, -1, 40.0f, posy, over);
      // header.writeSelectedRows(0, -1, 18.0f, (height / 2) + lateral.length() + 45, over);

    } else {

      header.writeSelectedRows(0, -1, 40.0f, (height / 2) + lateral.length() + 82, over);

    }
    // header.writeSelectedRows(0, -1,15,(height/2)+370, over);

  }

  private void tratarTablaInfo(CopiaInfo copia, boolean simple, PdfPTable footer)
      throws EeutilException {

    try {

      TextosProperties tprop = TextosProperties.getInstance();

      Font fontStyleHeaders = null;
      Font fontTexto = null;

      if (simple) {

        fontStyleHeaders =
            FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 6, Font.UNDERLINE);
        fontTexto = FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.BOLD);

        String all = "";
        String all2 = "";

        String cabecera =
            trataCabecera(copia.getTituloCSV(), tprop.getProperty("simple.cabecera.csv"));
        String concatenar = tratarParCabeceraValor(cabecera, CSVUtil.formatCSV(copia.getCsv()));
        all += concatenar;

        cabecera =
            trataCabecera(copia.getTituloFecha(), tprop.getProperty("simple.cabecera.fecha"));
        concatenar = tratarParCabeceraValor(cabecera, copia.getFecha());
        all += concatenar;

        if (all.endsWith(" | ")) {
          all = all.substring(0, all.length() - 3);
        }

        cabecera = trataCabecera(copia.getTituloExpediente(),
            tprop.getProperty("simple.cabecera.expediente"));
        concatenar = tratarParCabeceraValor(cabecera, copia.getExpediente());
        all2 += concatenar;

        cabecera = trataCabecera(copia.getTituloURL(), tprop.getProperty("simple.cabecera.url"));
        concatenar = tratarParCabeceraValor(cabecera, tratarURL(copia.getUrlSede()));
        all2 += concatenar;

        cabecera = trataCabecera(copia.getTituloNif(), tprop.getProperty("simple.cabecera.nif"));
        concatenar = tratarParCabeceraValor(cabecera, copia.getNif());
        all2 += concatenar;

        if (all2.endsWith(" | ")) {
          all2 = all2.substring(0, all2.length() - 3);
        }

        footer.addCell(new Phrase(all, fontTexto));
        footer.addCell(new Phrase(all2, fontTexto));

      } else {

        fontStyleHeaders =
            FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.UNDERLINE);
        fontTexto = FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.BOLD);

        footer.addCell(new Phrase(trataCabecera(copia.getTituloAplicacion(),
            tprop.getProperty("complejo.cabecera.aplicacion")), fontStyleHeaders));
        footer.addCell(new Phrase(
            trataCabecera(copia.getTituloCSV(), tprop.getProperty("complejo.cabecera.csv")),
            fontStyleHeaders));
        footer.addCell(new Phrase(
            trataCabecera(copia.getTituloFecha(), tprop.getProperty("complejo.cabecera.fecha")),
            fontStyleHeaders));

        footer.addCell(new Phrase(copia.getIdAplicacion(), fontTexto));
        footer.addCell(new Phrase(CSVUtil.formatCSV(copia.getCsv()), fontTexto));
        footer.addCell(new Phrase(copia.getFecha(), fontTexto));

        footer.addCell(new Phrase(trataCabecera(copia.getTituloExpediente(),
            tprop.getProperty("complejo.cabecera.expediente")), fontStyleHeaders));
        footer.addCell(new Phrase(
            trataCabecera(copia.getTituloURL(), tprop.getProperty("complejo.cabecera.url")),
            fontStyleHeaders));
        footer.addCell(new Phrase(
            trataCabecera(copia.getTituloNif(), tprop.getProperty("complejo.cabecera.nif")),
            fontStyleHeaders));

        footer.addCell(new Phrase(copia.getExpediente(), fontTexto));
        footer.addCell(parrafoURL(tratarURL(copia.getUrlSede()), fontTexto));
        // footer.addCell(new Phrase(tratarURL(copia.getUrlSede()),fontTexto));

        /*
         * String url = tratarURL(copia.getUrlSede()); Paragraph p = new Paragraph(); Anchor anchor
         * = new Anchor(url, fontTexto); anchor.setReference(url);
         * 
         * p.add(anchor); footer.addCell(p);
         */

        footer.addCell(new Phrase(copia.getNif(), fontTexto));
      }

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }

  private void tratarTablaFirmantes(List<FirmaInfo> listaFirmas, int iInicial, int iFinal,
      CopiaInfo copia, boolean simple, PdfPTable footer, boolean pintarFechaFirma)
      throws EeutilException {

    try {

      TextosProperties tprop = TextosProperties.getInstance();

      Font fontStyleHeaders = null;
      Font fontTexto = null;

      if (simple == true) {

        fontStyleHeaders =
            FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.UNDERLINE);
        fontTexto = FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.BOLD);

        for (int i = iInicial; i < iFinal; i++) {
          FirmaInfo f = listaFirmas.get(i);

          String app1 = f.getApellido1();
          String app2 = f.getApellido2();
          String nombre = f.getNombre();
          String fecha = f.getFecha();
          String nif = f.getNifcif();

          StringBuffer allBuff = new StringBuffer()
              .append(tprop.getProperty("simple.firmantes.firmante") + "(" + (i + 1) + ") : ");
          allBuff.append(nombre + " " + app1 + " " + app2 + " | ");
          if (nif != null && !nif.contentEquals("")) {
            allBuff.append(tprop.getProperty("simple.firmantes.nif") + ":" + nif + " | ");
          }

          if (pintarFechaFirma) {
            if (StringUtils.isNotEmpty(f.getExtras())) {
              allBuff.append(tprop.getProperty("simple.firmantes.fecha") + " : " + fecha + " | "
                  + tprop.getProperty("simple.firmantes.notas") + " : " + f.getExtras());
            } else {
              allBuff.append(tprop.getProperty("simple.firmantes.fecha") + " : " + fecha);
            }
          } else {
            if (StringUtils.isNotEmpty(f.getExtras())) {
              allBuff.append(tprop.getProperty("simple.firmantes.notas") + " : " + f.getExtras());
            }
          }

          footer.addCell(new Phrase(allBuff.toString(), fontTexto));

        }

      } else {

        fontStyleHeaders =
            FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.UNDERLINE);
        fontTexto = FontFactory.getFont(FontFactory.HELVETICA, ISO_8859_1, 8, Font.BOLD);

        boolean algunNif = algunNif(listaFirmas);

        footer.addCell(createCellFirmantes(tprop.getProperty("complejo.firmantes.firmante"),
            fontStyleHeaders, 1));
        footer.addCell(createCellFirmantes(tprop.getProperty("complejo.firmantes.nombre"),
            fontStyleHeaders, 1));
        footer.addCell(createCellFirmantes(tprop.getProperty("complejo.firmantes.fecha"),
            fontStyleHeaders, 1));
        if (algunNif) {
          footer.addCell(createCellFirmantes(tprop.getProperty("complejo.firmantes.nif"),
              fontStyleHeaders, 1));
        }
        footer.addCell(createCellFirmantes(tprop.getProperty("complejo.firmantes.notas"),
            fontStyleHeaders, 2));

        for (int i = iInicial; i < iFinal; i++) {
          FirmaInfo f = (listaFirmas != null ? listaFirmas.get(i) : null);

          String app1 = f != null ? f.getApellido1() : null;
          String app2 = f != null ? f.getApellido2() : null;
          String nombre = f != null ? f.getNombre() : null;
          String fecha = f != null ? f.getFecha() : null;
          String nif = f != null ? f.getNifcif() : null;

          String extras = "";
          if (f != null && f.getExtras() != null) {
            extras = f.getExtras();
          }

          footer.addCell(createCellFirmantes(
              tprop.getProperty("complejo.firmantes.firmante") + "[" + (i + 1) + "]", fontTexto,
              1));
          footer.addCell(createCellFirmantes(nombre + " " + app1 + " " + app2, fontTexto, 1));
          footer.addCell(createCellFirmantes(fecha, fontTexto, 1));
          if (algunNif) {
            footer.addCell(createCellFirmantes(nif, fontTexto, 1));
          }
          footer.addCell(createCellFirmantes(extras, fontTexto, 2));

        }

      }

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

  }

  /**
   * Crea la celda de la tabla de los firmantes.
   * 
   * @param texto
   * @param font
   * @param colspan
   * @return
   */
  private PdfPCell createCellFirmantes(String texto, Font font, int colspan) {
    PdfPCell cell = new PdfPCell();
    cell.setColspan(colspan);
    cell.addElement(new Phrase(texto, font));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPaddingTop(-0.2f);
    return cell;
  }

  private Image createImageCodeBar(String codigo, PdfContentByte pdfContentByte) {

    Barcode128 code128 = new Barcode128();
    code128.setCode(codigo);
    code128.setStartStopText(true);
    code128.setGenerateChecksum(false);
    code128.setExtended(true);
    code128.setBarHeight(20);

    Image imgCodeBar = code128.createImageWithBarcode(pdfContentByte, Color.BLACK, Color.BLACK);
    imgCodeBar.scalePercent(80);
    return imgCodeBar;
  }

  private PdfPTable createWideTable(int columnas, float width) {
    PdfPTable footer = new PdfPTable(columnas);
    footer.getDefaultCell().setBorderWidth(0);
    configureTable(footer, width);
    return footer;

  }

  private PdfPTable createWideTableRelativeWidths(float[] relativeWidths, float width) {
    PdfPTable footer = new PdfPTable(relativeWidths);
    footer.getDefaultCell().setBorderWidth(0);
    configureTable(footer, width);
    return footer;
  }

  private void configureTable(PdfPTable table, float width) {
    table.setTotalWidth(width - 20);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    table.getDefaultCell().setBorderWidth(0);
  }

  private boolean algunNif(List<FirmaInfo> listFirmaInfo) {

    boolean nifEncontrado = false;

    if (listFirmaInfo != null) {
      int i = 0;
      while (i < listFirmaInfo.size() && !nifEncontrado) {
        if (listFirmaInfo.get(i).getNifcif() != null
            && !listFirmaInfo.get(i).getNifcif().contentEquals("")) {
          nifEncontrado = true;
        } else {
          i++;
        }
      }
    }

    return nifEncontrado;

  }

  private String trataCabecera(String recibido, String defecto) {
    String cabecera = null;
    if (recibido == null || ("").equals(recibido)) {
      cabecera = defecto;
    } else if (recibido.equalsIgnoreCase(EMPTY)) {
      cabecera = "";
    } else {
      cabecera = recibido;
    }
    return cabecera;
  }

  private String tratarParCabeceraValor(String cabecera, String valor) {
    String resultado = "";
    // Si alguno de los dos no es vacío, se escribe la cabecera-valor de el campo.
    if (!("").equalsIgnoreCase(cabecera) && (!("").equalsIgnoreCase(valor) && valor != null)) {
      resultado = cabecera + " : " + valor + " | ";
    } else {
      if (!("").equalsIgnoreCase(cabecera)) {
        resultado = cabecera + " | ";
      }
      if (!("").equalsIgnoreCase(valor) && valor != null) {
        resultado = valor + " | ";
      }
    }
    return resultado;
  }

  private Paragraph parrafoURL(String url, Font font) {
    Paragraph p = new Paragraph();
    Anchor anchor = new Anchor(url, font);
    anchor.setReference(url);
    p.add(anchor);
    return p;
  }

  private String tratarURL(String valor) {
    String resultado = "";
    if (!valor.contentEquals(EMPTY)) {
      resultado = valor;
    }
    return resultado;
  }

  private PdfPCell createCodeBarCell(Image imgCodeBar) {
    PdfPCell cell2 = new PdfPCell(imgCodeBar);
    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell2.setColspan(3);
    cell2.setBorderWidth(0);
    return cell2;

  }

  /**
   * Metodo que devuelve el qr que se ha generado en imagen, se hace asi para que solo vaya al rest
   * la primera vez.
   * 
   * @param url
   * @param pdfContentByte
   * @return
   * @throws EeutilException
   */
  private Image pintarQR(String url, CopiaInfo copia) throws EeutilException {


    if (clientSecureRestRun.isEjecutarRUN(url)) {
      String shortURLQR = clientSecureRestRun.getURLCortaRUN(url);
      url = shortURLQR;
      copia.setUrlQR(shortURLQR);
    }

    // PdfDocument pdfDocument = null;
    try {
      byte[] qr = createQR(url, 8, 8);
      float percent = 65.0f;

      // PdfDocument pdfDocument = stamp.getOverContent(numPag).getPdfDocument();
      // pdfDocument = pdfContentByte.getPdfDocument();

      // Si lo queremos mas a la derecha disminuimos el segundo n�mero.
      float pos_x = 5.0f;
      // Si lo queremos mas abajo aumentamos el segundo n�mero.
      float pos_y = 18.0f;


      Image imagenQr = getImagenQr(qr, percent, pos_x, pos_y);

      // stamp.getOverContent(numPag).addImage(imagenLogo);
      // pdfContentByte.addImage(imagenQr);

      return imagenQr;

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

  }

  private Image getImagenQr(byte[] qr, float percent, float pos_x, float pos_y)
      throws IOException, BadElementException {
    Image img = Image.getInstance(qr);
    img.scalePercent(percent);
    img.setAbsolutePosition(pos_x, pos_y);
    return img;
  }

  private byte[] createQR(String url, int width, int height) throws EeutilException {
    // String sUrl = "https://portafirma.seap.minhap.es";

    byte[] salida = null;
    try (ByteArrayOutputStream out =
        QRCode.from(url).to(ImageType.JPG).withSize(width, height).stream();) {
      // salida= Arrays.copyOf(out.toByteArray(),out.toByteArray().length);
      salida = out.toByteArray();
    } catch (IOException e) {
      // logger.error("Error al crear el QR. URL:" + url);
      throw new EeutilException("Error al crear el QR. URL:" + url + " " + e.getMessage(), e);
    }
    return salida;
  }

  private float calculeWidth(PdfReader reader, int pageNumber, PdfWriter writer)
      throws EeutilException {
    float retorno = 0;

    // TODO:

    Rectangle page = reader.getPageSizeWithRotation(pageNumber);
    int rotation = page.getRotation();

    // PdfImportedPage importPage = writer.getImportedPage(reader, pageNumber);
    // int rotation = importPage.getRotation();
    switch (rotation) {
      case 90: {
        retorno = reader.getPageSizeWithRotation(pageNumber).getWidth() - 50;
        break;
      }
      case 180: {
        retorno = reader.getPageSize(pageNumber).getWidth() - 50;
        break;
      }
      case 270: {
        retorno = reader.getPageSizeWithRotation(pageNumber).getWidth() - 50;
        break;
      }
      default: {
        retorno = reader.getPageSize(pageNumber).getWidth() - 50;
        break;
      }
    }
    return retorno;
  }


  private String rellenarStringConBlancos(String lateral, int tamano) {
    if (lateral != null) {
      if (lateral.length() < tamano) {
        int caracteresEscribir = tamano - lateral.length();

        for (int i = 0; i < caracteresEscribir; i++) {
          lateral += " ";
        }


      }
    }

    return lateral;
  }

  public void generateURLRunSede(final CopiaInfo copia) throws EeutilException {

    if (copia != null && !"".equals(copia.getUrlSede())) {
      if (clientSecureRestRun.isEjecutarUrlNoQrRUN(copia.getUrlSede())) {
        String shortURLQR = clientSecureRestRun.getURLCortaRUN(copia.getUrlSede());
        copia.setUrlSede(shortURLQR);
      }
    }

  }

  // public static void main(String args[]) throws IOException, EeutilException
  // {
  //
  // PdfReader reader = new PdfReader("D:/Descargas/temporal.pdf");
  //
  // FileOutputStream fos = new FileOutputStream("D:/Descargas/salida.pdf");
  //
  // PdfStamper stamp = new PdfStamper(reader,fos);
  //
  // try
  // {
  //
  //
  //
  // int numPags = reader.getNumberOfPages();
  //
  // int i = 1;
  //
  // while (i <= numPags) {
  //
  // PdfContentByte over = stamp.getOverContent(i);
  // new PdfCopiaAutenticaUtils().pintarQR("http://gooogle.es", over);
  // new PdfCopiaAutenticaUtils().escribirLateral("HOLA QUE TAL TODO
  // dsfljdfjlldsfjjlsdflsjfdlsdljfjsdfjosdlf", reader.getPageSizeWithRotation(i).getHeight(), over,
  // reader.getPageSizeWithRotation(i).getWidth());
  // i++;
  //
  // }
  //
  // }
  // finally
  // {
  // if(stamp != null)
  // stamp.close();
  // if(fos != null)
  // fos.close();
  // if(reader != null)
  // reader.close();
  // }
  //
  //
  // }

}
