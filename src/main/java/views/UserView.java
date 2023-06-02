package views;

import java.util.Scanner;

import controllers.AccountController;
import controllers.TransactionsController;
import controllers.UserController;
import models.User;

public class UserView {

    static Scanner scan = new Scanner(System.in);

    public static String getUserInput() {
        return scan.nextLine();
    }

    public static User setUserCredentials() {
        User user = new User();

        System.out.print("Name: ");
        user.setName(getUserInput());

        System.out.print("Personnummer: ");
        user.setPersonnummer(getUserInput());

        System.out.print("Phone: ");
        user.setPhone(getUserInput());

        System.out.print("Email: ");
        user.setEmail(getUserInput());

        System.out.print("Address: ");
        user.setAddress(getUserInput());

        System.out.print("Password: ");
        user.setPassword(getUserInput());

        return user;
    }

    public void loginMenu(User user) {

        while (true) {
            System.out.println("What do you want to do?\n1: Make a transaction\n2: Add bank account\n3: Change user credentials\n4: Show transactions\n5: Delete bank account\n6: View credentials\n10: Log out\n69: Delete user");
            switch (getUserInput()) {
                case "1" -> TransactionsController.makeTransaction(user);
                case "2" -> AccountController.addAccount(user);
                case "3" -> userChangeMenu(user);
                case "4" -> TransactionsController.selectTransactionsBetweenDates(user);
                case "5" -> AccountController.deleteAccount(user);
                case "6" -> viewCredentials(user);
                case "10" -> {
                    // Logout
                    return;
                }
                case "69" -> {
                    // Delete user
                    if (UserController.deleteUser(user)) {
                        AccountController.deleteAllAccounts(user);
                        return;
                    }
                }
            }
        }
    }

    private void userChangeMenu(User user) {
        try {
            while (true) {
                String[] target = {"phone", "address", "email", "password"};
                //System.out.println("What do you want to change?\n1: Phone nr\n2: Address\n3: Email\n4: Password\n5: Back");
                System.out.println("What do you want to change?");
                int i;
                for (i = 0; i < target.length; i++) {
                    System.out.println(i + 1 + ": " + target[i]);
                }
                System.out.println(i + 1 + ": Back");
                int choice = Integer.parseInt(getUserInput()) - 1;
                if (choice < target.length) {
                    UserController.changeInfo(target[choice], user);
                } else if (choice == target.length) {
                    break;
                } else {
                    System.out.println("Invalid input.");
                }
            }
        } catch (Exception ignored) {
            System.out.println("Something went wrong, please try again.");
        }
    }

    private static void viewCredentials(User user) {
        System.out.println("User id: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Personnummer: " + user.getPersonnummer());
        System.out.println("Phone nr: " + user.getPhone());
        System.out.println("Email: " + user.getEmail());
        System.out.println("User account created: " + user.getCreated());
        System.out.println("Bank accounts: ");
        AccountController.getAllAccountsById(user, true);
        System.out.println(" ");
    }
}