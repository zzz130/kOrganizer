package name.karmanov;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@XmlRootElement(name = "clients")
public class ListClients {
    static final String CONFIG_DATAFILENAME = "kOrganiserData.xml";
    @XmlElement(name = "client")
    ArrayList<Client> clients = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (clients == null || clients.size() < 1) {
            sb.append("Нет данных о клиентах");
        }
        else {
            for (Client client : clients) {
                sb.append(client);
                sb.append(Util.N);
            }
            }
        return sb.toString();
    }

    /**
     * generate test data set
     */
    void generateTestData() {
        //-- create test clients
        clients.add(new Client(1, "Иванов", "Инженер", "ООО \"УУУ\""
                , "ivanov@uuu.ru", new String[] {"12-22", "13-31 спросить Степана"}));
        clients.add(new Client(2, "Иванов2", "Инженер", "ООО \"УУУ\""
                , "ivanov2@uuu.ru", new String[] {"12-22", "13-33"}));
        clients.add(new Client(3, "Иванов3", "Инженер", "ООО \"УУУ\""
                , "ivanov3@uuu.ru", new String[] {"4611"}));
        clients.add(new Client(4, "Иванов4", "Инженер", "ООО \"УУУ\""
                , "ivanov4@uuu.ru"));
        clients.add(new Client(5, "Иванов5", "Инженер", "ООО \"УУУ\""
                , "ivanov5@uuu.ru"));
        clients.add(new Client(6, "Иванов6", "Инженер", "ООО \"УУУ\""
                , "ivanov6@uuu.ru"));
        clients.add(new Client(7, "Иванов7", "Инженер", "ООО \"УУУ\""
                , "ivanov7@uuu.ru"));
        clients.add(new Client(8, "Иванов8", "Инженер", "ООО \"УУУ\""
                , "ivanov8@uuu.ru"));
        clients.add(new Client(9, "Иванов9", "Инженер", "ООО \"УУУ\""
                , "ivanov9@uuu.ru"));
        clients.add(new Client(10, "Иванов10", "Инженер", "ООО \"УУУ\""
                , "ivanov10@uuu.ru"));
        clients.add(new Client(11, "Иванов11", "Инженер", "ООО \"УУУ\""
                , "ivanov11@uuu.ru"));
        clients.add(new Client(12, "Иванов12", "Инженер", "ООО \"УУУ\""
                , "ivanov12@uuu.ru"));
        clients.add(new Client(13, "Иванов13", "Инженер", "ООО \"УУУ\""
                , "ivanov13@uuu.ru"));
        clients.add(new Client(14, "Иванов14", "Инженер", "ООО \"УУУ\""
                , "ivanov14@uuu.ru"));
        clients.add(new Client(15, "Иванов15", "Инженер", "ООО \"УУУ\""
                , "ivanov15@uuu.ru"));
        clients.add(new Client(16, "Иванов16", "Инженер", "ООО \"УУУ\""
                , "ivanov16@uuu.ru"));
        clients.add(new Client(17, "Иванов17", "Инженер", "ООО \"УУУ\""
                , "ivanov17@uuu.ru"));
        clients.add(new Client(18, "Иванов18", "Инженер", "ООО \"УУУ\""
                , "ivanov18@uuu.ru"));
        clients.add(new Client(19, "Иванов19", "Инженер", "ООО \"УУУ\""
                , "ivanov19@uuu.ru"));
        clients.add(new Client(20, "Иванов20", "Инженер", "ООО \"УУУ\""
                , "ivanov20@uuu.ru"));
    }

    /**
     * load data from xml file
     * @return bResult
     */
    boolean loadData() {
        try {
            //-- check if data file exists
            if (!Files.exists(Paths.get(CONFIG_DATAFILENAME))) {
                Util.out("Ошибка: Файл данных отсутствует");
                return true;
            }
            //--load data
            JAXBContext context = JAXBContext.newInstance(ListClients.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            ListClients listClients = null;
            try (InputStream fStream = Files.newInputStream(Paths.get(CONFIG_DATAFILENAME))) {
                listClients = (ListClients) unMarshaller.unmarshal(fStream);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (listClients == null) {
                Util.out("Ошибка: Данные в файле не найдены");
                return true;
            }
            if (listClients.clients == null || listClients.clients.size() < 1) {
                Util.out("Ошибка: Записи о клиентах отсутствуют");
                return true;
            }
            this.clients = listClients.clients;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * save data into xml file
     * @return bResult
     */
    boolean saveData() {
        try {
            //if (Files.exists(Paths.get(CONFIG_DATAFILENAME))) Files.delete(Paths.get(CONFIG_DATAFILENAME));
            //Files.createFile(Paths.get(CONFIG_DATAFILENAME));
            JAXBContext context = JAXBContext.newInstance(ListClients.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try (OutputStream fStream = Files.newOutputStream(Paths.get(CONFIG_DATAFILENAME))) {
                marshaller.marshal(this, fStream);
            }
            //-- debug display serialized data
            /*
            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);
            Util.out(writer);
            writer.close();
            //*/
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    Client findClientById(int id) {
        for (Client client : clients) {
            if (client.id == id) {
                return client;
            }
        }
        return null;
    }
}