package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.EnumComparator;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortCommand extends BaseCommand{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public SortCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskType = parameters.get(0);
        String sortBy = parameters.get(1);

        return switch (taskType) {
            case "allTasks" -> sortAllTasks();
            case "Bugs" -> sortBug(sortBy);
            case "Stories" -> sortStory(filterBy);
            case "Feedbacks" -> sortFeedback();
            case "Tasks" -> sortTask(filterBy);
            default ->
                    throw new IllegalArgumentException("What you are searching for might exist in some other space-time " +
                            "continuum");
        };
    }

    private String sortBug(String sortBy) {
        List<Bug> bugList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            bugList.addAll(b.getBugs());
        }
        switch (sortBy) {
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

    private String sortAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        for (Board b : teamOfLoggedInMember.getBoards()) {
            taskList.addAll(b.getTasks());
        }
        return taskList.stream().sorted().collect(StringBuilder::new,
                (stringBuilder, task) -> {
                    stringBuilder.append(task.returnTaskSimpleInfo());
                    stringBuilder.append(System.lineSeparator());
                },
                StringBuilder::append).toString();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }
}
