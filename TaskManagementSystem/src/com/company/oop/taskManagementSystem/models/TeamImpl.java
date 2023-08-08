package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {
    public static final int MIN_LENGTH_TEAM_NAME = 5;
    public static final int MAX_LENGTH_TEAM_NAME = 15;
    public static final String INVALID_TEAM_NAME_LENGTH_MESSAGE = String.format("Team name must be between %d and %d characters long!",
            MIN_LENGTH_TEAM_NAME, MAX_LENGTH_TEAM_NAME);
    private String name;
    private final List<Member> members;
    private final List<Board> boards;

    public TeamImpl(String name) {
        setName(name);
        members = new ArrayList<>();
        boards = new ArrayList<>();
    }

    public void setName(String name) {
        ValidationHelpers.validateStringLength(name, MIN_LENGTH_TEAM_NAME, MAX_LENGTH_TEAM_NAME,
                INVALID_TEAM_NAME_LENGTH_MESSAGE);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }
}
