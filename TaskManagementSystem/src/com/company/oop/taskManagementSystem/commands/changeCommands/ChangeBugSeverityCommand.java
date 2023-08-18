package com.company.oop.taskManagementSystem.commands.changeCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import javax.print.attribute.standard.Severity;
import java.util.List;

public class ChangeBugSeverityCommand extends BaseCommand {

    private static final String PRIORITY_SET_SUCCESSFULLY = "Severity of bug %s successfully changed from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;


    public ChangeBugSeverityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String bugToChange = parameters.get(0);
        BugSeverity newSeverityStatus = ParsingHelpers.tryParseEnum(parameters.get(1), BugSeverity.class);
        return changeSeverity(bugToChange, newSeverityStatus);
    }

    private String changeSeverity(String bugToChange, BugSeverity newSeverityStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardList = memberTeam.getBoards();

        for (Board board : boardList) {
            List<Bug> bugs = board.getBugs();

            for (Bug bug : bugs) {
                if (bug.getTitle().equals(bugToChange)) {
                    String oldSeverityStatus = bug.getSeverity().toString();
                    bug.changeSeverity(newSeverityStatus);
                    bug.logEvent(String.format("%s changed the severity of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldSeverityStatus, newSeverityStatus));
                    member.logEvent(String.format("%s changed the severity of bug %s from %s to %s.", member.getUsername(), bug.getTitle(), oldSeverityStatus, newSeverityStatus));
                    return String.format(PRIORITY_SET_SUCCESSFULLY, bugToChange, oldSeverityStatus, newSeverityStatus);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no bug %s in team %s!", bugToChange, memberTeam.getName()));
    }

}
