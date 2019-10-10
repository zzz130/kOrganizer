package name.karmanov.data;

import javax.xml.bind.annotation.XmlElement;
import java.util.Arrays;

public class Client {
    public int id = 0;
    public String name = "";
    public String position = "";
    public String organisation = "";
    public String email = "";
    @XmlElement(name = "phone")
    public String[] phones = null;

    @Override
    public String toString() {
        String result = "Клиент" +
                " #" + id +
                ", ФИО '" + name + '\'' +
                ", Должность '" + position + '\'' +
                ", Организация '" + organisation + '\'' +
                ", e-mail '" + email + '\'';
        if (phones == null || phones.length < 1) {
            result += ", номер телефона отсутствует";
        }
        else {
            if (phones.length > 1) {
                result += ", номера телефонов " + Arrays.toString(phones);
            }
            else {
                result += ", номер телефона [" + phones[0] + "]";
            }
        }
        return result;
    }
}