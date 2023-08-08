package com.company.oop.taskManagementSystem.core.contracts;

import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;

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

    //TODO UserImpl ++ квото друго има покрай него
}
