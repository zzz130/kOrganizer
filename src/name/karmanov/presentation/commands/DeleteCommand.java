package name.karmanov.presentation.commands;

import name.karmanov.data.Client;
import name.karmanov.presentation.ConsoleDialog;
import name.karmanov.storage.FileStorage;

public class DeleteCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "delete";
    private static final String COMMAND_DESCRIPTION = " [id] - удалить клиента с указанным номером";
    private ConsoleDialog consoleDialog;

    private static final String MSG_ERR__CLIENTNOTFOUND = "Ошибка: Клиент номер %d не найден!";
    private static final String MSG_ERR_DELETE_NOCLIENTNUMBER = "Ошибка: Не указан номер клиента для удаления!";
    private static final String MSG_ERR_DELETE_NOIDNUMBER = "Ошибка: Необходимо указать номер клиента для удаления!";
    private static final String MSG_ERR_DELETE_CANNOTDELETEFROMLIST = "Ошибка списка при удалении Клиента номер %d!";
    private static final String MSG_DELETE_CLIENTDELETEDOK = "Клиент номер %d успешно удален!";

    public DeleteCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        //-- parse input string
        String[] args = inputString.split(" ");
        if (args.length < 2) {
            consoleDialog.out(MSG_ERR_DELETE_NOCLIENTNUMBER);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            consoleDialog.out(MSG_ERR_DELETE_NOIDNUMBER);
            return;
        }
        //-- find client by id
        Client client = consoleDialog.getOrganizerData().findClientById(id);
        if (client == null) {
            consoleDialog.out(String.format(MSG_ERR__CLIENTNOTFOUND, id));
            return;
        }
        //-- delete client
        if (!consoleDialog.getOrganizerData().clients.remove(client)) {
            consoleDialog.out(String.format(MSG_ERR_DELETE_CANNOTDELETEFROMLIST, id));
            return;
        }
        //-- save changes
        FileStorage.saveData(consoleDialog.getOrganizerData());
        consoleDialog.out(client);
        consoleDialog.out(String.format(MSG_DELETE_CLIENTDELETEDOK, id));
    }
}