package com.company.oop.taskManagementSystem.commands.listCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class FilterCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public FilterCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskType = parameters.get(0);
        String filterBy = parameters.get(1);
        String pattern = parameters.get(2);

        return switch (taskType) {
            case "allTasks" -> filterAllTask(pattern);
            case "Bugs" -> filterBug(filterBy, pattern);
            case "Stories" -> filterStory(filterBy, pattern);
            case "Feedbacks" -> filterFeedback(pattern);
            case "Tasks" -> filterTask(filterBy, pattern);
            default ->
                    throw new IllegalArgumentException("What you are searching for might exist in some other space-time " +
                            "continuum");
        };
    }

    private String filterAllTask(String pattern) {
        List<Task> taskList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            taskList.addAll(b.getTasks());
        }
        return listMatchingTitle(pattern, taskList);
    }

    private String listMatchingTitle(String pattern, List<Task> taskList) {
        return taskList.stream()
                .filter(task -> task.getTitle().contains(pattern))
                .collect(StringBuilder::new,
                        (stringBuilder, task) -> {
                            stringBuilder.append(task.returnTaskSimpleInfo());
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
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
                return listMatchingStatus(pattern, bugList);
            }
            case "Assignee" -> {
                return listMatchingAssignee(pattern, bugList);
            }
            case "StatusAndAssignee" -> {
                String[] filterParams = pattern.split("/");
                return listMatchingAssigneeAndStatus(bugList, filterParams);
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private <T extends Task> String listMatchingStatus(String pattern, List<T> genericList) {
        return genericList.stream()
                .filter(taskType -> taskType.getStatus().toString().equals(pattern))
                .collect(StringBuilder::new,
                        (stringBuilder, taskType) -> {
                            stringBuilder.append(taskType);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }


    private <T extends Task & AssigneeGettable> String listMatchingAssignee(String pattern, List<T> genericList) {
        return genericList.stream()
                .filter(taskType -> taskType.getAssignee().equals(pattern))
                .collect(StringBuilder::new,
                        ((stringBuilder, taskType) -> {
                            stringBuilder.append(taskType);
                            stringBuilder.append(System.lineSeparator());
                        }), StringBuilder::append).toString();
    }

    private <T extends Task & AssigneeGettable> String listMatchingAssigneeAndStatus(List<T> genericList, String[] filterParams) {
        return genericList.stream()
                .filter(taskType -> taskType.getAssignee().equals(filterParams[1]))
                .filter(taskType -> taskType.getStatus().toString().equals(filterParams[0]))
                .collect(StringBuilder::new,
                        (stringBuilder, taskType) -> {
                            stringBuilder.append(taskType);
                            stringBuilder.append(System.lineSeparator());
                        },
                        StringBuilder::append).toString();
    }

    private String filterFeedback(String pattern) {
        List<Feedback> feedbackList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            feedbackList.addAll(b.getFeedbacks());
        }

        return listMatchingStatus(pattern, feedbackList);

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
                return listMatchingStatus(pattern, storyList);
            }
            case "Assignee" -> {
                return listMatchingAssignee(pattern, storyList);
            }
            case "StatusAndAssignee" -> {
                String[] filterParams = pattern.split("/");
                return listMatchingAssigneeAndStatus(storyList, filterParams);
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String filterTask(String filterBy, String pattern) {

        List<Story> storyList = new ArrayList<>();
        List<Bug> bugList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());


        for (Board b : teamOfLoggedInMember.getBoards()) {
            storyList.addAll(b.getStories());
            bugList.addAll(b.getBugs());
        }

        StringBuilder sb = new StringBuilder();
        String storyString;
        String bugString;

        switch (filterBy) {
            case "Status":
                storyString = listMatchingStatus(pattern, storyList);
                bugString = listMatchingStatus(pattern, bugList);
                return sb.append(storyString).append(System.lineSeparator()).append(bugString).toString();
            case "Assignee":
                storyString = listMatchingAssignee(pattern, storyList);
                bugString = listMatchingAssignee(pattern, bugList);
                return sb.append(storyString).append(System.lineSeparator()).append(bugString).toString();
            case "StatusAndAssignee":
                String[] filterParams = pattern.split("/");
                storyString = listMatchingAssigneeAndStatus(storyList, filterParams);
                bugString = listMatchingAssigneeAndStatus(bugList, filterParams);
                return sb.append(storyString).append(System.lineSeparator()).append(bugString).toString();
            default:
                throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

}
