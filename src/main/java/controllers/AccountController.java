package controllers;

import database.DBConn;
import models.Account;
import models.User;
import views.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountController extends DBConn {

    public static void addAccount(User user) {
        try {
            String query = "INSERT INTO accounts (owner_id, account_number, balance) VALUES (?, ?, ?)";
            Connection connection = DBConn.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            Account account = new Account();
            System.out.println("Account number:");
            account.setAccountNumber(Long.parseLong(UserView.getUserInput()));

            System.out.println("Balance:");
            account.setBalance(Long.parseLong(UserView.getUserInput()));

            statement.setInt(1, user.getId());
            statement.setLong(2, account.getAccountNumber());
            statement.setLong(3, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAccount(User user) {
        try {
            if (getAllAccountsById(user, true)) {
                String query = "DELETE FROM accounts WHERE id = ? AND owner_id = ?";
                Connection connection = DBConn.getConnection();
                PreparedStatement prepStatement = connection.prepareStatement(query);
                Account account = new Account();
                account.setOwnerId(user.getId());

                System.out.println("Which account do you want to delete?");
                account.setAccountId(Integer.parseInt(UserView.getUserInput()));

                prepStatement.setInt(1, account.getAccountId());
                prepStatement.setInt(2, account.getOwnerId());

                if (prepStatement.executeUpdate() > 0) {
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

    public static boolean getAllAccountsById(User user, boolean owner) {
        try {
            String query = "SELECT * FROM accounts WHERE owner_id = ?;";
            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, user.getId());

            ResultSet res = prepStatement.executeQuery();

            boolean accounts = false;
            while (res.next()) {
                Account account = new Account();
                account.setAccountId(res.getInt("id"));
                account.setAccountNumber(res.getLong("account_number"));

                System.out.print("Id:" + account.getAccountId());

                if (owner) {
                    System.out.print(" Account nr:" + account.getAccountNumber());
                    account.setBalance(res.getLong("balance"));
                    System.out.println(" Balance:" + account.getBalance());
                } else {
                    System.out.println(" Account nr:" + account.getAccountNumber());
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

    public static boolean getAllAccountsByName(User user) {
        try {
            String query = "SELECT * FROM accounts WHERE owner_id IN (SELECT id FROM users WHERE name = ?)";
            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, user.getName());
            ResultSet res = prepStatement.executeQuery();

            boolean accounts = false;
            while (res.next()) {
                Account account = new Account();
                account.setAccountId(res.getInt("id"));
                account.setAccountNumber(res.getLong("account_number"));

                System.out.print("Id:" + account.getAccountId());
                System.out.println(" Account nr:" + account.getAccountNumber());
                accounts = true;
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}