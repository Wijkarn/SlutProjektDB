package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables extends Model {

    public static void createTables() throws SQLException {
        Connection connection = Model.getConnection();
        createUsers(connection);
        createAccounts(connection);
        createTrans(connection);
        connection.close();
    }

    public static void createUsers(Connection connection) {
        try {
            String query = """
                    CREATE TABLE IF NOT EXISTS `accounts` (
                      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                      `owner_id` int NOT NULL,
                      `created` datetime DEFAULT (now()),
                      `balance` int NOT NULL,
                      `account_id` int NOT NULL,
                      UNIQUE KEY `id` (`id`)
                    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createAccounts(Connection connection) {
        try {
            String query = """
                    CREATE TABLE IF NOT EXISTS `accounts` (
                      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                      `owner_id` int NOT NULL,
                      `created` datetime DEFAULT (now()),
                      `balance` int NOT NULL,
                      `account_id` int NOT NULL,
                      UNIQUE KEY `id` (`id`)
                    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTrans(Connection connection) {
        try {
            String query = """
                    CREATE TABLE `users` (
                      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                      `personnummer` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                      `email` varchar(50) NOT NULL,
                      `phone` varchar(20) NOT NULL,
                      `password` varchar(100) NOT NULL,
                      `address` varchar(100) NOT NULL,
                      `name` varchar(50) NOT NULL,
                      `created` datetime DEFAULT (now()),
                      UNIQUE KEY `id` (`id`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
