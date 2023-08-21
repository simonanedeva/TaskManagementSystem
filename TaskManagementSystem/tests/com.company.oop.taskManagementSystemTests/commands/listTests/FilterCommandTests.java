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
    public void filterBug_ShouldReturnEmpty_When_NoBugWithThisAssignee(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Assignee");
        params.add("Simona");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterBug_ShouldReturnEmpty_When_NoBugWithThisStatus(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("Status");
        params.add("Fixed");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterBug_ShouldReturnEmpty_When_NoBugWithThisStatusAndAssignee(){
        BugImpl bug = initializeBug();
        board.addBug(bug);

        params.add("Bugs");
        params.add("StatusAndAssignee");
        params.add("Fixed/Victor");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterStory_ShouldReturnEmpty_When_NoStoryWithThisAssignee(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Assignee");
        params.add("Simona");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterStory_ShouldReturnEmpty_When_NoStoryWithThisStatusAndAssignee(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("StatusAndAssignee");
        params.add("NotDone/Victor");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterStory_ShouldReturnEmpty_When_NoStoryWithThisStatus(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Stories");
        params.add("Status");
        params.add("Done");

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
    public void filterFeedback_ShouldReturnEmpty_When_NoFeedbackWithThisStatus(){
        StoryImpl story = initializeStory();
        board.addStory(story);

        params.add("Feedbacks");
        params.add("Status");
        params.add("Done");
        filterCommand.execute(params);

        Assertions.assertEquals("Nothing to show.",filterCommand.execute(params));
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
