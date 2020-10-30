package com.alexkozyura.tutorial.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex Kozyura (mail@alexkozyura.com) on 30.10.2020
 */

public class DBUtils {

    private static Connection connection;

    public static void openConnection(String path){

        try{

            // dynamic sqlite driver registration
            Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();

            // create connection to DB
            String url = "jdbc:sqlite:" + path;

            if(connection == null){
                connection = DriverManager.getConnection(url);
            }

        } catch (Exception e){

            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static TableObject getResultTable(String sqlRequest){

        TableObject table = new TableObject();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()){

                // process response
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                ArrayList<String> tableHeaders = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++){
                    tableHeaders.add(metaData.getColumnName(i));
                }
                table.setHeaders(tableHeaders);

                ArrayList<TableRow> tableRows = new ArrayList<>();
                while (resultSet.next()){

                    ArrayList<Object> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++){
                        row.add(resultSet.getObject(i));
                    }
                    TableRow tableRow = new TableRow();
                    tableRow.setTableRow(row);
                    tableRows.add(tableRow);
                }
                table.setTableRows(tableRows);
            }

        } catch (Exception e){

            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return table;
    }

    public static void closeConnection(){

        try{

            if (connection != null){
                connection.close();
            }
        } catch (Exception e) {

            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
