package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.commands.*;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.enums.CommandType;
import com.company.oop.taskManagementSystem.core.contracts.CommandFactory;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {
    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TMSRepository tmsRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);
        switch (commandType) {
            case LOGIN:
                return new LoginCommand(tmsRepository);
            case LOGOUT:
                return new LogoutCommand(tmsRepository);
            case CREATEMEMBER:
                return new CreateMemberCommand(tmsRepository);
            case SHOWMEMBERS:
                return new ShowMembersCommand(tmsRepository);
            case SHOWMEMBERACTIVITY:
                return new ShowMemberActivity(tmsRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
