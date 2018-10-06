package dao.impl;

import dao.DailyRankDao;
import domain.Illustration;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DBPoolUtil;

import java.sql.SQLException;
import java.util.List;

public class DailyRankDaoImpl implements DailyRankDao {

    @Override
    public List<Illustration> getDailyRank(String date, int page) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBPoolUtil.getInstance().getDruidDataSource());
        String sql = "select * from illustration where date=? order by rank LIMIT 30 OFFSET  ?";
        List<Illustration> Illustrations = queryRunner.query(sql, new BeanListHandler<>(Illustration.class), date, page * 30);
        return Illustrations;
    }
}
