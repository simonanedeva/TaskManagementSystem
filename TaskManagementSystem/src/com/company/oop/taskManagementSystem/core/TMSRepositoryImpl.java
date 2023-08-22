package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;

import java.util.ArrayList;
import java.util.List;

public class TMSRepositoryImpl implements TMSRepository {
    public static final String NO_LOGGED_IN_MEMBER = "There is no logged in member.";
    public final static String NO_SUCH_MEMBER = "There is no member with username %s!";
    public final static String NO_SUCH_TEAM = "There is no team with name %s!";
    public final static String MEMBER_ALREADY_EXIST = "Member %s already exist. Choose a different username!";
    private final List<Member> members;
    private final List<Team> teams;
    private Member loggedMember;

    private int id;

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
        return members
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(NO_SUCH_MEMBER, username)));
    }

    public boolean memberExists(String username) {
        return members.
                stream()
                .anyMatch(u -> u.getUsername().equals(username));
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
    public Member createMember(String username) {
        return new MemberImpl(username);
    }

    @Override
    public Team createTeam(String teamName) {
        return new TeamImpl(teamName);
    }

    @Override
    public void addTeam(Team teamToAdd) {
        // TODO: 20-Aug-23 following if never used, consider removing it;
        if (teams.contains(teamToAdd)) {
            throw new IllegalArgumentException(String.format(MEMBER_ALREADY_EXIST, teamToAdd.getName()));
        }
        this.teams.add(teamToAdd);
    }

    @Override
    public boolean teamExists(String teamName) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(teamName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public Team findTeamByName(String teamName) {
        Team team = teams
                .stream()
                .filter(u -> u.getName().equalsIgnoreCase(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(NO_SUCH_TEAM, teamName)));
        return team;
    }

    @Override
    public Board createBoard(String boardName) {
        return new BoardImpl(boardName);
    }

    public Team findTeamOfMember(String member) {
        return teams
                .stream()
                .filter(team -> team.getMembers().stream()
                        .anyMatch(member1 -> member1.getUsername().equals(member)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Command is not supported as member %s is not part of any team!", loggedMember.getUsername())));
    }

    @Override
    public Story createStory(String title, String description, Priority priority, StorySize size, String assignee) {
        return new StoryImpl(++id, title, description, priority, size, assignee);
    }

    @Override
    public Bug createBug(String title, String boardToAdd, String description, List<String> stepsToReproduce,
                         Priority priority, BugSeverity severity, String assignee) {
        return new BugImpl(++id, title, description, stepsToReproduce, priority, severity, assignee);
    }

    public Feedback createFeedback(String title, String description, int rating) {
        return new FeedbackImpl(++id, title, description, rating);
    }
}

