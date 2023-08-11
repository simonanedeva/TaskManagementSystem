package com.company.oop.agency.tests.commands;

import com.company.oop.taskManagementSystem.commands.CreateMemberCommand;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.enums.FeedbackStatus;
import helpers.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateMemberCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command command;
    private TMSRepository repository;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateMemberCommand(repository);
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
    public void should_CreateNewMember_When_PassedValidInput() {
        List<String> parameters = List.of("Simona");

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getMembers().size());
    }

    @Test
    public void should_ThrowError_When_MemberAlreadyExist() {
        List<String> parameters = List.of("Simona");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            command.execute(parameters);
            command.execute(parameters);  // Attempting to execute again
        });
    }


}