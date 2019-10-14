package name.karmanov.presentation.commands;

import name.karmanov.presentation.ConsoleDialog;

public class HelpCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "help";
    private static final String COMMAND_DESCRIPTION = " - вывод справки по командам органайзера";
    private ConsoleDialog consoleDialog;

    private static final String MSG_HELP = "Команды:";
    private static final String MSG_HELP_COMMANDDESCRIPTION = "%s%s";

    public HelpCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        consoleDialog.out(MSG_HELP);
        for (AbstractCommand command : consoleDialog.getCommands()) {
            consoleDialog.out(String.format(MSG_HELP_COMMANDDESCRIPTION
                    , command.getCommandKey(), command.getCommandDescription()));
        }
    }
}