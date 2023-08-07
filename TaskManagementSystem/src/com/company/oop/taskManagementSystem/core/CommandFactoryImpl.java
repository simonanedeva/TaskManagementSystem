package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.commands.CreateMemberCommand;
import com.company.oop.taskManagementSystem.commands.LogoutCommand;
import com.company.oop.taskManagementSystem.commands.ShowMemberActivity;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.LoginCommand;
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
            case SHOWMEMBERACTIVITY:
                return new ShowMemberActivity(tmsRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
