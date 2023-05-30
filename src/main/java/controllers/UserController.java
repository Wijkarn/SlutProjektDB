package controllers;

import models.User;
import views.UserView;

import java.sql.Connection;

public class UserController {
    public static void createUser(Connection connection) {
        User user = UserView.getUserInput();
        user.add(connection);
    }
}