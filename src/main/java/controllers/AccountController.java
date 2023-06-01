package controllers;

import database.DBConn;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountController extends DBConn {

    public static void addAccount(User user) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Account number:");
            String accNum = scan.nextLine();
            System.out.println("Balance:");
            String bal = scan.nextLine();
            String query = "INSERT INTO accounts (owner_id, account_number, balance) VALUES (?, ?, ?)";
            Connection connection = DBConn.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            long accNumInt = Long.parseLong(accNum);
            long balInt = Long.parseLong(bal);

            statement.setInt(1, user.getId());
            statement.setLong(2, accNumInt);
            statement.setLong(3, balInt);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAccount(User user) {
        try {
            if (getAllAccountsById(user.getId(), true)) {
                System.out.println("Which account do you want to delete?");
                Scanner scan = new Scanner(System.in);
                String accountId = scan.nextLine();
                String query = "DELETE FROM accounts WHERE id = ? AND owner_id = ?";
                Connection connection = DBConn.getConnection();
                PreparedStatement prepStatement = connection.prepareStatement(query);
                prepStatement.setInt(1, Integer.parseInt(accountId));
                prepStatement.setInt(2, user.getId());
                int rowsAffected = prepStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Successfully deleted account!");

                } else {
                    System.out.println("Delete unsuccessful!");
                }
            } else {
                System.out.println("No accounts found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getAllAccountsById(int id, boolean owner) {
        try {
            String query = "SELECT * FROM accounts WHERE owner_id = ?;";
            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);

            ResultSet res = prepStatement.executeQuery();

            boolean accounts = false;
            while (res.next()) {
                int accountId = res.getInt("id");
                long accountNr = res.getLong("account_number");

                System.out.print("Id:" + accountId);

                if (owner) {
                    System.out.print(" Account nr:" + accountNr);
                    long bal = res.getLong("balance");
                    System.out.println(" Balance:" + bal);
                } else {
                    System.out.println(" Account nr:" + accountNr);
                }

                accounts = true;
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAllAccounts(User user) {
        try {
            String query = "DELETE FROM accounts WHERE owner_id = ?";
            Connection connection = DBConn.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.executeUpdate();

            statement.close();
            connection.close();
            System.out.println("Successfully deleted all bank accounts!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getAllAccountsByName(String name) {
        try {
            String query = "SELECT * FROM accounts WHERE owner_id IN (SELECT id FROM users WHERE name = ?)";
            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, name);

            ResultSet res = prepStatement.executeQuery();

            boolean yeet = false;
            while (res.next()) {
                int accountId = res.getInt("id");
                long accountNr = res.getLong("account_number");

                System.out.print("Id:" + accountId);
                System.out.println(" Account nr:" + accountNr);
                yeet = true;
            }
            return yeet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
