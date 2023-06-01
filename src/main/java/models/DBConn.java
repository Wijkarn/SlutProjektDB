package models;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public abstract class DBConn {
    private static MysqlDataSource dataSource;
    static String url = "";
    static int port = 0;
    static String database = "";
    static String username = "";
    static String password = "";

    protected DBConn() {

    }

    private static void initializeDataSource() {
        try {
            getInfoFromFile();
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

    public static void getInfoFromFile() {
        String filePath = "textfiles/logInInfo.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            url = reader.readLine();
            port = Integer.parseInt(reader.readLine());
            database = reader.readLine();
            username = reader.readLine();
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected MysqlDataSource getDataSource() {
        return dataSource;
    }
}