package name.karmanov;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class ConsoleDialog {
    /**
     * process console input
     * @param listClients clients to process
     */
    static void processInput(ListClients listClients) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String sInput;
            //-- print hello
            Util.out("Органайзер загружен, найдено " + listClients.clients.size() + " клиентов");
            //-- process input
            while (true) {
                //-- read input
                Util.out(">> Введите команду и нажмите Enter, для выхода введите пустую строку:");
                sInput = reader.readLine();
                if (sInput.length() == 0) {
                    Util.out("До свидания!");
                    break;
                }
                //-- process commands
                if (sInput.startsWith("help")) printHelp();
                else if (sInput.startsWith("list")) printList(listClients, sInput, reader);
                else if (sInput.startsWith("delete")) deleteClient(listClients, sInput);
                else if (sInput.startsWith("find")) findClientsByPhone(listClients, sInput);
                else if (sInput.startsWith("insert")) insertClient(listClients, reader);
                else if (sInput.startsWith("update")) updateClient(listClients, sInput, reader);
                else Util.out("Ошибка: Неизвестная команда, введите help для вывода списка команд");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * print help info
     */
    private static void printHelp() {
        Util.out(String.format("Команды:%n" +
                "help - вывод справки по командам органайзера%n"
                + "insert - добавить нового клиента%n"
                + "update [id] - редактировать клиента%n"
                + "delete [id] - удалить клиента с указанным номером%n"
                + "list - вывести список клиентов%n"
                + "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов, "
                + "сортируя по указанным полям, '-' для сортировки в обратном порядке)%n"
                + "find [телефон] - поиск клиентов по номеру телефона или части номера телефона"
        ));
    }

    /**
     * print clients list with possibility to sort by multiple fields
     * @param listClients list of clients
     * @param sInput user input string
     * @param reader console input reader
     */
    private static void printList(ListClients listClients, String sInput, BufferedReader reader) {
        ArrayList<Client> localClients;
        if (sInput.equals("list") || sInput.split(" ").length == 1) {
            //-- print only clients
            localClients = listClients.clients;
        }
        else {
            //--sort clients to print
            localClients = new ArrayList<>(listClients.clients);
            /*
            Collections.sort(localClients, Comparator.comparing(Client::getOrganisation)
                    .thenComparing(Client::getName)
                    .thenComparing(Client::getPosition));
            //*/
            String[] fields = sInput.split(" ")[1].split(";");
            localClients.sort(new Comparator<Client>() {
                @Override
                public int compare(Client o1, Client o2) {
                    int compareResult = 0;
                    for (String field : fields) {
                        boolean isNegativeSort = field.startsWith("-");
                        field = field.trim().replace("-", "");
                        switch (field) {
                            case "фио":
                                compareResult = o1.name.compareTo(o2.name);
                                break;
                            case "должность":
                                compareResult = o1.position.compareTo(o2.position);
                                break;
                            case "организация":
                                compareResult = o1.organisation.compareTo(o2.organisation);
                                break;
                            case "email":
                                compareResult = o1.email.compareTo(o2.email);
                                break;
                            case "телефон":
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
        //-- print clients list
        for (int i = 0; i < localClients.size(); i++) {
            Util.out(localClients.get(i));
            if (i > 0 && (i + 1) % 10 == 0 && i < localClients.size() - 1) {
                Util.out("...Нажмите Enter для продолжения...");
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

    /**
     * delete selected client by id
     * @param listClients list of clients
     * @param sInput user input string
     */
    private static void deleteClient(ListClients listClients, String sInput) {
        //-- parse input string
        String[] args = sInput.split(" ");
        if (args.length < 2) {
            Util.out("Ошибка: Не указан номер клиента для удаления!");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            Util.out("Ошибка: Необходимо указать номер клиента для удаления!");
            return;
        }
        //-- find client by id
        Client client = listClients.findClientById(id);
        if (client == null) {
            Util.out("Ошибка: Клиент номер " + id + " не найден!");
            return;
        }
        //-- delete client
        if (!listClients.clients.remove(client)) {
            Util.out("Ошибка списка при удалении Клиента номер " + id + "!");
            return;
        }
        //-- save changes
        listClients.saveData();
        Util.out(client);
        Util.out("Клиент номер " + id + " успешно удален!");
    }

    /**
     * find clients by phone number
     * @param listClients list of clients
     * @param sInput user input string
     */
    private static void findClientsByPhone(ListClients listClients, String sInput) {
        //-- parse input string
        String[] args = sInput.split(" ");
        if (args.length < 2) {
            Util.out("Ошибка: Не указан номер телефона клиента для поиска!");
            return;
        }
        String phone = args[1];
        //-- find client by phone number
        for (Client client : listClients.clients) {
            if (client.phones == null) continue;
            for (String s : client.phones) {
                if (s.contains(phone)) {
                    Util.out(client);
                    break;
                }
            }
        }
    }

    /**
     * insert new client
     * @param listClients list of clients
     * @param reader console input reader
     */
    private static void insertClient(ListClients listClients, BufferedReader reader) {
        try {
            //-- input client data
            Util.out("Создание нового клиента");
            Client client = new Client();
            Util.out("Введите ФИО:");
            client.name = reader.readLine();
            Util.out("Введите Должность:");
            client.position = reader.readLine();
            Util.out("Введите Название организации:");
            client.organisation = reader.readLine();
            while (true) {
                Util.out("Введите e-mail:");
                client.email = reader.readLine();
                if (client.email.length() > 0 && !client.email.contains("@")) {
                    Util.out("Ошибка: Неверный e-mail! E-mail должен содержать символ @ !");
                }
                else {
                    break;
                }
            }
            Util.out("Введите номер телефона клиента, если номеров несколько, разделяйте их точкой с запятой:");
            String phones = reader.readLine();
            if (phones.length() > 0) {
                client.phones = phones.split(";");
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
            Util.out(client);
            Util.out("Новый клиент успешно добавлен!");
        }
        catch (Exception e) {
            e.printStackTrace();
            Util.out("Ошибка при создании нового клиента!");
        }
    }

    /**
     * update client data
     * @param listClients list of clients
     * @param sInput user input string
     * @param reader console input reader
     */
    private static void updateClient(ListClients listClients, String sInput, BufferedReader reader) {
        try {
            //-- parse input string
            String[] args = sInput.split(" ");
            if (args.length < 2) {
                Util.out("Ошибка: Не указан номер клиента для изменения!");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
            }
            catch (Exception e) {
                Util.out("Ошибка: Необходимо указать номер клиента для изменения!");
                return;
            }
            //-- find client by id
            Client client = listClients.findClientById(id);
            if (client == null) {
                Util.out("Ошибка: Клиент номер " + id + " не найден!");
                return;
            }
            //-- update client data
            String s;
            Util.out("Изменение данных клиента");
            Util.out(client);
            Util.out("Текущее ФИО:" + client.name);
            Util.out("Введите новое ФИО или пустую строку если не хотите изменять информацию:");
            s = reader.readLine();
            if (s.length() > 0) {
                client.name = s;
            }
            Util.out("Текущая Должность:" + client.position);
            Util.out("Введите новую Должность или пустую строку если не хотите изменять информацию:");
            s = reader.readLine();
            if (s.length() > 0) {
                client.position = s;
            }
            Util.out("Текущая Организация:" + client.organisation);
            Util.out("Введите новую Организацию или пустую строку если не хотите изменять информацию:");
            s = reader.readLine();
            if (s.length() > 0) {
                client.organisation = s;
            }
            Util.out("Текущий e-mail:" + client.email);
            while (true) {
                Util.out("Введите новый e-mail или пустую строку если не хотите изменять информацию:");
                s = reader.readLine();
                if (s.length() > 0 && !s.contains("@")) {
                    Util.out("Ошибка: Неверный e-mail! E-mail должен содержать символ @ !");
                }
                else {
                    break;
                }
            }
            if (s.length() > 0) {
                client.email = s;
            }
            if (client.phones == null || client.phones.length < 1) {
                Util.out("Текущий номер телефона отсутствует");
            }
            else if (client.phones.length == 1) {
                Util.out("Текущий номер телефона: " + client.phones[0]);
            }
            else {
                Util.out("Текущие номера телефонов: " + Arrays.toString(client.phones));
            }
            Util.out("Введите номера телефонов через точку с запятой, "
                    + "или пустую строку если не хотите изменять данные:");
            s = reader.readLine();
            if (s.length() > 0) {
                client.phones = s.split(";");
            }
            //-- save data
            listClients.saveData();
            Util.out(client);
            Util.out("Данные клиента успешно изменены!");
        }
        catch (Exception e) {
            e.printStackTrace();
            Util.out("Ошибка при изменении данных клиента!");
        }
    }
}