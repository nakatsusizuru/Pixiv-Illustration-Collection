package utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class OAuthUtil {
    public static volatile String access_token;

    static {
        try {
            initializate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refreshAuthorization() throws NoSuchAlgorithmException, IOException {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("client_id", ConfigUtil.getConfig("pixiv-config > client_id")));
        formParams.add(new BasicNameValuePair("client_secret", ConfigUtil.getConfig("pixiv-config > client_secret")));
        formParams.add(new BasicNameValuePair("grant_type", "refresh_token"));
        formParams.add(new BasicNameValuePair("refresh_token", ConfigUtil.getConfig("pixiv-config > refresh_token")));
        formParams.add(new BasicNameValuePair("device_token", ConfigUtil.getConfig("pixiv-config > device_token")));
        formParams.add(new BasicNameValuePair("get_secure_url", "true"));
        post(formParams);
    }

    public static String getAccess_token() {
        return access_token;
    }

    private static void initializate() throws NoSuchAlgorithmException, IOException {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("client_id", ConfigUtil.getConfig("pixiv-config > client_id")));
        formParams.add(new BasicNameValuePair("client_secret", ConfigUtil.getConfig("pixiv-config > client_secret")));
        formParams.add(new BasicNameValuePair("grant_type", "password"));
        formParams.add(new BasicNameValuePair("username",ConfigUtil.getConfig("pixiv-config > username")));
        formParams.add(new BasicNameValuePair("password", ConfigUtil.getConfig("pixiv-config > password")));
        formParams.add(new BasicNameValuePair("device_token", ConfigUtil.getConfig("pixiv-config > device_token")));
        formParams.add(new BasicNameValuePair("get_secure_url", "true"));
        post(formParams);
    }
    private static void post(List<NameValuePair> formParams) throws NoSuchAlgorithmException, IOException {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpPost httppost = new HttpPost("https://oauth.api.pixivic.com/auth/token");
        HeaderUtil.decorateHeader(httppost);
        UrlEncodedFormEntity postData = new UrlEncodedFormEntity(formParams, "UTF-8");
        httppost.setEntity(new StringEntity(EntityUtils.toString(postData).replace("+", "")));
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httppost);
            String data = EntityUtils.toString(response.getEntity(), "UTF-8");
            int index = data.indexOf("\"access_token\":\"");
            access_token = data.substring(index + 16, index + 59);
            System.out.println(access_token);
        } finally {
            HttpClientPoolUtil.close(response);
        }

    }
}

