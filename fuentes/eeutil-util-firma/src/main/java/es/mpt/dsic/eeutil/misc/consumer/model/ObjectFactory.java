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


package es.mpt.dsic.eeutil.misc.consumer.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the es.mpt.dsic.eeutil.misc.consumer.model package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  private static final String HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES =
      "http://service.ws.inside.dsic.mpt.es/";
  private static final QName _ConvertirTCNAPdfResponse_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "convertirTCNAPdfResponse");
  private static final QName _VisualizarFacturaeResponse_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "visualizarFacturaeResponse");
  private static final QName _ConvertirTCNAPdf_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "convertirTCNAPdf");
  private static final QName _VisualizarFacturae_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "visualizarFacturae");
  private static final QName _ComprobarPDFAResponse_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "comprobarPDFAResponse");
  private static final QName _ComprobarPDFA_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "comprobarPDFA");
  private static final QName _PostProcesarFirma_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "postProcesarFirma");
  private static final QName _ConvertirPDFAResponse_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "convertirPDFAResponse");
  private static final QName _PostProcesarFirmaResponse_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "postProcesarFirmaResponse");
  private static final QName _ConvertirPDFA_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "convertirPDFA");
  private static final QName _ErrorTest_QNAME =
      new QName(HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, "ErrorTest");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: es.mpt.dsic.eeutil.misc.consumer.model
   * 
   */
  public ObjectFactory() {

    // do nothing.
  }

  /**
   * Create an instance of {@link PostProcesarFirma }
   * 
   */
  public PostProcesarFirma createPostProcesarFirma() {
    return new PostProcesarFirma();
  }

  /**
   * Create an instance of {@link PostProcesarFirmaResponse }
   * 
   */
  public PostProcesarFirmaResponse createPostProcesarFirmaResponse() {
    return new PostProcesarFirmaResponse();
  }

  /**
   * Create an instance of {@link ConvertirPDFAResponse }
   * 
   */
  public ConvertirPDFAResponse createConvertirPDFAResponse() {
    return new ConvertirPDFAResponse();
  }

  /**
   * Create an instance of {@link EstadoInfo }
   * 
   */
  public EstadoInfo createEstadoInfo() {
    return new EstadoInfo();
  }

  /**
   * Create an instance of {@link ConvertirPDFA }
   * 
   */
  public ConvertirPDFA createConvertirPDFA() {
    return new ConvertirPDFA();
  }

  /**
   * Create an instance of {@link VisualizarFacturae }
   * 
   */
  public VisualizarFacturae createVisualizarFacturae() {
    return new VisualizarFacturae();
  }

  /**
   * Create an instance of {@link ConvertirTCNAPdf }
   * 
   */
  public ConvertirTCNAPdf createConvertirTCNAPdf() {
    return new ConvertirTCNAPdf();
  }

  /**
   * Create an instance of {@link ComprobarPDFAResponse }
   * 
   */
  public ComprobarPDFAResponse createComprobarPDFAResponse() {
    return new ComprobarPDFAResponse();
  }

  /**
   * Create an instance of {@link ComprobarPDFA }
   * 
   */
  public ComprobarPDFA createComprobarPDFA() {
    return new ComprobarPDFA();
  }

  /**
   * Create an instance of {@link ConvertirTCNAPdfResponse }
   * 
   */
  public ConvertirTCNAPdfResponse createConvertirTCNAPdfResponse() {
    return new ConvertirTCNAPdfResponse();
  }

  /**
   * Create an instance of {@link VisualizarFacturaeResponse }
   * 
   */
  public VisualizarFacturaeResponse createVisualizarFacturaeResponse() {
    return new VisualizarFacturaeResponse();
  }

  /**
   * Create an instance of {@link ApplicationLogin }
   * 
   */
  public ApplicationLogin createApplicationLogin() {
    return new ApplicationLogin();
  }

  /**
   * Create an instance of {@link PdfSalida }
   * 
   */
  public PdfSalida createPdfSalida() {
    return new PdfSalida();
  }

  /**
   * Create an instance of {@link DocumentoEntrada }
   * 
   */
  public DocumentoEntrada createDocumentoEntrada() {
    return new DocumentoEntrada();
  }

  /**
   * Create an instance of {@link DocumentoContenido }
   * 
   */
  public DocumentoContenido createDocumentoContenido() {
    return new DocumentoContenido();
  }

  /**
   * Create an instance of {@link ContenidoInfo }
   * 
   */
  public ContenidoInfo createContenidoInfo() {
    return new ContenidoInfo();
  }

  /**
   * Create an instance of {@link SalidaVisualizacion }
   * 
   */
  public SalidaVisualizacion createSalidaVisualizacion() {
    return new SalidaVisualizacion();
  }

  /**
   * Create an instance of {@link TCNInfo }
   * 
   */
  public TCNInfo createTCNInfo() {
    return new TCNInfo();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirTCNAPdfResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "convertirTCNAPdfResponse")
  public JAXBElement<ConvertirTCNAPdfResponse> createConvertirTCNAPdfResponse(
      ConvertirTCNAPdfResponse value) {
    return new JAXBElement<>(_ConvertirTCNAPdfResponse_QNAME, ConvertirTCNAPdfResponse.class, null,
        value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link VisualizarFacturaeResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES,
      name = "visualizarFacturaeResponse")
  public JAXBElement<VisualizarFacturaeResponse> createVisualizarFacturaeResponse(
      VisualizarFacturaeResponse value) {
    return new JAXBElement<>(_VisualizarFacturaeResponse_QNAME, VisualizarFacturaeResponse.class,
        null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirTCNAPdf }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "convertirTCNAPdf")
  public JAXBElement<ConvertirTCNAPdf> createConvertirTCNAPdf(ConvertirTCNAPdf value) {
    return new JAXBElement<>(_ConvertirTCNAPdf_QNAME, ConvertirTCNAPdf.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link VisualizarFacturae }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "visualizarFacturae")
  public JAXBElement<VisualizarFacturae> createVisualizarFacturae(VisualizarFacturae value) {
    return new JAXBElement<>(_VisualizarFacturae_QNAME, VisualizarFacturae.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarPDFAResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "comprobarPDFAResponse")
  public JAXBElement<ComprobarPDFAResponse> createComprobarPDFAResponse(
      ComprobarPDFAResponse value) {
    return new JAXBElement<>(_ComprobarPDFAResponse_QNAME, ComprobarPDFAResponse.class, null,
        value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarPDFA }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "comprobarPDFA")
  public JAXBElement<ComprobarPDFA> createComprobarPDFA(ComprobarPDFA value) {
    return new JAXBElement<>(_ComprobarPDFA_QNAME, ComprobarPDFA.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link PostProcesarFirma }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "postProcesarFirma")
  public JAXBElement<PostProcesarFirma> createPostProcesarFirma(PostProcesarFirma value) {
    return new JAXBElement<>(_PostProcesarFirma_QNAME, PostProcesarFirma.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirPDFAResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "convertirPDFAResponse")
  public JAXBElement<ConvertirPDFAResponse> createConvertirPDFAResponse(
      ConvertirPDFAResponse value) {
    return new JAXBElement<>(_ConvertirPDFAResponse_QNAME, ConvertirPDFAResponse.class, null,
        value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link PostProcesarFirmaResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES,
      name = "postProcesarFirmaResponse")
  public JAXBElement<PostProcesarFirmaResponse> createPostProcesarFirmaResponse(
      PostProcesarFirmaResponse value) {
    return new JAXBElement<>(_PostProcesarFirmaResponse_QNAME, PostProcesarFirmaResponse.class,
        null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirPDFA }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "convertirPDFA")
  public JAXBElement<ConvertirPDFA> createConvertirPDFA(ConvertirPDFA value) {
    return new JAXBElement<>(_ConvertirPDFA_QNAME, ConvertirPDFA.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link EstadoInfo }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = HTTP_SERVICE_WS_INSIDE_DSIC_MPT_ES, name = "ErrorTest")
  public JAXBElement<EstadoInfo> createErrorTest(EstadoInfo value) {
    return new JAXBElement<>(_ErrorTest_QNAME, EstadoInfo.class, null, value);
  }

}
