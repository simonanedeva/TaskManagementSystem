package com.company.oop.taskManagementSystemTests.commands;

import com.company.oop.taskManagementSystem.commands.ShowTeamActivityCommand;
import com.company.oop.taskManagementSystem.commands.ShowTeamBoardsCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamActivityCommandTest {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public static final String NO_ACTIVITY_FOR_MEMBER = "Currently there is no activity to display for member %s.";

    private TMSRepository tmsRepository;
    private ShowTeamActivityCommand showTeamActivityCommand;
    private Team team;
    private Member member;

    @BeforeEach
    public void setUp(){
        tmsRepository = new TMSRepositoryImpl();
        showTeamActivityCommand = new ShowTeamActivityCommand(tmsRepository);
        member = new MemberImpl("TestMember");
        tmsRepository.login(member);
        team = new TeamImpl("TeamName");
        tmsRepository.addTeam(team);
        team.addMember(member);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> showTeamActivityCommand.execute(params));
    }

    @Test
    public void should_PrintMessage_When_NoActivity(){
        Board board = new BoardImpl("board1");
        team.addBoard(board);
        List<String> params = new ArrayList<>();
        params.add(team.getName());
        String result = showTeamActivityCommand.execute(params);
        Assertions.assertEquals
                (String.format("%s", tmsRepository.getLoggedInMember().getUsername())
                        + String.format(System.lineSeparator())
                        + String.format(NO_ACTIVITY_FOR_MEMBER, tmsRepository.getLoggedInMember().getUsername()),
                        result);
    }

    // TODO: 13-Aug-23 this motherfucker I'm fighting now!
    @Test
    public void should_PrintMessage_When_OneActivity(){
        Board board = new BoardImpl("board1");
        Feedback feedback = new FeedbackImpl(1, "titletitle", "decriptiondescription", 2);
        team.addBoard(board);
        board.addTask(feedback);
        member.addTask(feedback);
        List<String> params = new ArrayList<>();
        params.add(team.getName());
        String result = showTeamActivityCommand.execute(params);
        Assertions.assertEquals
                (member.displayActivityHistory(), result);
    }

}
