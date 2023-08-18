package com.company.oop.taskManagementSystemTests.commands.showTests;

import com.company.oop.taskManagementSystem.commands.showCommands.ShowTeamBoardsCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowTeamBoardsCommandTests {
    public static final String NO_BOARDS_MESSAGE = "Currently there are no boards in %s.";

    private TMSRepository tmsRepository;
    private ShowTeamBoardsCommand showTeamBoardsCommand;
    private Team team;
    private Board board;

    @BeforeEach
    public void SetUp(){
        tmsRepository= new TMSRepositoryImpl();
        showTeamBoardsCommand = new ShowTeamBoardsCommand(tmsRepository);
        Member member = new MemberImpl("TestMember");
        tmsRepository.login(member);
        team = new TeamImpl("TeamName");
        tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        board = new BoardImpl("Board");

    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        tmsRepository.findTeamOfMember(tmsRepository.getLoggedInMember().getUsername()).addBoard(board);
        List<String>params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> showTeamBoardsCommand.execute(params));
    }

    @Test
    public void should_PrintErrorMessage_When_NoBoards(){
        List<String>params = new ArrayList<>();
        params.add(team.getName());
        String result = showTeamBoardsCommand.execute(params);
        assertEquals(String.format(NO_BOARDS_MESSAGE, team.getName()), result);
    }

    @Test
    public void should_ExecuteCommand_When_SingleBoard() {
        tmsRepository.findTeamOfMember(tmsRepository.getLoggedInMember().getUsername()).addBoard(board);
        List<String>params = new ArrayList<>();
        params.add(team.getName());
        String result = showTeamBoardsCommand.execute(params);
        assertEquals(board.getName(), result);
    }

    @Test
    public void should_ExecuteCommand_When_MultipleBoards(){
        Board board2 = new BoardImpl("Board2");
        tmsRepository.findTeamOfMember(tmsRepository.getLoggedInMember().getUsername()).addBoard(board);
        tmsRepository.findTeamOfMember(tmsRepository.getLoggedInMember().getUsername()).addBoard(board2);
        List<String>params = new ArrayList<>();
        params.add(team.getName());
        String result = showTeamBoardsCommand.execute(params);
        assertEquals(String.format("%s, %s", board.getName(), board2.getName()), result);
    }
}
