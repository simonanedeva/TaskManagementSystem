package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateStoryCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateTeamCommand;
import com.company.oop.taskManagementSystem.core.CommandFactoryImpl;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
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

        // TODO: 19.08.23 fix the logic, as it breaks somewhere on the 3 rows below
        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        board = repository.createBoard("BoardOne");
        boardInTeamCommand.execute(List.of(board.getName(), team.getName()));
    }

    @Test
    // TODO: 19.08.23 uncomment when logic fixed
    public void should_CreateNewStory_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION, "BoardOne", VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE);

        String storyCreatedMessage = command.execute(parameters);
    }
//
//        // TODO: 19.08.23 think of a way to assert that the story was indeed created
//        //Assertions.assertEquals(1, BoardImpl.getStories());
//
//        Assertions.assertEquals(String.format(CreateStoryCommand.STORY_CREATED, VALID_TITLE, member, parameters.get(0)), storyCreatedMessage);
//    }

    //add test if assignee is not part of team of logged in member

}


//    @Test
//    public void should_ThrowException_When_UsernameTooLong() {
//        List<String> parameters = List.of(INVALID_USERNAME_TOO_LONG);
//
//        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            command.execute(parameters);
//        });
//
//        String expectedErrMessage = MemberImpl.USERNAME_LEN_ERR;
//        String actualErrMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
//    }
//
//    @Test
//    public void should_ThrowException_When_UsernameTooShort() {
//        List<String> parameters = List.of(INVALID_USERNAME_TOO_SHORT);
//
//        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            command.execute(parameters);
//        });
//
//        String expectedErrMessage = MemberImpl.USERNAME_LEN_ERR;
//        String actualErrMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
//    }
//
//    @Test
//    public void should_ThrowException_When_UsernameConstainsInvalidSymbol() {
//        List<String> parameters = List.of(INVALID_USERNAME_SYMBOL);
//
//        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            command.execute(parameters);
//        });
//
//        String expectedErrMessage = MemberImpl.USERNAME_PATTERN_ERR;
//        String actualErrMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
//    }
//
//    @Test
//    public void should_ThrowException_When_UsernameEmpty() {
//        List<String> parameters = List.of();   //an empty list of parameters
//
//        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
//    }
//
//    @Test
//    public void should_ThrowException_When_UsernameNull() {
//        List<String> parameters = null;
//
//        assertThrows(NullPointerException.class, () -> command.execute(parameters));
//    }
//
//    @Test
//    public void should_ThrowException_When_Invalid_ArgumentCount() {
//        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);
//
//        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
//    }
//
//    @Test
//    public void should_ThrowException_When_UsernameNotString() {
//        List<String> parameters = List.of(String.valueOf(1));
//
//        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
//    }
//
//    @Test
//    public void should_ThrowException_When_MemberAlreadyExist() {
//        List<String> parameters = List.of(VALID_USERNAME);
//
//        //Assert that we cannot create one memeber twice
//        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            command.execute(parameters);
//            command.execute(parameters);  // Attempting to execute again
//        });
//
//        //Assert that we throw the correct error message
//        String expectedErrMessage = String.format(CreateMemberCommand.MEMBER_ALREADY_EXIST, parameters.get(0));
//        String actualErrMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedErrMessage, actualErrMessage);
//    }
//
//}