package com.company.oop.taskManagementSystem.models.enums;

public enum FeedbackStatus {
    NEW,
    UNSCHEDULED,
    SCHEDULED,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case NEW:
                return "New";
            case UNSCHEDULED:
                return "Unscheduled";
            case SCHEDULED:
                return "Scheduled";
            case DONE:
                return "Done";
            default:
                return "Unknown";
        }
    }
}
