package com.alexkozyura.tutorial.jdbc.form;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = SQLiteConnection.getConnection()) {

            System.out.println(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
