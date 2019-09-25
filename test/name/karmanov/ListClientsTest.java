package name.karmanov;

import org.junit.jupiter.api.Test;

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
                + System.getProperty("line.separator")
                + "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', "
                + "номер телефона [4611]"
                + System.getProperty("line.separator")
                + "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', "
                + "номер телефона отсутствует"
                + System.getProperty("line.separator");
        assertEquals(expectedString, listClients.toString());
    }

    @Test
    void generateTestData() {
    }

    @Test
    void loadData() {
    }

    @Test
    void saveData() {
    }

    @Test
    void findClientById() {
    }
}