package name.karmanov.presentation.commands;

import name.karmanov.data.Client;
import name.karmanov.presentation.ConsoleDialog;

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
        for (Client client : consoleDialog.getOrganizerData().clients) {
            //-- check if client has a phone
            if (client.phones == null) {
                continue;
            }
            //-- check all phone number to see if it matches the search string
            for (String s : client.phones) {
                if (s.contains(phone)) {
                    //if phone found then print client and go to next client
                    consoleDialog.out(client);
                    break;
                }
            }
        }
    }
}