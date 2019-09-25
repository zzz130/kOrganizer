package name.karmanov;

public class Main {
    public static void main(String[] args) {
        //-- debug generate and store test data
        /*
        ListClients testClients = new ListClients();
        testClients.generateTestData();
        testClients.saveData();
        Util.out(testClients);
        //*/
        //-- load data
        ListClients listClients = new ListClients();
        listClients.loadData();
        //Util.out(listClients);
        //-- process input
        ConsoleDialog.processInput(listClients);
    }
}