package name.karmanov.data;

import javax.xml.bind.annotation.XmlElement;
import java.util.Arrays;

public class Client {
    private static final String CMD_KEY_SORT_NAME = "фио";
    private static final String CMD_KEY_SORT_POSITION = "должность";
    private static final String CMD_KEY_SORT_ORGANISATION = "организация";
    private static final String CMD_KEY_SORT_EMAIL = "email";
    private static final String CMD_KEY_SORT_PHONE = "телефон";
    private static final String CMD_KEY_SORT_NEGATIVE_PREFIX = "-";

    public int id = 0;
    public String name = "";
    public String position = "";
    public String organisation = "";
    public String email = "";
    @XmlElement(name = "phone")
    public String[] phones = null;

    public int customCompareTo(Client anotherClient, String[] sortFields) {
        int compareResult = 0;
        if (sortFields == null || sortFields.length == 0) {
            sortFields = new String[] {CMD_KEY_SORT_NAME};
        }
        for (String field : sortFields) {
            boolean isNegativeSort = field.startsWith(CMD_KEY_SORT_NEGATIVE_PREFIX);
            field = field.trim().replace(CMD_KEY_SORT_NEGATIVE_PREFIX, "");
            switch (field) {
                case CMD_KEY_SORT_NAME:
                    compareResult = this.name.compareTo(anotherClient.name);
                    break;
                case CMD_KEY_SORT_POSITION:
                    compareResult = this.position.compareTo(anotherClient.position);
                    break;
                case CMD_KEY_SORT_ORGANISATION:
                    compareResult = this.organisation.compareTo(anotherClient.organisation);
                    break;
                case CMD_KEY_SORT_EMAIL:
                    compareResult = this.email.compareTo(anotherClient.email);
                    break;
                case CMD_KEY_SORT_PHONE:
                    String s1 = "", s2 = "";
                    if (this.phones != null) {
                        s1 = Arrays.toString(this.phones);
                    }
                    if (anotherClient.phones != null) {
                        s2 = Arrays.toString(anotherClient.phones);
                    }
                    compareResult = s1.compareTo(s2);
                    break;
                default:
                    compareResult = Integer.compare(this.id, anotherClient.id);
            }
            if (isNegativeSort) {
                compareResult *= -1;
            }
            if (compareResult != 0) {
                break;
            }
        }
        return compareResult;
    }

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