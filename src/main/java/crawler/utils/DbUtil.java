package crawler.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;

//ssh连接数据库工具类
public class DbUtil {
    static int lport;//本地端口
    static String rhost;//远程MySQL服务器
    static int rport;//远程MySQL服务端口
    static String user;//SSH连接用户名
    static String password;//SSH连接密码
    static String host;//SSH服务器
    static int port;//SSH访问端口

    static {
        lport = 12345;
        rhost = "127.0.0.1";
        rport = 3306;
        user = "root";
        password = "数据库密码";
        host = "主机ip";
        port = 32655;
        init();
    }

    public static void init() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(lport, rhost, rport);
            System.out.println("ssh隧道搭建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mariadb://localhost:12345/PIC", "root", "linhao199741");
        System.out.println("连接到mariadb");
        return conn;
    }

    public static void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
