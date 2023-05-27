package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LogIn extends DBConn {

    public static int login(Scanner scan) {
        try {
            String checkPassword = "SELECT password FROM users WHERE personnummer= ?";

            System.out.println("Log In");
            System.out.println("Personnummer:");
            String username = scan.nextLine();

            System.out.println("Password:");
            String password = scan.nextLine();

            Connection connection = DBConn.getConnection();
            PreparedStatement checkPw = connection.prepareStatement(checkPassword);
            checkPw.setString(1, username);
            ResultSet pw = checkPw.executeQuery();
            if (pw.next()) {
                String hashPassword = pw.getString("password");

                if (Password.Verify(password, hashPassword)) {
                    String idQuery = "SELECT id FROM users WHERE personnummer = ?;";
                    PreparedStatement getId = connection.prepareStatement(idQuery);
                    getId.setString(1, username);

                    ResultSet result = getId.executeQuery();
                    if (result.next()) {
                        System.out.println("Login successful!");
                        int userId = result.getInt("id");
                        connection.close();
                        System.out.println(userId);
                        return userId;
                    } else {
                        failedLogIn(scan);
                    }
                } else {
                    failedLogIn(scan);
                }
            } else {
                failedLogIn(scan);
            }
            connection.close();
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void failedLogIn(Scanner scan) {
        System.out.println("Wrong personnummer or password!");
        login(scan);
    }
}
