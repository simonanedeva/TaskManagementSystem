package com.company.oop.taskManagementSystemTests.commands.listTests;

import com.company.oop.taskManagementSystem.commands.listCommands.FilterCommand;
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
import java.util.stream.Collectors;

public class FilterCommandTests {
    private TMSRepository tmsRepository;
    private FilterCommand filterCommand;
    private Team team;
    private Board board;
    List<String> params;

    @BeforeEach
    public void beforeEach(){
        tmsRepository = new TMSRepositoryImpl();
        filterCommand = new FilterCommand(tmsRepository);
        params = new ArrayList<>();
        Member member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        this.tmsRepository.addMember(member);
        this.tmsRepository.login(member);
        team = new TeamImpl("Javaholics");
        this.tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        board = new BoardImpl("BoardOne");
        this.team.addBoard(board);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    @Test
    public void should_ThrowException_When_IllegalAttributeToFilter(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Batman");
        params.add("Assignee");
        params.add("Victor");

        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    /** Bug tests following */
    @Test
    public void should_ThrowException_When_IllegalParameterToFilterBug(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Batman");
        params.add("Victor");

        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturn_When_BugWithThisAssigneeIsPresent(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Assignee");
        params.add("Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturnErrorMessage_When_NoBugWithThisAssignee(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Assignee");
        params.add("Simona");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturn_When_BugWithThisStatusIsPresent(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Status");
        params.add("Active");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturnErrorMessage_When_NoBugWithThisStatus(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Status");
        params.add("Fixed");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturn_When_BugWithThisStatusAndAssigneeIsPresent(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("StatusAndAssignee");
        params.add("Active/Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterBug_ShouldReturnErrorMessage_When_NoBugWithThisStatusAndAssignee(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("StatusAndAssignee");
        params.add("Fixed/Victor");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }


    /** Story tests following */
    @Test
    public void filterStory_ShouldReturn_When_StoryWithThisAssigneeIsPresent(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Assignee");
        params.add("Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void should_ThrowException_When_IllegalParameterToFilterStory(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Batman");
        params.add("Victor");

        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    @Test
    public void filterStory_ShouldReturnErrorMessage_When_NoStoryWithThisAssignee(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Assignee");
        params.add("Simona");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterStory_ShouldReturn_When_StoryWithThisStatusAndAssigneeIsPresent(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("StatusAndAssignee");
        params.add("NotDone/Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterStory_ShouldReturnErrorMessage_When_NoStoryWithThisStatusAndAssignee(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("StatusAndAssignee");
        params.add("NotDone/Victor");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterStory_ShouldReturn_When_StoryWithThisStatusPresent(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Status");
        params.add("NotDone");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterStory_ShouldReturnErrorMessage_When_NoStoryWithThisStatus(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Status");
        params.add("Done");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    /** Feedback tests following */

    @Test
    public void filterFeedback_ShouldReturn_When_FeedbackWithStatusExist(){
        FeedbackImpl feedback = initializeFeedback();
        board.addFeedback(feedback);

        params.add("Feedbacks");
        params.add("Status");
        params.add("New");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterFeedback_ShouldReturnErrorMessage_When_NoFeedbackWithThisStatus(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Feedbacks");
        params.add("Status");
        params.add("Done");
        filterCommand.execute(params);

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    /** Tasks tests following */

    @Test
    public void should_ThrowException_When_IllegalParameterToFilterTasks(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("Batman");
        params.add("Victor");

        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturn_When_TaskWithThisAssigneeIsPresent(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("Assignee");
        params.add("Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturnErrorMessage_When_NoTaskWithThisAssignee(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("Assignee");
        params.add("Simona");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturn_When_TaskWithThisStatusIsPresent(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("Status");
        params.add("Active");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturnErrorMessage_When_NoTaskWithThisStatus(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("Status");
        params.add("Fixed");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturn_When_TasksWithThisStatusAndAssigneeIsPresent(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Bugs");
        params.add("StatusAndAssignee");
        params.add("Active/Victor");

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterTasks_ShouldReturnErrorMessage_When_NoTaskWithThisStatusAndAssignee(){
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("Tasks");
        params.add("StatusAndAssignee");
        params.add("Fixed/Victor");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE,filterCommand.execute(params));
    }

    @Test
    public void filterAllTask_ShouldReturn_When_TaskTitleMatchPattern() {
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("allTasks");
        params.add("Title");
        params.add(TaskConstants.VALID_TASK_TITLE);

        Assertions.assertNotNull(filterCommand.execute(params));
    }

    @Test
    public void filterAllTask_ShouldReturnErrorMessage_When_NoMatch() {
        BugImpl bug = initializeBug();
        StoryImpl story = initializeStory();
        FeedbackImpl feedback = initializeFeedback();
        board.addBug(bug);
        board.addStory(story);
        board.addFeedback(feedback);

        params.add("allTasks");
        params.add("Title");
        params.add("Batman");

        Assertions.assertEquals(FilterCommand.EMPTY_ERR_MESSAGE, filterCommand.execute(params));
    }


    /** Helper methods for Bug, Story and Feedback initialization */
    public static BugImpl initializeBug() {
        return new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE,
                TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY, TaskConstants.VALID_TASK_ASSIGNEE);
    }
    public static FeedbackImpl initializeFeedback() {
        return new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_FEEDBACK_RATING);
    }
    public static StoryImpl initializeStory() {
        return new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SIZE,
                TaskConstants.VALID_TASK_ASSIGNEE);
    }
}
