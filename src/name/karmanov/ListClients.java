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

    public class LoadResult {
        private boolean result;
        private String message;

        public LoadResult(boolean result, String message) {
            this.result = result;
            this.message = message;
        }

        public boolean isResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }
    }

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
                sb.append(System.getProperty("line.separator"));
            }
            }
        return sb.toString();
    }

    public LoadResult loadData() {
        try {
            if (!Files.exists(Paths.get(CONFIG_DATAFILENAME))) {
                return new LoadResult(true, MSG_ERR_NODATAFILE);
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
                return new LoadResult(true, MSG_ERR_NODATAINFILE);
            }
            if (listClients.clients == null || listClients.clients.size() < 1) {
                return new LoadResult(true, MSG_ERR_NOCLIENTRECORDS);
            }
            this.clients = listClients.clients;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new LoadResult(false, "");
        }
        return new LoadResult(true, "");
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