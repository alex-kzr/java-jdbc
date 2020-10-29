package com.alexkozyura.tutorial.jdbc;

import java.sql.*;

/**
 * Created by Alex Kozyura (mail@alexkozyura.com) on 30.10.2020
 */

public class Start {
    public static void main(String[] args){

        Connection connection = null; // connection to db
        Statement statement = null; // store and execute sql requests
        ResultSet resultSet = null; // get responses result

        try {

            // dynamic sqlite driver registration
            Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
            // DriverManager.registerDriver(driver);

            // create connection to DB
            String url = "jdbc:sqlite:e:\\Docs\\workspace\\Java\\java-jdbc\\car-shop.db";
            connection = DriverManager.getConnection(url);

            // prepare SQL request
            String  sql = "SELECT * FROM dict_model";
            statement = connection.createStatement();

            // execute SQL request
            resultSet = statement.executeQuery(sql);

            // process result
            while (resultSet.next()){
                System.out.println(resultSet.getString("dmd_name_en") + " - " + resultSet.getObject("dmd_name_ru"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (resultSet!=null) resultSet.close();
                if (statement!=null) statement.close();
                if (connection!=null) statement.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
