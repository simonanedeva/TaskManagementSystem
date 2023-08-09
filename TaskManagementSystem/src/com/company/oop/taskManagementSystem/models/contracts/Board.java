package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface Board {

    public String getName();

    public List<Task> getTasks();

    public List<ActivityLog> getActivityHistory();

    String displayActivityHistory();

    void addTask(Task task);

    public void logEvent(String event);
}
