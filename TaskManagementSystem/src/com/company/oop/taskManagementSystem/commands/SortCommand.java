package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class SortCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public SortCommand(TMSRepository tmsRepository) {
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
        String sortBy = parameters.get(1);

        return switch (taskType) {
            case "allTasks" -> sortAllTasks();
            case "Bugs" -> sortBug(sortBy);
            case "Stories" -> sortStory(sortBy);
            case "Feedbacks" -> sortFeedback(sortBy);
            case "allTasksWithAssignee" -> sortTasksWithAssignee();
            default ->
                    throw new IllegalArgumentException("What you are searching for might exist in some other space-time " +
                            "continuum");
        };
    }

    public <T> String filter(List<T>list , Predicate<T> predicate){
        List<T> someList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            someList.addAll(b.getBugs());
        }
        return null;
    }

    private String sortBug(String sortBy) {
        List<Bug> bugList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            bugList.addAll(b.getBugs());
        }
        switch (sortBy) {

            case "Title" -> {
                return bugList.stream()
                        .sorted(Comparator.comparing(Bug::getTitle))
                        .collect(StringBuilder::new,
                                (stringBuilder, bug) -> {
                                    stringBuilder.append(bug);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Status" -> {
                return bugList.stream()
                        .sorted(Comparator.comparing(Bug::getStatus))
                        .collect(StringBuilder::new,
                                (stringBuilder, bug) -> {
                                    stringBuilder.append(bug);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();

            }
            case "Priority" -> {
                return bugList.stream()
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .collect(StringBuilder::new,
                                (stringBuilder, bug) -> {
                                    stringBuilder.append(bug);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Severity" -> {
                return bugList.stream()
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .collect(StringBuilder::new,
                                (stringBuilder, bug) -> {
                                    stringBuilder.append(bug);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Assignee" -> {
                return bugList.stream()
                        .sorted(Comparator.comparing(Bug::getAssignee))
                        .collect(StringBuilder::new,
                                (stringBuilder, bug) -> {
                                    stringBuilder.append(bug);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String sortStory(String sortBy) {
        List<Story> storyList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            storyList.addAll(b.getStories());
        }

        switch (sortBy) {

            case "Title" -> {
                return storyList.stream()
                        .sorted(Comparator.comparing(Story::getTitle))
                        .collect(StringBuilder::new,
                                (stringBuilder, story) -> {
                                    stringBuilder.append(story);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Status" -> {
                return storyList.stream()
                        .sorted(Comparator.comparing(Story::getStatus))
                        .collect(StringBuilder::new,
                                (stringBuilder, story) -> {
                                    stringBuilder.append(story);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();

            }
            case "Priority" -> {
                return storyList.stream()
                        .sorted(Comparator.comparing(Story::getPriority))
                        .collect(StringBuilder::new,
                                (stringBuilder, story) -> {
                                    stringBuilder.append(story);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Size" -> {
                return storyList.stream()
                        .sorted(Comparator.comparing(Story::getSize))
                        .collect(StringBuilder::new,
                                (stringBuilder, story) -> {
                                    stringBuilder.append(story);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Assignee" -> {
                return storyList.stream()
                        .sorted(Comparator.comparing(Story::getAssignee))
                        .collect(StringBuilder::new,
                                (stringBuilder, story) -> {
                                    stringBuilder.append(story);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String sortFeedback(String sortBy) {

        List<Feedback> feedbackList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            feedbackList.addAll(b.getFeedbacks());
        }
        switch (sortBy) {

            case "Title" -> {
                return feedbackList.stream()
                        .sorted(Comparator.comparing(Feedback::getTitle))
                        .collect(StringBuilder::new,
                                (stringBuilder, feedback) -> {
                                    stringBuilder.append(feedback);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();
            }
            case "Status" -> {
                return feedbackList.stream()
                        .sorted(Comparator.comparing(Feedback::getStatus))
                        .collect(StringBuilder::new,
                                (stringBuilder, feedback) -> {
                                    stringBuilder.append(feedback);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();

            }
            case "Rating" -> {
                return feedbackList.stream()
                        .sorted(Comparator.comparing(Feedback::getRating))
                        .collect(StringBuilder::new,
                                (stringBuilder, feedback) -> {
                                    stringBuilder.append(feedback);
                                    stringBuilder.append(System.lineSeparator());
                                },
                                StringBuilder::append).toString();

            }
            default -> throw new IllegalArgumentException("Unable to filter this way.");
        }
    }

    private String sortTasksWithAssignee() {

        List<Story> storyList = new ArrayList<>();
        List<Bug> bugList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());


        for (Board b : teamOfLoggedInMember.getBoards()) {
            storyList.addAll(b.getStories());
            bugList.addAll(b.getBugs());
        }

        List<Task> combinedTasks = new ArrayList<>();
        combinedTasks.addAll(storyList);
        combinedTasks.addAll(bugList);

        Collections.sort(combinedTasks, Comparator.comparing(Task::getTitle));

        StringBuilder sb = new StringBuilder();

        for (Task task : combinedTasks) {
            sb.append(task.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    private String sortAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            taskList.addAll(b.getTasks());
        }

        StringBuilder sb = new StringBuilder();
        taskList.stream()
                .sorted(Comparator.comparing(Task::getTitle))
                .forEach(task -> {
                    sb.append(task.returnTaskSimpleInfo());
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

}
