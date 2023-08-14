package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryImplTests {


    public static final Priority VALID_PRIORITY = Priority.HIGH;
    public static final StorySize VALID_SIZE = StorySize.SMALL;
    public static final String VALID_ASSIGNEE = "Victor";

    @Test
    public void should_CreateStory_When_PassedValidInput() {
        StoryImpl story = initialize_Story();

        Assertions.assertAll(
                () -> assertEquals(TaskImplTests.VALID_TASK_ID, story.getId()),
                () -> assertEquals(TaskImplTests.VALID_TASK_TITLE, story.getTitle()),
                () -> assertEquals(TaskImplTests.VALID_TASK_DESCRIPTION, story.getDescription()),
                () -> assertEquals(VALID_PRIORITY, story.getPriority()),
                () -> assertEquals(VALID_SIZE, story.getSize()),
                () -> assertEquals(VALID_ASSIGNEE, story.getAssignee())
        );
    }

    // TODO: 14.08.23 think of a way to test invalidID 
    @Test
    public void constructor_Should_ThrowException_When_TitleTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskImplTests.VALID_TASK_ID, "Short", TaskImplTests.VALID_TASK_DESCRIPTION, VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskImplTests.VALID_TASK_ID, TaskImplTests.VALID_TASK_TITLE, "Short", VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE));
    }

    // TODO: 14.08.23 do we need to test what happens if assignee is not part of the team here, or in the commands?

    @Test
    public void getter_Should_Return_ValidPriority() {
        StoryImpl story = initialize_Story();

        assertEquals(VALID_PRIORITY, story.getPriority());
    }

    //TODO TESTS:
    //get size
    //get status
    //get assignee
    //get type
    //change priority method
    //change size method


    //Helpers method to initialize a valid StoryImpl
    public StoryImpl initialize_Story() {
        return new StoryImpl(TaskImplTests.VALID_TASK_ID, TaskImplTests.VALID_TASK_TITLE, TaskImplTests.VALID_TASK_DESCRIPTION, VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE);
    }


}
