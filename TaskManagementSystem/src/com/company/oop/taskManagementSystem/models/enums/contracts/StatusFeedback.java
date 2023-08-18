package com.company.oop.taskManagementSystem.models.enums.contracts;

import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public interface StatusFeedback {
    StatusValues[] allowedValues = {StatusValues.NEW, StatusValues.UNSCHEDULED, StatusValues.SCHEDULED, StatusValues.DONE};

}
