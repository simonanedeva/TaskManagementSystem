package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;

public class CreateBugCommand extends BaseCommand {

    // TODO: 9.08.23 implementation works ONLY when extractDescriptionParameters V.2 method in TMS Engine is uncommented

    private static final String BUG_CREATED = "Bug %s created successfully in board %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    public static final String ASSIGNEE_ERR_MESSAGE = "Assignee %s is not part of team %s!";

    public CreateBugCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        // TODO: 9.08.23 change extractDescriptionParameters to fit this command (suggestion in comment in class)

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String description = parameters.get(0);
        String title = parameters.get(2);
        String boardToAdd = parameters.get(3);
        List<String> stepsToReproduce = Arrays.asList(parameters.get(1).split("; "));
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(4), Priority.class);
        BugSeverity severity = ParsingHelpers.tryParseEnum(parameters.get(5), BugSeverity.class);
        String assignee = parameters.get(6);

        return createBug(title,boardToAdd,description,stepsToReproduce,priority,severity,assignee);
    }

    private String createBug(String title, String boardToAdd, String description, List<String> stepsToReproduce, Priority priority, BugSeverity severity, String assignee) {
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Member> membersInTeam = teamOfLoggedInMember.getMembers();
        throwIfInvalidAssignee(assignee, teamOfLoggedInMember, membersInTeam);

        List<Board> boards= teamOfLoggedInMember.getBoards();
        Board board = findBoardInTeam(boards,boardToAdd);
        Bug bugToAdd = getTmsRepository().createBug(title,boardToAdd,description,stepsToReproduce,priority,severity,assignee);
        List<Task> taskList = board.getTasks();
        throwIfTaskExist(title, taskList);

        board.addBug(bugToAdd);

        member.logEvent(String.format("Bug %s created by member %s",title, member.getUsername()));
        bugToAdd.logEvent(String.format("Bug %s created by member %s",title, member.getUsername()));

        return String.format(BUG_CREATED, title,boardToAdd);
    }

    private static void throwIfTaskExist(String nameOfTask, List<Task> taskList) {
        for (Task task : taskList) {
            if (task.getTitle().equals(nameOfTask)) {
                throw new IllegalArgumentException("Task with such a title already exists");
            }
        }
    }

    private static void throwIfInvalidAssignee(String assignee, Team teamOfLoggedInMember, List<Member> membersInTeam) {
        boolean isMember = false;
        for (Member member1 : membersInTeam) {
            if(member1.getUsername().equals(assignee)) {
                isMember=true;
            }
        }
        if (!isMember) {
            throw new IllegalArgumentException(String.format(ASSIGNEE_ERR_MESSAGE, assignee, teamOfLoggedInMember.getName()));
        }
    }

    private Board findBoardInTeam (List<Board> boardList, String board){
        for (Board board1 : boardList) {
            if (board1.getName().equals(board)){
                return board1;
            }
        }
        throw new IllegalArgumentException("Board does not exist in this team");
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }
}
