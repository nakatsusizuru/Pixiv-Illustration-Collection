package dao;

import domain.Illustration;

import java.sql.SQLException;
import java.util.List;

public interface DailyRankDao {
    public List<Illustration> getDailyRank(String date, int page) throws SQLException;
}
