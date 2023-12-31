package com.company.oop.taskManagementSystemTests.models.taskModels;

import com.company.oop.taskManagementSystem.models.BugImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryImplTests {

    @Test
    public void constructor_Should_CreateStory_When_ValidInput() {
        StoryImpl story = initializeStory();

        Assertions.assertAll(
                () -> assertEquals(TaskConstants.VALID_TASK_ID, story.getId()),
                () -> assertEquals(TaskConstants.VALID_TASK_TITLE, story.getTitle()),
                () -> assertEquals(TaskConstants.VALID_TASK_DESCRIPTION, story.getDescription()),
                () -> assertEquals(TaskConstants.VALID_PRIORITY, story.getPriority()),
                () -> assertEquals(TaskConstants.VALID_SIZE, story.getSize()),
                () -> assertEquals(TaskConstants.VALID_TASK_ASSIGNEE, story.getAssignee())
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_TitleTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.INVALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SIZE, TaskConstants.VALID_TASK_ASSIGNEE));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.INVALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SIZE, TaskConstants.VALID_TASK_ASSIGNEE));
    }


    @Test
    public void getter_Should_Return_ValidPriority() {
        StoryImpl story = initializeStory();

        assertEquals(TaskConstants.VALID_PRIORITY, story.getPriority());
    }

    @Test
    public void getter_Should_Return_ValidSize() {
        StoryImpl story = initializeStory();

        assertEquals(TaskConstants.VALID_SIZE, story.getSize());
    }

    @Test
    public void getter_Should_Return_ValidInitialStatus() {
        StoryImpl story = initializeStory();

        assertEquals(TaskConstants.INITIAL_STORY_STATUS, story.getStatus());
    }

    @Test
    public void getter_Should_Return_ValidAssignee() {
        StoryImpl story = initializeStory();

        assertEquals(TaskConstants.VALID_TASK_ASSIGNEE, story.getAssignee());
    }

    @Test
    public void getter_Should_Return_ValidType() {
        StoryImpl story = initializeStory();

        assertEquals("StoryImpl", story.getType());
    }

    @Test
    public void method_Should_Change_Priority() {
        StoryImpl story = initializeStory();

        story.changePriority(Priority.LOW);

        assertEquals(Priority.LOW, story.getPriority());
    }

    @Test
    public void method_Should_Change_Size() {
        StoryImpl story = initializeStory();

        story.changeSize(StorySize.MEDIUM);

        assertEquals(StorySize.MEDIUM, story.getSize());
    }

    @Test
    public void method_Should_Change_Status() {
        StoryImpl story = initializeStory();

        story.changeStatus(StatusValues.INPROGRESS);

        assertEquals(StatusValues.INPROGRESS, story.getStatus());
    }

    @Test
    public void method_Should_Change_Assignee_WhenValidArgument() {
        StoryImpl story = initializeStory();

        story.changeAssignee("Simona");

        assertEquals("Simona", story.getAssignee());
    }

    @Test
    public void method_Should_ThrowException_When_NewAssigneeSameAsOld() {
        StoryImpl story = initializeStory();

        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeAssignee("Victor"));
    }

    @Test
    public void changePriority_shouldThrow_When_PriorityAlreadySetToSameValue(){
        StoryImpl story = initializeStory();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> story.changePriority(TaskConstants.VALID_PRIORITY));
    }

    @Test
    public void isValidStatus_ShouldReturnTrue_When_ValidStatus() {
        StoryImpl story = initializeStory();
        Assertions.assertTrue(story.isValidStatus(StatusValues.NOTDONE));
    }

    @Test
    public void isValidStatus_ShouldThrow_When_InvalidStatus() {
        StoryImpl story = initializeStory();
        Assertions.assertThrows(IllegalArgumentException.class, () -> story.isValidStatus(StatusValues.UNSCHEDULED));
    }


    public static StoryImpl initializeStory() {
        return new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SIZE, TaskConstants.VALID_TASK_ASSIGNEE);
    }


}