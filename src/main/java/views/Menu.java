package views;

import controllers.UserController;
import models.DBConn;
import models.User;

import java.util.Scanner;

public class Menu extends DBConn{

    public static void menu() {
        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\nQ/Quit");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()) {
                case "1" -> {
                    User user = User.logIn();
                    if(user != null){
                        new UserView().landingPage(user);
                    }
                }
                //Add.addMenu(scan, User.login(scan));
                case "2" ->
                    //Add.addMenu(scan, 0);
                        UserController.createUser(DBConn.getConnection());
                case "q", "quit" -> System.exit(0);
            }
        }
    }
}
