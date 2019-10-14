package name.karmanov.presentation;

import name.karmanov.OrganizerDataTest;
import name.karmanov.data.OrganizerData;
import name.karmanov.presentation.ConsoleDialog;
import name.karmanov.storage.FileStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleDialogTest {
    private static final String RN = System.getProperty("line.separator");
    private ByteArrayOutputStream baOs;
    private PrintStream out;
    private String backupDataFileName;

    @BeforeEach
    void BeforeEach() {
        //-- init output
        baOs = new ByteArrayOutputStream();
        out = new PrintStream(baOs);
        //-- backup data file
        backupDataFileName = FileStorage.CONFIG_DATAFILENAME + "_temp" + (1000 * Math.random()) + ".bak";
        try {
            if (Files.exists(Paths.get(FileStorage.CONFIG_DATAFILENAME))){
                Files.move(Paths.get(FileStorage.CONFIG_DATAFILENAME), Paths.get(backupDataFileName));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
            try {
                //-- restore data file
                if (Files.exists(Paths.get(FileStorage.CONFIG_DATAFILENAME))){
                    Files.delete(Paths.get(FileStorage.CONFIG_DATAFILENAME));
                }
                if (Files.exists(Paths.get(backupDataFileName))){
                    Files.move(Paths.get(backupDataFileName), Paths.get(FileStorage.CONFIG_DATAFILENAME));
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
            if (Files.exists(Paths.get(FileStorage.CONFIG_DATAFILENAME))){
                Files.delete(Paths.get(FileStorage.CONFIG_DATAFILENAME));
            }
            if (Files.exists(Paths.get(backupDataFileName))){
                Files.move(Paths.get(backupDataFileName), Paths.get(FileStorage.CONFIG_DATAFILENAME));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    void processInput_Empty() {
        //-- prepare testing
        String testInput = RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Error() {
        //-- prepare testing
        String testInput = "sdfdsfds" + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Ошибка: Неизвестная команда, введите help для вывода списка команд" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Help() {
        //-- prepare testing
        String testInput = "help" + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Команды:" + RN +
                "help - вывод справки по командам органайзера" + RN +
                "list - вывести список клиентов" + RN +
                "list [номер;фио;должность;организация;email;телефон] - вывести список клиентов, сортируя по " +
                "указанным полям, '-' для сортировки в обратном порядке)" + RN +
                "find [телефон] - поиск клиентов по номеру телефона или части номера телефона" + RN +
                "insert - добавить нового клиента" + RN +
                "update [id] - редактировать клиента" + RN +
                "delete [id] - удалить клиента с указанным номером" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_List() {
        //-- prepare testing
        String testInput = "list" + RN + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов"  + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:"  + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]"  + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]"  + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]"  + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует"  + RN +
                "...Нажмите Enter для продолжения..."  + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует"  + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует"  + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:"  + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_ListSort() {
        //-- prepare testing
        String testInput = "list -Фио" + RN + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + RN +
                "...Нажмите Enter для продолжения..." + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Delete() {
        //-- prepare testing
        String testInput = "delete 13" + RN + "list" + RN + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент номер 13 успешно удален!" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + RN +
                "...Нажмите Enter для продолжения..." + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
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
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(FileStorage.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
            return;
        }
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Find() {
        //-- prepare testing
        String testInput = "find 13" + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail " +
                "'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail " +
                "'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Insert() {
        //-- prepare testing
        String testInput = "insert" + RN + "Петров" + RN + "Менеджер" + RN + "ООО \"ЕЕЕ\"" + RN
                + "petrov@eee.ru" + RN + "122-1335" + RN + "list" + RN + RN + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Создание нового клиента" + RN +
                "Введите ФИО:" + RN +
                "Введите Должность:" + RN +
                "Введите Название организации:" + RN +
                "Введите e-mail:" + RN +
                "Введите номер телефона клиента, если номеров несколько, разделяйте их точкой с запятой:" + RN +
                "Клиент #21, ФИО 'Петров', Должность 'Менеджер', Организация 'ООО \"ЕЕЕ\"', e-mail 'petrov@eee.ru', номер телефона [122-1335]" + RN +
                "Новый клиент успешно добавлен!" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + RN +
                "...Нажмите Enter для продолжения..." + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + RN +
                "...Нажмите Enter для продолжения..." + RN +
                "Клиент #21, ФИО 'Петров', Должность 'Менеджер', Организация 'ООО \"ЕЕЕ\"', e-mail 'petrov@eee.ru', номер телефона [122-1335]" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
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
                "    <client>\n" +
                "        <id>21</id>\n" +
                "        <name>Петров</name>\n" +
                "        <position>Менеджер</position>\n" +
                "        <organisation>ООО \"ЕЕЕ\"</organisation>\n" +
                "        <email>petrov@eee.ru</email>\n" +
                "        <phone>122-1335</phone>\n" +
                "    </client>\n" +
                "</clients>\n";
        //-- perform testing
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(FileStorage.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
            return;
        }
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }

    @Test
    void processInput_Update() {
        //-- prepare testing
        String testInput = "update 2" + RN + "Иванов22" + RN + RN + "ООО \"ААА\"" + RN
                + "ivanov22@aaa.ru" + RN + "13;11" + RN + "list" + RN + RN + RN;
        String expectedString = "Органайзер загружен, найдено 20 клиентов" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Изменение данных клиента" + RN +
                "Клиент #2, ФИО 'Иванов2', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov2@uuu.ru', номера телефонов [12-22, 13-33]" + RN +
                "Текущее ФИО: Иванов2" + RN +
                "Введите новое ФИО или пустую строку если не хотите изменять информацию:" + RN +
                "Текущая Должность: Инженер" + RN +
                "Введите новую Должность или пустую строку если не хотите изменять информацию:" + RN +
                "Текущая Организация: ООО \"УУУ\"" + RN +
                "Введите новую Организацию или пустую строку если не хотите изменять информацию:" + RN +
                "Текущий e-mail: ivanov2@uuu.ru" + RN +
                "Введите новый e-mail или пустую строку если не хотите изменять информацию:" + RN +
                "Текущие номера телефонов: [12-22, 13-33]" + RN +
                "Введите номера телефонов через точку с запятой, или пустую строку если не хотите изменять данные:" + RN +
                "Клиент #2, ФИО 'Иванов22', Должность 'Инженер', Организация 'ООО \"ААА\"', e-mail 'ivanov22@aaa.ru', номера телефонов [13, 11]" + RN +
                "Данные клиента успешно изменены!" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "Клиент #1, ФИО 'Иванов', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov@uuu.ru', номера телефонов [12-22, 13-31 спросить Степана]" + RN +
                "Клиент #2, ФИО 'Иванов22', Должность 'Инженер', Организация 'ООО \"ААА\"', e-mail 'ivanov22@aaa.ru', номера телефонов [13, 11]" + RN +
                "Клиент #3, ФИО 'Иванов3', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov3@uuu.ru', номер телефона [4611]" + RN +
                "Клиент #4, ФИО 'Иванов4', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov4@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #5, ФИО 'Иванов5', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov5@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #6, ФИО 'Иванов6', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov6@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #7, ФИО 'Иванов7', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov7@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #8, ФИО 'Иванов8', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov8@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #9, ФИО 'Иванов9', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov9@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #10, ФИО 'Иванов10', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov10@uuu.ru', номер телефона отсутствует" + RN +
                "...Нажмите Enter для продолжения..." + RN +
                "Клиент #11, ФИО 'Иванов11', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov11@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #12, ФИО 'Иванов12', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov12@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #13, ФИО 'Иванов13', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov13@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #14, ФИО 'Иванов14', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov14@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #15, ФИО 'Иванов15', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov15@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #16, ФИО 'Иванов16', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov16@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #17, ФИО 'Иванов17', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov17@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #18, ФИО 'Иванов18', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov18@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #19, ФИО 'Иванов19', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov19@uuu.ru', номер телефона отсутствует" + RN +
                "Клиент #20, ФИО 'Иванов20', Должность 'Инженер', Организация 'ООО \"УУУ\"', e-mail 'ivanov20@uuu.ru', номер телефона отсутствует" + RN +
                ">> Введите команду и нажмите Enter, для выхода введите пустую строку:" + RN +
                "До свидания!" + RN;
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
                "        <name>Иванов22</name>\n" +
                "        <position>Инженер</position>\n" +
                "        <organisation>ООО \"ААА\"</organisation>\n" +
                "        <email>ivanov22@aaa.ru</email>\n" +
                "        <phone>13</phone>\n" +
                "        <phone>11</phone>\n" +
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
        new ConsoleDialog(new ByteArrayInputStream(testInput.getBytes()), out
                , OrganizerDataTest.generateTestData(new OrganizerData())).run();
        //-- read created data file
        try {
            String sFileData = new String(Files.readAllBytes(Paths.get(FileStorage.CONFIG_DATAFILENAME)));
            assertEquals(expectedFileContents, sFileData);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
            return;
        }
        //-- assert results
        out.flush();
        assertEquals(expectedString, baOs.toString());
    }
}