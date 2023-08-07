package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    private final static String USER_NOT_LOGGED = "You are not logged in! Please login first!";
    private final TMSRepository tmsRepository;

    protected BaseCommand(TMSRepository tmsRepository) {
        this.tmsRepository = tmsRepository;
    }

    protected TMSRepository getTmsRepository(){
        return tmsRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (requiresLogin() && !tmsRepository.hasLoggedInUser()) {
            throw new IllegalArgumentException(USER_NOT_LOGGED);
        }
        return executeCommand(parameters);
    }

    protected abstract boolean requiresLogin();

    protected abstract String executeCommand(List<String> parameters);
}
