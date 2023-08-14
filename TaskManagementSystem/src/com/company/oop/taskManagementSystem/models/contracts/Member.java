package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface Member {
    String getUsername();

    public List<ActivityLog> getActivityHistory();

    void addSteps(String steps);

    String printMembers();

    String displayActivityHistory();

    void logEvent(String event);
}
