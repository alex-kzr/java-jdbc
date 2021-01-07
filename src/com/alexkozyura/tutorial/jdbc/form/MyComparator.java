package com.alexkozyura.tutorial.jdbc.form;

import java.util.Comparator;

public class MyComparator implements Comparator {

    @Override
    public int compare(Object object1, Object object2) {

        Double double1 = Double.parseDouble(object1.toString());
        Double double2 = Double.parseDouble(object2.toString());

        if (double1 < double2) {
            return 1;
        } else if (double1 > double2) {
            return -1;
        } else {
            return 0;
        }
    }
}
