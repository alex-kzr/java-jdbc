package com.alexkozyura.tutorial.jdbc.form;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class MyTableRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jTable,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {

        if (isSelected)  {
            setBackground(Color.ORANGE);
            setForeground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        if (value.toString().contains("@")) setValue("<html><a href=\"#\">" + value + "</href></html>");

        return this;
    }
}
