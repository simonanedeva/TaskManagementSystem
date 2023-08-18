package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AddMemberToTeamCommand extends BaseCommand {

    private static final String MEMBER_ADDED_TO_TEAM = "Member %s added to team %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String MEMBER_IS_PART_OF_TEAM_ERR_MESSAGE = "Member %s is already a part of team %s!";

    public AddMemberToTeamCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String memberToAdd = parameters.get(0);
        String teamToAdd = parameters.get(1);
        return addToTeam(memberToAdd, teamToAdd);
    }

    private String addToTeam(String memberToAdd, String teamToAdd) {
        Member member = getTmsRepository().findMemberByUsername(memberToAdd);
        Team team = getTmsRepository().findTeamByName(teamToAdd);
        throwIfMemberIsPartOfATeam(memberToAdd);
        team.addMember(member);

        return String.format(MEMBER_ADDED_TO_TEAM, memberToAdd, teamToAdd);
    }

    private void throwIfMemberIsPartOfATeam(String memberToAdd) {
        List<Team> teams = getTmsRepository().getTeams();
        for (Team team : teams) {
            List<Member> memberList = team.getMembers();
            memberList.stream()
                    .filter(member -> member.getUsername().equals(memberToAdd))
                    .forEach(member -> {
                        throw new IllegalArgumentException(
                                String.format(MEMBER_IS_PART_OF_TEAM_ERR_MESSAGE, memberToAdd, team.getName()));
                    });
        }
    }

}
