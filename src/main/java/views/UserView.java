package views;

import java.util.Scanner;

import models.Account;
import models.User;

public class UserView {
    public static User getUserInput() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.print("Name: ");
        user.setName(scanner.nextLine());

        System.out.print("Personnummer: ");
        user.setPersonnummer(scanner.nextLine());

        System.out.print("Phone: ");
        user.setPhone(scanner.nextLine());

        System.out.print("Email: ");
        user.setEmail(scanner.nextLine());

        System.out.print("Address: ");
        user.setAddress(scanner.nextLine());

        System.out.print("Password: ");
        user.setPassword(scanner.nextLine());

        return user;
    }

    public void landingPage(User user) {

        while (true) {
            System.out.println("What do you want to do?\n1: Add account\n2: Make a transaction\n3: Change user credentials\n4: Log out");

            switch (new Scanner(System.in).nextLine()) {
                case "1" -> {
                    System.out.println("Add account skapad!");
                    Account.addAccount(user);
                }
                //case "2" ->
                //Add.addMenu(scan, 0);
                // UserController.createUser(DBConn.getConnection());
                case "4" -> {
                    return;
                }
            }

        }
    }
}