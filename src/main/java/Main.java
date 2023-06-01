import database.CreateTables;
import views.Menu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            CreateTables.createTables();
            Menu.menu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}