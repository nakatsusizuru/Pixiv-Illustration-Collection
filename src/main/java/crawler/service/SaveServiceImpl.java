package crawler.service;

import crawler.dao.DailyDaoImpl;
import crawler.domain.Illustration;

import java.sql.SQLException;

public class SaveServiceImpl {
    public void save(Illustration[] illustrations) throws SQLException {
        new DailyDaoImpl().save(illustrations);
    }
}
