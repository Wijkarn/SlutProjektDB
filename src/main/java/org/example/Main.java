package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            CreateTables.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}