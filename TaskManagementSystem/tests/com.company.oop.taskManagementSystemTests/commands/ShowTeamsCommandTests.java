package com.company.oop.taskManagementSystemTests.commands;

import com.company.oop.taskManagementSystem.commands.ShowTeamsCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamsCommandTests {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String NO_TEAMS_MESSAGE = "No teams to show.";
    private TMSRepository tmsRepository;
    private ShowTeamsCommand showTeamsCommand;

    @BeforeEach
    public void setUp(){
        tmsRepository = new TMSRepositoryImpl();
        showTeamsCommand = new ShowTeamsCommand(tmsRepository);
        Member member = new MemberImpl("TestMember");
        tmsRepository.login(member);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String>params = new ArrayList<>();
        params.add("String1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> showTeamsCommand.execute(params));
    }

    @Test
    public void should_ShowMessage_When_NoTeams(){
        List<String> teamList = TestHelpers.getList(0);
        Assertions.assertEquals(NO_TEAMS_MESSAGE, showTeamsCommand.execute(teamList));
    }

    @Test
    public void should_ShowMessage_When_SingleTeamExists(){
        Team team = new TeamImpl("TestTeam");
        List<String> teamList = TestHelpers.getList(0);
        tmsRepository.addTeam(team);
        Assertions.assertEquals("TestTeam", showTeamsCommand.execute(teamList));
    }

    @Test
    public void should_ShowMessage_When_MultipleTeams(){
        Team team = new TeamImpl("TestTeam");
        Team team2 = new TeamImpl("TestTeam2");
        List<String> teamList = TestHelpers.getList(0);
        tmsRepository.addTeam(team);
        tmsRepository.addTeam(team2);
        Assertions.assertEquals("TestTeam, TestTeam2", showTeamsCommand.execute(teamList));
    }

}
