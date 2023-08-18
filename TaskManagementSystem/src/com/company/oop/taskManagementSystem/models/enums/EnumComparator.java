package com.company.oop.taskManagementSystem.models.enums;

import java.util.Comparator;

public class EnumComparator implements Comparator<StatusValues> {
    @Override
    public int compare(StatusValues o1, StatusValues o2) {
        return o1.getLabel().compareTo(o2.getLabel());
    }
}

