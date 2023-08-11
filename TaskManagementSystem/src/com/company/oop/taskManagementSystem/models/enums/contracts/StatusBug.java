package com.company.oop.taskManagementSystem.models.enums.contracts;

import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public interface StatusBug {

    StatusValues[] allowedValues = {StatusValues.ACTIVE, StatusValues.FIXED};

}
