package crawler.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawler.constant.Constant;
import crawler.domain.Illustration;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//图片上传与获取外链
public class UploadUtil {

    private final static String USERNAME;//用户名
    private final static String PASSWORD;//密码
    private final static String LOGINURL;//登陆url
    private final static String IMGURLPRE;//外链前缀
    private final static String IMGURLPOST;//外链后缀
    private final static String UPLOADURL;//上传url
    private final static List<NameValuePair> PARAMS = new ArrayList<>();//登陆参数
    private static String cookies;//用户cookie

    static {
        USERNAME = Base64.getEncoder().encodeToString(ConfigUtil.getConfig("sina-config > username").getBytes());
        PASSWORD = ConfigUtil.getConfig("sina-config > password");
        LOGINURL = "https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=1403138799543";
        IMGURLPRE = "https://ws4.sinaimg.cn/large/";
        IMGURLPOST = ".jpg";
        UPLOADURL = "http://picupload.service.weibo.com/interface/pic_upload.php?mime=image%2Fjpeg&data=base64&url=0&markpos=1&logo=&nick=0&marks=1&app=miniblog&cb=http://weibo.com/aj/static/upimgback.html?_wv=5&callback=STK_ijax_";
        PARAMS.add(new BasicNameValuePair("entry", "sso"));
        PARAMS.add(new BasicNameValuePair("gateway", "1"));
        PARAMS.add(new BasicNameValuePair("from", "null"));
        PARAMS.add(new BasicNameValuePair("savestate", "30"));
        PARAMS.add(new BasicNameValuePair("useticket", "0"));
        PARAMS.add(new BasicNameValuePair("vsnf", "1"));
        PARAMS.add(new BasicNameValuePair("pagerefer", ""));
        PARAMS.add(new BasicNameValuePair("su", USERNAME));
        PARAMS.add(new BasicNameValuePair("service", "sso"));
        PARAMS.add(new BasicNameValuePair("sp", PASSWORD));
        PARAMS.add(new BasicNameValuePair("sr", "1920*1080"));
        PARAMS.add(new BasicNameValuePair("encoding", "UTF-8"));
        PARAMS.add(new BasicNameValuePair("cdult", "3"));
        PARAMS.add(new BasicNameValuePair("domain", "sina.com.cn"));
        PARAMS.add(new BasicNameValuePair("prelt", "0"));
        PARAMS.add(new BasicNameValuePair("returntype", "TEXT"));
        try {
            cookies = getSinaCookies();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登录
    private static String getSinaCookies() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(LOGINURL);
        UrlEncodedFormEntity postData = new UrlEncodedFormEntity(PARAMS, "UTF-8");
        httppost.setEntity(new StringEntity(EntityUtils.toString(postData).replace("+", "")));
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httppost);
            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                HeaderElement[] elements = header.getElements();
                for (HeaderElement element : elements) {
                    if (element.getName().equals("SUB"))
                        return "SUB=" + element.getValue() + ";";
                }
            }
            return null;
        } finally {
            HttpClientPoolUtil.close(response);
        }

    }

    //上传
    public static void upload(Illustration illustration) throws IOException {
        String url = uploadFile(illustration.getRank());
        url=url.substring(url.indexOf("{"), url.length() );
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        if (rootNode.path("code").asText().equals("A20001")) {
            url=rootNode.path("data").path("pics").path("pic_1").path("pid").asText();
            if(url!=null)
                illustration.setUrl(IMGURLPRE+url+IMGURLPOST);
        } else {
            System.out.println(illustration.getTitle() + "上传至新浪图床失败");
        }
    }

    //上传文件
    private static String uploadFile(String filepath) throws IOException {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpPost httppost = new HttpPost(UPLOADURL);
        httppost.addHeader("Connection", "Keep-Alive");
        httppost.addHeader("Charset", "UTF-8");
        httppost.addHeader("Cookie", cookies);
        httppost.addHeader("Content-Type", "multipart/form-data;boundary=" + "*****");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity entity = builder.create()
                .addBinaryBody("pic1", new File("/home/PIC/" + Constant.DATE + "/" + filepath + ".png"), ContentType.IMAGE_PNG, "e:/1/6.png")
                .setBoundary("*****")
                .build();
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httppost);
            return EntityUtils.toString(response.getEntity());
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }
}
