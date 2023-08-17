package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface Member {
    String getUsername();

    public List<ActivityLog> getActivityHistory();

    String displayActivityHistory();

    void logEvent(String event);
}
