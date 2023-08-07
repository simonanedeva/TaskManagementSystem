package com.company.oop.taskManagementSystem.models.enums;

public enum StoryStatus {

    NOTDONE,
    INPROGRESS,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOTDONE:
                return "Not Done";
            case INPROGRESS:
                return "InProgress";
            case DONE:
                return "Done";
            default:
                return "Unknown";
        }
    }
}
