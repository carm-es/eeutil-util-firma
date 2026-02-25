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

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import es.mpt.dsic.inside.aop.AuditExternalServiceAnnotation;
import es.mpt.dsic.inside.configure.ConfigureRestInfo;
import es.mpt.dsic.inside.utils.exception.EeutilException;

/**
 * Clase del cliente para acceso a los servicios de run. xxx = entorno. POST
 * https://xxx-run-api.redsara.es/api/login_check ==>Existe un acceso post para la autenticacion y
 * obtencion del bearer POST https://xxx-run-api.redsara.es/api/v1/url ==>Existe un acceso para el
 * alta de la url por parte de run GET https://xxx-run.redsara.es/ID_BINARIO ==>Existe un acceso
 * para la consulta del identificador para la obtencion de url
 * 
 * @author mamoralf
 *
 */
@Service("clientSecureRestRun")
public class ClientSecureRestRun {

  protected static final Log logger = LogFactory.getLog(ClientSecureRestRun.class);

  @Autowired
  private RandomStringGeneratorComponent randomStringGeneratorComponent;

  private static final String TOKEN_NAME_RESPONSE = "token";

  /**
   * 
   * Obtenemos el token ya sea generando uno nuevo o reutilizando uno que sigue vigente.
   * 
   */


  private String obtenerJWTRun() throws EeutilException {
    // nos traemos el jwt del singleton
    String jwtGestionado = JwtTokenRunAlmacenador.getInstance().getJwtRun();

    if (jwtGestionado == null || esNecesarioTraerNuevoJwt(jwtGestionado)) {
      JwtTokenRunAlmacenador.getInstance().setJwtRun(getNewToken());
    }

    return JwtTokenRunAlmacenador.getInstance().getJwtRun();

  }

  /**
   * Control de parametros de la app a traves de los parametros
   * 
   * #true => llamar a run, false=> no llamar a run run.active=true #run.active = true && size = 0
   * (se llama a run para cualquier tamano de url) #run.active = true && size >= 0 (se llama a run
   * para tamano >= X) run.active.size=0
   */
  public boolean isEjecutarRUN(String url) {
    Boolean isActive = ConfigureRestInfo.isActiveRun();

    if (isActive) {
      Integer tamanoRun = ConfigureRestInfo.getActiveRunSize();

      if (tamanoRun == 0 || tamanoRun <= url.length()) {
        return true;
      } else {
        return false;
      }

    }

    return false;
  }


  /**
   * Se puede ejecutar run para url que no sean la de qr (se comprueba si esta activo y si el tamano
   * es distinto de 0 y mayor al limite de la propiedad
   * 
   * @param url
   * @return
   */
  public boolean isEjecutarUrlNoQrRUN(String url) {
    Boolean isActive = ConfigureRestInfo.isActiveRun();

    if (isActive) {
      Integer tamanoRun = ConfigureRestInfo.getActiveUrlNoQrSize();

      if (tamanoRun == 0 || tamanoRun <= url.length()) {
        return true;
      } else {
        return false;
      }

    }

    return false;
  }

  /***
   * Permite obtener la urlCorta de run
   * 
   * @param urlQR
   * @return Devuelve la url corta de run, si no es capaz devuelve la url original
   */

