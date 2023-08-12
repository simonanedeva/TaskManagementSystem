package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Task;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MemberImpl implements Member {
    public static final int USERNAME_LEN_MIN = 5;
    public static final int USERNAME_LEN_MAX = 15;
    private static final String USERNAME_REGEX_PATTERN = "^[A-Za-z0-9]+$";
    private static final String USERNAME_PATTERN_ERR = "Username contains invalid symbols!";
    private static final String USERNAME_LEN_ERR = format(
            "Username must be between %d and %d characters long!",
            USERNAME_LEN_MIN,
            USERNAME_LEN_MAX);

    private String username;
    private final List<Task> tasks;
    private final List<ActivityLog> activityHistory;

    public MemberImpl(String username) {
        setUsername(username);
        tasks = new ArrayList<>();
        activityHistory = new ArrayList<>();
    }

    private void setUsername(String username) {
        ValidationHelpers.validateIntRange(username.length(), USERNAME_LEN_MIN, USERNAME_LEN_MAX, USERNAME_LEN_ERR);
        ValidationHelpers.validatePattern(username, USERNAME_REGEX_PATTERN, USERNAME_PATTERN_ERR);
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public List<Task> getTasks(){
        return new ArrayList<>(tasks);
    }

    public List<ActivityLog> getActivityHistory(){
        return new ArrayList<>(activityHistory);
    }
    public String displayActivityHistory() {
        StringBuilder sb = new StringBuilder();
        for (ActivityLog log : activityHistory) {
           sb.append(log.viewInfo()).append(System.lineSeparator());
        }
        return sb.toString();
    }
    @Override
    public void addSteps(String steps) {
    }

    @Override
    public String printMembers() {
        return null;
        // TODO: 7.08.23 finish the method
    }

    public void logEvent(String event) {
        this.activityHistory.add(new ActivityLogImpl(event));
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }

}
