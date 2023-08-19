package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamImplTests {

    // TODO: 18.08.23 implement 

    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String VALID_TEAM_NAME = TestHelpers.getString(NAME_MIN_LENGTH + 1);

    @Test
    public void constructor_Should_InitializeName_When_ArgumentsAreValid() {
        TeamImpl team = initilizeValidTeam();
        Assertions.assertEquals(VALID_TEAM_NAME, team.getName());
    }

    @Test
    public void constructor_Should_InitializeMembers_When_ArgumentsAreValid() {
        TeamImpl team = initilizeValidTeam();
        Assertions.assertNotNull(team.getMembers());
    }

    @Test
    public void constructor_Should_InitializeBoards_When_ArgumentsAreValid() {
        TeamImpl team = initilizeValidTeam();
        Assertions.assertNotNull(team.getBoards());
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameLength() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new TeamImpl(TestHelpers.getString(NAME_MIN_LENGTH - 1))),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new TeamImpl(TestHelpers.getString(NAME_MAX_LENGTH + 1)))

        );
    }

    @Test
    public void addMember_Should_AddMemberToList() {
        TeamImpl team = initilizeValidTeam();
        Member member = MemberImplTests.initilizeValidMember();

        team.addMember(member);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, team.getMembers().size()),
                () -> Assertions.assertTrue(team.getMembers().contains(member))
        );
    }

    @Test
    public void addBoard_Should_AddBoardToList() {
        TeamImpl team = initilizeValidTeam();
        Board board = BoardImplTests.initilizeValidBoard();

        team.addBoard(board);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, team.getBoards().size()),
                () -> Assertions.assertTrue(team.getBoards().contains(board))
        );
    }

    @Test
    public void getMembers_Should_ReturnCopyOfCollection() {
        TeamImpl team = initilizeValidTeam();
        Member member = MemberImplTests.initilizeValidMember();

        team.addMember(member);
        team.getMembers().clear();

        Assertions.assertEquals(1, team.getMembers().size());
    }

    @Test
    public void getBoards_Should_ReturnCopyOfCollection() {
        TeamImpl team = initilizeValidTeam();
        Board board = BoardImplTests.initilizeValidBoard();

        team.addBoard(board);
        team.getBoards().clear();

        Assertions.assertEquals(1, team.getBoards().size());
    }

    public static TeamImpl initilizeValidTeam() {
        return new TeamImpl(VALID_TEAM_NAME);
    }

}
