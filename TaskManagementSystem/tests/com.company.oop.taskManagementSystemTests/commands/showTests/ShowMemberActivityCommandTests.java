package com.company.oop.taskManagementSystemTests.commands.showTests;


import com.company.oop.taskManagementSystem.commands.showCommands.ShowBoardActivityCommand;
import com.company.oop.taskManagementSystem.commands.showCommands.ShowMemberActivityCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowMemberActivityCommandTests {
    private ShowMemberActivityCommand showMemberActivityCommand;
    private Board board;
    private List<String> params;
    private Member member;

    @BeforeEach
    public void beforeEach() {
        TMSRepository tmsRepository = new TMSRepositoryImpl();
        showMemberActivityCommand = new ShowMemberActivityCommand(tmsRepository);
        params = new ArrayList<>();
        member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        tmsRepository.addMember(member);
        tmsRepository.login(member);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> showMemberActivityCommand.execute(params));
    }

    @Test
    public void showMemberActivity_ShouldReturn_When_ActivityPresent() {
        member.logEvent("eventLog");
        params.add(member.getUsername());

        Assertions.assertEquals(member.displayActivityHistory(), showMemberActivityCommand.execute(params));
    }

    @Test
    public void showMemberActivity_ShouldReturnErrorMessage_When_NoActivity() {
        params.add(member.getUsername());

        Assertions.assertEquals(String.format(ShowMemberActivityCommand.NO_ACTIVITY_FOR_MEMBER, member.getUsername()),
                showMemberActivityCommand.execute(params));
    }
}
