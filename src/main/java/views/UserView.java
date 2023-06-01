package views;

import java.util.Scanner;

import controllers.AccountController;
import controllers.TransactionsController;
import controllers.UserController;
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

    public void loginMenu(User user) {

        while (true) {
            System.out.println("What do you want to do?\n1: Make a transaction\n2: Add bank account\n3: Change user credentials\n4: Show transactions\n5: Delete bank account\n6: View credentials\n10: Log out\n69: Delete user");

            switch (new Scanner(System.in).nextLine().toLowerCase()) {
                case "1":
                    TransactionsController.makeTransaction(user);
                    break;
                case "2":
                    AccountController.addAccount(user);
                    break;
                case "3":
                    userChangeMenu(user);
                    break;
                case "4":
                    TransactionsController.selectTransactionsBetweenDates(user);
                    break;
                case "5":
                    AccountController.deleteAccount(user);
                    break;
                case "6":
                    viewCredentials(user);
                    break;
                case "10":
                    return;
                case "69":
                    UserController.deleteUser(user);
                    AccountController.deleteAllAccounts(user);
                    return;
            }
        }
    }

    private void userChangeMenu(User user) {
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("What do you want to change?\n1: Phone nr\n2: Address\n3: Email\n4: Password");
            switch (scan.nextLine()) {
                case "1":
                    UserController.changeInfo("phone", user);
                    break;
                case "2":
                    UserController.changeInfo("address", user);
                    break;
                case "3":
                    UserController.changeInfo("email", user);
                    break;
                case "4":
                    UserController.changeInfo("password", user);
                    break;
            }
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
        AccountController.getAccounts(user.getId(), true);
        System.out.println(" ");
    }
}