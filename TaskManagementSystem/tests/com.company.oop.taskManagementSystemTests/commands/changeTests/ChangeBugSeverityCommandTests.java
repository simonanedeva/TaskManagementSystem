package com.company.oop.taskManagementSystemTests.commands.changeTests;

import com.company.oop.taskManagementSystem.commands.changeCommands.ChangeBugSeverityCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Bug;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateBugCommandTests;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeBugSeverityCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = ChangeBugSeverityCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new ChangeBugSeverityCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
        team.addMember(member);

        team.addBoard(repository.createBoard(boardName));


        Bug bug = repository.createBug(CreateBugCommandTests.VALID_TITLE, boardName, CreateBugCommandTests.VALID_DESCRIPTION, List.of(CreateBugCommandTests.VALID_STEPS_TO_REPRODUCE.split(",")), Priority.LOW,
                BugSeverity.MINOR, CreateBugCommandTests.VALID_ASSIGNEE);
        team.getBoards().get(0).addBug(bug);
    }

    @Test
    public void should_ChangeBugSeverityStatus_When_PassedValidInput() {
        List<String> parameters = List.of(CreateBugCommandTests.VALID_TITLE, BugSeverity.CRITICAL.toString());

        command.execute(parameters);

        Assertions.assertEquals(BugSeverity.CRITICAL, repository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getSeverity());
    }

    @Test
    public void should_LogEventToBugHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateBugCommandTests.VALID_TITLE, BugSeverity.CRITICAL.toString());

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getActivityHistory().size());
    }

    @Test
    public void should_LogEventToLoggedInMemberHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateBugCommandTests.VALID_TITLE, BugSeverity.CRITICAL.toString());

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getLoggedInMember().getActivityHistory().size());
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_BugSeverityIsAlreadySetToPassedValue() {
        List<String> parameters = List.of(CreateBugCommandTests.VALID_TITLE, repository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getSeverity().toString());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_BugDoesNotExist() {
        List<String> parameters = List.of("SomeOtherBugName", BugSeverity.CRITICAL.toString());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_InvalidSeverityStatus() {
        List<String> parameters = List.of(CreateBugCommandTests.VALID_TITLE, "GoshoCritical");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_LoggedInMemberIsInOtherTeam() {
        repository.addTeam(repository.createTeam("AlcoHolics"));
        Team team = repository.findTeamByName("AlcoHolics");
        team.addMember(repository.createMember("Viktor"));
        team.addBoard(repository.createBoard("Shame"));
        repository.createBug("SomeOtherBugName", "Shame", CreateBugCommandTests.VALID_DESCRIPTION, List.of(CreateBugCommandTests.VALID_STEPS_TO_REPRODUCE.split(",")), Priority.LOW,
                BugSeverity.MINOR, CreateBugCommandTests.VALID_ASSIGNEE);

        List<String> parameters = List.of("SomeOtherBugName", BugSeverity.CRITICAL.toString());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
}
