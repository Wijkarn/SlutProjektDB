package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Add {

    public static void addMenu(Scanner scan, int userId) {
        if (userId > 0) {
            System.out.println("What do you want to add?");
        } else if (userId == 0) {
            addUser(scan);
        } else {

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
