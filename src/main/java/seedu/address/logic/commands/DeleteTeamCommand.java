package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.TutorialTeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * A class used to handle the deletion of tutorial team.
 */
public class DeleteTeamCommand extends Command {
    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Removed %1$s from %2$s %3$s!";
    public static final String COMMAND_WORD = "/delete_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a team from the tutorial class specified\n"
            + "Parameters:" + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS " + PREFIX_TEAMNAME + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULECODE + "CS2103T "
            + PREFIX_TUTORIALCLASS + "T09 " + PREFIX_TEAMNAME + "Team 1";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;
    private final TutorialTeam team;

    /**
     * Constructs a DeleteTeamCommand to delete the specified {@code TutorialTeam}
     * from the specified {@code ModuleCode} and {@code TutorialClass}.
     * @param module        The module code of the tutorial class to be deleted.
     * @param tutorialClass The tutorial class to be deleted.
     * @param teamName      The name of the team to be deleted.
     */
    public DeleteTeamCommand(ModuleCode module, TutorialClass tutorialClass, String teamName) {
        requireAllNonNull(module, tutorialClass, teamName);
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.team = new TutorialTeam(teamName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                getModule(), getTutorialClass());
        ModuleCode module = moduleAndTutorialClass.getModule();
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        if (tutorialClass.hasTeam(team)) {
            tutorialClass.deleteTeam(team);
        } else {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_TEAM_NOT_FOUND, team,
                    module, tutorialClass));
        }
        model.getAddressBook().setTutorialTeamsInClass(tutorialClass);
        model.getAddressBook().setTutorialClassesInModules(module);
        return new CommandResult(generateSuccessMessage(module, tutorialClass, team));
    }


    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Generates a command execution success message based on whether the tutorial
     * team is successfully deleted.
     * @param module         The module code of the tutorial class.
     * @param tutorialString The tutorial class.
     * @param team           The team to be deleted.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString, TutorialTeam team) {
        return String.format(MESSAGE_DELETE_TEAM_SUCCESS, team, module, tutorialString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTeamCommand)) {
            return false;
        }

        DeleteTeamCommand e = (DeleteTeamCommand) other;
        return module.equals(e.module) && tutorialClass.equals(e.tutorialClass)
            && team.equals(e.team);
    }
}
