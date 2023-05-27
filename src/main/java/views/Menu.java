package views;

import models.Add;
import models.LogIn;

import java.util.Scanner;

public class Menu {

    public static void menu(){
        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.println("What do you want to do?\n1: Log in\n2: Create new user\nQ/Quit");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()){
                case "1":
                    Add.addMenu(scan, LogIn.login(scan));
                    break;
                case "2":
                    Add.addMenu(scan, 0);
                    break;
                case "q", "quit":
                    System.exit(0);
            }
        }
    }
}
