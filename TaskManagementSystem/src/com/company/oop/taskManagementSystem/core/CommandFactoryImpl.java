package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.commands.assignCommands.AssignBugCommand;
import com.company.oop.taskManagementSystem.commands.assignCommands.AssignStoryCommand;
import com.company.oop.taskManagementSystem.commands.*;
import com.company.oop.taskManagementSystem.commands.addCommands.AddCommentToTaskCommand;
import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.changeCommands.*;
import com.company.oop.taskManagementSystem.commands.createCommands.*;
import com.company.oop.taskManagementSystem.commands.listCommands.FilterCommand;
import com.company.oop.taskManagementSystem.commands.listCommands.SortCommand;
import com.company.oop.taskManagementSystem.commands.showCommands.*;
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
                return new ShowMemberActivityCommand(tmsRepository);
            case CREATETEAM:
                return new CreateTeamCommand(tmsRepository);
            case SHOWTEAMS:
                return new ShowTeamsCommand(tmsRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand(tmsRepository);
            case SHOWTEAMMEMBERS:
                return new ShowTeamMembersCommand(tmsRepository);
            case ADDMEMBERTOTEAM:
                return new AddMemberToTeamCommand(tmsRepository);
            case CREATEBOARDINTEAM:
                return new CreateBoardInTeamCommand(tmsRepository);
            case SHOWTEAMBOARDS:
                return new ShowTeamBoardsCommand(tmsRepository);
            case CREATEFEEDBACK:
                return new CreateFeedbackCommand(tmsRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(tmsRepository);
            case CREATESTORY:
                return new CreateStoryCommand(tmsRepository);
            case CREATEBUG:
                return new CreateBugCommand(tmsRepository);
            case ADDCOMMENTTOTASK:
                return new AddCommentToTaskCommand(tmsRepository);
            case CHANGEPRIORITY:
                return new ChangePriorityCommand(tmsRepository);
            case CHANGESTATUS:
                return new ChangeStatusCommand(tmsRepository);
            case CHANGEBUGSEVERITY:
                return new ChangeBugSeverityCommand(tmsRepository);
            case CHANGESTORYSIZE:
                return new ChangeStorySizeCommand(tmsRepository);
            case CHANGEFEEDBACKRATING:
                return new ChangeFeedbackRatingCommand(tmsRepository);
            case ASSIGNBUG:
                return new AssignBugCommand(tmsRepository);
            case ASSIGNSTORY:
                return new AssignStoryCommand(tmsRepository);
            case FILTER:
                return new FilterCommand(tmsRepository);
            case SORT:
                return new SortCommand(tmsRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
