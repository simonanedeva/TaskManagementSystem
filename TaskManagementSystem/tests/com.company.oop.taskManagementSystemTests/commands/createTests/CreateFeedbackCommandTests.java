package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateFeedbackCommand;
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

public class CreateFeedbackCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateFeedbackCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String VALID_RATING = "1";

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    //boardToAdd

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateFeedbackCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        boardInTeamCommand.execute(List.of(boardName, team.getName()));
    }

    @Test
    public void should_CreateNewBug_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_TITLE, boardName, VALID_RATING);

        String feedbackCreatedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getFeedbacks().size());

        Assertions.assertEquals(String.format(CreateFeedbackCommand.FEEDBACK_CREATED, VALID_TITLE, repository.getTeams().get(0).getBoards().get(0).getName(),
                parameters.get(0)), feedbackCreatedMessage);
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
    public void should_ThrowException_When_TaskExist() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_TITLE, boardName, VALID_RATING);

        command.execute(parameters);

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            String actualErrorMessage = e.getMessage();

            String expectedErrorMessage = String.format(CreateFeedbackCommand.TITLE_EXIST_ERR_MESSAGE);

            Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
            Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
        }
    }

    @Test
    public void should_ThrowException_When_BoardNotPartOfTeam() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_TITLE, "NewBoard", VALID_RATING);

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            String actualErrorMessage = e.getMessage();

            String expectedErrorMessage = String.format(CreateFeedbackCommand.BOARD_NOT_EXIST_ERR_MESSAGE, "NewBoard");

            Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
            Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
        }
    }

}