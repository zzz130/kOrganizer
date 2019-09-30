package name.karmanov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleDialogTest {
    @Test
    void processInput_Empty() {
        String testInput = Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void processInput_Error() {
        String testInput = "sdfdsfds" + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Ошибка: Неизвестная команда, введите help для вывода списка команд" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void processInput_Help() {
        String testInput = "help" + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Команды:" + Util.N +
                "help - вывод справки по командам органайзера" + Util.N +
                "insert - добавить нового клиента" + Util.N +
                "update [id] - редактировать клиента" + Util.N +
                "delete [id] - удалить клиента с указанным номером" + Util.N +
                "list - вывести список клиентов" + Util.N +
                "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов, сортируя по " +
                "указанным полям, '-' для сортировки в обратном порядке)" + Util.N +
                "find [телефон] - поиск клиентов по номеру телефона или части номера телефона" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void processInput_List() {
        String testInput = "list" + Util.N + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов"  + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:"  + Util.N +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]"  + Util.N +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]"  + Util.N +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]"  + Util.N +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует"  + Util.N +
                "...Нажмите Enter для продолжения..."  + Util.N +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует"  + Util.N +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует"  + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:"  + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void processInput_ListFilter() {
        String testInput = "list -Фио" + Util.N + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + Util.N +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + Util.N +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + Util.N +
                "...Нажмите Enter для продолжения..." + Util.N +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void processInput_Delete() {
        String testInput = "delete 13" + Util.N + "list" + Util.N + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент номер 13 успешно удален!" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + Util.N +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + Util.N +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + Util.N +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + Util.N +
                "...Нажмите Enter для продолжения..." + Util.N +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + Util.N +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
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
        //-- change output
        ByteArrayOutputStream baOs = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baOs);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream baIs = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(baIs);
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
            return;
        }

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(ListClients.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
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
            return;
        }
        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Find() {
        String testInput = "find 13" + Util.N + Util.N;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail " +
                "'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + Util.N +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail " +
                "'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + Util.N +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + Util.N +
                "До свидания!" + Util.N;
        //-- change output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(psOut);
        //-- change input
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        InputStream oldIn = System.in;
        System.setIn(bais);

        //-- perform testing
        ListClients listClients = new ListClients();
        listClients.generateTestData();
        ConsoleDialog.processInput(listClients);

        //-- restore input and output
        System.out.flush();
        System.setOut(oldOut);
        System.setIn(oldIn);
        //-- test results
        //Util.out(baos.toString());
        assertEquals(expectedString, baos.toString());
    }

    @Test
    void insertClient() {
        //TODO test
    }

    @Test
    void updateClient() {
        //TODO test
    }
}