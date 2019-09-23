package name.karmanov;

import javax.xml.bind.annotation.XmlElement;
import java.util.Arrays;

public class Client {
    public int id;
    public String name;
    public String position;
    public String organisation;
    public String email;
    @XmlElement(name = "phone")
    public String[] phones = null;

    public Client(int id, String name, String position, String organisation, String email) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.organisation = organisation;
        this.email = email;
    }

    public Client(int id, String name, String position, String organisation, String email, String[] phones) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.organisation = organisation;
        this.email = email;
        this.phones = phones;
    }

    public Client() {
    }

    @Override
    public String toString() {
        String result = "Клиент" +
                " #" + id +
                ", ФИО '" + name + '\'' +
                ", Должность '" + position + '\'' +
                ", Организация '" + organisation + '\'' +
                ", e-mail '" + email + '\'' +
                ", телефон ";
        if (phones == null || phones.length < 1) {
            result += "отсутствует";
        }
        else {
            result += Arrays.toString(phones);
        }
        return result;
    }
}