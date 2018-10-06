package service.impl;

import domain.Illustration;
import service.DailyRankService;
import dao.impl.DailyRankDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class DailyRankServiceImpl implements DailyRankService {

    public List<Illustration> getDailyRank(String date, int page) throws SQLException {
        return new DailyRankDaoImpl().getDailyRank(date,page);
    }
}
