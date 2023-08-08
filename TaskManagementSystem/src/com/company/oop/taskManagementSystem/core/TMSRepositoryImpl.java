package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class TMSRepositoryImpl implements TMSRepository {
    private static final String NO_LOGGED_IN_MEMBER = "There is no logged in member.";
    private final static String NO_SUCH_MEMBER = "There is no member with username %s!";
    private final static String MEMBER_ALREADY_EXIST = "Member %s already exist. Choose a different username!";
    private final List<Member> members;
    private final List<Team> teams;
    private Member loggedMember;

    public TMSRepositoryImpl() {
        this.members = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.loggedMember = null;
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Member memberToAdd) {
        if (members.contains(memberToAdd)) {
            throw new IllegalArgumentException(String.format(MEMBER_ALREADY_EXIST, memberToAdd.getUsername()));
        }
        this.members.add(memberToAdd);
    }

    @Override
    public Member findMemberByUsername(String username) {
        Member member = members
                .stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(NO_SUCH_MEMBER, username)));
        return member;
    }

    // TODO: 7.08.23 not sure how the above one works and made this one but we can rewrite it later. Also added to the interface.
    public boolean memberExists (String username) {
        for (Member member : members) {
            if (member.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Member getLoggedInMember() {
        if (loggedMember == null) {
            throw new IllegalArgumentException(NO_LOGGED_IN_MEMBER);
        }
        return loggedMember;
    }

    @Override
    public boolean hasLoggedInMember() {
        return loggedMember != null;
    }

    @Override
    public void login(Member member) {
        loggedMember = member;
    }

    @Override
    public void logout() {
        loggedMember = null;
    }

    @Override
    public Member createMember(String username){
        return new MemberImpl(username);
    }

    @Override
    public Team createTeam(String teamName) {
        return new TeamImpl(teamName);
    }

    @Override
    public void addTeam(Team teamToAdd) {
        if (teams.contains(teamToAdd)) {
            throw new IllegalArgumentException(String.format(MEMBER_ALREADY_EXIST, teamToAdd.getName()));
        }
        this.teams.add(teamToAdd);
    }

    @Override
    public boolean teamExists(String teamName) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(teamName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }
}
