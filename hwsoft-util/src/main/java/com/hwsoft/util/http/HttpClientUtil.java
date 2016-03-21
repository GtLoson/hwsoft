package com.hwsoft.util.http;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {

  private static Log logger = LogFactory.getLog(HttpClientUtil.class);

  /**
   * 提交http请求，并返回相关数据
   *
   * @param uri
   * @param requestType
   * @param requestMethod
   * @param paramMap
   * @return
   */
  public static String submitWithFormOrUriParam(final String uri, final RequestType requestType, final RequestMethod requestMethod, final ResponseFormat responseFormat, Map<String, String> paramMap) {
    HttpUriRequest httpUriRequest = null;
    try {
      httpUriRequest = _configHttpUriRequest(requestMethod, paramMap, uri);
      return _requestHttpClient(uri, requestType, responseFormat, httpUriRequest);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 提价http请求， body context
   *
   * @param uri
   * @param requestType
   * @param paramMap
   * @param bodyContext
   * @return
   */
  public static String submitWithBodyContext(final String uri, final RequestType requestType, final ResponseFormat responseFormat, final Map<String, String> paramMap, final String bodyContext) {
    HttpUriRequest httpUriRequest = _configHttpUriRequestWithBodyContext(paramMap, bodyContext, uri);
    return _requestHttpClient(uri, requestType, responseFormat, httpUriRequest);
  }

  /*
   * 封装http client 并请求数据
   */
  private static String _requestHttpClient(String uri, RequestType requestType, ResponseFormat responseFormat, HttpUriRequest httpUriRequest) {
    HttpClient httpClient = _configHttpClient(requestType);
    if (httpClient != null) {
      String response = _executeRequest(httpClient, httpUriRequest, uri);
      return response;
    }
    logger.warn("can't return anything, may be client is null");
    return null;
  }

  /*
   * 执行http请求
   */
  private static String _executeRequest(HttpClient httpClient, HttpUriRequest httpUriRequest, final String uri) {
    try {
      String response = httpClient.execute(httpUriRequest, new ResponseHandler<String>() {
        @Override
        public String handleResponse(HttpResponse response)
            throws IOException {
          HttpEntity entity = response.getEntity();
          if (entity == null) {
            logger.debug("request from " + uri + ", but not found return entity");
            return null;
          }
          String charSet = EntityUtils.getContentCharSet(entity);
          String result = new String(EntityUtils.toByteArray(entity),
              charSet == null ? "UTF-8" : charSet);
          EntityUtils.consume(entity);
          if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            logger.warn("request from " + uri + ", and found return entity, but status is " + response.getStatusLine().getStatusCode());
            return null;
          }
          return result;
        }

      });
      return response;
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
    //  httpClient.getConnectionManager().shutdown();
      ((HttpRequestBase)httpUriRequest).releaseConnection();
    }
    return null;
  }

  /*
   * config http request client
   */
  private static HttpUriRequest _configHttpUriRequest(final RequestMethod requestMethod, final Map<String, String> paramMap, final String uri) throws UnsupportedEncodingException {
    List<ParamNameValuePair> ps = new ArrayList<ParamNameValuePair>();
    StringBuffer getMethodUri = new StringBuffer(uri);
    if (paramMap != null && !paramMap.isEmpty()) {
      getMethodUri.append(uri.endsWith("?") ? "" : "" + "?");
      Set<String> keys = paramMap.keySet();
      List<String> keyList = Lists.newArrayList(keys);
      for (int i = 0; i < keyList.size(); i++) {
        String key = keyList.get(i);
        if (paramMap.get(key) != null && !"".equals(paramMap.get(key))) {
          if (requestMethod == RequestMethod.GET) {
            if (i > 0) {
              getMethodUri.append("&" + key + "=" + paramMap.get(key));
            } else {
              getMethodUri.append(key + "=" + paramMap.get(key));
            }
          } else {
            HttpClientUtil hcu = new HttpClientUtil();
            ps.add(hcu.new ParamNameValuePair(key.trim(), paramMap.get(key)
                .trim()));
          }
        }
      }
    }
    if (RequestMethod.GET.equals(requestMethod)) {
      logger.info("请求地址：" + getMethodUri.toString());
      HttpGet httpRequest = new HttpGet(getMethodUri.toString());
      return httpRequest;
    } else {
      HttpEntity entity = new UrlEncodedFormEntity(ps, "UTF-8");
      HttpPost request = new HttpPost(uri);
      request.setEntity(entity);
      return request;
    }
  }

  /*
   * config http client for body context
   */
  private static HttpUriRequest _configHttpUriRequestWithBodyContext(final Map<String, String> paramMap, final String bodyContext, final String uri) {
    List<ParamNameValuePair> ps = new ArrayList<ParamNameValuePair>();
    StringBuffer getMethodUri = new StringBuffer(uri);
    if (paramMap != null && !paramMap.isEmpty()) {
      // getMethodUri.append(uri.endsWith("?") ? uri : uri + "?");
      for (String key : paramMap.keySet()) {
        if (paramMap.get(key) != null && !"".equals(paramMap.get(key))) {
          HttpClientUtil hcu = new HttpClientUtil();
          ps.add(hcu.new ParamNameValuePair(key.trim(), paramMap.get(key)
              .trim()));
          // getMethodUri.append("&" + key + "=" + paramMap.get(key));
        }
      }
    }
    HttpEntity entity = new StringEntity(bodyContext, "UTF-8");
    HttpPost request = new HttpPost(URLEncoder.encode(getMethodUri.toString()));
    request.setEntity(entity);
    return request;
  }

  /*
   * 获取http client
   */
  private static HttpClient _configHttpClient(final RequestType requestType) {
    HttpClient httpClient = null;
    try {
      if (RequestType.HTTP.equals(requestType)) {
        httpClient = HttpConnectionManager.getHttpClient();
      } else {
        httpClient = HttpConnectionManager.getHttpClientWithSSL(22);
      }
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return httpClient;
  }

  /**
   * HTTP Connection Mananger
   */
  @SuppressWarnings("deprecation")
  static class HttpConnectionManager {

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 1000;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 2000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 100;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 2000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 2000;

    /**
     * 多线程管理
     */
    private static PoolingClientConnectionManager poolingClientConnectionManager;

    /**
     * 连接参数
     */
    private static HttpParams httpClientParams;

    static {
      poolingClientConnectionManager = new PoolingClientConnectionManager();
      poolingClientConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
      poolingClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
      httpClientParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(httpClientParams,
          CONNECT_TIMEOUT);
      HttpConnectionParams.setSoTimeout(httpClientParams, WAIT_TIMEOUT);
    }

    private static X509TrustManager tm = new X509TrustManager() {
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkClientTrusted(
          java.security.cert.X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }

      @Override
      public void checkServerTrusted(
          java.security.cert.X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }
    };

    /**
     * 获取默认http client
     *
     * @return
     */
    public static HttpClient getHttpClient() {
      return new DefaultHttpClient(poolingClientConnectionManager, httpClientParams);
    }

    /**
     * 获取 https client （ssl with keys）
     *
     * @param keystore
     * @param arg0
     * @param port
     * @return
     * @throws java.security.KeyStoreException
     * @throws java.security.KeyManagementException
     * @throws java.security.UnrecoverableKeyException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.cert.CertificateException
     * @throws java.io.IOException
     */
    public static HttpClient getHttpClientWithSSL(String keystore,
                                                  String arg0, int port) throws KeyStoreException, KeyManagementException,
        UnrecoverableKeyException, NoSuchAlgorithmException,
        CertificateException, IOException {
      KeyStore trustStore = KeyStore.getInstance(KeyStore
          .getDefaultType());
      if (arg0 != null && keystore != null) {
        FileInputStream instream = new FileInputStream(new File(
            keystore));
        try {
          trustStore.load(instream, arg0.toCharArray());
        } finally {
          try {
            instream.close();
          } catch (Exception ignore) {
            ignore.printStackTrace();
          }
        }
      }
      SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
      Scheme sch = new Scheme("https", port, socketFactory);
      HttpClient client = getHttpClient();
      client.getConnectionManager().getSchemeRegistry().register(sch);
      return client;
    }

    /**
     * 获取https client （ssl without keys）
     *
     * @param port
     * @return
     * @throws java.security.KeyManagementException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static HttpClient getHttpClientWithSSL(int port) throws KeyManagementException,
        NoSuchAlgorithmException {
      HttpClient client = getHttpClient();
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = client.getConnectionManager();
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", ssf, port));
      client = new DefaultHttpClient(ccm, client.getParams());
      return client;
    }
  }

  /**
   * form param request-body model
   *
   * @author jinkai.xie
   */
  class ParamNameValuePair implements NameValuePair {

    private String name;

    private String value;

    ParamNameValuePair(String name, String value) {
      this.name = name;
      this.value = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.http.NameValuePair#getName()
     */
    @Override
    public String getName() {
      return name;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.http.NameValuePair#getValue()
     */
    @Override
    public String getValue() {
      return value;
    }
  }
}
