package models;

import database.DBConn;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Transactions extends DBConn{
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
            if (Account.getAccounts(Integer.parseInt(scan.nextLine()), false)) {
                System.out.println("To what account? (id)");
                int receiverAccountId = Integer.parseInt(scan.nextLine());

                System.out.println("From what account? (id)");
                if (Account.getAccounts(user.getId(), true)) {
                    int senderAccount = Integer.parseInt(scan.nextLine());
                    boolean yeet = updateSenderBal(transAmount, senderAccount);

                    if (yeet) {
                        updateReceiverBal(transAmount, receiverAccountId);
                        String query = "INSERT INTO transactions (amount, receiver_account_id, sender_account_id) VALUES (?, ?, ?)";
                        Connection connection = DBConn.getConnection();
                        PreparedStatement prepStatement = connection.prepareStatement(query);

                        prepStatement.setInt(1, transAmount);
                        prepStatement.setInt(2, receiverAccountId);
                        prepStatement.setInt(3, senderAccount);

                        prepStatement.executeUpdate();
                    }
                } else {
                    System.out.println("No accounts found!");
                }
            } else {
                System.out.println("User has no accounts!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateSenderBal(int amount, int accountId) {
        try {
            String query = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?;";

            Connection connection = DBConn.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, amount);
            prepStatement.setInt(2, accountId);
            prepStatement.setInt(3, amount);

            int rowsUpdated = prepStatement.executeUpdate();
            if (rowsUpdated > 0) {
                // Commit the transaction if the update was successful
                connection.commit();
                System.out.println("Successful transaction!");
                connection.setAutoCommit(true);
                return true;
            } else {
                // Rollback the transaction if the update failed (insufficient funds or sender account not found)
                connection.rollback();
                System.out.println("Transaction failed. Insufficient funds or sender account not found.");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateReceiverBal(int amount, int accountId) {
        try {
            String query = "UPDATE accounts SET balance = balance + ? WHERE id = ?;";

            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, amount);
            prepStatement.setInt(2, accountId);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void selectTransactionsBetweenDates(User user) {
        try {
            System.out.println("Which account do want to see your history?");

            if (Account.getAccounts(user.getId(), true)) {
                Scanner scan = new Scanner(System.in);
                int accountId = scan.nextInt();
                System.out.println("Start date YYYYMMDD");
                LocalDate startDate = getDateObj();

                if (startDate != null) {
                    System.out.println("End date YYYYMMDD");
                    LocalDate endDate = getDateObj();
                    if (endDate != null) {

                        Connection connection = DBConn.getConnection();
                        String query = "SELECT * FROM transactions WHERE (sender_account_id = ? OR receiver_account_id = ?) AND date_sent BETWEEN ? AND ? ORDER BY date_sent ASC";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, accountId);
                        statement.setInt(2, accountId);
                        statement.setObject(3, startDate);
                        statement.setObject(4, endDate);
                        ResultSet resultSet = statement.executeQuery();

                        System.out.println("----------------");
                        while (resultSet.next()) {
                            int transactionId = resultSet.getInt("id");
                            LocalDate dateSent = resultSet.getObject("date_sent", LocalDate.class);
                            int amount = resultSet.getInt("amount");
                            int receiverId = resultSet.getInt("receiver_account_id");
                            int senderId = resultSet.getInt("sender_account_id");

                            System.out.println("Transaction ID: " + transactionId);
                            System.out.println("Date Sent: " + dateSent);
                            System.out.println("Amount: " + amount);
                            System.out.println("Sender account id: " + senderId);
                            System.out.println("Receiver account id: " + receiverId);
                            System.out.println("----------------");
                        }

                        resultSet.close();
                        statement.close();
                        connection.close();
                    } else {
                        System.out.println("Invalid End Date!");
                    }
                } else {
                    System.out.println("Invalid Start Date!");
                }
            } else {
                System.out.println("No accounts found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate getDateObj() {
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();

        if (userInput.length() >= 8 && !userInput.matches(".*[a-z].*")) {
            String fullDate = userInput.replace("-", "");
            fullDate = fullDate.replace("/", "");
            int year = Integer.parseInt(fullDate.substring(0, 4));
            int month = Integer.parseInt(fullDate.substring(4, 6));
            int day = Integer.parseInt(fullDate.substring(6, 8));

            return LocalDate.of(year, month, day);
        }
        return null;
    }
}
