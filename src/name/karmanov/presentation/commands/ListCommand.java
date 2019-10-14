package name.karmanov.presentation.commands;

import name.karmanov.data.Client;
import name.karmanov.presentation.ConsoleDialog;

import java.util.ArrayList;

public class ListCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "list";
    private static final String COMMAND_DESCRIPTION = " - вывести список клиентов"
            + System.getProperty("line.separator") +
            "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов,"
            + " сортируя по указанным полям, '-' для сортировки в обратном порядке)";
    private ConsoleDialog consoleDialog;

    private static final String MSG_LIST_PAGING = "...Нажмите Enter для продолжения...";

    public ListCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        ArrayList<Client> localClients;
        if (inputString.equals(COMMAND_KEY) || inputString.split(" ").length == 1) {
            //-- print all clients without sorting
            localClients = consoleDialog.getOrganizerData().clients;
        }
        else {
            //--sort clients to print
            localClients = new ArrayList<>(consoleDialog.getOrganizerData().clients);
            String[] customSortFields = inputString.toLowerCase().split(" ")[1].split(";");
            localClients.sort((c1, c2) -> c1.compareTo(c2, customSortFields));
        }
        //-- print clients list with paging
        for (int i = 0; i < localClients.size(); i++) {
            consoleDialog.out(localClients.get(i));
            if (i > 0 && (i + 1) % 10 == 0 && i < localClients.size() - 1) {
                consoleDialog.out(MSG_LIST_PAGING);
                consoleDialog.safeReadLine();
            }
        }
    }
}