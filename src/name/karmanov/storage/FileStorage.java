package name.karmanov.storage;

import name.karmanov.data.OrganizerData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileStorage {
    public static final String CONFIG_DATAFILENAME = "kOrganizerData.xml";

    private static final String MSG_ERR_NODATAFILE = "Ошибка: Файл данных отсутствует";
    private static final String MSG_ERR_NODATAINFILE = "Ошибка: Данные в файле не найдены";
    private static final String MSG_ERR_NOCLIENTRECORDS= "Ошибка: Записи о клиентах отсутствуют";

    public static Container loadData() {
        OrganizerData organizerData = null;
        try {
            if (!Files.exists(Paths.get(CONFIG_DATAFILENAME))) {
                return new Container(true, MSG_ERR_NODATAFILE, null);
            }
            JAXBContext context = JAXBContext.newInstance(OrganizerData.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            try (InputStream fStream = Files.newInputStream(Paths.get(CONFIG_DATAFILENAME))) {
                organizerData = (OrganizerData) unMarshaller.unmarshal(fStream);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (organizerData == null || organizerData.clients == null) {
                return new Container(true, MSG_ERR_NODATAINFILE, null);
            }
            if (organizerData.clients.size() < 1) {
                return new Container(true, MSG_ERR_NOCLIENTRECORDS, organizerData);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Container(false, e.getMessage(), null);
        }
        return new Container(true, "", organizerData);
    }

    public static void saveData(OrganizerData organizerData) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrganizerData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try (OutputStream fStream = Files.newOutputStream(Paths.get(CONFIG_DATAFILENAME))) {
                marshaller.marshal(organizerData, fStream);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Container {
        private boolean loadResult;
        private String message;
        private OrganizerData organizerData;

        public Container(boolean loadResult, String message, OrganizerData organizerData) {
            this.loadResult = loadResult;
            this.message = message;
            this.organizerData = organizerData;
        }

        public boolean isLoadResult() {
            return loadResult;
        }

        public String getMessage() {
            return message;
        }

        public OrganizerData getOrganizerData() {
            return organizerData;
        }
    }
}