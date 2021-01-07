package com.alexkozyura.tutorial.jdbc.form;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = SQLiteConnection.getConnection()) {

            TableModel tableModel = new MyTableModel(connection, "dict_brand");

            JTable jTable = new JTable(tableModel);

            TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(tableModel);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}