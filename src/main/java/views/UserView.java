package views;

import java.util.Scanner;

import models.Account;
import models.Transactions;
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
            System.out.println("What do you want to do?\n1: Make a transaction\n2: Add bank account\n3: Change user credentials\n4: Show transactions\n5: Delete bank account\n10: Log out\n69: Delete user");

            switch (new Scanner(System.in).nextLine().toLowerCase()) {
                case "1":
                    Transactions.makeTransaction(user);
                    break;
                case "2":
                    Account.addAccount(user);
                    break;
                case "3":
                    userChangeMenu(user);
                    break;
                case "4":
                    Transactions.selectTransactionsBetweenDates(user);
                    break;
                case "5":
                    Account.deleteAccount(user);
                    break;
                case "10":
                    return;
                case "69":
                    User.deleteUser(user);
                    Account.deleteAllAccounts(user);
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
                    User.changeInfo("phone", user);
                    break;
                case "2":
                    User.changeInfo("address", user);
                    break;
                case "3":
                    User.changeInfo("email", user);
                    break;
                case "4":
                    User.changeInfo("password", user);
                    break;
            }
        }
    }
}