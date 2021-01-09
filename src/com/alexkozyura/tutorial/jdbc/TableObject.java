package com.alexkozyura.tutorial.jdbc;

import java.util.ArrayList;

/**
 * Created by Alex Kozyura (mail@alexkozyura.com) on 30.10.2020
 */

public class TableObject {

    private ArrayList<String> headers;
    private ArrayList<TableRow> tableRows;

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public ArrayList<TableRow> getTableRows() {
        return tableRows;
    }

    public void setTableRows(ArrayList<TableRow> tableRows) {
        this.tableRows = tableRows;
    }

    public TableObject() {
    }

}