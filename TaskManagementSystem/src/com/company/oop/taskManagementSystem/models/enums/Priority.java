package com.company.oop.taskManagementSystem.models.enums;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    @Override
    public String toString() {
        switch (this) {
            case HIGH:
                return "High";
            case MEDIUM:
                return "Medium";
            case LOW:
                return "Low";
            default:
                return "Unknown";
        }
    }
}
