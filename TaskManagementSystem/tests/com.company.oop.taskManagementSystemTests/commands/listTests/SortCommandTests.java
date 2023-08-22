package com.company.oop.taskManagementSystemTests.commands.listTests;

import com.company.oop.taskManagementSystem.commands.listCommands.SortCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystemTests.models.taskModels.BugImplTests;
import com.company.oop.taskManagementSystemTests.models.taskModels.FeedbackImplTests;
import com.company.oop.taskManagementSystemTests.models.taskModels.StoryImplTests;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SortCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = SortCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_BUGS_ATTRIBUTE = "Bugs";
    public static final String VALID_STORIES_ATTRIBUTE = "Stories";
    public static final String VALID_FEEDBACKS_ATTRIBUTE = "Feedbacks";
    public static final String VALID_TASKS_WITH_ASSIGNEE_ATTRIBUTE = "allTasksWithAssignee";
    public static final String VALID_ALL_TASKS_ATTRIBUTE = "allTasks";
    private TMSRepository tmsRepository;
    private SortCommand sortCommand;
    private Team team;
    private Board board;

    @BeforeEach
    public void beforeEach() {
        tmsRepository = new TMSRepositoryImpl();
        sortCommand = new SortCommand(tmsRepository);
        Member member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        tmsRepository.addMember(member);
        tmsRepository.login(member);
        team = new TeamImpl("Javaholics");
        tmsRepository.addTeam(team);
        team.addMember(member);
        board = new BoardImpl("BoardOne");
        team.addBoard(board);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS+1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_IllegalAttributeToSortBy(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of("Cats",BugImplTests.initializeBug().getTitle());

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    /** Bug tests **/
    @Test
    public void should_ThrowException_When_IllegalParameterToSortBug(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Johnny Bravo");

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    // TODO: 22.08.23 it would be great if we can check whether the tasks,bugs,stories etc. are sorted as per the
    //  commands instruction.
    @Test
    public void sortBugByTitle_ShouldReturn(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Title");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByTitle_ShouldReturnMessage_When_NoBugsToSort(){
        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Title");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByStatus_ShouldReturn(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Status");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByStatus_ShouldReturnMessage_When_NoBugsToSort(){
        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Status");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByPriority_ShouldReturn(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Priority");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByPriority_ShouldReturnMessage_When_NoBugsToSort(){
        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Priority");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortBugBySeverity_ShouldReturn(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Severity");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortBugSeverity_ShouldReturnMessage_When_NoBugsToSort(){
        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Severity");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByAssignee_ShouldReturn(){
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Assignee");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortBugByAssignee_ShouldReturnMessage_When_NoBugsToSort(){
        List<String> parameters = List.of(VALID_BUGS_ATTRIBUTE,"Assignee");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    /** Story tests **/
    @Test
    public void should_ThrowException_When_IllegalParameterToSortStory(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Johnny Bravo");

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    // TODO: 22.08.23 it would be great if we can check whether the tasks,bugs,stories etc. are sorted as per the
    //  commands instruction.
    @Test
    public void sortStoryByTitle_ShouldReturn(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Title");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByTitle_ShouldReturnMessage_When_NoStoriesToSort(){
        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Title");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByStatus_ShouldReturn(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Status");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByStatus_ShouldReturnMessage_When_NoStoriesToSort(){
        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Status");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByPriority_ShouldReturn(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Priority");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByPriority_ShouldReturnMessage_When_NoStoriesToSort(){
        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Priority");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryBySize_ShouldReturn(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Size");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortStorySize_ShouldReturnMessage_When_NoStoriesToSort(){
        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Size");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByAssignee_ShouldReturn(){
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Assignee");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortStoryByAssignee_ShouldReturnMessage_When_NoStoriesToSort(){
        List<String> parameters = List.of(VALID_STORIES_ATTRIBUTE,"Assignee");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    /** Feedback tests **/
    @Test
    public void should_ThrowException_When_IllegalParameterToSortFeedback(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        board.addFeedback(feedback);

        List<String> parameters = List.of(
                VALID_FEEDBACKS_ATTRIBUTE,"Johnny Bravo");

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    // TODO: 22.08.23 it would be great if we can check whether the tasks,bugs,stories etc. are sorted as per the
    //  commands instruction.
    @Test
    public void sortFeedbackByTitle_ShouldReturn(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        board.addFeedback(feedback);

        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Title");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortFeedbackByTitle_ShouldReturnMessage_When_NoFeedbacksToSort(){
        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Title");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortFeedbackByStatus_ShouldReturn(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        board.addFeedback(feedback);

        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Status");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortFeedbackByStatus_ShouldReturnMessage_When_NoFeedbacksToSort(){
        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Status");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    @Test
    public void sortFeedbackByRating_ShouldReturn(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        board.addFeedback(feedback);

        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Rating");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortFeedbackByRating_ShouldReturnMessage_When_NoFeedbacksToSort(){
        List<String> parameters = List.of(VALID_FEEDBACKS_ATTRIBUTE,"Rating");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

    /** Task tests **/
    @Test
    public void should_ThrowException_When_IllegalParameterToSortTasksWithAssignee(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        Bug bug = BugImplTests.initializeBug();
        board.addFeedback(feedback);
        board.addBug(bug);
        board.addStory(story);

        List<String> parameters = List.of(VALID_TASKS_WITH_ASSIGNEE_ATTRIBUTE,"Johnny Bravo");

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    @Test
    public void sortTasksWithAssigneeByTitle_ShouldReturn(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        Bug bug = BugImplTests.initializeBug();
        board.addFeedback(feedback);
        board.addBug(bug);
        board.addStory(story);

        List<String> parameters = List.of(VALID_TASKS_WITH_ASSIGNEE_ATTRIBUTE,"Title");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortTasksWithAssigneeByTitle_ShouldReturnMessage_When_NoTasksToSort(){
        List<String> parameters = List.of(VALID_TASKS_WITH_ASSIGNEE_ATTRIBUTE, "Title");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }


    @Test
    public void should_ThrowException_When_IllegalParameterToSortAllTasks(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        Bug bug = BugImplTests.initializeBug();
        board.addFeedback(feedback);
        board.addBug(bug);
        board.addStory(story);

        List<String> parameters = List.of(VALID_ALL_TASKS_ATTRIBUTE,"Johnny Bravo");

        Assertions.assertThrows(IllegalArgumentException.class, () -> sortCommand.execute(parameters));
    }

    @Test
    public void sortAllTasksByTitle_ShouldReturn(){
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        Bug bug = BugImplTests.initializeBug();
        board.addFeedback(feedback);
        board.addBug(bug);
        board.addStory(story);

        List<String> parameters = List.of(VALID_ALL_TASKS_ATTRIBUTE,"Title");

        Assertions.assertNotNull(sortCommand.execute(parameters));
    }

    @Test
    public void sortAllTasksByTitle_ShouldReturnMessage_When_NoTasksToSort(){
        List<String> parameters = List.of(VALID_ALL_TASKS_ATTRIBUTE,"Title");

        Assertions.assertEquals("Nothing to show.",sortCommand.execute(parameters));
    }

}
