package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {
    public static final int TEAM_NAME_MIN_LENGTH = 5;
    public static final int TEAM_NAME_MAX_LENGTH = 15;
    public static final String TEAM_NAME_LEN_ERR = String.format("Team name must be between %d and %d characters long!",
            TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH);
    private String name;
    private final List<Member> members;
    private final List<Board> boards;

    public TeamImpl(String name) {
        setName(name);
        members = new ArrayList<>();
        boards = new ArrayList<>();
    }

    public void setName(String name) {
        ValidationHelpers.validateStringLength(name, TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH,
                TEAM_NAME_LEN_ERR);
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
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }
}
