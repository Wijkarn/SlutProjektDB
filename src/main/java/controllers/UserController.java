package controllers;

import database.DBConn;
import hashing.Password;
import models.User;
import views.UserView;

import java.sql.*;
import java.util.Scanner;

public class UserController extends DBConn {
    public static void createUser() {
        User user = UserView.getUserInput();
        add(user);
    }

    public static void add(User user) {
        try {
            Connection connection = DBConn.getConnection();
            String query = "INSERT INTO users (name, email, phone, address, password, personnummer) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddress());
            statement.setString(5, Password.hash(user.getPassword()));
            statement.setString(6, user.getPersonnummer());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User logIn() {
        Scanner input = new Scanner(System.in);
        try {
            String checkPassword = "SELECT * FROM users WHERE personnummer = ?;";

            System.out.println("Log In");
            System.out.println("Personnummer:");
            String personnummer = input.nextLine();

            System.out.println("Password:");
            String password = input.nextLine();

            Connection connection = DBConn.getConnection();
            PreparedStatement checkPw = connection.prepareStatement(checkPassword);
            checkPw.setString(1, personnummer);
            ResultSet pw = checkPw.executeQuery();

            if (pw.next()) {
                String hashPassword = pw.getString("password");

                if (Password.Verify(password, hashPassword)) {
                    System.out.println("Login successful!");
                    int userId = pw.getInt("id");
                    String name = pw.getString("name");
                    String adress = pw.getString("address");
                    String phone = pw.getString("phone");
                    String ssn = pw.getString("personnummer");
                    String email = pw.getString("email");
                    Timestamp created = pw.getTimestamp("created");

                    System.out.println("Welcome " + name + "!");
                    connection.close();

                    return new User(userId, name, email, password, phone, adress, ssn, created);
                } else {
                    System.out.println("Wrong password!");
                }
            } else {
                System.out.println("Personnummer does not exist!");

            }
            connection.close();
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(User user) {
        try {
            System.out.println("Are you sure you want to delete your user account and all other data in your account? Y/N WARNING THIS CAN NOT BE UNDONE!");
            Scanner scan = new Scanner(System.in);
            if ("y".equalsIgnoreCase(scan.nextLine())) {
                String query = "DELETE FROM users WHERE id = ?";
                Connection connection = DBConn.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, user.getId());
                statement.executeUpdate();

                statement.close();
                connection.close();
                System.out.println("Successfully deleted user account!");
            } else {
                System.out.println("Delete aborted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeInfo(String choice, User user) {
        try {
            System.out.println("Are you sure you want to delete " + choice + "?");
            Scanner scan = new Scanner(System.in);
            if ("y".equalsIgnoreCase(scan.nextLine())) {
                System.out.println("New " + choice + ":");
                String newData = scan.nextLine();

                String query = "UPDATE users SET " + choice + " = ? WHERE id=?;";
                Connection connection = DBConn.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                if ("password".equalsIgnoreCase(choice)) {
                    statement.setString(1, Password.hash(newData));
                } else if ("phone".equalsIgnoreCase(choice)) {
                    statement.setLong(1, Long.parseLong(newData));
                } else {
                    statement.setString(1, newData);
                }
                statement.setInt(2, user.getId());

                statement.executeUpdate();

                statement.close();
                connection.close();
                System.out.println("Successfully updated " + choice + "! New " + choice + ":" + newData);
            } else {
                System.out.println("Aborting update");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}