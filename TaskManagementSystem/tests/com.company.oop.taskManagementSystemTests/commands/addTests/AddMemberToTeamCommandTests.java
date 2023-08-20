package com.company.oop.taskManagementSystemTests.commands.addTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddCommentToTaskCommand;
import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateStoryCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddMemberToTeamCommandTests {


    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = AddCommentToTaskCommand.EXPECTED_NUMBER_OF_ARGUMENTS;

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new AddMemberToTeamCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
    }

    @Test
    public void should_AddMemberToTeam_When_PassedValidInput() {
        List<String> parameters = List.of(member.getUsername(), team.getName());

        String commentAddedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getMembers().size());

        Assertions.assertEquals(String.format(AddMemberToTeamCommand.MEMBER_ADDED_TO_TEAM, member.getUsername(), team.getName()), commentAddedMessage);
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
    public void should_ThrowException_When_MemberIsPartOfTeam() {
        List<String> parameters = List.of(member.getUsername(), team.getName());

        team.addMember(member);

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(AddMemberToTeamCommand.MEMBER_IS_PART_OF_TEAM_ERR_MESSAGE, member.getUsername(), team.getName());

        Assertions.assertEquals(commentErrorMessage, expectedErrorMessage);
    }

}
