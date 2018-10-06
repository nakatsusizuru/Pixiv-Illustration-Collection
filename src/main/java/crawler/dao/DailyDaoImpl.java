package crawler.dao;

import crawler.constant.Constant;
import crawler.domain.Illustration;
import crawler.utils.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//保存到数据库
public class DailyDaoImpl {
    public void save(Illustration[] illustrations) throws SQLException {
        String date = Constant.DATE;
        StringBuilder sql = new StringBuilder("INSERT INTO illustration\n" +
                "(DATE,ID, ARTISTNAME, ARTISTID, TITLE,URL,RANK,HEIGHT,WIDTH)\n" +
                "VALUES");
        for (Illustration illustration : illustrations) {
            sql.append("('" + date + "'," + illustration.getId() + ",'" + illustration.getUser().getName().replace("\\","\\\\").replace("'", "\\'").replace(":","\\:") + "'," + illustration.getUser().getId() +
                    ",'" + illustration.getTitle().replace("\\","\\\\").replace("'", "\\'").replace(":","\\:")+ "','" + illustration.getUrl() + "'," + illustration.getRank() + "," + illustration.getImageInfo().height +
                    "," + illustration.getImageInfo().width + "),");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(";\n");
        Connection conn = null;
        Statement ps = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.createStatement();
            ps.execute(String.valueOf(sql));
            ps.execute("INSERT INTO date\n" +
                    "(DATE)\n" + "VALUES(\"" + date + "\");");
        } finally {
            DbUtil.close(null, ps, conn);
        }
    }
}
