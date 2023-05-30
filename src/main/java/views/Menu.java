package views;

import controllers.UserController;
import models.DBConn;
import models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Menu extends DBConn {

    public static void menu() throws SQLException {
        Scanner scan = new Scanner(System.in);

        // TA BORT throws SQLException I MAIN SEN
        int id = 2; // Replace with the specific ID you want to query
        LocalDate startDate = LocalDate.of(2023, 1, 1); // Replace with the desired start date
        LocalDate endDate = LocalDate.of(2023, 12, 31); // Replace with the desired end date

        selectDataBetweenDates(id, startDate, endDate);

        while (true) {
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\nQ/Quit");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()) {
                case "1":
                    User user = User.logIn();
                    if (user != null) {
                        new UserView().loginMenu(user);
                    }
                    break;
                case "2":
                    UserController.createUser(DBConn.getConnection());
                    break;
                case "q", "quit":
                    System.exit(0);
                    break;
            }
        }
    }

    public static void selectDataBetweenDates(int id, LocalDate startDate, LocalDate endDate) throws SQLException {
        try {
            Connection connection = DBConn.getConnection();
            String query = "SELECT * FROM transactions "
                    + "WHERE sender_account_id = ? "
                    + "AND date_sent BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setObject(2, startDate);
            statement.setObject(3, endDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and process the data from the result set
                int transactionId = resultSet.getInt("id");
                // ... Retrieve other column values as needed
                LocalDate dateSent = resultSet.getObject("date_sent", LocalDate.class);

                System.out.println("Transaction ID: " + transactionId);
                System.out.println("Date Sent: " + dateSent);
                // ... Print other column values or perform desired processing
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
