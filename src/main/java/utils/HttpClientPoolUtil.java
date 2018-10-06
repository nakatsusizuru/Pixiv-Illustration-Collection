package utils;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HttpClientPoolUtil {
    private static final int MAX_CONN = 200; // 最大连接数
    private static final int APP_API_PIXIV_NET_MAX_PRE_ROUTE = 50;
    private static final int I_PXIMG_NET_MAX_PRE_ROUTE = 20;
    private static final int OAUTH_SECURE_PIXIV_NET_MAX_PRE_ROUTE = 2;
    private static final int PICUPLOAD_SERVICE_WEIBO_COM_MAX_PER_ROUTE = 50;
    private static final int WS4_SINAIMG_CN_COM_MAX_PER_ROUTE = 50;
    private static PoolingHttpClientConnectionManager manager; //连接池管理类
    private static IdleConnectionMonitorThread staleMonitor; //连接池监控类

    static {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        manager.setMaxPerRoute(new HttpRoute(new HttpHost("api.pixivic.com", 443)), APP_API_PIXIV_NET_MAX_PRE_ROUTE);
        manager.setMaxPerRoute(new HttpRoute(new HttpHost("i.pximg.net", 443)), I_PXIMG_NET_MAX_PRE_ROUTE);
        manager.setMaxPerRoute(new HttpRoute(new HttpHost("oauth.api.pixivic.com", 443)), OAUTH_SECURE_PIXIV_NET_MAX_PRE_ROUTE);
        manager.setMaxPerRoute(new HttpRoute(new HttpHost("picupload.service.weibo.com", 80)), PICUPLOAD_SERVICE_WEIBO_COM_MAX_PER_ROUTE);
        manager.setMaxPerRoute(new HttpRoute(new HttpHost("ws4.sinaimg.cn", 443)), WS4_SINAIMG_CN_COM_MAX_PER_ROUTE);

        manager.setMaxTotal(MAX_CONN);
        //监视无用或者超时连接
        staleMonitor
                = new IdleConnectionMonitorThread(manager);
        staleMonitor.start();
        try {
            staleMonitor.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void quit() {
        staleMonitor.shutdown();
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .disableCookieManagement()
                .setConnectionManager(manager)
                .build();
        return httpClient;
    }

    public static void close(CloseableHttpResponse response) throws IOException {
        if (response != null) {
            if (response.getEntity() != null)
                EntityUtils.consume(response.getEntity());
            response.close();
        }
    }

}
