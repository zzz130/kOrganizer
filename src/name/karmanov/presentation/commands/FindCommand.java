package name.karmanov.presentation.commands;

import name.karmanov.presentation.ConsoleDialog;

import java.util.Arrays;

public class FindCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "find";
    private static final String COMMAND_DESCRIPTION = " [телефон] - поиск клиентов по номеру телефона"
            + " или части номера телефона";
    private ConsoleDialog consoleDialog;

    private static final String MSG_ERR_FIND_NOPHONENUMBERSPECIFIED = "Ошибка: Не указан номер телефона клиента для поиска!";

    public FindCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        //-- parse input string
        String[] args = inputString.split(" ");
        if (args.length < 2) {
            consoleDialog.out(MSG_ERR_FIND_NOPHONENUMBERSPECIFIED);
            return;
        }
        String phone = args[1];
        //-- find client by phone number
        consoleDialog.getOrganizerData().clients.stream()
                .filter(x -> !(x.phones == null || x.phones.length == 0))
                .filter(x -> Arrays.stream(x.phones).anyMatch(y -> y.contains(phone)))
                .forEach(consoleDialog::out);
    }
}