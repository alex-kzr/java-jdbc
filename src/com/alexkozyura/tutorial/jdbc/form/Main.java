package com.alexkozyura.tutorial.jdbc.form;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = SQLiteConnection.getConnection()) {

            TableModel tableModel = new MyTableModel(connection, "dict_brand");

            JTable jTable = new JTable(tableModel);

            TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(tableModel);
            tableRowSorter.setComparator(2, new MyComparator());
            jTable.setRowSorter(tableRowSorter);

            MyTableRenderer cellRenderer = new MyTableRenderer();
            jTable.setDefaultRenderer(Object.class, cellRenderer);

            JScrollPane scroller = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JFrame jFrame = new JFrame("Загрузка данных в JTable");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.getContentPane().add(scroller);
            jFrame.pack();
            jFrame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
