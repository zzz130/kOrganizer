package name.karmanov.presentation;

import name.karmanov.data.OrganizerData;
import name.karmanov.presentation.commands.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

public class ConsoleDialog {
    private static final String MSG_MENU_HELLO = "Органайзер загружен, найдено %d клиентов";
    private static final String MSG_MENU_INPUT = ">> Введите команду и нажмите Enter, для выхода введите пустую строку:";
    private static final String MSG_MENU_GOODBYE = "До свидания!";
    private static final String MSG_MENU_ERR_UNKNOWNCOMMAND = "Ошибка: Неизвестная команда, введите help для вывода списка команд";
    private static final String MSG_ERR_CONSTRUCTOR_INSTREAMNULL = "Error: inStream is null!";
    private static final String MSG_ERR_CONSTRUCTOR_OUTSTREAMNULL = "Error: outStream is null!";

    private InputStream inStream;
    private PrintStream outStream;
    private OrganizerData organizerData;
    private BufferedReader reader;
    private List<AbstractCommand> commands;

    public ConsoleDialog(InputStream inStream, PrintStream outStream, OrganizerData organizerData) {
        this.inStream = inStream;
        this.outStream = outStream;
        this.organizerData = organizerData;
        if (inStream == null) {
            throw new NullPointerException(MSG_ERR_CONSTRUCTOR_INSTREAMNULL);
        }
        if (outStream == null) {
            throw new NullPointerException(MSG_ERR_CONSTRUCTOR_OUTSTREAMNULL);
        }
        commands = new ArrayList<>();
        commands.add(new HelpCommand(this));
        commands.add(new ListCommand(this));
        commands.add(new FindCommand(this));
        commands.add(new InsertCommand(this));
        commands.add(new UpdateCommand(this));
        commands.add(new DeleteCommand(this));
    }

    public void out(Object message) {
        outStream.println(message);
    }

    public String safeReadLine() {
        try {
            return reader.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public OrganizerData getOrganizerData() {
        return organizerData;
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(inStream));
            out(String.format(MSG_MENU_HELLO, organizerData.clients.size()));
            while (true) {
                boolean isCommandFound = false;
                out(MSG_MENU_INPUT);
                String inputString = safeReadLine().trim().toLowerCase();
                String inputKey = inputString.split(" ")[0];
                if (inputKey.length() == 0) {
                    out(MSG_MENU_GOODBYE);
                    break;
                }
                for (AbstractCommand command : commands) {
                    if (inputKey.equals(command.getCommandKey())) {
                        isCommandFound = true;
                        command.run(inputString);
                        break;
                    }
                }
                if (!isCommandFound) {
                    out(MSG_MENU_ERR_UNKNOWNCOMMAND);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}