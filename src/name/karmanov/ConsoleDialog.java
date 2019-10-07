package name.karmanov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

class ConsoleDialog {
    private static final String MSG_HELP = "Команды:" + Util.N
            + "help - вывод справки по командам органайзера" + Util.N
            + "insert - добавить нового клиента" + Util.N
            + "update [id] - редактировать клиента" + Util.N
            + "delete [id] - удалить клиента с указанным номером" + Util.N
            + "list - вывести список клиентов" + Util.N
            + "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов, "
            + "сортируя по указанным полям, '-' для сортировки в обратном порядке)" + Util.N
            + "find [телефон] - поиск клиентов по номеру телефона или части номера телефона";
    private static final String MSG_MENU_HELLO = "Органайзер загружен, найдено %d клиентов";
    private static final String MSG_MENU_INPUT = ">> Введите команду и нажмите Enter, для выхода введите пустую строку:";
    private static final String MSG_MENU_GOODBYE = "До свидания!";
    private static final String MSG_MENU_ERR_UNKNOWNCOMMAND = "Ошибка: Неизвестная команда, введите help для вывода списка команд";
    private static final String CMD_KEY_HELP = "help";
    private static final String CMD_KEY_LIST = "list";
    private static final String CMD_KEY_DELETE = "delete";
    private static final String CMD_KEY_FIND = "find";
    private static final String CMD_KEY_INSERT = "insert";
    private static final String CMD_KEY_UPDATE = "update";
    private static final String CMD_KEY_SORT_NAME = "фио";
    private static final String CMD_KEY_SORT_POSITION = "должность";
    private static final String CMD_KEY_SORT_ORGANISATION = "организация";
    private static final String CMD_KEY_SORT_EMAIL = "email";
    private static final String CMD_KEY_SORT_PHONE = "телефон";
    private static final String CMD_KEY_SORT_NEGATIVE_PREFIX = "-";
    private static final String MSG_LIST_PAGING = "...Нажмите Enter для продолжения...";
    private static final String MSG_ERR_DELETE_NOCLIENTNUMBER = "Ошибка: Не указан номер клиента для удаления!";
    private static final String MSG_ERR_DELETE_NOIDNUMBER = "Ошибка: Необходимо указать номер клиента для удаления!";
    private static final String MSG_ERR__CLIENTNOTFOUND = "Ошибка: Клиент номер %d не найден!";
    private static final String MSG_ERR_DELETE_CANNOTDELETEFROMLIST = "Ошибка списка при удалении Клиента номер %d!";
    private static final String MSG_DELETE_CLIENTDELETEDOK = "Клиент номер %d успешно удален!";
    private static final String MSG_ERR_FIND_NOPHONENUMBERSPECIFIED = "Ошибка: Не указан номер телефона клиента для поиска!";
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

    private InputStream inStream;
    private PrintStream outStream;

    public ConsoleDialog(InputStream inStream, PrintStream outStream) {
        this.inStream = inStream;
        this.outStream = outStream;
        if (inStream == null) {
            throw new NullPointerException("Error: inStream is null!");
        }
        if (outStream == null) {
            throw new NullPointerException("Error: outStream is null!");
        }
    }

    private void out(Object message) {
        outStream.println(message);
    }

