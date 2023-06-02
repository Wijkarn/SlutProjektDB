package controllers;

import database.DBConn;
import models.Account;
import models.Transaction;
import models.User;
import views.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionsController extends DBConn {

    public static void makeTransaction(User user) {
        try {
            Transaction trans = new Transaction();
            System.out.println("How much do you want to send?");
            trans.setAmount(Integer.parseInt(UserView.getUserInput()));

            System.out.println("Who do you want to send to? (user name)");
            User receiverUser = new User();
            receiverUser.setName(UserView.getUserInput());
            if (AccountController.getAllAccountsByName(receiverUser)) {
                System.out.println("To what account? (id)");
                trans.setReceiverId(Integer.parseInt(UserView.getUserInput()));

                if (AccountController.getAllAccountsById(user, true)) {
                    Account senderAccount = new Account();
                    System.out.println("From what account? (id)");
                    senderAccount.setAccountId(Integer.parseInt(UserView.getUserInput()));
                    trans.setSenderId(senderAccount.getAccountId());

                    if (updateSenderBal(trans)) {
                        updateReceiverBal(trans);
                        String query = "INSERT INTO transactions (amount, receiver_account_id, sender_account_id) VALUES (?, ?, ?)";
                        Connection connection = DBConn.getConnection();
                        PreparedStatement prepStatement = connection.prepareStatement(query);

                        prepStatement.setInt(1, trans.getAmount());
                        prepStatement.setInt(2, trans.getReceiverId());
                        prepStatement.setInt(3, trans.getSenderId());

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

    public static boolean updateSenderBal(Transaction trans) {
        try {
            String query = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?;";

            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, trans.getAmount());
            prepStatement.setInt(2, trans.getSenderId());
            prepStatement.setInt(3, trans.getAmount());

            int rowsUpdated = prepStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Successful transaction!");
                return true;
            } else {
                System.out.println("Transaction failed. Insufficient funds or sender account not found.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateReceiverBal(Transaction trans) {
        try {
            String query = "UPDATE accounts SET balance = balance + ? WHERE id = ?;";

            Connection connection = DBConn.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, trans.getAmount());
            prepStatement.setInt(2, trans.getReceiverId());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void selectTransactionsBetweenDates(User user) {
        try {
            if (AccountController.getAllAccountsById(user, true)) {
                System.out.println("Which account do want to see your history?");
                Transaction trans = new Transaction();
                trans.setSenderId(Integer.parseInt(UserView.getUserInput()));
                System.out.println("Start date YYYYMMDD");
                LocalDate startDate = getDateObj();

                if (startDate != null) {
                    System.out.println("End date YYYYMMDD");
                    LocalDate endDate = getDateObj();
                    if (endDate != null) {

                        Connection connection = DBConn.getConnection();
                        String query = "SELECT * FROM transactions WHERE (sender_account_id = ? OR receiver_account_id = ?) AND date_sent BETWEEN ? AND ? ORDER BY date_sent ASC";
                        PreparedStatement prepStatement = connection.prepareStatement(query);
                        prepStatement.setInt(1, trans.getSenderId());
                        prepStatement.setInt(2, trans.getSenderId());
                        prepStatement.setObject(3, startDate);
                        prepStatement.setObject(4, endDate);
                        ResultSet resultSet = prepStatement.executeQuery();

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
                        prepStatement.close();
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
        String userInput = UserView.getUserInput().trim();

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