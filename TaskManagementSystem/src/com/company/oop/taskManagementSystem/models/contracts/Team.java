package com.company.oop.taskManagementSystem.models.contracts;

import java.util.ArrayList;
import java.util.List;

public interface Team {
    public String getName();

    public List<Member> getMembers();

    public List<Board> getBoards();

    public void addMember(Member member);

    public void addBoard(Board board);
}
