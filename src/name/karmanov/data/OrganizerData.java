package name.karmanov.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "clients")
public class OrganizerData {
    private static final String MSG_TOSTRING = "Нет данных о клиентах";

    @XmlElement(name = "client")
    public ArrayList<Client> clients = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (clients == null || clients.size() == 0) {
            sb.append(MSG_TOSTRING);
        }
        else {
            clients.forEach(x -> {
                sb.append(x);
                sb.append(System.getProperty("line.separator"));
            });
        }
        return sb.toString();
    }

    public Client findClientById(int id) {
        return clients.stream().filter(x -> x.id == id).findAny().orElse(null);
    }
}