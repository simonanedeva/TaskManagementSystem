package com.company.oop.taskManagementSystemTests.commands.logTests;

import com.company.oop.taskManagementSystem.commands.LoginCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class loginCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command command;
    private TMSRepository repository;
    private Member member;
    private String username = "Victor";

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new LoginCommand(repository);

        member = repository.createMember(username);
        repository.addMember(member);
    }

    @Test
    public void should_Login_When_ValidInput() {
        List<String> params = List.of(username);

        String message = command.execute(params);

        Assertions.assertEquals(repository.getLoggedInMember().getUsername(), username);

        Assertions.assertEquals(message, String.format(LoginCommand.MEMBER_LOGGED_IN, username));
    }

    @Test
    public void should_Throw_When_UserDoesNotExists() {
        List<String> parameters = List.of("Simona");

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_Throw_When_UserAlreadyLoggedIn() {
        List<String> parameters = List.of(username);

        String message = command.execute(parameters);

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));

        Assertions.assertEquals(message, String.format(LoginCommand.MEMBER_LOGGED_IN_ALREADY, username));
    }


}
