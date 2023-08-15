package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AssignBugCommand extends BaseCommand {

    private static final String TASK_REASSIGNED_SUCCESSFULLY = "Assignee of bug %s successfully reassigned from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public AssignBugCommand(TMSRepository tmsRepository) {
        super(tmsRepository);

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
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Bug> bugs = board.getBugs();
            for (Bug bug : bugs) {
                if (bug.getTitle().equals(bugToReassign)) {
                    Member oldAssignee = getTmsRepository().findMemberByUsername(bug.getAssignee());
                    bug.changeAssignee(newAssignee);
                    bug.logEvent(String.format("%s changed the assignee of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldAssignee.getUsername(), newAssignee));
                    member.logEvent(String.format("%s changed the assignee of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldAssignee.getUsername(), newAssignee));
                    return String.format(TASK_REASSIGNED_SUCCESSFULLY, bugToReassign, oldAssignee.getUsername(), newAssignee);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no bug %s in team %s!", bugToReassign, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
