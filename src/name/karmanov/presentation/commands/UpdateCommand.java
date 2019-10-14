package name.karmanov.presentation.commands;

import name.karmanov.data.Client;
import name.karmanov.presentation.ConsoleDialog;
import name.karmanov.storage.FileStorage;

import java.util.Arrays;

public class UpdateCommand extends AbstractCommand {
    private static final String COMMAND_KEY = "update";
    private static final String COMMAND_DESCRIPTION = " [id] - редактировать клиента";
    private ConsoleDialog consoleDialog;

    private static final String MSG_ERR__CLIENTNOTFOUND = "Ошибка: Клиент номер %d не найден!";
    private static final String MSG__EMAILKEY = "@";
    private static final String MSG_ERR__INVALIDEMAIL = "Ошибка: Неверный e-mail! E-mail должен содержать символ @ !";
    private static final String MSG__PHONESKEY = ";";
    private static final String MSG_ERR_UPDATE_NOCLIENTNUMBER = "Ошибка: Не указан номер клиента для изменения!";
    private static final String MSG_ERR_UPDATE_PROVIDECLIENTNUMBER = "Ошибка: Необходимо указать номер клиента для изменения!";
    private static final String MSG_ERR_UPDATE_CANNOTUPDATE = "Ошибка при изменении данных клиента!";
    private static final String MSG_UPDATE_CHANGECLIENTDATA = "Изменение данных клиента";
    private static final String MSG_UPDATE_CURRENTNAME = "Текущее ФИО: %s";
    private static final String MSG_UPDATE_CURRENTPOSITION = "Текущая Должность: %s";
    private static final String MSG_UPDATE_CURRENTORGANISATION = "Текущая Организация: %s";
    private static final String MSG_UPDATE_CURRENTEMAIL = "Текущий e-mail: %s";
    private static final String MSG_UPDATE_CURRENTNOPHONE = "Текущий номер телефона отсутствует";
    private static final String MSG_UPDATE_CURRENTPHONE = "Текущий номер телефона: %s";
    private static final String MSG_UPDATE_CURRENTPHONES = "Текущие номера телефонов: %s";
    private static final String MSG_UPDATE_ENTERNAME = "Введите новое ФИО или пустую строку если не хотите изменять информацию:";
    private static final String MSG_UPDATE_ENTERPOSITION = "Введите новую Должность или пустую строку если не хотите изменять информацию:";
    private static final String MSG_UPDATE_ENTERORGANISATION = "Введите новую Организацию или пустую строку если не хотите изменять информацию:";
    private static final String MSG_UPDATE_ENTEREMAIL = "Введите новый e-mail или пустую строку если не хотите изменять информацию:";
    private static final String MSG_UPDATE_ENTERPHONES = "Введите номера телефонов через точку с запятой, или пустую строку если не хотите изменять данные:";
    private static final String MSG_UPDATE_CHANGEOK = "Данные клиента успешно изменены!";

    public UpdateCommand(ConsoleDialog consoleDialog) {
        super(COMMAND_KEY, COMMAND_DESCRIPTION);
        this.consoleDialog = consoleDialog;
    }

    @Override
    public void run(String inputString) {
        try {
            //-- parse input string
            String[] args = inputString.split(" ");
            if (args.length < 2) {
                consoleDialog.out(MSG_ERR_UPDATE_NOCLIENTNUMBER);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
            }
            catch (Exception e) {
                consoleDialog.out(MSG_ERR_UPDATE_PROVIDECLIENTNUMBER);
                return;
            }
            //-- find client by id
            Client client = consoleDialog.getOrganizerData().findClientById(id);
            if (client == null) {
                consoleDialog.out(String.format(MSG_ERR__CLIENTNOTFOUND, id));
                return;
            }
            //-- update client data
            String s;
            consoleDialog.out(MSG_UPDATE_CHANGECLIENTDATA);
            consoleDialog.out(client);
            consoleDialog.out(String.format(MSG_UPDATE_CURRENTNAME, client.name));
            consoleDialog.out(MSG_UPDATE_ENTERNAME);
            s = consoleDialog.safeReadLine();
            if (s.length() > 0) {
                client.name = s;
            }
            consoleDialog.out(String.format(MSG_UPDATE_CURRENTPOSITION, client.position));
            consoleDialog.out(MSG_UPDATE_ENTERPOSITION);
            s = consoleDialog.safeReadLine();
            if (s.length() > 0) {
                client.position = s;
            }
            consoleDialog.out(String.format(MSG_UPDATE_CURRENTORGANISATION, client.organisation));
            consoleDialog.out(MSG_UPDATE_ENTERORGANISATION);
            s = consoleDialog.safeReadLine();
            if (s.length() > 0) {
                client.organisation = s;
            }
            consoleDialog.out(String.format(MSG_UPDATE_CURRENTEMAIL, client.email));
            while (true) {
                consoleDialog.out(MSG_UPDATE_ENTEREMAIL);
                s = consoleDialog.safeReadLine();
                if (s.length() > 0 && !s.contains(MSG__EMAILKEY)) {
                    consoleDialog.out(MSG_ERR__INVALIDEMAIL);
                }
                else {
                    break;
                }
            }
            if (s.length() > 0) {
                client.email = s;
            }
            if (client.phones == null || client.phones.length < 1) {
                consoleDialog.out(MSG_UPDATE_CURRENTNOPHONE);
            }
            else if (client.phones.length == 1) {
                consoleDialog.out(String.format(MSG_UPDATE_CURRENTPHONE, client.phones[0]));
            }
            else {
                consoleDialog.out(String.format(MSG_UPDATE_CURRENTPHONES, Arrays.toString(client.phones)));
            }
            consoleDialog.out(MSG_UPDATE_ENTERPHONES);
            s = consoleDialog.safeReadLine();
            if (s.length() > 0) {
                client.phones = s.split(MSG__PHONESKEY);
            }
            //-- save data
            FileStorage.saveData(consoleDialog.getOrganizerData());
            consoleDialog.out(client);
            consoleDialog.out(MSG_UPDATE_CHANGEOK);
        }
        catch (Exception e) {
            e.printStackTrace();
            consoleDialog.out(MSG_ERR_UPDATE_CANNOTUPDATE);
        }
    }
}