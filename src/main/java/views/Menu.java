package views;

import controllers.UserController;
import database.DBConn;
import models.User;

import java.util.Scanner;

public class Menu extends DBConn {

    public static void menu() {

        while (true) {
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\n3: Quit");
            switch (new Scanner(System.in).nextLine()) {
                case "1" -> {

                    User user = UserController.logIn();
                    if (user != null) {
                        new UserView().loginMenu(user);
                    }
                }
                case "2" -> UserController.createUser();
                case "3" -> {
                    return;
                }
                default -> System.out.println("Something went wrong, please try again.");
            }
        }
    }
}
