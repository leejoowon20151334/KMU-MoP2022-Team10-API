package com.MoP2022.Team10.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBConfig {
    public static String url;
    public static String username;
    public static String password;
    public static String driver;

    @Value("${db.url}")
    public void setUrl(String url) {
        DBConfig.url = url;
    }

    @Value("${db.username}")
    public void setUsername(String username) {
        DBConfig.username = username;
    }

    @Value("${db.password}")
    public void setPassword(String password) {
        DBConfig.password = password;
    }

    @Value("${db.driver}")
    public void setDriver(String driver) {
        DBConfig.driver = driver;
    }
}
