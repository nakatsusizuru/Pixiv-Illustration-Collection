package crawler.utils;

import crawler.constant.Constant;
import crawler.domain.Illustration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//日排行插画储存到本地
public class DownloadUtil {
    private final static long MAXLENGTH = 20971520;

    //保存插画到本地
    public static void download(Illustration illustration) throws IOException {
        HttpGet httpget;
        CloseableHttpClient httpclient = HttpClientPoolUtil.getHttpClient();
        String url;
        //多页与单页
        if (illustration.getMeta_single_page().getOriginal_image_url() != null) {
            url = illustration.getMeta_single_page().getOriginal_image_url();
        } else {
            url = illustration.getMeta_pages().get(0).getImage_urls().getOriginal();
        }
        httpget = new HttpGet(url);
        httpget.addHeader("Referer", "https://app-api.pixiv.net/");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                long filelength = entity.getContentLength();
                //图片尺寸过大的处理
                if (filelength > MAXLENGTH) {
                    httpget = new HttpGet(url.replace("https://i.pximg.net/img-original/img", "https://i.pximg.net/img-master/img").replace(".jpg", "_master1200.jpg"));
                    httpget.addHeader("Referer", "https://app-api.pixiv.net/");
                    response = httpclient.execute(httpget);
                    entity = response.getEntity();
                }
                InputStream in = entity.getContent();
                savePicToDisk(in, "/home/PIC/" + Constant.DATE + "/", String.valueOf(illustration.getRank()) + ".png");
                in.close();
            }
        } finally {
            HttpClientPoolUtil.close(response);
        }
    }

    private static void savePicToDisk(InputStream in, String dirPath, String filePath) {
        try {
            File dir = new File(dirPath);
            if (dir == null || !dir.exists()) {
                dir.mkdirs();
            }
            //文件真实路径
            String realPath = dirPath.concat(filePath);
            File file = new File(realPath);
            if (file == null || !file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}