package com.company.oop.taskManagementSystemTests.utils;

import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.models.enums.StorySize;

import java.util.Arrays;
import java.util.List;

public class TaskConstants {

    private static final int TITLE_MIN_LENGTH = 10;
    private static final int TITLE_MAX_LENGTH = 50;
    private static final int DESCRIPTION_MIN_LENGTH = 10;
    private static final int DESCRIPTION_MAX_LENGTH = 500;

    private static final String VALID_TITLE = TestHelpers.getString(TITLE_MIN_LENGTH + 1);
    private static final String INVALID_TITLE = TestHelpers.getString(TITLE_MAX_LENGTH + 1);
    private static final String VALID_DESCRIPTION = TestHelpers.getString(DESCRIPTION_MIN_LENGTH + 1);
    private static final String INVALID_DESCRIPTION = TestHelpers.getString(DESCRIPTION_MAX_LENGTH + 1);

    public static final int VALID_TASK_ID = 1;
    public static final String VALID_TASK_TITLE = "ThisIsAValidTitle";
    public static final String VALID_TASK_DESCRIPTION = "{{Description of task}}";
    public static final String VALID_TASK_ASSIGNEE = "Victor";


    //Story
    public static final Priority VALID_PRIORITY = Priority.HIGH;
    public static final StorySize VALID_SIZE = StorySize.SMALL;
    public static final StatusValues INITIAL_STORY_STATUS = StatusValues.NOTDONE;


    //Feedback
    public static final int VALID_RATING = 1;

    //Bug
    public static final List<String> VALID_STEPS_TO_REPRODUCE = Arrays.asList(
            "Open the application",
            "Click \"Log In\"",
            "The application freezes!"
    );
    public static final BugSeverity VALID_SEVERITY = BugSeverity.CRITICAL;
    public static final StatusValues INITIAL_BUG_STATUS = StatusValues.ACTIVE;



}
