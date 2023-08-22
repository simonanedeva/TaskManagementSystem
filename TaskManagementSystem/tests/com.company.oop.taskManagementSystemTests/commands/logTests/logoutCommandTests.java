package com.company.oop.taskManagementSystemTests.commands.logTests;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.commands.LoginCommand;
import com.company.oop.taskManagementSystem.commands.LogoutCommand;
import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.assignCommands.AssignStoryCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBugCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class logoutCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command command;
    private TMSRepository repository;
    private Member member;
    private String username = "Victor";

    @BeforeEach
    public void before() {
        repository = new TMSRepositoryImpl();
        command = new LogoutCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);
    }

    @Test
    public void should_Logout_User() {

        command.execute(new ArrayList<>());

        String expectedMessage = TMSRepositoryImpl.NO_LOGGED_IN_MEMBER;
        String actualMessage = "";

        try {
            repository.getLoggedInMember().toString();
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void should_Throw_When_NoLoggedUser() {

        command.execute(List.of());

        String expectedMessage = BaseCommand.MEMBER_NOT_LOGGED;
        String actualMessage = "";

        try {
            command.execute(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        Assertions.assertEquals(expectedMessage, actualMessage);
    }


}
