package crawler.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawler.constant.Constant;
import crawler.domain.Illustration;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

//检测图片外链有效性
public class ScanUrlUtil {
    private final static String UPLOADURL;
    private final static String IMGUR_CLIENT_ID;

    static {
        UPLOADURL = "https://sm.ms/api/upload";
        IMGUR_CLIENT_ID = ConfigUtil.getConfig("imgur-config > client_id");
    }

    //校测是否失效
    public static boolean scan(Illustration illustration) throws IOException {
        System.out.println("开始扫描" + illustration.getTitle() + "在微博图床的外链");
        HttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(illustration.getUrl());
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
        httpGet.addHeader("Referer", "https://www.oysterqaq.com/collection/");
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) client.execute(httpGet);
            if (response.getFirstHeader("Content-Length").getValue().equals("3763")) {
                System.out.println(illustration.getTitle() + "微博图床外链已被和谐,将重新上传至smms");
                response.close();
                return false;
            } else {
                System.out.println(illustration.getTitle() + "未被和谐");
                return true;
            }
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

    //失效后上传
    public static void reUpload(Illustration illustration) throws IOException {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpPost httppost = new HttpPost("https://upload.cc/image_upload");
        httppost.addHeader("Connection", "Keep-Alive");
        httppost.addHeader("Content-Type", "multipart/form-data;boundary=" + "*****");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity entity = builder.create()
                .addBinaryBody("uploaded_file[]", new File("/home/PIC/" + Constant.DATE + "/" + illustration.getRank() + ".png"), ContentType.IMAGE_PNG, "e:/1/6.png")
                .setBoundary("*****")
                .build();
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httppost);
            String data = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(data);
            if (!rootNode.path("total_success").asText().equals("0")) {
                illustration.setUrl("https://upload.cc/"+rootNode.get("success_image").get(0).get("url").asText());
                System.out.println(illustration.getTitle() + "重新上传至upload.cc完毕,url为" + illustration.getUrl());
            } else {
                System.out.println(illustration.getTitle() + "触碰upload.cc审查规则,将重新上传到imgur");
                reReUpload(illustration);
            }
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

    //再次失效后上传
    private static void reReUpload(Illustration illustration) throws IOException {
        CloseableHttpClient httpClient = HttpClientPoolUtil.getHttpClient();
        HttpPost httpPost = new HttpPost("https://api.imgur.com/3/image");
        httpPost.setHeader("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity entity = builder.create()
                .addBinaryBody("image", new File("/home/PIC/" + Constant.DATE + "/" + illustration.getRank() + ".png"), ContentType.IMAGE_PNG, "e:/1/6.png")
                .build();
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            String data = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(data);
            if (rootNode.path("success").asText().equals("true")) {
                illustration.setUrl(rootNode.path("data").path("link").asText());
                System.out.println(illustration.getTitle() + "上传至imgur成功,url为" + illustration.getUrl());
            } else {
                System.out.println(illustration.getTitle() + "上传至imgur失败");
                illustration.setUrl("无法上传至任何图床");
            }

        } finally {
            HttpClientPoolUtil.close(response);
        }

    }

}
