package crawler.utils;

import org.apache.http.client.methods.HttpRequestBase;

import java.security.NoSuchAlgorithmException;

//请求头装饰
public class HeaderUtil {
    public static void decorateHeader(HttpRequestBase httpRequestBase) throws NoSuchAlgorithmException {
        httpRequestBase.addHeader("User-Agent", "PixivAndroidApp/5.0.93 (Android 5.1; m2)");
        httpRequestBase.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpRequestBase.addHeader("App-OS", "android");
        httpRequestBase.addHeader("App-OS-Version", "5.1");
        httpRequestBase.addHeader("App-Version", "5.0.93");
        httpRequestBase.addHeader("Accept-Encoding", "gzip");
        String[] a = HashUtil.gethash();
        httpRequestBase.addHeader("X-Client-Hash", a[1]);
        httpRequestBase.addHeader("X-Client-Time", a[0]);
    }
}
