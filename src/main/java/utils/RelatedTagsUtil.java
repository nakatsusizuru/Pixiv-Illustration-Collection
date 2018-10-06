package utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import java.io.IOException;

public class RelatedTagsUtil {
    public static String getRelatedTags(String keyword) throws IOException {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpGet httpget = new HttpGet("https://tag.api.pixivic.com/search.php?s_mode=s_tag&word=" + keyword);
        httpget.setHeader("accept-language","zh-CN,zh;q=0.9");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");
            String tags = Jsoup.parse(html)
                    .getElementsByTag("input").eq(3)
                    .attr("data-related-tags");
            return tags;
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }
}
