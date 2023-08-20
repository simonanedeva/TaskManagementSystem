package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.ActivityLogImpl;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityLogImplTests {

    public static final String VALID_DESCRIPTION = "Valid description";

    @Test
    public void constructor_Should_InitializeDescription_When_ArgumentsAreValid() {
        ActivityLog activityLog = initilizeValidActivityLog();
        Assertions.assertEquals(VALID_DESCRIPTION, activityLog.getDescription());
    }

    @Test
    public void constructor_Should_InitializeTimestamp_When_ArgumentsAreValid() {
        ActivityLog activityLog = initilizeValidActivityLog();
        Assertions.assertNotNull(activityLog.getTimestamp());
    }

    private static ActivityLog initilizeValidActivityLog() {
        return new ActivityLogImpl(VALID_DESCRIPTION);
    }
    // TODO: 18.08.23 implement 

}
