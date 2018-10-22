package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 *  Answers the current trivia question
 */
public class TriviaAnsCommand extends Command {
    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_SUCCESS = "Correct!\n";

    public static final String MESSAGE_WRONG = "Wrong!\n";

    public static final String MESSAGE_FAILURE = "Please use the trivia command to get a question.";

    public static final String MESSAGE_NEXT = "Next question:";

    public static final String MESSAGE_END = "Trivia ended! Type trivia to play again!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " your answer";

    private final String answer;

    private boolean correct;

    public TriviaAnsCommand(String answer) {
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();
        ArrayList<Word> triviaList = model.getTriviaList();
        String messageOutput = "";

        if (triviaQ == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        correct = triviaQ.getName().toString().equals(answer);

        model.clearTrivia();
        model.setTriviaQuestion();
        triviaQ = model.getTrivia();

        if (correct) {
            messageOutput = MESSAGE_SUCCESS;
        }

        if (!correct) {
            messageOutput = MESSAGE_WRONG;
        }

        if (triviaList.size() == 1) {
            model.toggleTriviaMode();
            messageOutput += MESSAGE_END;
        }

        else {
            messageOutput += MESSAGE_NEXT + triviaQ.getMeaning().toString();
        }



        return new CommandResult(messageOutput);
    }
}
