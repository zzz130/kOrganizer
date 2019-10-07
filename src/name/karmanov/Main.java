package name.karmanov;

public class Main {
    public static void main(String[] args) {
        ListClients listClients = new ListClients();
        String errorMessage = "";
        boolean bResult = listClients.loadData(errorMessage);
        if (errorMessage.length() > 0) {
            System.out.println(errorMessage);
        }
        if (!bResult) {
            return;
        }
        ConsoleDialog consoleDialog = new ConsoleDialog(System.in, System.out);
        consoleDialog.processInput(listClients);
    }
}