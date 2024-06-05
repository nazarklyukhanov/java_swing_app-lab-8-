package org.example.filelogic;

import org.example.exceptions.RootException;
import org.example.managers.CollectionManager;
import org.example.recources.MusicBand;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Данный класс выполняет запись данных в формате XML
 *
 */
public class WriterXML {
    /**
     * Записывает данные коллекции в файл
     *
     */
    public static void write(String path) throws IOException, RootException {
        File file = new File(path);

        if (!file.canWrite()){
            throw new RootException("You do not have enough rights to write to the file");
        }
        StringBuilder xml = new StringBuilder("""
                <?xml version="1.0" encoding="UTF-8" ?>

                <collection>
                \t<musicbands>
                """);

        LinkedHashMap<String, MusicBand> table = CollectionManager.getLinkedHashMap();
        for (String key : table.keySet()) {
            xml.append("\t\t<musicband ");
            MusicBand musicBand = table.get(key);
            xml.append(musicBand.toString()).append("/>\n");
        }

        xml.append("\t</musicbands>\n" + "</collection>");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        bufferedOutputStream.write(xml.toString().getBytes());
        bufferedOutputStream.close();
    }
}
