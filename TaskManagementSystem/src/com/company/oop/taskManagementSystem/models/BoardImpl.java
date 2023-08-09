package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Task;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements Board {

    private String name;
    private final List<Task> tasks;
    private final List<ActivityLog> activityHistory;

    public BoardImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        activityHistory = new ArrayList<>();
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<ActivityLog> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    public String displayActivityHistory() {
        StringBuilder sb = new StringBuilder();
        for (ActivityLog log : activityHistory) {
            sb.append(log.viewInfo()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void logEvent(String event) {
        this.activityHistory.add(new ActivityLogImpl(event));
    }


}
