package com.alexkozyura.tutorial.jdbc.to_file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

                resultBuilder.append("\n");
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

            writeTextToFile(resultBuilder.toString());

            System.out.println(resultBuilder);

            DBUtils.closeConnection();

        } catch (Exception e){

            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static void writeTextToFile(String str){

        try {

            Writer fileWriter = new FileWriter("result.txt");
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e){

            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
