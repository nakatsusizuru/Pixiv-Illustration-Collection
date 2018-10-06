package crawler.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawler.constant.Constant;
import crawler.domain.Illustration;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

//获取日排行插画数组
public class GetIllustrationsUtil {

    public static Illustration[] getIllustrations() throws NoSuchAlgorithmException, IOException {
        String data;
        Illustration[][] illustrations = new Illustration[7][];
        Illustration[] illustrationsLIst;
        for (int i = 0; i < 7; i++) {
            data = getRankJson(i);
            illustrations[i] = jsonToObject(data);
        }
        illustrationsLIst = arrangeArray(illustrations);
        return illustrationsLIst;
    }

    //获取日排行json数据
    public static String getRankJson(int index) throws NoSuchAlgorithmException, IOException {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpGet httpget = new HttpGet("https://search.api.pixivic.com/v1/illust/ranking?mode=day&offset=" + index * 30 + "&date=" + Constant.DATE);
        HeaderUtil.decorateHeader(httpget);
        httpget.addHeader("Authorization", "Bearer " + OAuthUtil.getAccess_token());
        CloseableHttpResponse response = null;
        String json = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            json = EntityUtils.toString(entity);
            return json;
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

    //转换日排行json数据
    public static Illustration[] jsonToObject(String data) throws IOException {
        data = data.replace("{\"illusts\":", "");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(data, Illustration[].class);
    }

    //整理日排行插画数组
    public static Illustration[] arrangeArray(Illustration[][] illustrations) {
        int count = 0;
        int index = 0;
        for (Illustration[] illustration : illustrations) {
            count += illustration.length;
        }
        Illustration[] data = new Illustration[count];
        for (Illustration[] illustration : illustrations) {
            System.arraycopy(illustration, 0, data, index, illustration.length);
            index += illustration.length;
        }
        for (int i = 0; i < data.length; i++) {
            data[i].setRank(i);
        }
        return data;
    }
}
