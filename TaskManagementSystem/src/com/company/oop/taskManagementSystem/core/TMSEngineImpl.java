package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.contracts.CommandFactory;
import com.company.oop.taskManagementSystem.core.contracts.TMSEngine;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TMSEngineImpl implements TMSEngine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERROR = "Command cannot be empty.";
    private static final String MAIN_SPLIT_SYMBOL = " ";
    private static final String DESCRIPTION_OPEN_SYMBOL = "{{";
    private static final String DESCRIPTION_CLOSE_SYMBOL = "}}";
    private static final String REPORT_SEPARATOR = "####################";

    private final CommandFactory commandFactory;
    private final TMSRepository tmsRepository;

    public TMSEngineImpl() {
        this.commandFactory = new CommandFactoryImpl();
        this.tmsRepository = new TMSRepositoryImpl();
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    print(EMPTY_COMMAND_ERROR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    print(ex.getMessage());
                } else {
                    print(ex.toString());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        List<String> parameters = extractCommandParameters(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, tmsRepository);
        String executionResult = command.execute(parameters);
        print(executionResult);
    }

    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

    private List<String> extractCommandParameters(String inputLine) {
        if (inputLine.contains(DESCRIPTION_OPEN_SYMBOL)) {
            return extractDescriptionParameters(inputLine);
        }
        String[] commandParts = inputLine.split(" ");
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }

    public List<String> extractDescriptionParameters(String fullCommand) {
        int indexOfFirstSeparator = fullCommand.indexOf(MAIN_SPLIT_SYMBOL);
        int indexOfOpenDescription = fullCommand.indexOf(DESCRIPTION_OPEN_SYMBOL);
        int indexOfCloseDescription = fullCommand.indexOf(DESCRIPTION_CLOSE_SYMBOL);
        List<String> parameters = new ArrayList<>();
        if (indexOfOpenDescription >= 0) {
            parameters.add(fullCommand.substring(indexOfOpenDescription + DESCRIPTION_OPEN_SYMBOL.length(), indexOfCloseDescription));
            fullCommand = fullCommand.replaceAll("\\{\\{.+(?=}})}}", "");
        }

        List<String> result = new ArrayList<>
                (Arrays.asList(fullCommand.substring(indexOfFirstSeparator + 1)
                        .split(MAIN_SPLIT_SYMBOL)));
        result.removeAll(Arrays.asList(" ", "", null));
        parameters.addAll(result);
        return parameters;
    }

    private void print(String result) {
        System.out.println(result);
        System.out.println(REPORT_SEPARATOR);
    }

}
