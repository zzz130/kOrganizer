package name.karmanov;

public class Main {
    public static void main(String[] args) {
        //-- init data
        ListClients listClients = new ListClients();
        ListClients.LoadResult loadResult = listClients.loadData();
        if (loadResult.getMessage().length() > 0) {
            System.out.println(loadResult.getMessage());
        }
        if (!loadResult.isResult()) {
            return;
        }
        //-- run
        ConsoleDialog consoleDialog = new ConsoleDialog(System.in, System.out);
        consoleDialog.processInput(listClients);
    }
}