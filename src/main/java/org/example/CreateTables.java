package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables extends Model {

    public static void createTest() {
        try {
            Connection connection = Model.getConnection();

            String accounts = """
                    CREATE TABLE IF NOT EXISTS `accounts` (
                      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                      `owner_id` int NOT NULL,
                      `created` datetime DEFAULT (now()),
                      `balance` int NOT NULL,
                      `account_id` int NOT NULL,
                      UNIQUE KEY `id` (`id`)
                    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Statement account = connection.createStatement();
            account.executeUpdate(accounts);

            String trans = """
                    CREATE TABLE IF NOT EXISTS `transactions` (
                      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                      `from` int NOT NULL,
                      `to` int NOT NULL,
                      `amount` int NOT NULL,
                      `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      UNIQUE KEY `id` (`id`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Statement transac = connection.createStatement();
            transac.executeUpdate(trans);

            String users = """
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
            Statement user = connection.createStatement();
            user.executeUpdate(users);

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
