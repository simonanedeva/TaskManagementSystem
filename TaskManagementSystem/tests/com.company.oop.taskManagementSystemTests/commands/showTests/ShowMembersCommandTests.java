package com.company.oop.taskManagementSystemTests.commands.showTests;

import com.company.oop.taskManagementSystem.commands.showCommands.ShowBoardActivityCommand;
import com.company.oop.taskManagementSystem.commands.showCommands.ShowMembersCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowMembersCommandTests {
    private ShowMembersCommand showMembersCommand;
    List<String> params;
    private Member member;
    private Member member2;

    @BeforeEach
    public void beforeEach() {
        TMSRepository tmsRepository = new TMSRepositoryImpl();
        showMembersCommand = new ShowMembersCommand(tmsRepository);
        params = new ArrayList<>();
        member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        member2 = new MemberImpl("Member2");
        tmsRepository.addMember(member);
        tmsRepository.addMember(member2);
        tmsRepository.login(member);
        Team team = new TeamImpl("Javaholics");
        tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        team.addMember(member2);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        params.add(0, "xxx");
        Assertions.assertThrows(IllegalArgumentException.class, () -> showMembersCommand.execute(params));
    }

    @Test
    public void showMembers_ShouldReturn_When_MembersPresent() {

        Assertions.assertEquals(String.format("%s, %s", member.getUsername(), member2.getUsername()) ,
                showMembersCommand.execute(params));
    }


}
