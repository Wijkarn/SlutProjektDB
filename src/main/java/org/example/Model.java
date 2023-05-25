package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public abstract class Model {
    private static MysqlDataSource dataSource;

    static String url = "localhost";
    static int port = 3306;
    static String database = "SlutProjekt";
    static String username = "root";
    static String password = "Omegalul69";

    protected Model() {

    }

    private static void initializeDataSource() {
        try {
            dataSource = new MysqlDataSource();
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setUrl("jdbc:mysql://" + url + ":" + port + "/" + database + "?serverTimezone=UTC");
            dataSource.setUseSSL(false);
        } catch (SQLException e) {
            System.out.println("Initialize Failed");
        }
    }

    //Skapar en tillfällig koppling till databasen
    protected static Connection getConnection() {
        try {
            initializeDataSource();
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("failed!");
            System.exit(0);
            return null;
        }
    }

    protected MysqlDataSource getDataSource() {
        return dataSource;
    }
}