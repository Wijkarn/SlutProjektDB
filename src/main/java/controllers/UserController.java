package controllers;

import models.User;
import views.UserView;

public class UserController {
    public static void createUser() {
        User user = UserView.getUserInput();
        user.add();
    }
}