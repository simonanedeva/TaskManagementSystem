package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Task;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

import static java.lang.String.format;

public class MemberImpl implements Member {
    public static final int USERNAME_LEN_MIN = 2;
    public static final int USERNAME_LEN_MAX = 20;
    private static final String USERNAME_REGEX_PATTERN = "^[A-Za-z0-9]+$";
    private static final String USERNAME_PATTERN_ERR = "Username contains invalid symbols!";
    private static final String USERNAME_LEN_ERR = format(
            "Username must be between %d and %d characters long!",
            USERNAME_LEN_MIN,
            USERNAME_LEN_MAX);

    private String username;
    private List<Task> tasks;


    public MemberImpl(String username) {
        ValidationHelpers.validateIntRange(username.length(), USERNAME_LEN_MIN, USERNAME_LEN_MAX, USERNAME_LEN_ERR);
        ValidationHelpers.validatePattern(username, USERNAME_REGEX_PATTERN, USERNAME_PATTERN_ERR);
        setUsername(username);
    }

    private void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void addSteps(String steps) {
        }

    }