    public void processInput(ListClients listClients) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream))) {
            String sInput;
            out(String.format(MSG_MENU_HELLO, listClients.clients.size()));
            while (true) {
                out(MSG_MENU_INPUT);
                sInput = reader.readLine().trim().toLowerCase();
                if (sInput.length() == 0) {
                    out(MSG_MENU_GOODBYE);
                    break;
                }
                if (sInput.startsWith(CMD_KEY_HELP)) printHelp();
                else if (sInput.startsWith(CMD_KEY_LIST)) printList(listClients, sInput, reader);
                else if (sInput.startsWith(CMD_KEY_DELETE)) deleteClient(listClients, sInput);
                else if (sInput.startsWith(CMD_KEY_FIND)) findClientsByPhone(listClients, sInput);
                else if (sInput.startsWith(CMD_KEY_INSERT)) insertClient(listClients, reader);
                else if (sInput.startsWith(CMD_KEY_UPDATE)) updateClient(listClients, sInput, reader);
                else out(MSG_MENU_ERR_UNKNOWNCOMMAND);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printHelp() {
        out(MSG_HELP);
    }

    private void printList(ListClients listClients, String sInput, BufferedReader reader) {
        ArrayList<Client> localClients;
        if (sInput.equals(CMD_KEY_LIST) || sInput.split(" ").length == 1) {
            //-- print all clients without sorting
            localClients = listClients.clients;
        }
        else {
            //--sort clients to print
            localClients = new ArrayList<>(listClients.clients);
            String[] fields = sInput.split(" ")[1].split(";");
            localClients.sort(new Comparator<Client>() {
                @Override
                public int compare(Client o1, Client o2) {
                    int compareResult = 0;
                    for (String field : fields) {
                        boolean isNegativeSort = field.startsWith(CMD_KEY_SORT_NEGATIVE_PREFIX);
                        field = field.trim().replace(CMD_KEY_SORT_NEGATIVE_PREFIX, "");
                        switch (field) {
                            case CMD_KEY_SORT_NAME:
                                compareResult = o1.name.compareTo(o2.name);
                                break;
                            case CMD_KEY_SORT_POSITION:
                                compareResult = o1.position.compareTo(o2.position);
                                break;
                            case CMD_KEY_SORT_ORGANISATION:
                                compareResult = o1.organisation.compareTo(o2.organisation);
                                break;
                            case CMD_KEY_SORT_EMAIL:
                                compareResult = o1.email.compareTo(o2.email);
                                break;
                            case CMD_KEY_SORT_PHONE:
                                String s1 = "", s2 = "";
                                if (o1.phones != null) {
                                    s1 = Arrays.toString(o1.phones);
                                }
                                if (o2.phones != null) {
                                    s2 = Arrays.toString(o2.phones);
                                }
                                compareResult = s1.compareTo(s2);
                                break;
                            default:
                                compareResult = Integer.compare(o1.id, o2.id);
                        }
                        if (isNegativeSort) {
                            compareResult *= -1;
                        }
                        if (compareResult != 0) break;
                    }
                    return compareResult;
                }
            });
        }
        //-- print clients list with paging
        for (int i = 0; i < localClients.size(); i++) {
            out(localClients.get(i));
            if (i > 0 && (i + 1) % 10 == 0 && i < localClients.size() - 1) {
                out(MSG_LIST_PAGING);
                try {
                    reader.readLine();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void deleteClient(ListClients listClients, String sInput) {
        //-- parse input string
        String[] args = sInput.split(" ");
        if (args.length < 2) {
            out(MSG_ERR_DELETE_NOCLIENTNUMBER);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            out(MSG_ERR_DELETE_NOIDNUMBER);
            return;
        }
        //-- find client by id
        Client client = listClients.findClientById(id);
        if (client == null) {
            out(String.format(MSG_ERR__CLIENTNOTFOUND, id));
            return;
        }
        //-- delete client
        if (!listClients.clients.remove(client)) {
            out(String.format(MSG_ERR_DELETE_CANNOTDELETEFROMLIST, id));
            return;
        }
        //-- save changes
        listClients.saveData();
        out(client);
        out(String.format(MSG_DELETE_CLIENTDELETEDOK, id));
    }

    private void findClientsByPhone(ListClients listClients, String sInput) {
        //-- parse input string
        String[] args = sInput.split(" ");
        if (args.length < 2) {
            out(MSG_ERR_FIND_NOPHONENUMBERSPECIFIED);
            return;
        }
        String phone = args[1];
        //-- find client by phone number
        for (Client client : listClients.clients) {
            //-- check if client has a phone
            if (client.phones == null) {
                continue;
            }
            //-- check all phone number to see if it matches the search string
            for (String s : client.phones) {
                if (s.contains(phone)) {
                    //if phone found then print client and go to next client
                    out(client);
                    break;
                }
            }
        }
    }

    private void insertClient(ListClients listClients, BufferedReader reader) {
        try {
            //-- input client data
            out(MSG_INSERT_CREATENEWCLIENT);
            Client client = new Client();
            out(MSG_INSERT_ENTER_NAME);
            client.name = reader.readLine();
            out(MSG_INSERT_ENTER_POSITION);
            client.position = reader.readLine();
            out(MSG_INSERT_ENTER_ORGANISATION);
            client.organisation = reader.readLine();
            while (true) {
                out(MSG_INSERT_ENTER_EMAIL);
                client.email = reader.readLine();
                if (client.email.length() > 0 && !client.email.contains(MSG__EMAILKEY)) {
                    out(MSG_ERR__INVALIDEMAIL);
                }
                else {
                    break;
                }
            }
            out(MSG_INSERT_ENTER_PHONES);
            String phones = reader.readLine();
            if (phones.length() > 0) {
                client.phones = phones.split(MSG__PHONESKEY);
            }
            //-- get new id
            int id = 0;
            if (listClients.clients.size() > 0) {
                id = listClients.clients.get(0).id;
            }
            for (Client c : listClients.clients) {
                if (c.id > id) id = c.id;
            }
            if (id < Integer.MAX_VALUE) {
                id++;
            }
            client.id = id;
            //-- save data
            listClients.clients.add(client);
            listClients.saveData();
            out(client);
            out(MSG_INSERT_CLIENTCREATEDOK);
        }
        catch (Exception e) {
            e.printStackTrace();
            out(MSG_ERR_INSERT_CANNOTCREATECLIENT);
        }
    }

    private void updateClient(ListClients listClients, String sInput, BufferedReader reader) {
        try {
            //-- parse input string
            String[] args = sInput.split(" ");
            if (args.length < 2) {
                out(MSG_ERR_UPDATE_NOCLIENTNUMBER);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
            }
            catch (Exception e) {
                out(MSG_ERR_UPDATE_PROVIDECLIENTNUMBER);
                return;
            }
            //-- find client by id
            Client client = listClients.findClientById(id);
            if (client == null) {
                out(String.format(MSG_ERR__CLIENTNOTFOUND, id));
                return;
            }
            //-- update client data
            String s;
            out(MSG_UPDATE_CHANGECLIENTDATA);
            out(client);
            out(String.format(MSG_UPDATE_CURRENTNAME, client.name));
            out(MSG_UPDATE_ENTERNAME);
            s = reader.readLine();
            if (s.length() > 0) {
                client.name = s;
            }
            out(String.format(MSG_UPDATE_CURRENTPOSITION, client.position));
            out(MSG_UPDATE_ENTERPOSITION);
            s = reader.readLine();
            if (s.length() > 0) {
                client.position = s;
            }
            out(String.format(MSG_UPDATE_CURRENTORGANISATION, client.organisation));
            out(MSG_UPDATE_ENTERORGANISATION);
            s = reader.readLine();
            if (s.length() > 0) {
                client.organisation = s;
            }
            out(String.format(MSG_UPDATE_CURRENTEMAIL, client.email));
            while (true) {
                out(MSG_UPDATE_ENTEREMAIL);
                s = reader.readLine();
                if (s.length() > 0 && !s.contains(MSG__EMAILKEY)) {
                    out(MSG_ERR__INVALIDEMAIL);
                }
                else {
                    break;
                }
            }
            if (s.length() > 0) {
                client.email = s;
            }
            if (client.phones == null || client.phones.length < 1) {
                out(MSG_UPDATE_CURRENTNOPHONE);
            }
            else if (client.phones.length == 1) {
                out(String.format(MSG_UPDATE_CURRENTPHONE, client.phones[0]));
            }
            else {
                out(String.format(MSG_UPDATE_CURRENTPHONES, Arrays.toString(client.phones)));
            }
            out(MSG_UPDATE_ENTERPHONES);
            s = reader.readLine();
            if (s.length() > 0) {
                client.phones = s.split(MSG__PHONESKEY);
            }
            //-- save data
            listClients.saveData();
            out(client);
            out(MSG_UPDATE_CHANGEOK);
        }
        catch (Exception e) {
            e.printStackTrace();
            out(MSG_ERR_UPDATE_CANNOTUPDATE);
        }
    }
}