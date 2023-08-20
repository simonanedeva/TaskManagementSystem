package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateStoryCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//CREATESTORY TitleOfStory BoardOne {{Description of the board}} LOW LARGE Simona

public class CreateStoryCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateStoryCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String INVALID_TITLE_TOO_SHORT = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH - 1);
    public static final String INVALID_TITLE_TOO_LONG = TestHelpers.getString(TaskImpl.TITLE_MAX_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String INVALID_DESCRIPTION_TOO_SHORT = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH - 1);
    public static final String INVALID_DESCRIPTION_TOO_LONG = TestHelpers.getString(TaskImpl.DESCRIPTION_MAX_LENGTH + 1);
    public static final String VALID_PRIORITY = "HIGH";
    public static final String VALID_SIZE = "LARGE";
    public static final String VALID_ASSIGNEE = "Victor";
    public static final String VALID_ID = "1";

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private Board board;

    //boardToAdd

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateStoryCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        // TODO: 19.08.23 fix the logic, as it breaks somewhere on the rows below
        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        board = repository.createBoard("BoardOne");
        boardInTeamCommand.execute(List.of(board.getName(), team.getName()));
        team.addBoard(board);
    }

    @Test
    public void should_CreateNewStory_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_TITLE, board.getName(), VALID_DESCRIPTION, VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE);

        String storyCreatedMessage = command.execute(parameters);

        // TODO: 19.08.23 think of a way to assert that the story was indeed created
        //Assertions.assertEquals(1, BoardImpl.getStories());

        Assertions.assertEquals(String.format(CreateStoryCommand.STORY_CREATED, VALID_TITLE, member, parameters.get(0)), storyCreatedMessage);
    }

    //add test if assignee is not part of team of logged in member

}