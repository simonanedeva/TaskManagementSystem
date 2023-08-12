package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackRatingCommand extends BaseCommand {
    public static final String RATING_SET_SUCCESSFULLY = "Rating of feedback %s successfully changed from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ChangeFeedbackRatingCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String feedbackToChange = parameters.get(0);
        // TODO: 12.08.23 this one bellow has to to made to throw in case of inputing s-th different from integer.
        int newRating = ParsingHelpers.tryParseInt(parameters.get(1), "Please provide a valid whole number!");
        return changeRating(feedbackToChange, newRating);
    }

    private String changeRating(String feedbackToChange, int newRating) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardList = memberTeam.getBoards();

        for (Board board : boardList) {
            List<Task> tasks = board.getTasks();

            for (Task task : tasks) {
                if (task.getTitle().equals(feedbackToChange) && task.getType().equals("FeedbackImpl")) {
                    //we need to cast just here; making a validation above to ensure casting success
                    Feedback feedback = (Feedback) task;
                    int oldRating = feedback.getRating();
                    if (newRating == oldRating){
                        throw new IllegalArgumentException(String.format("Rating already set to %s!", oldRating));
                    }
                    feedback.changeRating(newRating);
                    board.logEvent(String.format("%s changed the rating of feedback %s from %s to %s.", member.getUsername(), task.getTitle(), oldRating, newRating));
                    member.logEvent(String.format("%s changed the rating of feedback %s from %s to %s.", member.getUsername(), task.getTitle(), oldRating, newRating));
                    return String.format(RATING_SET_SUCCESSFULLY, feedbackToChange, oldRating, newRating);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no feedback %s in team %s!", feedbackToChange, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
