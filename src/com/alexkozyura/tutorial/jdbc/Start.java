package com.alexkozyura.tutorial.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alex Kozyura (mail@alexkozyura.com) on 30.10.2020
 */

public class Start {
    public static void main(String[] args){

        try {

            Scanner scanner = new Scanner(new FileInputStream("sql.txt"), "UTF-8");

            String dbPath = scanner.nextLine();
            System.out.println("DB path: " + dbPath);

            if(!new File(dbPath).exists()){
                System.out.println("Файл базы данных не найден!");
                return;
            }

            StringBuilder requestsBuilder = new StringBuilder();
            try {
                while (scanner.hasNextLine()){
                    requestsBuilder.append(scanner.nextLine());
                }
            } finally {
                scanner.close();
            }

            String[] sqlRequests = requestsBuilder.toString().split(";");
            for (int i = 0; i < sqlRequests.length; i++) {
                sqlRequests[i] = sqlRequests[i] + ";";
            }

            StringBuilder resultBuilder = new StringBuilder();

            DBUtils.openConnection(dbPath);

            for (String sqlRequest : sqlRequests){
                resultBuilder.append("Request : " + sqlRequest + "\n");
                resultBuilder.append("Response : " + "\n");
                TableObject table = DBUtils.getResultTable(sqlRequest);

                for(String header : table.getHeaders()){
                    resultBuilder.append(header + ", ");
                }
                resultBuilder.append("\n");

                for(TableRow tableRow: table.getTableRows()){
                    for(Object cell : tableRow.getTableRow()){
                        resultBuilder.append(cell + ", ");
                    }
                    resultBuilder.append("\n");
                }
            }

            System.out.println(resultBuilder);

        } catch (Exception e){

            e.printStackTrace();
        }




//        Connection connection = null; // connection to db
//        Statement statement = null; // store and execute sql requests
//        ResultSet resultSet = null; // get responses result
//
//        try {
//
//
//            connection = DriverManager.getConnection(url);
//
//            // prepare SQL request
//            String  sql = "SELECT * FROM dict_model";
//            statement = connection.createStatement();
//
//            // execute SQL request
//            resultSet = statement.executeQuery(sql);
//
//
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet!=null) resultSet.close();
//                if (statement!=null) statement.close();
//                if (connection!=null) statement.close();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
}
