package name.karmanov;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Printer {
    /**
     * print your message
     * @param message object
     */
    public static void out(Object message) {
        System.out.println(message);
    }

    /**
     * process console input
     * @param listClients clients to process
     */
    public static void processInput(ListClients listClients) {
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
                else if (sInput.startsWith("list")) printList(listClients);
                else if (sInput.startsWith("delete")) printDelete(listClients, sInput);
                else Util.out("Неизвестная команда, help для вывода списка команд");
            }
        }
        catch (Exception e) {
            Util.out(e.getStackTrace());
        }
    }

    private static void printHelp() {
        Util.out(String.format("Команды:%n" +
                "help - вывод справки по командам органайзера%n"
//                + "insert - добавить нового клиента%n"
//                + "update [id] - редактировать клиента%n"
                + "delete [id] - удалить клиента с указанным номером%n"
                + "list - вывести список клиентов%n"
//                + "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов, сортируя их по указанным полям%n"
//                + "find [телефон] - поиск клиентов по номеру телефона или его части"
        ));

    }

    private static void printList(ListClients listClients) {
        Util.out(listClients);
    }

    private static void printDelete(ListClients listClients, String sInput) {
        //--
    }
}