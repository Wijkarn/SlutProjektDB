package models;

import java.sql.*;
import java.util.Scanner;

public class Transactions {
    private int transactionId;
    private int receiverId;
    private int senderId;
    private int amount;
    private Timestamp date;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    public Transactions() {

    }

    public Transactions(int transactionId, int senderId, int receiverId, int amount, Timestamp date) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.date = date;
    }

    public static void makeTransaction(User user) {
        try {
            System.out.println("How much do you want to send?");
            Scanner scan = new Scanner(System.in);
            int transAmount = Integer.parseInt(scan.nextLine());

            System.out.println("Who do you want to send to? (user id)");
            if (getAccounts(Integer.parseInt(scan.nextLine()), true)) {
                System.out.println("To what account? (id)");
                int receiverAccountId = Integer.parseInt(scan.nextLine());

                System.out.println("From what account? (id)");
                if (getAccounts(user.getId(), false)) {
                    int senderAccount = Integer.parseInt(scan.nextLine());

                    String query = "INSERT INTO transactions (amount, receiver_account_id, sender_account_id) VALUES (?, ?, ?)";
                    Connection connection = DBConn.getConnection();
                    PreparedStatement prepStatement = connection.prepareStatement(query);

                    prepStatement.setInt(1, transAmount);
                    prepStatement.setInt(2, receiverAccountId);
                    prepStatement.setInt(3, senderAccount);

                    prepStatement.executeUpdate();

                    updateBal(transAmount, receiverAccountId);
                    updateBal(transAmount * (-1), senderAccount);
                    System.out.println("Transaction successful!");
                } else {
                    System.out.println("No accounts found!");
                }
            } else {
                System.out.println("User has no accounts!");
            }
            //Transactions transaction = new Transactions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean getAccounts(int id, boolean owner) {
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
                System.out.print(" Account nr:" + accountNr);
                if(owner){
                    long bal = res.getLong("balance");
                    System.out.println(" Balance:" + bal);
                }

                accounts = true;
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showTransactions(User user) {
        try {
            System.out.println("Startdate:");
            Scanner scan = new Scanner(System.in);
            String start = scan.nextLine();
            System.out.println("End date");
            String end = scan.nextLine();

            String query = "SELECT * FROM transactions WHERE sender_account_id = ? (SELECT date_sent FROM transactions WHERE date_sent >= ? AND date_sent < ?);";

            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, user.getId());
            prepStatement.setString(2, start);
            prepStatement.setString(3, end);

            ResultSet res = prepStatement.executeQuery();
            System.out.println(res);
            while (res.next()) {
                int bal = res.getInt("amount");
                int receiver = res.getInt("receiver_account_id");
                Timestamp date = res.getTimestamp("date");
                System.out.println("Amount: " + bal + " Receiver: " + receiver + " Date: " + date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateBal(int amount, int accountId) {
        try {
            String query = "UPDATE accounts SET balance = (balance + ?) WHERE id = ?;";

            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, amount);
            prepStatement.setInt(2, accountId);

            System.out.println(amount + " " + accountId);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
