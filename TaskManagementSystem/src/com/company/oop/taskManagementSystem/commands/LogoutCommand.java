package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;

import java.util.List;

public class LogoutCommand extends BaseCommand{

    public final static String MEMBER_LOGGED_OUT = "Member logged out!";
    public LogoutCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        getTmsRepository().logout();
        return MEMBER_LOGGED_OUT;
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
