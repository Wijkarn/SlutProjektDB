package models;

import database.DBConn;

import java.sql.*;
import java.util.Scanner;

public class Account extends DBConn{

    private int accountId;
    private int ownerId;
    private long balance;
    private String accountNumber;
    private Timestamp created;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Account() {

    }

    public Account(int accountId, int ownerId, int balance, String accountNumber, Timestamp created) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.created = created;
    }

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
            if (getAccounts(user.getId(), true)) {
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

    public static boolean getAccounts(int id, boolean owner) {
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
}
