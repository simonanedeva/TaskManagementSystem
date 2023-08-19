package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.createCommands.CreateMemberCommand;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateMemberCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateMemberCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_USERNAME = TestHelpers.getString(MemberImpl.USERNAME_MIN_LENGTH + 1);
    public static final String INVALID_USERNAME_TOO_SHORT = TestHelpers.getString(MemberImpl.USERNAME_MIN_LENGTH - 1);
    public static final String INVALID_USERNAME_TOO_LONG = TestHelpers.getString(MemberImpl.USERNAME_MAX_LENGTH + 1);
    public static final String INVALID_USERNAME_SYMBOL = "Vic$$$";


    private Command command;
    private TMSRepository repository;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateMemberCommand(repository);
    }

    @Test
    public void should_CreateNewMember_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_USERNAME);

        //saving the command as String to save the message, otherwise can be written as command.execute(parameters) only
        String memberRegisteredMessage = command.execute(parameters);

        //assert that 1 member is created
        Assertions.assertEquals(1, repository.getMembers().size());
        //assert that we get the correct success message
        Assertions.assertEquals(String.format(CreateMemberCommand.MEMBER_REGISTERED, parameters.get(0)), memberRegisteredMessage);
    }

    @Test
    public void should_ThrowException_When_UsernameTooLong() {
        List<String> parameters = List.of(INVALID_USERNAME_TOO_LONG);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = MemberImpl.USERNAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_UsernameTooShort() {
        List<String> parameters = List.of(INVALID_USERNAME_TOO_SHORT);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = MemberImpl.USERNAME_LEN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_UsernameContainsInvalidSymbol() {
        List<String> parameters = List.of(INVALID_USERNAME_SYMBOL);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
        });

        String expectedErrMessage = MemberImpl.USERNAME_PATTERN_ERR;
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

    @Test
    public void should_ThrowException_When_UsernameEmpty() {
        List<String> parameters = List.of();   //an empty list of parameters

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_UsernameNull() {
        List<String> parameters = null;

        assertThrows(NullPointerException.class, () -> command.execute(parameters));
    }

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

    @Test
    public void should_ThrowException_When_MemberAlreadyExist() {
        List<String> parameters = List.of(VALID_USERNAME);

        //Assert that we cannot create one memeber twice
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
            command.execute(parameters);  // Attempting to execute again
        });

        //Assert that we throw the correct error message
        String expectedErrMessage = String.format(CreateMemberCommand.MEMBER_ALREADY_EXIST, parameters.get(0));
        String actualErrMessage = exception.getMessage();

        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
    }

}