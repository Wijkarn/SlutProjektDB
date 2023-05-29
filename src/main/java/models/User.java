package models;

import java.sql.*;
import java.util.Scanner;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private Timestamp created;

    private String personnummer;

    public User() {

    }

    public User(String personnummer, String password) {
        this.password = password;
        this.personnummer = personnummer;
    }

    public User(int id, String name, String email, String password, String phone, String address, String personnummer, Timestamp created) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.personnummer = personnummer;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPersonnummer(String personnummer) {
        this.personnummer = personnummer;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void add(Connection connection) {
        try {
            String query = "INSERT INTO users (name, email, phone, address, password, personnummer) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, Password.hash(password));
            statement.setString(6, personnummer);

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
}
