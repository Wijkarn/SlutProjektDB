package controllers;

import database.DBConn;
import hashing.Password;
import models.User;
import views.UserView;

import java.sql.*;

public class UserController extends DBConn {
    public static void createUser() {
        addUser(UserView.setUserCredentials());
    }

    public static void addUser(User user) {
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

            if (statement.executeUpdate() > 0) System.out.println("Successfully created user!");
        } catch (SQLException ignored) {
            System.out.println("An error occurred creating user.");
        }
    }

    public static User logIn() {
        try {
            String checkPassword = "SELECT * FROM users WHERE personnummer = ?;";
            Connection connection = DBConn.getConnection();
            PreparedStatement checkPw = connection.prepareStatement(checkPassword);
            System.out.println("Log In");
            System.out.println("Personnummer:");
            checkPw.setString(1, UserView.getUserInput());
            System.out.println("Password:");
            String password = UserView.getUserInput();

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

                    return new User(userId, name, email, hashPassword, phone, adress, ssn, created);
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

    public static boolean deleteUser(User user) {
        try {
            System.out.println("Are you sure you want to delete your user account and all other data in your account? Y/N WARNING THIS CAN NOT BE UNDONE!");
            if ("y".equalsIgnoreCase(UserView.getUserInput())) {
                String query = "DELETE FROM users WHERE id = ?";
                Connection connection = DBConn.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, user.getId());
                statement.executeUpdate();

                statement.close();
                connection.close();
                System.out.println("Successfully deleted user account!");
                return true;
            } else {
                System.out.println("Delete aborted!");
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeInfo(String choice, User user) {
        try {
            System.out.println("Are you sure you want to update " + choice + "? Y/N");
            if ("y".equalsIgnoreCase(UserView.getUserInput())) {
                System.out.println("New " + choice + ":");
                String newData = UserView.getUserInput();

                String query = "UPDATE users SET " + choice + " = ? WHERE id=?;";
                Connection connection = DBConn.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                switch (choice) {
                    case "password" -> {
                        user.setPassword(Password.hash(newData));
                        statement.setString(1, user.getPassword());
                    }
                    case "phone" -> {
                        user.setPhone(newData);
                        statement.setString(1, user.getPhone());
                    }
                    case "address" -> {
                        user.setAddress(newData);
                        statement.setString(1, user.getAddress());
                    }
                    case "email" -> {
                        user.setEmail(newData);
                        statement.setString(1, user.getEmail());
                    }
                }
                statement.setInt(2, user.getId());

                statement.executeUpdate();

                connection.close();
                System.out.println("Successfully updated " + choice + "!");
            } else {
                System.out.println("Aborting update");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}