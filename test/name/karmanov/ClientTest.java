package name.karmanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void testToString() {
        Client client = new Client(1, "Иванов", "Инженер", "ООО \"УУУ\""
                , "ivanov@uuu.ru", new String[] {"12-22", "13-31 спросить Степана"});
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]";
        assertEquals(expectedString, client.toString());
    }

    @Test
    void testToString2() {
        Client client = new Client(1, "Иванов", "Инженер", "ООО \"УУУ\""
                , "ivanov@uuu.ru");
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номер телефона отсутствует";
        assertEquals(expectedString, client.toString());
    }

    @Test
    void testToString3() {
        Client client = new Client(1, "Иванов", "Инженер", "ООО \"УУУ\""
                , "ivanov@uuu.ru", new String[] {"12-22"});
        String expectedString = "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', "
                + "e-mail 'ivanov@uuu.ru', номер телефона [12-22]";
        assertEquals(expectedString, client.toString());
    }
}