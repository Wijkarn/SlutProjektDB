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
            System.out.println("What do you want to do?\n1: Add bank account\n2: Make a transaction\n3: Change user credentials\n4: Show transactions\n10: Log out");

            switch (new Scanner(System.in).nextLine()) {
                case "1":
                    Account.addAccount(user);
                    break;
                case "2":
                    Transactions.makeTransaction(user);
                    break;
                case "3":

                    break;
                case "4":
                    //Transactions.showTransactions(user);
                    Transactions.selectDataBetweenDates(user);
                    break;
                case "10":
                    return;
            }
        }
    }
}