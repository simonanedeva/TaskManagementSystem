package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateTeamCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateBoardInTeamCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateBoardInTeamCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_BOARD_NAME = TestHelpers.getString(BoardImpl.BOARD_NAME_MIN_LENGTH + 1);
    public static final String INVALID_BOARD_NAME_TOO_SHORT = TestHelpers.getString(BoardImpl.BOARD_NAME_MIN_LENGTH - 1);
    public static final String INVALID_BOARD_NAME_TOO_LONG = TestHelpers.getString(BoardImpl.BOARD_NAME_MAX_LENGTH + 1);

    private Command command;
    private TMSRepository repository;
    String teamToAdd;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateBoardInTeamCommand(repository);

        //create and login a member
        Member member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        //create a team
        Team team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
        teamToAdd = team.getName();
    }

    @Test
    public void should_CreateNewBoard_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_BOARD_NAME, teamToAdd);

        String boardCreatedMessage = command.execute(parameters);

        // TODO: 19.08.23 we don't keep all our boards in the repo; think about doing so, or another way to test
        //Assertions.assertEquals(1, repository.getBoard().size());

        //assert that we get the correct success message
        Assertions.assertEquals(String.format(CreateTeamCommand.TEAM_REGISTERED, parameters.get(0)), boardCreatedMessage);
    }

    @Test
    public void should_ThrowException_When_BoardNameTooLong() {
        List<String> parameters = List.of(INVALID_BOARD_NAME_TOO_LONG, teamToAdd);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = BoardImpl.BOARD_NAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_BoardNameTooShort() {
        List<String> parameters = List.of(INVALID_BOARD_NAME_TOO_SHORT, teamToAdd);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = BoardImpl.BOARD_NAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }


    @Test
    public void should_ThrowException_When_TeamNameEmpty() {
        List<String> parameters = List.of();

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_ParametersNull() {
        List<String> parameters = null;

        assertThrows(NullPointerException.class, () -> command.execute(parameters));
    }

// TODO: 19.08.23 TEAM -> think of potential scenarios with the 2nd parameter as well

    @Test
    public void should_ThrowException_When_Invalid_ArgumentCount() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_UsernameNotString() {
        List<String> parameters = List.of(String.valueOf(1));

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    //    public static final String BOARD_IS_PART_OF_TEAM_ERR_MESSAGE = "Board %s is already a part of team %s!";
    @Test
    public void should_ThrowException_When_BoardAlreadyPartOfTeam() {
        List<String> parameters = List.of(VALID_BOARD_NAME, teamToAdd);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
            command.execute(parameters);  // Attempting to execute again
        });

        //Assert that we throw the correct error message
        String expectedErrMessage = String.format(CreateBoardInTeamCommand.BOARD_IS_PART_OF_TEAM_ERR_MESSAGE, VALID_BOARD_NAME, teamToAdd, parameters.get(0));
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

}


