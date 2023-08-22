package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.ActivityLogImpl;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
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

    @Test
    public void viewInfo_ShouldReturn_When_Prompted() {
        ActivityLog activityLog = initilizeValidActivityLog();
        Assertions.assertNotNull(activityLog.viewInfo());
    }

    private static ActivityLog initilizeValidActivityLog() {
        return new ActivityLogImpl(VALID_DESCRIPTION);
    }

}
