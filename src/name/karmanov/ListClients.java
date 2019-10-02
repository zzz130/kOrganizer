package name.karmanov;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@XmlRootElement(name = "clients")
public class ListClients {
    public static final String CONFIG_DATAFILENAME = "kOrganiserData.xml";

    private static final String MSG_TOSTRING = "Нет данных о клиентах";
    private static final String MSG_ERR_NODATAFILE = "Ошибка: Файл данных отсутствует";
    private static final String MSG_ERR_NODATAINFILE = "Ошибка: Данные в файле не найдены";
    private static final String MSG_ERR_NOCLIENTRECORDS= "Ошибка: Записи о клиентах отсутствуют";

    @XmlElement(name = "client")
    public ArrayList<Client> clients = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (clients == null || clients.size() < 1) {
            sb.append(MSG_TOSTRING);
        }
        else {
            for (Client client : clients) {
                sb.append(client);
                sb.append(Util.N);
            }
            }
        return sb.toString();
    }

    public void generateTestData() {
        Client client;
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
    }

    public boolean loadData() {
        try {
            if (!Files.exists(Paths.get(CONFIG_DATAFILENAME))) {
                Util.out(MSG_ERR_NODATAFILE);
                return true;
            }
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
                Util.out(MSG_ERR_NODATAINFILE);
                return true;
            }
            if (listClients.clients == null || listClients.clients.size() < 1) {
                Util.out(MSG_ERR_NOCLIENTRECORDS);
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

    public void saveData() {
        try {
            JAXBContext context = JAXBContext.newInstance(ListClients.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try (OutputStream fStream = Files.newOutputStream(Paths.get(CONFIG_DATAFILENAME))) {
                marshaller.marshal(this, fStream);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client findClientById(int id) {
        for (Client client : clients) {
            if (client.id == id) {
                return client;
            }
        }
        return null;
    }
}