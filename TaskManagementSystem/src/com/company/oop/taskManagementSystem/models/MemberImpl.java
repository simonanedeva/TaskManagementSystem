package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MemberImpl implements Member {
    public static final int USERNAME_MIN_LENGTH = 5;  //MIN_LENGTH_USERNAME
    public static final int USERNAME_MAX_LENGTH = 15;
    public static final String REGEX_PATTERN_USERNAME = "^[A-Za-z0-9]+$";
    public static final String USERNAME_PATTERN_ERR = "Username contains invalid symbols!";
    public static final String USERNAME_LEN_ERR = format(
            "Username must be between %d and %d characters long!",
            USERNAME_MIN_LENGTH,
            USERNAME_MAX_LENGTH);

    private String username;
    private final List<ActivityLog> activityHistory;

    public MemberImpl(String username) {
        setUsername(username);
        activityHistory = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

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

    public void logEvent(String event) {
        this.activityHistory.add(new ActivityLogImpl(event));
    }

    private void setUsername(String username) {
        ValidationHelpers.validateIntRange(username.length(), USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH, USERNAME_LEN_ERR);
        ValidationHelpers.validatePattern(username, REGEX_PATTERN_USERNAME, USERNAME_PATTERN_ERR);
        this.username = username;
    }

}
