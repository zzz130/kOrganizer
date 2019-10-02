package name.karmanov;

public class Main {
    public static void main(String[] args) {
        ListClients listClients = new ListClients();
        if (!listClients.loadData()) return;
        ConsoleDialog consoleDialog = new ConsoleDialog();
        consoleDialog.processInput(listClients);
    }
}