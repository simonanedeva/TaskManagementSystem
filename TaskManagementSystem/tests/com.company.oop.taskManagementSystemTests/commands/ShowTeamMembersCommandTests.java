package com.company.oop.taskManagementSystemTests.commands;

import com.company.oop.taskManagementSystem.commands.ShowTeamMembersCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import helpers.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowTeamMembersCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_MEMBERS_MESSAGE = "Currently there are no members in %s.";
    private TMSRepository tmsRepository;
    private ShowTeamMembersCommand showTeamMembersCommand;
    private List<String> parameters;
    private Team team;
    private Member member;

    @BeforeEach
    public void setUp(){
        tmsRepository = new TMSRepositoryImpl();
        showTeamMembersCommand = new ShowTeamMembersCommand(tmsRepository);
//        create and login member
        member = new MemberImpl("TestMember");
        tmsRepository.login(member);
//        create and add team to repo
        team = new TeamImpl("TestTeam");
        tmsRepository.addTeam(team);
//        create a valid command
        parameters = new ArrayList<>();
        parameters.add("TestTeam");
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String>params = new ArrayList<>();
        params.add("String1");
        params.add("String1");
        // TODO: 13-Aug-23 it works but not as expected :(
        Assertions.assertThrows(IllegalArgumentException.class, () -> showTeamMembersCommand.execute(params));
    }


    @Test
    public void should_ShowMessage_When_NoMembersInTeam(){
        String result = showTeamMembersCommand.execute(parameters);
        Assertions.assertEquals(String.format(NO_MEMBERS_MESSAGE, parameters.get(0)), result);
    }

    @Test
    public void should_ShowMember_When_SingleMemberInTeam(){
        team.addMember(member);
        String result = showTeamMembersCommand.execute(parameters);
        Assertions.assertEquals("TestMember" ,result);
    }

    @Test
    public void should_ShowMembers_When_MultipleMembersInTeam(){
        Member member2 = new MemberImpl("TestMember2");
        team.addMember(member);
        team.addMember(member2);
        String result = showTeamMembersCommand.execute(parameters);
        Assertions.assertEquals("TestMember, TestMember2", result);
    }
}
