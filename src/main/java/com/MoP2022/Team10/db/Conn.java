package com.MoP2022.Team10.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Conn {

    private static HikariConfig config = new HikariConfig();
    private static DataSource ds;

    static{
        config.setDriverClassName(DBConfig.driver);
        config.setJdbcUrl(DBConfig.url);
        config.setUsername(DBConfig.username);
        config.setPassword(DBConfig.password);
        config.setMaximumPoolSize(30);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
