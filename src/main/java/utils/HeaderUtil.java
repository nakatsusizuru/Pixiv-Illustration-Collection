package utils;

import org.apache.http.client.methods.HttpRequestBase;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

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
    public static void decorateResponse(HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("expires", -1);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
    }
}
