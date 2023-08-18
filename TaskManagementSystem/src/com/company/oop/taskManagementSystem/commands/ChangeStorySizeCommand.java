package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStorySizeCommand extends BaseCommand {

    private static final String SIZE_SET_SUCCESSFULLY = "Size of story %s successfully changed from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ChangeStorySizeCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String storyToChange = parameters.get(0);
        StorySize newSizeStatus = ParsingHelpers.tryParseEnum(parameters.get(1), StorySize.class);
        return changeSize(storyToChange, newSizeStatus);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    private String changeSize(String storyToChange, StorySize newSizeStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardList = memberTeam.getBoards();

        for (Board board : boardList) {
            List<Story> stories = board.getStories();

            for (Story story : stories) {
                if (story.getTitle().equals(storyToChange)) {
                    String oldSize = story.getSize().toString();
                    story.changeSize(newSizeStatus);
                    story.logEvent(String.format("%s changed the size of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldSize, newSizeStatus));
                    member.logEvent(String.format("%s changed the size of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldSize, newSizeStatus));
                    return String.format(SIZE_SET_SUCCESSFULLY, storyToChange, oldSize, newSizeStatus);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToChange, memberTeam.getName()));
    }


}
