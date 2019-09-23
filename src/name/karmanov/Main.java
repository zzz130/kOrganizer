package name.karmanov;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
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
            Printer.processInput(listClients);
        }
        catch (Exception e) {
            Util.out(e.getStackTrace());
        }
    }
}