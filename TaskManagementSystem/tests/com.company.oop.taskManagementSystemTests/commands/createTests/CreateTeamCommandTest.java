package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateTeamCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateTeamCommandTest {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateTeamCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TEAM_NAME = TestHelpers.getString(TeamImpl.TEAM_NAME_MIN_LENGTH + 1);
    public static final String INVALID_TEAM_NAME_TOO_SHORT = TestHelpers.getString(TeamImpl.TEAM_NAME_MIN_LENGTH - 1);
    public static final String INVALID_TEAM_NAME_TOO_LONG = TestHelpers.getString(TeamImpl.TEAM_NAME_MAX_LENGTH + 1);

    private Command command;
    private TMSRepository repository;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateTeamCommand(repository);

        //create and login a member
        Member member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);
    }


    @Test
    public void should_CreateNewTeam_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_TEAM_NAME);

        String teamCreatedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().size());

        //assert that we get the correct success message
        Assertions.assertEquals(String.format(CreateTeamCommand.TEAM_REGISTERED, parameters.get(0)), teamCreatedMessage);
    }

    @Test
    public void should_ThrowException_When_TeamNameTooLong() {
        List<String> parameters = List.of(INVALID_TEAM_NAME_TOO_LONG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = TeamImpl.TEAM_NAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_TeamNameTooShort() {
        List<String> parameters = List.of(INVALID_TEAM_NAME_TOO_SHORT);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = TeamImpl.TEAM_NAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_TeamNameEmpty() {
        List<String> parameters = List.of();

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_TeamNameNull() {
        List<String> parameters = null;

        assertThrows(NullPointerException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_Invalid_ArgumentCount() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_TeamNameNotString() {
        List<String> parameters = List.of(String.valueOf(1));

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_TeamAlreadyExist() {
        List<String> parameters = List.of(VALID_TEAM_NAME);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
            command.execute(parameters);  // Attempting to execute again
        });
        String expectedErrMessage = String.format(CreateTeamCommand.TEAM_ALREADY_EXIST, parameters.get(0));
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }


}
