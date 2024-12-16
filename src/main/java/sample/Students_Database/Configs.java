package sample.Students_Database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

// Получение данных для подключения к базе данных из файла

public class Configs {
    static String dbHost;
    static String dbPort;
    static String dbUser;
    static String dbPass;
    static String dbName;

    // Получение данных о БД из файла

    public static void init() {
        File xmlFile = new File("src/main/resources/DataBaseConfigs.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            System.out.println("Открыт файл");
            Element element = (Element) doc.getElementsByTagName("databaseConnection").item(0);
            doc.getDocumentElement().normalize();
            dbHost = getElementValue(element, "host");
            dbPort = getElementValue(element, "port");
            dbName = getElementValue(element, "databaseName");
            dbUser = getElementValue(element, "username");
            dbPass = getElementValue(element, "password");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Возвращает параметр по его названию

    private static String getElementValue(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