  public String getURLCortaRUN(String urlOriginal) {
    String shortURLQR = null;

    try {

      String token = obtenerJWTRun();

      CloseableHttpClient httpClient = null;
      CloseableHttpResponse response = null;


      try {
        response = getResponseClientRunPeticionSecure(httpClient,
            ConfigureRestInfo.getBaseUrlSecureRun() + "v1/url", token, urlOriginal);
        shortURLQR = gestionarResponsePeticion(token, response, response.getEntity());

      } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
          | IOException e) {
        throw new EeutilException(e.getMessage(), e);
      } catch (EeutilException e) {
        if (e.getCOD_ERROR_RUN() != null && "100".equals(e.getCOD_ERROR_RUN())) {
          try {
            // hacemos un reintento para generar de nuevo una clave y procesarlo, si falla lanzamos
            // la excepcion para arriba, ya no la capturamos mas
            response = getResponseClientRunPeticionSecure(httpClient,
                ConfigureRestInfo.getBaseUrlSecureRun() + "v1/url", token, urlOriginal);
            shortURLQR = gestionarResponsePeticion(token, response, response.getEntity());
          } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
              | IOException e2) {
            throw new EeutilException(e2.getMessage(), e2);
          }
        } else {
          throw new EeutilException(e.getMessage(), e);
        }
      } finally {
        cerrarRequestResponse(httpClient, response);
      }

    } catch (EeutilException e) {
      logger.error("Error al convertir url de run, recogemos el url original " + e.getMessage(), e);
      shortURLQR = urlOriginal;
    } catch (Exception e) {
      logger.error("Error al convertir url de run, recogemos el url original " + e.getMessage(), e);
      shortURLQR = urlOriginal;
    }


    return shortURLQR;
  }

  private String getNewToken() throws EeutilException {

    String token = null;

    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;

    try {

      // System.setProperty("javax.net.ssl.trustStore",
      // "C:/d/proyectos/EEUTIL/java/workspace_EEUtils_repo_git/config_jetty/local/keystore/minhap.jks");
      // System.setProperty("javax.net.ssl.trustStorePassword", "password");

      try {
        response = getResponseClientRunAuth(httpClient,
            ConfigureRestInfo.getBaseUrlSecureRun() + "login_check",
            ConfigureRestInfo.getRunUsername(), ConfigureRestInfo.getRunPassword());
      } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
          | IOException e) {
        throw new EeutilException(e.getMessage(), e);
      }
      HttpEntity entityResponse = response.getEntity();

      token = gestionarResponseAuth(token, response, entityResponse);

      return token;

    } catch (Exception e) {
      throw new EeutilException(e.getMessage(), e);
    }

    finally {
      cerrarRequestResponse(httpClient, response);
    }
  }

  private String gestionarResponseAuth(String token, CloseableHttpResponse response,
      HttpEntity entityResponse) throws EeutilException {
    if (response.getStatusLine().getStatusCode() != 200) {
      throw new HttpClientErrorException(
          HttpStatus.valueOf(response.getStatusLine().getStatusCode()),
          response.getStatusLine().getReasonPhrase());

    } else if (response.getStatusLine().getStatusCode() == 200) {
      String resultado = null;
      if (entityResponse != null) {

        JSONObject jsonObject = null;
        try {
          resultado = EntityUtils.toString(entityResponse);
          jsonObject = (JSONObject) new JSONParser().parse(resultado);

        } catch (IOException | ParseException e) {
          throw new EeutilException(e.getMessage(), e);
        } catch (Exception e) {
          throw new EeutilException(e.getMessage(), e);
        }
        JSONObject data = (JSONObject) jsonObject.get("data");
        token = (String) data.get(TOKEN_NAME_RESPONSE);

      } else {
        throw new HttpClientErrorException(HttpStatus.OK,
            "No se ha podido devolver el token correctamente");
      }

    }
    return token;
  }

  @AuditExternalServiceAnnotation(nombreModulo = "eeutil-util-firma")
  private CloseableHttpResponse getResponseClientRunAuth(CloseableHttpClient httpClient, String url,
      String username, String password)
      throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

    String json = null;

    // SSLContext sslContext =
    // new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
    // httpClient = HttpClients.custom().setSSLContext(sslContext)
    // .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

    org.apache.http.ssl.SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();
    sslContextBuilder.loadTrustMaterial(new org.apache.http.conn.ssl.TrustSelfSignedStrategy());
    SSLContext sslContext = sslContextBuilder.build();
    org.apache.http.conn.ssl.SSLConnectionSocketFactory sslSocketFactory =
        new SSLConnectionSocketFactory(sslContext,
            new org.apache.http.conn.ssl.DefaultHostnameVerifier());

    HttpClientBuilder httpClientBuilder =
        HttpClients.custom().setSSLSocketFactory(sslSocketFactory);
    httpClient = httpClientBuilder.build();

    // httpClient= HttpClients.createDefault();

    HttpPost httpPost = new HttpPost(url);

    json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

    // StringEntity entity = new StringEntity(json,StandardCharsets.UTF_8);
    StringEntity entity = new StringEntity(json);

    // HttpEntity entity = MultipartEntityBuilder
    // .create()
    // .setContentType(ContentType.MULTIPART_FORM_DATA)
    // .addTextBody("username", "RICARDO")
    // .addTextBody("password", "RICARDO")
    // .build();

    httpPost.setEntity(entity);

    httpPost.setHeader("Content-type", "application/json");
    httpPost.setHeader("Accept", "*/*");
    httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
    httpPost.setHeader("Connection", "keep-alive");

    // httpPost.addHeader(tokenNameIgae,tokenValueIgae);

    return httpClient.execute(httpPost);

  }


  private static void cerrarRequestResponse(CloseableHttpClient httpClient,
      CloseableHttpResponse response) throws EeutilException {

    try {


      if (response != null) {
        response.close();
      }
      if (httpClient != null) {
        httpClient.close();
      }

    } catch (IOException e) {
      throw new EeutilException(e.getMessage(), e);
    }
  }

  private Long obtenerExpiracionToken(String token) throws EeutilException {

    // del token cogemos los caracteres hasta el punto( no inclusive)

    String[] aParteToken = token.split(Pattern.quote("."));

    String parteToken = aParteToken[1];

    // hacemos un decode
    String json = new String(Base64.decodeBase64(parteToken));

    // lo convertimos en un objeto json
    JSONObject jsonObject;
    try {
      jsonObject = (JSONObject) new JSONParser().parse(json);
    } catch (ParseException e) {
      throw new EeutilException(e.getMessage(), e);
    }

    // cogemos los campos iat y exp. (solo exp=expiracion) entre iat y exp hemos
    // visto que hay 1 hora.
    // aLong[0]=Long.valueOf(jsonObject.get("iat").toString());
    return Long.valueOf(jsonObject.get("exp").toString());

  }

  /**
   * Metodo que comprueba que el token esta en el rango de uso (hasta 5 minutos de la expiracion de
   * este), s
   * 
   * @return true Si es necesario traer y sobreescribir un nuevo token false Si no es necesario
   * @throws EeutilException
   */
  private boolean esNecesarioTraerNuevoJwt(String token) throws EeutilException {

    // el tiempo de expiracion del token esta en segundos, convertirlo a ms.
    Long tiempoExpiracionToken = obtenerExpiracionToken(token) * 1000;

    Long tiempoActual = System.currentTimeMillis();

    // si el tiempo actual es mayor a 5 minutos antes de que termine la expiracion
    // del token.
    return (tiempoActual >= (tiempoExpiracionToken - 300000)) ? true : false;
  }



  @AuditExternalServiceAnnotation(nombreModulo = "eeutil-util-firma")
  private CloseableHttpResponse getResponseClientRunPeticionSecure(CloseableHttpClient httpClient,
      String url, String token, String urlQR)
      throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

    String json = null;

    // SSLContext sslContext =
    // new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
    // httpClient = HttpClients.custom().setSSLContext(sslContext)
    // .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

    org.apache.http.ssl.SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();
    sslContextBuilder.loadTrustMaterial(new org.apache.http.conn.ssl.TrustSelfSignedStrategy());
    SSLContext sslContext = sslContextBuilder.build();
    org.apache.http.conn.ssl.SSLConnectionSocketFactory sslSocketFactory =
        new SSLConnectionSocketFactory(sslContext,
            new org.apache.http.conn.ssl.DefaultHostnameVerifier());

    HttpClientBuilder httpClientBuilder =
        HttpClients.custom().setSSLSocketFactory(sslSocketFactory);
    httpClient = httpClientBuilder.build();

    // httpClient= HttpClients.createDefault();

    HttpPost httpPost = new HttpPost(url);

    // List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    // nvps.add(new BasicNameValuePair("urls", "[{\"url\":\""+urlQR+"\"}]"));

    // genemos nosotros la key que queremos guardar, 7 caracteres.
    String shortUrlKey = randomStringGeneratorComponent.generarCadenaAleatoriaOptimized();
    // String shortUrlKey="ipNJTfcV";

    json = "[{\"url\":\"" + urlQR + "\",\"surl\":\"" + shortUrlKey + "\"}]";

    StringEntity entity = new StringEntity(json);

    httpPost.setEntity(entity);
    // httpPost.setEntity(new UrlEncodedFormEntity(nvps));


    httpPost.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
    httpPost.setHeader("Accept", "*/*");
    httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
    httpPost.setHeader("Connection", "keep-alive");
    httpPost.setHeader("Authorization", "Bearer " + token);

    // httpPost.addHeader(tokenNameIgae,tokenValueIgae);

    return httpClient.execute(httpPost);

  }


  private String gestionarResponsePeticion(String token, CloseableHttpResponse response,
      HttpEntity entityResponse) throws EeutilException {
    String surl = null;
    if (response.getStatusLine().getStatusCode() != 200) {
      throw new HttpClientErrorException(
          HttpStatus.valueOf(response.getStatusLine().getStatusCode()),
          response.getStatusLine().getReasonPhrase());

    } else if (response.getStatusLine().getStatusCode() == 200) {
      String resultado = null;
      if (entityResponse != null) {

        JSONObject jsonObject = null;
        try {
          resultado = EntityUtils.toString(entityResponse);
          jsonObject = (JSONObject) new JSONParser().parse(resultado);

        } catch (IOException | ParseException e) {
          throw new EeutilException(e.getMessage(), e);
        } catch (Exception e) {
          throw new EeutilException(e.getMessage(), e);
        }

        JSONObject jsonObje = (JSONObject) jsonObject.get("data");
        JSONArray jsonAnonShortenedExistentUrls =
            (JSONArray) jsonObje.get("nonShortenedExistentUrls");
        JSONArray jsonAshortenedExistentUrls = (JSONArray) jsonObje.get("shortenedUrls");
        JSONArray jsonNonShortenedUrl = (JSONArray) jsonObje.get("nonShortenedUrl");

        // GESTION DE ERRORES
        // si hay un error porque existe la clave que queremos introducir
        if (jsonNonShortenedUrl != null && !jsonNonShortenedUrl.isEmpty()
            && ((JSONObject) jsonNonShortenedUrl.get(0)).get("error") != null) {

          JSONObject strjsonNonShortenedUrl = ((JSONObject) jsonNonShortenedUrl.get(0));

          throw new EeutilException("100",
              "Clave de run duplicada. " + strjsonNonShortenedUrl.get("error"), true);
        }
        // si hay un error (preguntar a RUN que tipo de error
        else if (jsonAnonShortenedExistentUrls != null && !jsonAnonShortenedExistentUrls.isEmpty()
            && ((JSONObject) jsonAnonShortenedExistentUrls.get(0)).get("error") != null) {

          JSONObject strjsonNonShortenedUrl = ((JSONObject) jsonNonShortenedUrl.get(0));

          throw new EeutilException("101",
              "Error no especificado." + strjsonNonShortenedUrl.get("error"), true);
        }


        // siguientes ejecuciones
        else if (jsonAnonShortenedExistentUrls != null
            && !jsonAnonShortenedExistentUrls.isEmpty()) {
          JSONObject jsonO = (JSONObject) jsonAnonShortenedExistentUrls.get(0);
          surl = (String) jsonO.get("sUrl");
        }
        // primera ejecucion
        else if (jsonAshortenedExistentUrls != null && !jsonAshortenedExistentUrls.isEmpty()) {

          JSONObject jsonO = (JSONObject) jsonAshortenedExistentUrls.get(0);
          surl = (String) jsonO.get("sUrl");
        }
        if (surl == null || "".equals(surl)) {
          throw new EeutilException(
              "No se ha podido obtener el link corto del sistema RUN, su valor es vacio");
        }


      } else {
        throw new HttpClientErrorException(HttpStatus.OK,
            "No se ha podido devolver el token correctamente");
      }

    }
    return ConfigureRestInfo.getBaseUrlOriginalSecureRun() + surl;
  }



  /*
   * public static void main(String[] args) throws EeutilException, IOException,
   * KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SAXException,
   * ParseException {
   * 
   * String token =
   * "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJpYXQiOjE2NTk1MjQ0NzAsImV4cCI6MTY1OTUyODA3MCwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJuYW1lIjoiUklDQVJETyJ9.Dl0H2AH0pTzx_bwrkMfXcvnjfWHUSXf1IvBhDYaywhML2vk0uns1oYt1lhuHHsNjMEqa5fn1ssQbCnbVPj62AuqbZQO8Re2uldd16eSqSNk3cGglxiFKzQfpJE9LUVlgw9DSiQMxCJVrA8ybt1ewYevnpifQjjR5ONTmwPCBLuU9k6wjC25Uty0Huogj05dInXSJSMWLJgwC4GfzEhIibu3QWcArs7H9BFxc_H04dNTnJv7Fpg-L3fRkf6rX-DucL4eMJfaV_q2RiGaRMkxSMRxW48RkJWR3pPyY0twbKpMljxREv3yIjyYx3fcMJ6KXYbBUIakU1mIz8IKny3JOhPOfUp1sCGEWsP_pOA0w76vw2a0DLqNM_1AthS8h04aM1_Vb0rqOeZrb_ad1CJkLCXsX1c_2RUr9Yg43azKTqFAzQ891-HeT0USCGgKadzDubkKeLJLg2ZqUwZHmn9a3PQBD6Gm7mG2jNjHTLNJQTpyOOW5RAdHt8w89RQBCZoO-ZMA9QBSW8eXtYQLDtoGnx54y3exBfGXqG2z9uf2nCFoNeRdJtaXOYO_za3LBSH7pHqB7LsOuQK91V5HcizMv6Ty-MeqIvoHY87kP4QdN815J76wWeokHYvrpjkWyQPnI_OEBw3kmzbass76baat74QrHDGiZ281krxv5Px5QlsM";
   * // long millisExpirationToken = new ClientSecureRestRun().obtenerExpiracionToken(token);
   * 
   * CloseableHttpClient httpClient = null; CloseableHttpResponse response = null;
   * 
   * 
   * response= new ClientSecureRestRun().getResponseClientRunPeticionSecure(httpClient,
   * "https://des-run-api.redsara.es/api/v1/url", token, "http://ggogle.eess"); new
   * ClientSecureRestRun().gestionarResponsePeticion(token, response, response.getEntity());
   * cerrarRequestResponse(httpClient, response);
   * 
   * }
   */
}
