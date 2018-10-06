package service;

import domain.Illustration;

import java.sql.SQLException;
import java.util.List;

public interface DailyRankService {
    List<Illustration> getDailyRank(String date, int page) throws SQLException;
}
