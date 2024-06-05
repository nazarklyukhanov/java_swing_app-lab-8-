package org.example.filelogic;

import org.example.exceptions.ReadFileException;
import org.example.exceptions.RootException;
import org.example.managers.CollectionManager;
import org.example.recources.Coordinates;
import org.example.recources.Label;
import org.example.recources.MusicBand;
import org.example.recources.MusicGenre;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import static org.example.system.Main.LOGGER;

/**
 * Данный класс выполняет чтение данных, которые хранятся в формате XML
 *
 * @author nazarklyukhanov
 * @see Hashtable
 * @since 1.0
 */
public class ReaderXML {
    /**
     * Читает данные из файла в коллекцию
     *
     * @param path путь до файла
     * @throws Exception если возникла проблема
     * @see CollectionManager
     */
    public static void read(String path) throws RootException, ReadFileException {
        File file = new File(path);
        if (!file.canRead()) {
            throw new RootException("You do not have enough rights to read the file");
        }
        try {
            // Чтение из файла
            var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            if (text.isEmpty()) {
                LOGGER.info("No element to add, your collection is clear");
                return;
            }

            InputSource in = new InputSource(new StringReader(text.toString()));

            // Получение фабрики, чтобы после получить билдер документов.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
            Document document = builder.parse(in);


            NodeList organisationElements = document.getDocumentElement().getElementsByTagName("musicband");

            if (organisationElements.getLength() == 0) {
                LOGGER.info("No element to add, your collection is clear");
                return;
            }

            Set<String> keySet = new HashSet<String>();
            Set<String> idSet = new HashSet<String>();
            Hashtable<String, MusicGenre> table = new Hashtable<>();
            // Перебор всех элементов employee
            for (int i = 0; i < organisationElements.getLength(); i++) {
                Node musicBand = organisationElements.item(i);

                // Получение атрибутов каждого элемента
                NamedNodeMap attributes = musicBand.getAttributes();
                String[] data = new String[]{attributes.getNamedItem("id").getNodeValue(), attributes.getNamedItem("name").getNodeValue(),
                        attributes.getNamedItem("coordinates_x").getNodeValue(), attributes.getNamedItem("coordinates_y").getNodeValue(),
                        attributes.getNamedItem("creationDate").getNodeValue(), attributes.getNamedItem("numberOfParticipants").getNodeValue(),
                        attributes.getNamedItem("albumsCount").getNodeValue(),
                        attributes.getNamedItem("genre").getNodeValue(), attributes.getNamedItem("label_sales").getNodeValue()};
                String key = data[1];


                MusicBand m1 = new MusicBand( Integer.parseInt(data[0]), data[1], new Coordinates(Float.parseFloat(data[2]),
                        Float.parseFloat(data[3])),
                        LocalDateTime.parse(data[4]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6]),
                        MusicGenre.valueOf(data[7]),  new Label(Integer.parseInt(data[8])));
                CollectionManager.add(key, m1);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw new ReadFileException();
        }
    }
}
