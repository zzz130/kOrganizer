package name.karmanov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListClientsTest {
    private static final String RN = System.getProperty("line.separator");
    private String backupDataFileName;

    public static ListClients generateTestData(ListClients listClients) {
        Client client;
        ArrayList<Client> clients = listClients.clients;
        client = new Client();
        client.id = 1;
        client.name = "Иванов";
        client.position = "Инженер";
        client.organisation = "ООО \"УУУ\"";
        client.email = "ivanov@uuu.ru";
        clients.add(client);
        for (int i = 2; i <= 20; i++) {
            client = new Client();
            client.id = i;
            client.name = "Иванов" + i;
            client.position = "Инженер";
            client.organisation = "ООО \"УУУ\"";
            client.email = "ivanov" + i + "@uuu.ru";
            clients.add(client);
        }
        clients.get(0).phones = new String[] {"12-22", "13-31 спросить Степана"};
        clients.get(1).phones = new String[] {"12-22", "13-33"};
        clients.get(2).phones = new String[] {"4611"};
        return listClients;
    }

    @BeforeEach
    void BeforeEach() {
        //-- backup data file
        backupDataFileName = ListClients.CONFIG_DATAFILENAME + "_temp" + (1000 * Math.random()) + ".bak";
        try {
            if (Files.exists(Paths.get(ListClients.CONFIG_DATAFILENAME))){
                Files.move(Paths.get(ListClients.CONFIG_DATAFILENAME), Paths.get(backupDataFileName));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
            try {
                //-- restore data file
                if (Files.exists(Paths.get(ListClients.CONFIG_DATAFILENAME))){
                    Files.delete(Paths.get(ListClients.CONFIG_DATAFILENAME));
                }
                if (Files.exists(Paths.get(backupDataFileName))){
                    Files.move(Paths.get(backupDataFileName), Paths.get(ListClients.CONFIG_DATAFILENAME));
                }
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    @AfterEach
    void AfterEach() {
        //-- restore data file
        try {
            if (Files.exists(Paths.get(ListClients.CONFIG_DATAFILENAME))){
                Files.delete(Paths.get(ListClients.CONFIG_DATAFILENAME));
            }
            if (Files.exists(Paths.get(backupDataFileName))){
                Files.move(Paths.get(backupDataFileName), Paths.get(ListClients.CONFIG_DATAFILENAME));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    void testToString() {
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + RN;
        ListClients listClients = new ListClients();
        ListClientsTest.generateTestData(listClients);
        assertEquals(expectedString, listClients.toString());
    }

    @Test
    void saveAndLoadData() {
        //-- prepare testing
        String expectedLoadMessage = "";
        String expectedFileContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<clients>\n" +
                "    <client>\n" +
                "        <id>1</id>\n" +
                "        <name>Иванов</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov@uuu.ru</email>\n" +
                "        <phone>12-22</phone>\n" +
                "        <phone>13-31 спросить Степана</phone>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>2</id>\n" +
                "        <name>Иванов2</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov2@uuu.ru</email>\n" +
                "        <phone>12-22</phone>\n" +
                "        <phone>13-33</phone>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>3</id>\n" +
                "        <name>Иванов3</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov3@uuu.ru</email>\n" +
                "        <phone>4611</phone>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>4</id>\n" +
                "        <name>Иванов4</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov4@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>5</id>\n" +
                "        <name>Иванов5</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov5@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>6</id>\n" +
                "        <name>Иванов6</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov6@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>7</id>\n" +
                "        <name>Иванов7</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov7@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>8</id>\n" +
                "        <name>Иванов8</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov8@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>9</id>\n" +
                "        <name>Иванов9</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov9@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>10</id>\n" +
                "        <name>Иванов10</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov10@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>11</id>\n" +
                "        <name>Иванов11</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov11@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>12</id>\n" +
                "        <name>Иванов12</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov12@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>13</id>\n" +
                "        <name>Иванов13</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov13@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>14</id>\n" +
                "        <name>Иванов14</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov14@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>15</id>\n" +
                "        <name>Иванов15</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov15@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>16</id>\n" +
                "        <name>Иванов16</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov16@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>17</id>\n" +
                "        <name>Иванов17</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov17@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>18</id>\n" +
                "        <name>Иванов18</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov18@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>19</id>\n" +
                "        <name>Иванов19</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov19@uuu.ru</email>\n" +
                "    </client>\n" +
                "    <client>\n" +
                "        <id>20</id>\n" +
                "        <name>Иванов20</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"УУУ\"</organisation>\n" +
                "        <email>ivanov20@uuu.ru</email>\n" +
                "    </client>\n" +
                "</clients>\n";
        //-- perform testing
        ListClients listClients = new ListClients();
        ListClientsTest.generateTestData(listClients);
        listClients.saveData();
        ListClients listClients2 = new ListClients();
        ListClients.LoadResult loadResult = listClients2.loadData();
        assertTrue(loadResult.isResult());
        assertEquals(expectedLoadMessage, loadResult.getMessage());
        if (!loadResult.isResult()) {
            return;
        }
        assertEquals(listClients.toString(), listClients2.toString());
        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(ListClients.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    void LoadDataZeroClients() {
        //-- prepare testing
        String expectedLoadMessage = "Ошибка: Записи о клиентах отсутствуют";
        String expectedFileContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<clients/>\n";
        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.saveData();
        ListClients listClients2 = new ListClients();
        ListClients.LoadResult loadResult = listClients2.loadData();
        assertTrue(loadResult.isResult());
        assertEquals(expectedLoadMessage, loadResult.getMessage());
        if (!loadResult.isResult()) {
            return;
        }
        assertEquals(listClients.toString(), listClients2.toString());
        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(ListClients.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    void LoadDataNoDataFile() {
        //-- prepare testing
        String expectedLoadMessage = "Ошибка: Файл данных отсутствует";
        //-- delete data file
        try {
            if (Files.exists(Paths.get(ListClients.CONFIG_DATAFILENAME))){
                Files.delete(Paths.get(ListClients.CONFIG_DATAFILENAME));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
        //-- perform testing
        ListClients listClients = new ListClients();
        ListClients.LoadResult loadResult = listClients.loadData();
        assertTrue(loadResult.isResult());
        assertEquals(expectedLoadMessage, loadResult.getMessage());
    }

    @Test
    void findClientById() {
        String expectedString = "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"',"
                + " e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]";
        ListClients listClients = new ListClients();
        ListClientsTest.generateTestData(listClients);
        Client client = listClients.findClientById(2);
        assertEquals(expectedString, client.toString());
    }
}