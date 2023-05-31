package models;

import java.sql.*;
import java.time.LocalDate;
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
                if (owner) {
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

    public static void selectTransactionsBetweenDates(User user) {
        try {
            System.out.println("Which account do want to see your history?");

            if (getAccounts(user.getId(), true)) {
                Scanner scan = new Scanner(System.in);
                int accountId = scan.nextInt();
                System.out.println("Start date YYYYMMDD");
                LocalDate startDate = getDateObj();

                if (startDate != null) {
                    System.out.println("End date YYYYMMDD");
                    LocalDate endDate = getDateObj();
                    if (endDate != null) {

                        Connection connection = DBConn.getConnection();
                        String query = "SELECT * FROM transactions WHERE sender_account_id = ? AND date_sent BETWEEN ? AND ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, accountId);
                        statement.setObject(2, startDate);
                        statement.setObject(3, endDate);
                        ResultSet resultSet = statement.executeQuery();

                        System.out.println("----------------");
                        while (resultSet.next()) {
                            int transactionId = resultSet.getInt("id");
                            LocalDate dateSent = resultSet.getObject("date_sent", LocalDate.class);
                            int amount = resultSet.getInt("amount");
                            int receiverId = resultSet.getInt("receiver_account_id");


                            System.out.println("Transaction ID: " + transactionId);
                            System.out.println("Date Sent: " + dateSent);
                            System.out.println("Amount: " + amount);
                            System.out.println("Your account id: " + accountId);
                            System.out.println("Receiver account id: " + receiverId);
                            System.out.println("----------------");
                            // ... Print other column values or perform desired processing
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
