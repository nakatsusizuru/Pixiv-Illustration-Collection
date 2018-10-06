package crawler.multiThreadUtils;

import crawler.domain.Illustration;
import crawler.utils.DownloadUtil;
import crawler.utils.ImageSizeUtil;
import crawler.utils.ScanUrlUtil;
import crawler.utils.UploadUtil;

import java.io.IOException;

public class MultiThreadCrawler implements Runnable {
    private Illustration illustration;

    public MultiThreadCrawler(Illustration illustration) {
        this.illustration = illustration;
    }

    @Override
    public void run() {
        try {
            DownloadUtil.download(illustration);//下载到本地
            System.out.println("图片已保存到本地");
            ImageSizeUtil.setImageSize(illustration);//检测图片尺寸
            UploadUtil.upload(illustration);//上传到微博图床
            System.out.println("已上传至新浪图床");
            Thread.sleep(1000*60);
            if (!ScanUrlUtil.scan(illustration))//检测微博图床外链是否失效
                ScanUrlUtil.reUpload(illustration);//重新上传到sm.ms与imgur图床
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
