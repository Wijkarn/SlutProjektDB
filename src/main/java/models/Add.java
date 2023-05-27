package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Add extends DBConn {

    public static void addMenu(Scanner scan, int userId) {
        if (userId > 0) {
            loop:
            while (true) {
                System.out.println("What do you want to add?");
                String choice = scan.nextLine();
                switch (choice.toLowerCase()) {
                    case "1":
                        System.out.println("Add account");
                        addAccount(scan, userId);
                        break;
                    case "back":
                        break loop;
                }
            }
        } else if (userId == 0) {
            addUser(scan);
        } else {

        }
    }

    private static void addAccount(Scanner scan, int userId) {
        try {
            System.out.println("Account number:");
            String accNum = scan.nextLine();
            System.out.println("Balance:");
            String bal = scan.nextLine();
            String query = "INSERT INTO accounts (owner_id, account_number, balance) VALUES (?, ?, ?)";
            Connection connection = DBConn.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            long accNumInt = Long.parseLong(accNum);
            long balInt = Long.parseLong(bal);

            statement.setInt(1, userId);
            statement.setLong(2, accNumInt);
            statement.setLong(3, balInt);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addUser(Scanner scan) {
        try {
            System.out.println("Name:");
            String name = scan.nextLine();
            System.out.println("Personnummer: YYYYMMDDXXXX");
            String nummer = scan.nextLine();
            System.out.println("Email:");
            String email = scan.nextLine();
            System.out.println("Password:");
            String pw = scan.nextLine();
            System.out.println("Address:");
            String address = scan.nextLine();
            System.out.println("Phone nr:");
            String phone = scan.nextLine();

            Connection connection = DBConn.getConnection();

            String query = "INSERT INTO users (name, email, phone, address, password, personnummer) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, Password.hash(pw));
            statement.setString(6, nummer);

            int result = statement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
