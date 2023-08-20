package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBugCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateBugCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateBugCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String VALID_STEPS_TO_REPRODUCE = ("Open the application, Click Log In, The application freezes!");
    public static final String VALID_PRIORITY = "LOW";
    public static final String VALID_SEVERITY = "MINOR";
    public static final String VALID_ASSIGNEE = "Victor";

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    //boardToAdd

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateBugCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        //board = repository.createBoard("BoardOne");
        boardInTeamCommand.execute(List.of(boardName, team.getName()));
    }

    @Test
    public void should_CreateNewBug_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_STEPS_TO_REPRODUCE, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SEVERITY, VALID_ASSIGNEE);

        String bugCreatedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getBugs().size());

        Assertions.assertEquals(String.format(CreateBugCommand.BUG_CREATED, VALID_TITLE, repository.getTeams().get(0).getBoards().get(0).getName(),
                parameters.get(0)), bugCreatedMessage);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        // Arrange
        List<String> params = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void should_ThrowException_When_NoArguments() {
        List<String> parameters = List.of();

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }


    @Test
    public void should_ThrowException_When_AssigneeNotPartOfLoggedInMemberTeam() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_STEPS_TO_REPRODUCE, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SEVERITY, "Nikolay");

        String bugErrorMessage = "";

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            bugErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(CreateBugCommand.ASSIGNEE_ERR_MESSAGE, "Nikolay", team.getName(), repository.getTeams().get(0).getBoards().get(0).getName(),
                parameters.get(0));

        Assertions.assertEquals(expectedErrorMessage, bugErrorMessage);

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));

    }
}


