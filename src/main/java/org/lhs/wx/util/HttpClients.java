package org.lhs.wx.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by longhuashen on 17/5/2.
 */
public class HttpClients {

    private static Logger logger= LoggerFactory.getLogger("HttpClients");


    public static Charset CHARSET_UTF_8=Charset.forName("UTF-8");

    private static HttpClients ourInstance = new HttpClients();

    public static HttpClients getInstance() {
        return ourInstance;
    }

    private HttpClients() {
    }

    private CloseableHttpClient httpClient;

    public CloseableHttpClient httpClient() {

        if (httpClient != null) {
            return httpClient;
        }


        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();


        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build();

        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setDefaultSocketConfig(socketConfig);

        //构建客户端
        httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
        return httpClient;
    }


    class AnyTrustStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) {
            return true;
        }
    }


    /**
     * 执行请求
     * @param httpUriRequest 请求
     * @return 执行结果字符串
     */
    public static String execute(HttpUriRequest httpUriRequest){
        return execute(httpUriRequest,null);
    }


    /**
     * 执行请求
     * @param httpUriRequest 执行请求
     * @param charset       字符集，默认为 UTF-8
     * @return 执行结果字符串
     */
    public static String execute(HttpUriRequest httpUriRequest, Charset charset){

        try {
            CloseableHttpResponse response = getInstance().httpClient().execute(httpUriRequest);
            return EntityUtils.toString(response.getEntity(),charset);
        } catch (IOException e) {
            logger.warn("execute https:request error httpUriRequest:"+httpUriRequest,e);
            throw new RuntimeException(e);
        }
    }

    public static CloseableHttpClient client(){
        return getInstance().httpClient();
    }

    /**
     *
     * @param httpUriRequest 请求对象
     * @param charset       字符集
     * @return              请求响应
     */
    public static CloseableHttpResponse executeRequest(HttpUriRequest httpUriRequest, Charset charset){
        try {
            return getInstance().httpClient().execute(httpUriRequest);
        } catch (IOException e) {
            logger.warn("execute https:request error httpUriRequest:" + httpUriRequest, e);
            throw new RuntimeException(e);
        }

    }
}
