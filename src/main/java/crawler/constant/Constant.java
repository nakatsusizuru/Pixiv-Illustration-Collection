package crawler.constant;

import java.time.LocalDate;

//获取日期
public interface Constant {
    String DATE = String.valueOf(LocalDate.now().minusDays(2));
}
