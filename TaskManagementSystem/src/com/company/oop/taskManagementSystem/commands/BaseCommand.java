package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;

import java.util.List;

public abstract class BaseCommand implements Command {

    public final static String MEMBER_NOT_LOGGED = "You are not logged in! Please login first!";
    private final TMSRepository tmsRepository;

    protected BaseCommand(TMSRepository tmsRepository) {
        this.tmsRepository = tmsRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (requiresLogin() && !tmsRepository.hasLoggedInMember()) {
            throw new IllegalArgumentException(MEMBER_NOT_LOGGED);
        }
        return executeCommand(parameters);
    }

    protected TMSRepository getTmsRepository() {
        return tmsRepository;
    }

    protected abstract boolean requiresLogin();

    protected abstract String executeCommand(List<String> parameters);
}
