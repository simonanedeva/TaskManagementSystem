package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.ActivityLogImpl;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberImplTests {

    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String VALID_MEMBER_NAME = TestHelpers.getString(NAME_MIN_LENGTH + 1);


    @Test
    public void constructor_Should_InitializeName_When_ArgumentsAreValid() {
        Member member = initilizeValidMember();
        Assertions.assertEquals(VALID_MEMBER_NAME, member.getUsername());
    }

    @Test
    public void constructor_Should_InitializeActivityHistoryList_When_ArgumentsAreValid() {
        Member member = initilizeValidMember();
        Assertions.assertNotNull(member.getActivityHistory());
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameLength() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new MemberImpl(TestHelpers.getString(NAME_MIN_LENGTH - 1))),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new TeamImpl(TestHelpers.getString(NAME_MAX_LENGTH + 1)))

        );
    }

    @Test
    public void logEvent_Should_LogToActivityHistory() {
        Member member = initilizeValidMember();
        member.logEvent("Test event");
        Assertions.assertEquals(1, member.getActivityHistory().size());
    }

    @Test
    public void getActivityHistory_Should_ReturnCopyOfCollection() {
        Member member = initilizeValidMember();

        member.logEvent("Test event");
        member.getActivityHistory().clear();

        Assertions.assertEquals(1, member.getActivityHistory().size());
    }

    public static Member initilizeValidMember() {
        return new MemberImpl(VALID_MEMBER_NAME);
    }
    // TODO: 18.08.23 implement
}
