package com.company.oop.taskManagementSystem.commands.assignCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AssignBugCommand extends BaseCommand {

    private static final String TASK_REASSIGNED_SUCCESSFULLY = "Assignee of bug %s successfully reassigned from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String ASSIGNEE_ERR_MESSAGE = "Assignee %s is not part of team %s!";

    public AssignBugCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String bugToReassign = parameters.get(0);
        String newAssignee = parameters.get(1);
        return reassignBug(bugToReassign, newAssignee);
    }

    private String reassignBug(String bugToReassign, String newAssignee) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Member> membersInTeam = memberTeam.getMembers();
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Bug> bugs = board.getBugs();
            for (Bug bug : bugs) {
                if (bug.getTitle().equals(bugToReassign)) {
                    Member oldAssignee = getTmsRepository().findMemberByUsername(bug.getAssignee());
                    throwIfInvalidAssignee(newAssignee, memberTeam, membersInTeam);
                    bug.changeAssignee(newAssignee);
                    bug.logEvent(String.format("%s changed the assignee of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldAssignee.getUsername(), newAssignee));
                    member.logEvent(String.format("%s changed the assignee of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldAssignee.getUsername(), newAssignee));
                    return String.format(TASK_REASSIGNED_SUCCESSFULLY, bugToReassign, oldAssignee.getUsername(), newAssignee);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no bug %s in team %s!", bugToReassign, memberTeam.getName()));
    }

    private static void throwIfInvalidAssignee(String assignee, Team teamOfLoggedInMember, List<Member> membersInTeam) {
        boolean isMember = false;
        for (Member member1 : membersInTeam) {
            if (member1.getUsername().equals(assignee)) {
                isMember = true;
            }
        }
        if (!isMember) {
            throw new IllegalArgumentException(String.format(ASSIGNEE_ERR_MESSAGE, assignee, teamOfLoggedInMember.getName()));
        }
    }

}
