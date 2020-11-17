package com.alexkozyura.tutorial.jdbc.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private static Connection connection;

    public static Connection getConnection() {

        String url = "jdbc:sqlite:car-shop.db";

        try {
            if  (connection == null) {
                connection = DriverManager.getConnection(url);
            }

            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
