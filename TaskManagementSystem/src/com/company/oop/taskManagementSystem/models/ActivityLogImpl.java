package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogImpl implements ActivityLog {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm");

    private final String description;
    private final LocalDateTime timestamp;

    public ActivityLogImpl(String description) {
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public String viewInfo() {
        return String.format("[%s] %s", timestamp.format(formatter), description);
    }
}
