package name.karmanov.presentation.commands;

import name.karmanov.data.Client;
import name.karmanov.presentation.ConsoleDialog;
import name.karmanov.storage.FileStorage;

public class InsertCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "insert";
    private static final String COMMAND_DESCRIPTION = " - добавить нового клиента";
    private ConsoleDialog consoleDialog;

    private static final String MSG_ERR__INVALIDEMAIL = "Ошибка: Неверный e-mail! E-mail должен содержать символ @ !";
    private static final String MSG_ERR_INSERT_CANNOTCREATECLIENT = "Ошибка при создании нового клиента!";
    private static final String MSG_INSERT_CREATENEWCLIENT = "Создание нового клиента";
    private static final String MSG_INSERT_ENTER_NAME = "Введите ФИО:";
    private static final String MSG_INSERT_ENTER_POSITION = "Введите Должность:";
    private static final String MSG_INSERT_ENTER_ORGANISATION = "Введите Название организации:";
    private static final String MSG_INSERT_ENTER_EMAIL = "Введите e-mail:";
    private static final String MSG__EMAILKEY = "@";
    private static final String MSG_INSERT_ENTER_PHONES = "Введите номер телефона клиента, если номеров несколько, разделяйте их точкой с запятой:";
    private static final String MSG__PHONESKEY = ";";
    private static final String MSG_INSERT_CLIENTCREATEDOK = "Новый клиент успешно добавлен!";

    public InsertCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        try {
            //-- input client data
            consoleDialog.out(MSG_INSERT_CREATENEWCLIENT);
            Client client = new Client();
            consoleDialog.out(MSG_INSERT_ENTER_NAME);
            client.name = consoleDialog.safeReadLine();
            consoleDialog.out(MSG_INSERT_ENTER_POSITION);
            client.position = consoleDialog.safeReadLine();
            consoleDialog.out(MSG_INSERT_ENTER_ORGANISATION);
            client.organisation = consoleDialog.safeReadLine();
            while (true) {
                consoleDialog.out(MSG_INSERT_ENTER_EMAIL);
                client.email = consoleDialog.safeReadLine();
                if (client.email.length() > 0 && !client.email.contains(MSG__EMAILKEY)) {
                    consoleDialog.out(MSG_ERR__INVALIDEMAIL);
                }
                else {
                    break;
                }
            }
            consoleDialog.out(MSG_INSERT_ENTER_PHONES);
            String phones = consoleDialog.safeReadLine();
            if (phones.length() > 0) {
                client.phones = phones.split(MSG__PHONESKEY);
            }
            //-- get new id
            int id = 0;
            if (consoleDialog.getOrganizerData().clients.size() > 0) {
                id = consoleDialog.getOrganizerData().clients.get(0).id;
            }
            for (Client c : consoleDialog.getOrganizerData().clients) {
                if (c.id > id) id = c.id;
            }
            if (id < Integer.MAX_VALUE) {
                id++;
            }
            client.id = id;
            //-- save data
            consoleDialog.getOrganizerData().clients.add(client);
            FileStorage.saveData(consoleDialog.getOrganizerData());
            consoleDialog.out(client);
            consoleDialog.out(MSG_INSERT_CLIENTCREATEDOK);
        }
        catch (Exception e) {
            e.printStackTrace();
            consoleDialog.out(MSG_ERR_INSERT_CANNOTCREATECLIENT);
        }
    }
}