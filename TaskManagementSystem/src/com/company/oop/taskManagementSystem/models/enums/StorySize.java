package com.company.oop.taskManagementSystem.models.enums;

public enum StorySize {

    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        switch (this) {
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                return "Unknown";
        }
    }
}
