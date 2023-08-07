package com.company.oop.taskManagementSystem.models.enums;

public enum BugStatus {

    ACTIVE,
    FIXED;

    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
            default:
                return "Unknown";
        }
    }
}
