package org.example.managers;

import org.example.handlers.HashHandler;
import org.example.recources.Coordinates;
import org.example.recources.Label;
import org.example.recources.MusicBand;
import org.example.recources.MusicGenre;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import static org.example.system.Main.LOGGER;

public class DataBaseManager {
    private static String URL;
    private static String username;
    private static String password;
    private static Connection connection;
    private static DataBaseManager instance = null;
    private static final String ADD_USER_REQUEST = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String GET_USER_BY_USERNAME = "SELECT id, login, password FROM users WHERE login = ?";
    private static final String INSERT_MUSICBAND= "INSERT INTO musicband (name, owner_id, cor_x, cor_y, creation_date, " +
            "numberofparticipants, albumscount, genre, sales) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_OWNER_BY_KEY = "SELECT owner_id FROM musicband WHERE name = ?";
    private static final String REMOVE_MUSICBAND = "DELETE FROM musicband WHERE name = ?";

    private static final String UPDATE_MUSICBAND_BY_ID = "UPDATE musicband SET " +
            "name = ?, owner_id = ?, cor_x = ?, cor_y = ?, creation_date = ?, numberOfParticipants = ?," +
            " albumsCount = ?, genre = ?, sales = ? WHERE id = ? AND owner_id = ?";
    public DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public static void connectToDataBase() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connection is ready");
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    // корректность имени + пароля
    public static boolean checkUser(String login, String password) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_USER_BY_USERNAME);
            getStatement.setString(1, login);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(HashHandler.encryptString(password));
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // регистрация пользователя
    public static boolean insertUser(String username, String password) {
        try (PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST)) {
            addStatement.setString(1, username);
            addStatement.setString(2, HashHandler.encryptString(password));
            addStatement.executeUpdate();
            addStatement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't register user. Reason: " + e.getMessage());
            return false;
        }
    }
    public static int getUserId(String login) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_USER_BY_USERNAME);
            getStatement.setString(1, login);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean insertMusicBand(MusicBand musicBand, String login) {
        int userId = getUserId(login);
        System.out.println(userId);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_MUSICBAND)) {
            statement.setInt(2, userId);
            insertMusicBandDataIntoStatement(musicBand, statement);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Couldn't add organization. Reason: " + e.getMessage());
            return false;
        }
    }

    private static void insertMusicBandDataIntoStatement(MusicBand musicBand, PreparedStatement statement) {
        try {
            statement.setString(1, musicBand.getName());
            statement.setFloat(3, musicBand.getCoordinates().getX());
            statement.setFloat(4, musicBand.getCoordinates().getY());
            statement.setDate(5, Date.valueOf(LocalDate.now()));
            statement.setInt(6, musicBand.getNumberOfParticipants());
            statement.setInt(7, musicBand.getAlbumsCount());
            statement.setString(8, musicBand.getGenre().toString());
            statement.setInt(9, musicBand.getLabel().getSales());
        } catch (SQLException e) {
            LOGGER.error("Couldn't insert organization data into statement. Reason: " + e.getMessage());
        }
    }

    private static MusicBand extractMusicBandFromEntry(ResultSet rs) throws SQLException {
        MusicBand musicBand = new MusicBand();
        musicBand.setId( rs.getInt("id"));
        musicBand.setOwner_id( rs.getInt("owner_id"));
        musicBand.setName(rs.getString("name"));
        musicBand.setCoordinates(new Coordinates(rs.getFloat("cor_x"), rs.getFloat("cor_y")));
        musicBand.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        musicBand.setNumberOfParticipants(rs.getInt("numberofparticipants"));
        musicBand.setAlbumsCount(rs.getInt("albumsCount"));
        musicBand.setGenre(MusicGenre.valueOf(rs.getString("genre")));
        musicBand.setLabel(new Label((rs.getInt("sales"))));
        return musicBand;
    }

    public static LinkedHashMap<String, MusicBand> getDataFromDatabase() {
        LinkedHashMap<String, MusicBand> musicBands = new LinkedHashMap<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM musicband");
            while (rs.next()) {
                try {
                    MusicBand musicBand = extractMusicBandFromEntry(rs);
                    musicBands.put(rs.getString("name"), musicBand);
                } catch (Exception e) {
                    LOGGER.error("Invalid route entry in DB. Reason: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't load data from DB. Reason: " + e.getMessage());
            System.exit(-1);
        }
        return musicBands;
    }

    public static boolean removeMusicBandByName(String login, String key) {
        int userId = getUserId(login);
        if (userId == getOwnerId(key)) {
            try (PreparedStatement statement = connection.prepareStatement(REMOVE_MUSICBAND)) {
                statement.setString(1, key);
                statement.executeUpdate();
                statement.close();
                return true;
            } catch (SQLException e) {
                LOGGER.error("Couldn't remove music band. Reason: " + e.getMessage());
                return false;
            }
        } else return false;
    }

    public static int getOwnerId(String key) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_OWNER_BY_KEY);
            getStatement.setString(1, key);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("owner_id");
            }
            return -2;
        } catch (Exception e) {
            return -2;
        }
    }
    public static boolean updateMusicBandById(int id, String key, String login, MusicBand musicBand) {
        int userId = getUserId(login);
        if (userId == getOwnerId(key)) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_MUSICBAND_BY_ID)) {
                statement.setInt(2, userId);
                statement.setInt(11, userId);
                statement.setString(1, key);
                insertMusicBandDataIntoStatement(musicBand, statement);
                statement.setInt(10, id);
                statement.executeUpdate();
                statement.close();
                return true;
            } catch (SQLException e) {
                LOGGER.error("Couldn't update music band. Reason: " + e.getMessage());
                return false;
            }
        } else return false;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        DataBaseManager.URL = URL;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataBaseManager.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBaseManager.password = password;
    }
}
