package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterCommand extends BaseCommand{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public FilterCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskType = parameters.get(0);
        String filterBy = parameters.get(1);
        String pattern = parameters.get(2);

        return switch (taskType) {
            case "allTasks" -> filterAllTask(filterBy, pattern);
            case "Bugs" -> filterBug(filterBy, pattern);
            case "Stories" -> filterStory(filterBy, pattern);
            case "Feedbacks" -> filterFeedback(filterBy, pattern);
            case "Tasks" -> filterTask(filterBy, pattern);
            default ->
                    throw new IllegalArgumentException("What you are searching for might exist in some other space-time " +
                            "continuum");
        };
    }

    private <T extends Task & AssigneeGettable> String filterTasks(List<T> taskList, String filterBy, String pattern) {
        Predicate<T> predicate;

        switch (filterBy) {
            case "Status":
                predicate = task -> task.getStatus().equals(pattern);
                break;
            case "Assignee":
                predicate = task -> task.getAssignee().equals(pattern);
                break;
            case "StatusAndAssignee":
                String[] filterParams = pattern.split("/");
                predicate = task -> task.getAssignee().equals(filterParams[1]) && task.getStatus().equals(filterParams[0]);
                break;
            default:
                throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String filterAllTask(String filterBy, String pattern) {
        return null;
    }

    private String filterBug(String filterBy, String pattern) {
        List<Bug> bugList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            bugList.addAll(b.getBugs());
        }
        switch (filterBy) {
            case "Status" -> {
                return listBugsMatchingStatus(pattern, bugList);
            }
            case "Assignee" -> {
                return listBugsMatchingAssignee(pattern, bugList);
            }
            case "StatusAndAssignee" -> {
                String[] filterParams = pattern.split("/");
                return listBugsMatchingAssigneeAndStatus(bugList, filterParams);
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private static String listBugsMatchingStatus(String pattern, List<Bug> bugList) {
        return bugList.stream().filter(bug -> bug.getStatus().equals(pattern))
                .collect(StringBuilder::new,
                        (stringBuilder, bug) -> {
                            stringBuilder.append(bug);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }

    private static String listBugsMatchingAssignee(String pattern, List<Bug> bugList) {
        return bugList.stream()
                .filter(bug -> bug.getAssignee().equals(pattern))
                .collect(StringBuilder::new,
                        ((stringBuilder, bug) -> {
                            stringBuilder.append(bug);
                            stringBuilder.append(System.lineSeparator());
                        }), StringBuilder::append).toString();
    }

    private static String listBugsMatchingAssigneeAndStatus(List<Bug> bugList, String[] filterParams) {
        return bugList.stream()
                .filter(bug -> bug.getAssignee().equals(filterParams[1]))
                .filter(bug -> bug.getStatus().equals(filterParams[0]))
                .collect(StringBuilder::new,
                        (stringBuilder, bug) -> {
                            stringBuilder.append(bug);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }


    //Filter allTasks Title kotka;
    //Filter Bug Status FIXED
    //Filter Bug Assignee Simona
    //Filter Bug StatusAndAssignee FIXED/Simona
    //Filter Task Title kotka

    private String filterFeedback(String filterBy, String pattern) {
        return null;
    }

    private String filterStory(String filterBy, String pattern) {
        List<Story> storyList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            storyList.addAll(b.getStories());
        }
        switch (filterBy) {
            case "Status" -> {
                return listStoriesMatchingStatus(pattern, storyList);
            }
            case "Assignee" -> {
                return listStoriesMatchingAssignee(pattern, storyList);
            }
            case "StatusAndAssignee" -> {
                String[] filterParams = pattern.split("/");
                return listStoriesMatchingAssigneeAndStatus(storyList, filterParams);
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String listStoriesMatchingAssigneeAndStatus(List<Story> storyList, String[] filterParams) {
        return storyList.stream()
                .filter(story -> story.getAssignee().equals(filterParams[1]))
                .filter(story -> story.getStatus().equals(filterParams[0]))
                .collect(StringBuilder::new,
                        (stringBuilder, story) -> {
                            stringBuilder.append(story);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }

    private String listStoriesMatchingAssignee(String pattern, List<Story> storyList) {
        return storyList.stream().filter(story -> story.getAssignee().equals(pattern))
                .collect(StringBuilder::new,
                        ((stringBuilder, story) -> {
                            stringBuilder.append(story);
                            stringBuilder.append(System.lineSeparator());
                        }), StringBuilder::append).toString();
    }

    private String listStoriesMatchingStatus(String pattern, List<Story> storyList) {
        return storyList.stream().filter(story -> story.getStatus().equals(pattern))
                .collect(StringBuilder::new,
                        (stringBuilder, story) -> {
                            stringBuilder.append(story);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }

    private String filterTask(String filterBy, String pattern){
        return null;
    }


    @Override
    protected boolean requiresLogin() {
        return false;
    }
}
