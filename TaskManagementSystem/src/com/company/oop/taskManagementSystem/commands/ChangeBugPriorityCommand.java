package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeBugPriorityCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    protected ChangeBugPriorityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }


    @Override
    protected boolean requiresLogin() {
        return false;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String bugInContext = parameters.get(0);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class);
        return changeBugPriority(bugInContext, priority);
    }

    private String changeBugPriority(String bugInContext, Priority priority) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardList = memberTeam.getBoards();
//        for (Board board : boardList) {
//            List<Task> tasks = board.getTasks();
//            for (Task task : tasks) {
//                if (task.getTitle().equals(bugInContext)) {
//                    Bug bug = (Bug) task;
//                    String oldPriority = bug.getPriority().toString();
//                    bug.changePriority(priority);
//                    board.logEvent(String.format("%s changed the status of task %s from %s to %s", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
//                    member.logEvent(String.format("%s changed the status of task %s from %s to %s", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
//                    taskExist = true;
//                    break;
//                }
//            }
//        }
        return null;
    }


}
