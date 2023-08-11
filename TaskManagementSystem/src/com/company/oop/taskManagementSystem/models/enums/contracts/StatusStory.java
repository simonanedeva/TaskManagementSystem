package com.company.oop.taskManagementSystem.models.enums.contracts;

import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public interface StatusStory {
    StatusValues[] allowedValues = {StatusValues.NOTDONE, StatusValues.INPROGRESS, StatusValues.DONE};
}
