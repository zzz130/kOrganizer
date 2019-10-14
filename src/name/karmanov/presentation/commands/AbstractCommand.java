package name.karmanov.presentation.commands;

public abstract class AbstractCommand {
    private String COMMAND_KEY;
    private String COMMAND_DESCRIPTION;

    public AbstractCommand(String COMMAND_KEY, String COMMAND_DESCRIPTION) {
        this.COMMAND_KEY = COMMAND_KEY;
        this.COMMAND_DESCRIPTION = COMMAND_DESCRIPTION;
    }

    public String getCommandKey() {
        return COMMAND_KEY;
    }

    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    public abstract void run(String inputString);
}