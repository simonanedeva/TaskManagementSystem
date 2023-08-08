package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AddMemberToTeamCommand extends BaseCommand{

    private static final String MEMBER_ADDED_TO_TEAM = "Member %s added to team %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String MEMBER_IS_PART_OF_TEAM_ERR_MESSAGE = "Member %s is already a part of team %s!";

    public AddMemberToTeamCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
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
        throwIfMemberExists(memberToAdd, teamToAdd, team);
        team.addMember(member);

        return String.format(MEMBER_ADDED_TO_TEAM, memberToAdd, teamToAdd);
    }

    private static void throwIfMemberExists(String memberToAdd, String teamToAdd, Team team) {
        List<Member> memberList = team.getMembers();
        for (Member member1 : memberList) {
            if (member1.getUsername().equals(memberToAdd)){
                throw new IllegalArgumentException(String.format(MEMBER_IS_PART_OF_TEAM_ERR_MESSAGE, memberToAdd, teamToAdd));
            }
        }
    }

    // TODO: 8.08.23  Look at this - maybe it can be done with generics - it's also repeated in CreateBoard.

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
