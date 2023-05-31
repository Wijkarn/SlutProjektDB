package views;

import controllers.UserController;
import models.DBConn;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu extends DBConn {

    public static void menu() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\nQ/Quit");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()) {
                case "1":
                    User user = User.logIn();
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
