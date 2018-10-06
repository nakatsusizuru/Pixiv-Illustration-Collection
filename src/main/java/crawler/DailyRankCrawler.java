package crawler;

import crawler.domain.Illustration;
import crawler.multiThreadUtils.MultiThreadCrawler;
import crawler.service.DailyRankService;
import crawler.service.SaveServiceImpl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DailyRankCrawler {
    //入口
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, SQLException, InterruptedException {
        Illustration[] illustrations = new DailyRankService().getDailyRankArray();
        System.out.println("抓取日排行完毕");
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 35, 1, TimeUnit.DAYS, queue);
        for (Illustration illustration : illustrations) {
            executor.execute(new Thread(new MultiThreadCrawler(illustration)));
        }
        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                new SaveServiceImpl().save(illustrations);
                System.out.println("保存到数据库完毕");
                System.out.println("结束-------------------------------------------------------------------------------------------");
                System.exit(0);
            }
            Thread.sleep(1000 * 30);
        }

    }

}

