package com.alexkozyura.tutorial.jdbc.form;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTableModel extends AbstractTableModel {

    private static Connection connection;

    private Object[][] tableContent;
    private String[] columnNames;
    private Class[] columnClasses;

    public MyTableModel(Connection connection, String tableName) throws SQLException {
        super();
        MyTableModel.connection = connection;
        getTableContent(tableName);
    }

    private void getTableContent(String tableName) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

        ArrayList columnNamesList = new ArrayList();
        ArrayList columnTypesList = new ArrayList();

        while (resultSet.next()) {

            columnNamesList.add(resultSet.getString("COLUMN_NAME"));

            int columnDataType = resultSet.getInt("DATA_TYPE");

            switch (columnDataType) {
                case Types.INTEGER:
                    columnTypesList.add(Integer.class);
                    break;
                case Types.FLOAT:
                    columnTypesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.REAL:
                    columnTypesList.add(Double.class);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    columnTypesList.add(java.sql.Date.class);
                    break;
                default:
                    columnTypesList.add(String.class);
                    break;
            }
        }

        columnNames = new String[columnNamesList.size()];
        columnNamesList.toArray(columnNames);

        columnClasses = new Class[columnTypesList.size()];
        columnTypesList.toArray(columnClasses);

        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM " + tableName);

        ArrayList rowList = new ArrayList();

        while (resultSet.next()) {

            ArrayList cellList = new ArrayList();

            for (int i = 0; i < columnClasses.length; i++) {

                Object cellValue = null;

                if (columnClasses[i] == String.class) {
                    cellValue = resultSet.getString(columnNames[i]);
                } else if (columnClasses[i] == Integer.class) {
                    cellValue = new Integer(resultSet.getInt(columnNames[i]));
                } else if (columnClasses[i] == Float.class) {
                    cellValue = new Float(resultSet.getFloat(columnNames[i]));
                } else if (columnClasses[i] == Double.class) {
                    cellValue = new Double(resultSet.getDouble(columnNames[i]));
                } else if (columnClasses[i] == java.sql.Date.class) {
                    cellValue = resultSet.getDate(columnNames[i]);
                } else {
                    System.out.println("Can't define column type " + columnNames[i]);
                }

                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);
        }

        tableContent = new Object[rowList.size()][];
        for (int i = 0; i < tableContent.length; i++) {
            tableContent[i] = (Object[]) rowList.get(i);
        }

        if (resultSet!=null) resultSet.close();
        if (statement!=null) statement.close();
    }

    public boolean updateDB(String tableName) {

        ArrayList<String> sqlList = new ArrayList<>();

        for (int i = 0; i < tableContent.length; i++) {
            Object[] objects = tableContent[i];
            sqlList.add("update " + tableName + " set name_ru='" + objects[1] + "', name_en='" + objects[2] + "' where id=" + objects[0] + ";");
        }

        Statement statement = null;

        try {

            statement = connection.createStatement();

            for (String sql : sqlList) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {

            Logger.getLogger(MyTableModel.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {

            try {

                if (statement!=null) statement.close();
            } catch (SQLException e) {

                Logger.getLogger(MyTableModel.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return true;
    }

    @Override
    public int getRowCount() {
        return tableContent.length;
    }

    @Override
    public int getColumnCount() {

        if (tableContent.length == 0) {
            return 0;
        } else {
            return tableContent[0].length;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableContent[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        tableContent[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex,columnIndex);
    }

    @Override
    public Class getColumnClass(int column) {
        return columnClasses[column];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return false;
        }

        return true;
    }
}
