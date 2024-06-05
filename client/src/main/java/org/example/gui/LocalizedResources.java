package org.example.gui;

import java.util.HashMap;
import java.util.Map;

public class LocalizedResources {
    public static final Map<String, Map<String, String>> localizedResources;
    public static final Map<String, String> MESSAGES_RU; // Русский
    public static final Map<String, String> MESSAGES_RO; // Румынский
    public static final Map<String, String> MESSAGES_SV; // Шведский
    public static final Map<String, String> MESSAGES_ES; // Испанский Эквадор

    static {
        localizedResources = new HashMap<>();

        MESSAGES_RU = new HashMap<>();
        MESSAGES_RU.put("menu", "Меню");
        MESSAGES_RU.put("main", "Главная");
        MESSAGES_RU.put("map", "Карта");
        MESSAGES_RU.put("commands", "Команды");
        MESSAGES_RU.put("login", "Имя");
        MESSAGES_RU.put("password", "Пароль");
        MESSAGES_RU.put("create", "Создать");
        MESSAGES_RU.put("update", "Обновить");
        MESSAGES_RU.put("delete", "Удалить");
        MESSAGES_RU.put("name", "Имя");
        MESSAGES_RU.put("ownerId", "ID владельца");
        MESSAGES_RU.put("creationDate", "Дата создания");
        MESSAGES_RU.put("numberOfParticipants", "Количество участников");
        MESSAGES_RU.put("albumsCount", "Число альбомов");
        MESSAGES_RU.put("genre", "Музыкальный жанр");
        MESSAGES_RU.put("labelSales", "Выручка лейбла");
        localizedResources.put("Русский", MESSAGES_RU);

        // Румынский
        MESSAGES_RO = new HashMap<>();
        MESSAGES_RO.put("menu", "Meniu");
        MESSAGES_RO.put("main", "Principal");
        MESSAGES_RO.put("map", "Hartă");
        MESSAGES_RO.put("commands", "Comenzi");
        MESSAGES_RO.put("login", "Nume");
        MESSAGES_RO.put("password", "Parolă");
        MESSAGES_RO.put("create", "Crea");
        MESSAGES_RO.put("update", "Actualiza");
        MESSAGES_RO.put("delete", "Șterge");
        MESSAGES_RO.put("name", "Nume");
        MESSAGES_RO.put("ownerId", "ID proprietar");
        MESSAGES_RO.put("creationDate", "Data creării");
        MESSAGES_RO.put("numberOfParticipants", "Număr de participanți");
        MESSAGES_RO.put("albumsCount", "Numărul de albume");
        MESSAGES_RO.put("genre", "Gen muzical");
        MESSAGES_RO.put("labelSales", "label");
        localizedResources.put("Română", MESSAGES_RO);

        // Шведский
        MESSAGES_SV = new HashMap<>();
        MESSAGES_SV.put("menu", "Meny");
        MESSAGES_SV.put("main", "Huvudsida");
        MESSAGES_SV.put("map", "Karta");
        MESSAGES_SV.put("commands", "Kommandon");
        MESSAGES_SV.put("login", "Användarnamn");
        MESSAGES_SV.put("password", "Lösenord");
        MESSAGES_SV.put("create", "Skapa");
        MESSAGES_SV.put("update", "Uppdatera");
        MESSAGES_SV.put("delete", "Radera");
        MESSAGES_SV.put("name", "Namn");
        MESSAGES_SV.put("ownerId", "Ägar-ID");
        MESSAGES_SV.put("creationDate", "Skapningsdatum");
        MESSAGES_SV.put("numberOfParticipants", "Antal deltagare");
        MESSAGES_SV.put("albumsCount", "Antal album");
        MESSAGES_SV.put("genre", "Musikgenre");
        MESSAGES_SV.put("labelSales", "label");
        localizedResources.put("Svenska", MESSAGES_SV);

        // Испанский (Эквадор)
        MESSAGES_ES = new HashMap<>();
        MESSAGES_ES.put("menu", "Menú");
        MESSAGES_ES.put("main", "Principal");
        MESSAGES_ES.put("map", "Mapa");
        MESSAGES_ES.put("commands", "Comandos");
        MESSAGES_ES.put("login", "Nombre de usuario");
        MESSAGES_ES.put("password", "Contraseña");
        MESSAGES_ES.put("create", "Crear");
        MESSAGES_ES.put("update", "Actualizar");
        MESSAGES_ES.put("delete", "Eliminar");
        MESSAGES_ES.put("name", "Nombre");
        MESSAGES_ES.put("ownerId", "ID del propietario");
        MESSAGES_ES.put("creationDate", "Fecha de creación");
        MESSAGES_ES.put("numberOfParticipants", "Número de participantes");
        MESSAGES_ES.put("albumsCount", "Número de álbumes");
        MESSAGES_ES.put("genre", "Género musical");
        MESSAGES_ES.put("labelSales", "label");
        localizedResources.put("Español (Ecuador)", MESSAGES_ES);
    }
}