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
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            String tableHeader = "";
            for(int i = 1; i <= columnCount; i++){
                tableHeader += metaData.getColumnName(i) + ", ";
            }
            System.out.println(tableHeader);

            while (resultSet.next()){

                String tableRow = "";
                for (int i = 1; i <= columnCount; i++){
                    tableRow += resultSet.getString(i) + ", ";
                }
                System.out.println(tableRow);
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
