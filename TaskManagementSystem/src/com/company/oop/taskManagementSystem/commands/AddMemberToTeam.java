package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;

import java.util.List;

public class AddMemberToTeam extends BaseCommand{
    protected AddMemberToTeam(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        return null;
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
