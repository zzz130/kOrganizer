package name.karmanov.data;

import name.karmanov.OrganizerDataTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void testToString() {
        Client client;
        client = new Client();
        client.id = 1;
        client.name = "Иванов";
        client.position = "Инженер";
        client.organisation = "ООО \"УУУ\"";
        client.email = "ivanov@uuu.ru";
        client.phones = new String[] {"12-22", "13-31 спросить Степана"};
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]";
        assertEquals(expectedString, client.toString());
    }

    @Test
    void testToString2() {
        Client client;
        client = new Client();
        client.id = 1;
        client.name = "Иванов";
        client.position = "Инженер";
        client.organisation = "ООО \"УУУ\"";
        client.email = "ivanov@uuu.ru";
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номер телефона отсутствует";
        assertEquals(expectedString, client.toString());
    }

    @Test
    void testToString3() {
        Client client;
        client = new Client();
        client.id = 1;
        client.name = "Иванов";
        client.position = "Инженер";
        client.organisation = "ООО \"УУУ\"";
        client.email = "ivanov@uuu.ru";
        client.phones = new String[] {"12-22"};
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номер телефона [12-22]";
        assertEquals(expectedString, client.toString());
    }

    @Test
    void customCompareTo_Name() {
        String sortString = "Фио";
        String[] customSortFields = sortString.toLowerCase().split(";");
        OrganizerData organizerData = OrganizerDataTest.generateTestData(new OrganizerData());
        assertTrue(organizerData.clients.get(1).customCompareTo(organizerData.clients.get(2), customSortFields) < 0);
    }

    @Test
    void customCompareTo_NameDesc() {
        String sortString = "-Фио";
        String[] customSortFields = sortString.toLowerCase().split(";");
        OrganizerData organizerData = OrganizerDataTest.generateTestData(new OrganizerData());
        assertTrue(organizerData.clients.get(1).customCompareTo(organizerData.clients.get(2), customSortFields) > 0);
    }
}