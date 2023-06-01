package views;

import controllers.UserController;
import database.DBConn;
import models.User;

import java.util.Scanner;

public class Menu extends DBConn {

    public static void menu() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\nQ/Quit");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()) {
                case "1":
                    User user = UserController.logIn();
                    if (user != null) {
                        new UserView().loginMenu(user);
                    }
                    break;
                case "2":
                    UserController.createUser();
                    break;
                case "q", "quit":
                    System.exit(0);
                    break;
            }
        }
    }
}
