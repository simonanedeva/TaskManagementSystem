package com.company.oop.taskManagementSystem.core.contracts;

import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;

import java.util.List;

public interface TMSRepository {
    List<Member> getMembers();

    Member getLoggedInMember();

    void addMember(Member memberToAdd);

    Member findMemberByUsername(String username);

    boolean hasLoggedInMember();

    void login(Member member);

    void logout();

    boolean memberExists(String username);

    Member createMember(String username);

    Team createTeam(String teamName);

    void addTeam (Team teamToAdd);

    boolean teamExists(String teamName);

    List<Team> getTeams();

    Team findTeamByName(String teamName);

    Board createBoard(String boardName);

    Feedback createFeedback(String title, String description, int rating);

    Team FindTeamOfMEmeber(String member);

    Task createStory(String title, String description, Priority priority, StorySize size, String assignee);

    //TODO UserImpl ++ квото друго има покрай него
}
