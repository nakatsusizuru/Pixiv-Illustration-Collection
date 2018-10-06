package utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUtil {
    public static String orderByPop(String keyword, int page) throws Exception {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        String url="https://app-api.pixiv.net/v1/search/illust?filter=for_android&sort=popular_desc&search_target=partial_match_for_tags&offset=" + page * 30+"&word="+URLDecoder.decode(keyword,"UTF-8");
        HttpGet httpget = new HttpGet("https://search.api.pixivic.com/v1/search/illust?filter=for_android&sort=popular_desc&search_target=partial_match_for_tags&word="+keyword+"&offset="+page*30);
        httpget.addHeader("Authorization", "Bearer " + OAuthUtil.getAccess_token());
        HeaderUtil.decorateHeader(httpget);
        httpget.setHeader("Content-Type","");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);
            return data;
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

    public static String orderByDate(String keyword, int page) throws Exception {
        CloseableHttpClient client = HttpClientPoolUtil.getHttpClient();
        HttpGet httpget = new HttpGet("https://app-api.pixiv.net/v1/search/illust?word=" + keyword + "&sort=popular_desc&offset=" + page * 30);
        HeaderUtil.decorateHeader(httpget);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);
            StringBuilder sb = new StringBuilder();
            System.out.println(data);
            Pattern pattern = Pattern.compile("(?<!:)(\\{|\\}\\,\\{)\"id\":[0-9]+\\,|\"large\":\"https:\\\\\\/\\\\\\/i\\.pximg\\.net[^\\.]+?\\.(:?jp|pn)g\"(?=\\}\\,\"caption\")|\\,(?=\"caption\")|\"total_bookmarks\":[0-9]+");// 匹配的模式
            Matcher m = pattern.matcher(data);
            while (m.find()) {
                System.out.println("11");
                sb.append(m.group());
            }
            return null;
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

}
