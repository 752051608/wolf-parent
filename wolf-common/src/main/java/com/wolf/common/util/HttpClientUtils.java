package com.wolf.common.util;

import com.wolf.common.exception.RestApiException;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hong
 */
//@Slf4j
public class HttpClientUtils {
    private static final Logger log = LoggerFactory.getLogger("erpLog");
    /**
     * HttpClient 对象
     */
    private static CloseableHttpClient httpClient = null;

    // Httpclient 初始化
    static {
        // 注册访问协议相关的 Socket 工厂
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        // Http 连接池
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        poolingHttpClientConnectionManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());
        poolingHttpClientConnectionManager.setMaxTotal(100);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
        // Http 请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                //连接目标超时
                .setConnectTimeout(10000)
                //从连接池中获取可用连接超时
                .setConnectionRequestTimeout(10000)
                //等待响应超时（读取数据超时）
                .setSocketTimeout(10000)
                .build();
        // 创建监听器，在 JVM 停止或重启时，关闭连接池释放掉连接
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("执行关闭 HttpClient");
                httpClient.close();
                log.info("已经关闭 HttpClient");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }));
        // 创建 HttpClient 对象
        httpClient = HttpClients.custom()
                // 设置 HttpClient 请求参数
                .setDefaultRequestConfig(requestConfig)
                // 设置连接池
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
    }

    /**
     * 获取 Httpclient 对象
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpclient() {
        return httpClient;
    }

    /**
     * 发送get请求
     *
     * @param path
     * @param params
     * @return
     * @throws RestApiException
     * @throws URISyntaxException
     */
    public static String getRequest(String path, Map<String, String> params) throws RestApiException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(path);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (!CollectionUtils.isEmpty(params)) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> param : entries) {
                nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        uriBuilder.setParameters(nameValuePairs);
        HttpGet get = new HttpGet(uriBuilder.build());
        try {
            HttpResponse response = HttpClientUtils.getHttpclient().execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400)
                throw new RuntimeException((new StringBuilder()).append("Could not access protected resource. Server returned http code: ").append(code).toString());
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            throw new RestApiException("postRequest -- Client protocol exception!", e);
        } catch (IOException e) {
            throw new RestApiException("postRequest -- IO error!", e);
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * 以表单形式发送post请求
     *
     * @param path
     * @param params
     * @return
     * @throws RestApiException
     */
    public static String postForm(String path, Map<String, String> params) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> param : entries) {
            nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, Charsets.UTF_8);
        return postRequest(path, "application/x-www-form-urlencoded", entity);
    }

    /**
     * 以json形式发送请求
     *
     * @param path
     * @param json
     * @return
     * @throws RestApiException
     */
    public static String postJSON(String path, String json) throws Exception {
        StringEntity entity = new StringEntity(json, Charsets.UTF_8);
        return postRequest(path, "application/json", entity);
    }

    /**
     * 发送post请求，json格式
     *
     * @param path
     * @param mediaType
     * @param entity
     * @return
     * @throws RestApiException
     */
    public static String postRequest(String path, String mediaType, HttpEntity entity) throws Exception {
        log.debug("[postRequest] resourceUrl: {}", path);
        HttpPost post = new HttpPost(path);
        post.addHeader("Content-Type", mediaType);
        post.addHeader("Accept", "application/json");
        post.setEntity(entity);
        try {
            HttpResponse response = HttpClientUtils.getHttpclient().execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400)
                throw new RestApiException(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RestApiException("postRequest -- Client protocol exception!", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestApiException("postRequest -- IO error!", e);
        } finally {
            post.releaseConnection();
        }
    }

    /**
     * 构建请求声明
     *
     * @return
     */
    private static String getBaseRequestInfo() {
        StringBuffer sb = new StringBuffer("");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<soap:Envelope "
                + "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
                + "    xmlns:xsd='http://www.w3.org/2001/XMLSchema' "
                + " xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' >");
        return sb.toString();
    }

    /**
     * 构建请求header
     *
     * @return
     */
    private static String getBaseRequestHeader() {
        StringBuffer sb = new StringBuffer("");
        sb.append("<soap:Header>");
        sb.append("<CrmSoapHeader MyAttribute=\"\" xmlns=\"http://tempuri.org/\">");
        sb.append("<UserId>MYCRM</UserId>");
        sb.append("<Password>MYCRM</Password>");
        sb.append("<MissionId /><Ts /><Sign />");
        sb.append("</CrmSoapHeader>");
        sb.append("</soap:Header>");
        sb.append("<soap:Body>");
        return sb.toString();
    }

    /**
     * 构建请求header
     *
     * @return
     */
    private static String getBaseRequestTail() {
        StringBuffer sb = new StringBuffer("");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    /**
     * 从返回信息中获取body中的有效信息
     *
     * @return
     */
    private static String getBodyResult(String result) {
        log.info("szc获请求的返回原始信息为：{}", result);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        int start = result.indexOf("<soap:Body>");
        int end = result.indexOf("</soap:Body>");
        return result.substring(start + 11, end).replaceAll("xmlns=\"http://tempuri.org/\"", "");
    }

    /**
     * @param url
     * @param request
     * @return
     */
    public static String postWsRequest(String url, Object request) throws Exception {
        String baseRequestInfo = getBaseRequestInfo();
        String baseRequestHeader = getBaseRequestHeader();
        String baseRequestTail = getBaseRequestTail();
        //拼接请求参数
        StringBuffer sb = new StringBuffer("");
        sb.append(baseRequestInfo);
        sb.append(baseRequestHeader);
        sb.append(XMLUtils.toXML(request));
        sb.append(baseRequestTail);
        log.info("szc发送请求的信息为：{}", sb.toString());
        StringEntity entity = new StringEntity(sb.toString(), Charsets.UTF_8);
        return getBodyResult(postRequest(url, "text/xml; charset=utf-8", entity));
    }


    public static void main(String[] args) throws URISyntaxException {
        List<NameValuePair> parametersBody = new ArrayList();
        parametersBody.add(new BasicNameValuePair("userId", "admin"));
        String result = HttpClientUtils.getRequest("http://localhost:8888/index", null);
        System.out.println(result);
//
//        List<NameValuePair> parametersBody = new ArrayList();
//        parametersBody.add(new BasicNameValuePair("username", "admin"));
//        parametersBody.add(new BasicNameValuePair("password", "123456"));
//        String result = HttpClientUtils.postForm("http://www.test.com/login", parametersBody);
//
//
//        Map<String, String> map = new HashMap<>();
//        map.put("username", "admin");
//        map.put("password", "123456");
//        String result = HttpClientUtils.postJSON("http://www.test.com/login", json);
        System.out.println(result);
    }
}
