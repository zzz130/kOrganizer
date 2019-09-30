package name.karmanov;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ListClientsTest {
    @Test
    void testToString() {
        ListClients listClients = new ListClients();
        listClients.clients.add(new Client(1, "Иванов", "Инженер", "ООО \"УУУ\""
                , "ivanov@uuu.ru", new String[] {"12-22", "13-31 спросить Степана"}));
        listClients.clients.add(new Client(3, "Иванов3", "Инженер", "ООО \"УУУ\""
                , "ivanov3@uuu.ru", new String[] {"4611"}));
        listClients.clients.add(new Client(4, "Иванов4", "Инженер", "ООО \"УУУ\""
                , "ivanov4@uuu.ru"));
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]"
                + Util.N
                + "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', "
                + "номер телефона [4611]"
                + Util.N
                + "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', "
                + "номер телефона отсутствует"
                + Util.N;
        assertEquals(expectedString, listClients.toString());
    }

    @Test
    void saveAndLoadData() {
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
        //-- backup data file
        String backupDataFileName = ListClients.CONFIG_DATAFILENAME + "_temp" + (1000 * Math.random()) + ".bak";
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
            return;
        }

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        listClients.saveData();
        ListClients listClients2 = new ListClients();
        listClients2.loadData();
        assertEquals(listClients.toString(), listClients2.toString());

        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(ListClients.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
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
            return;
        }
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
    void findClientById() {
        String expectedString = "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"',"
                + " e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]";
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        Client client = listClients.findClientById(2);
        assertEquals(expectedString, client.toString());
    }
}