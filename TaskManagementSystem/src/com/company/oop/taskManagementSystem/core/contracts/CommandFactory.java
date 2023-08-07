package com.company.oop.taskManagementSystem.core.contracts;

import com.company.oop.taskManagementSystem.commands.contracts.Command;

public interface CommandFactory {
    Command createCommandFromCommandName(String commandTypeAsString, TMSRepository tmsRepository);
}
