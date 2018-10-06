package utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;


import java.sql.SQLException;
import java.util.Properties;

public class DBPoolUtil {
    private static DruidDataSource druidDataSource = null;

    private DBPoolUtil() {
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(DBPoolUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties); //DruidDataSrouce工厂模式
        } catch (Exception e) {
            System.out.println("获取配置失败");
        }
    }

    //内部枚举实现单例
    private enum Singleton {
        INSTANCE;
        private DBPoolUtil dbPool;

        Singleton() {
            dbPool = new DBPoolUtil();
        }

        public DBPoolUtil getInstance() {
            return dbPool;
        }
    }

    public static DBPoolUtil getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public DruidDataSource getDruidDataSource() {
        return druidDataSource;
    }
}
