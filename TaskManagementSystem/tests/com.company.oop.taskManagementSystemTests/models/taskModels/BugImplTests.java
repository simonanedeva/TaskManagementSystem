package com.company.oop.taskManagementSystemTests.models.taskModels;

import com.company.oop.taskManagementSystem.models.BugImpl;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BugImplTests {


    @Test
    public void should_CreateBug_When_ValidInput() {
        BugImpl bug = initializeBug();

        Assertions.assertAll(
                () -> assertEquals(TaskConstants.VALID_TASK_ID, bug.getId()),
                () -> assertEquals(TaskConstants.VALID_TASK_TITLE, bug.getTitle()),
                () -> assertEquals(TaskConstants.VALID_TASK_DESCRIPTION, bug.getDescription()),
                () -> assertEquals(TaskConstants.VALID_STEPS_TO_REPRODUCE, bug.getStepsToReproduce()),
                () -> assertEquals(TaskConstants.VALID_PRIORITY, bug.getPriority()),
                () -> assertEquals(TaskConstants.VALID_SEVERITY, bug.getSeverity()),
                () -> assertEquals(TaskConstants.VALID_TASK_ASSIGNEE, bug.getAssignee())
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_TitleTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.INVALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY, TaskConstants.VALID_TASK_ASSIGNEE));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.INVALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY, TaskConstants.VALID_TASK_ASSIGNEE));
    }

    @Test
    public void getter_Should_Return_ValidPriority() {
        BugImpl bug = initializeBug();

        assertEquals(TaskConstants.VALID_PRIORITY, bug.getPriority());
    }

    @Test
    public void getter_Should_Return_ValidSeverity() {
        BugImpl bug = initializeBug();

        assertEquals(TaskConstants.VALID_SEVERITY, bug.getSeverity());
    }

    @Test
    public void getter_Should_Return_ValidInitialStatus() {
        BugImpl bug = initializeBug();

        assertEquals(TaskConstants.INITIAL_BUG_STATUS, bug.getStatus());
    }

    @Test
    public void getter_Should_Return_ValidChangedStatus() {
        BugImpl bug = initializeBug();

        bug.changeStatus(StatusValues.INPROGRESS);

        assertEquals(StatusValues.INPROGRESS, bug.getStatus());
    }

    @Test
    public void getter_Should_Return_ValidAssignee() {
        BugImpl bug = initializeBug();

        assertEquals(TaskConstants.VALID_TASK_ASSIGNEE, bug.getAssignee());
    }

    @Test
    public void getter_Should_Return_ValidType() {
        BugImpl bug = initializeBug();

        assertEquals("BugImpl", bug.getType());
    }

    @Test
    public void method_Should_Change_Priority() {
        BugImpl bug = initializeBug();

        bug.changePriority(Priority.LOW);

        assertEquals(Priority.LOW, bug.getPriority());
    }


    @Test
    public void method_Should_Change_Assignee_WhenValidArgument() {
        BugImpl bug = initializeBug();

        bug.changeAssignee("Simona");

        assertEquals("Simona", bug.getAssignee());
    }

    @Test
    public void method_Should_ThrowException_When_NewAssigneeSameAsOld() {
        BugImpl bug = initializeBug();

        Assertions.assertThrows(IllegalArgumentException.class, () -> bug.changeAssignee("Victor"));
    }

    @Test
    public void changePriority_shouldThrow_When_PriorityAlreadySetToSameValue(){
        BugImpl bug = initializeBug();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bug.changePriority(TaskConstants.VALID_PRIORITY));
    }

    @Test
    public void isValidStatus_ShouldReturnTrue_When_ValidStatus() {
        BugImpl bug = initializeBug();
        Assertions.assertTrue(bug.isValidStatus(StatusValues.ACTIVE));
    }

    @Test
    public void isValidStatus_ShouldThrow_When_InvalidStatus() {
        BugImpl bug = initializeBug();
        Assertions.assertThrows(IllegalArgumentException.class, () -> bug.isValidStatus(StatusValues.UNSCHEDULED));
    }

    //Helpers method to initialize a valid BugImpl
    public static BugImpl initializeBug() {
        return new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE,
                TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY, TaskConstants.VALID_TASK_ASSIGNEE);
    }


}
