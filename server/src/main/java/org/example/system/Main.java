package org.example.system;

import org.example.managers.DataBaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    /**
     * The main method is the entry point of the application.
     *
     * @param args the command line arguments
     */
    public static final String DataBase_URL = "jdbc:postgresql://localhost:1567/studs";
    public static final String DataBase_Helios_URL = "jdbc:postgresql://pg:5432/studs";

    public static void main(String[] args) {
        try {
            Properties info = new Properties();
            info.load(new FileInputStream(args[2]));
            DataBaseManager.setURL(DataBase_URL);
            DataBaseManager.setUsername(info.getProperty("user"));
            DataBaseManager.setPassword(info.getProperty("password"));
            DataBaseManager.connectToDataBase();
            Server server = new Server(Integer.parseInt(args[0]), args[1]);
            server.start();
        } catch (Exception e){
            LOGGER.debug("Something wrong with server");
        }

    }
}