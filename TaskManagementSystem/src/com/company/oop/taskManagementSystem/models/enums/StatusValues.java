package com.company.oop.taskManagementSystem.models.enums;

import com.company.oop.taskManagementSystem.models.contracts.Task;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusBug;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusFeedback;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusStory;

public enum StatusValues implements StatusBug, StatusStory, StatusFeedback {

    ACTIVE("1"),
    FIXED("2"),
    NOTDONE("1"),
    INPROGRESS("2"),
    DONE("4"),
    NEW("1"),
    UNSCHEDULED("2"),
    SCHEDULED("3");

    private final String label;

    StatusValues(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
            case NOTDONE:
                return "Not Done";
            case INPROGRESS:
                return "InProgress";
            case DONE:
                return "Done";
            case NEW:
                return "New";
            case UNSCHEDULED:
                return "Unscheduled";
            case SCHEDULED:
                return "Scheduled";
            default:
                return "Unknown";
        }
    }

}
