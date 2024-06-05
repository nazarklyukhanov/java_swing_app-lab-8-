package org.example.gui;

import org.example.gui.mapFrme.MapFrame;
import org.example.recources.*;
import org.example.recources.Label;
import org.example.system.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class MusicBandWindow {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem homeMenuItem;
    private JMenuItem mapMenuItem;
    private JMenuItem teamsMenuItem;
    private boolean menuCollapsed = false;
    // Кнопки
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField editField;
    private JTextField deleteField;
    public LinkedHashMap<String, MusicBand> musicBands = new LinkedHashMap<>();
    public static ArrayList<MusicBand> musicBandArrayList = new ArrayList<>();

    public MusicBandWindow() {
        initialize();
    }


    private void initialize() {
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Таблица
        tableModel = new DefaultTableModel(new String[]{
                "ID", "name", "owner_id",
                "cor_x", "cor_y",
                "creation_date", "numberOfParticipants", "albumsCount", "genre", "sales"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Запрещаем редактирование ячеек
            }
        };
        table = new JTable(tableModel);
        table.setRowSorter(new TableRowSorter<>(tableModel));

        try {
            Client.sendRequest(new Request("show", null, Client.getLogin(), Client.getPassword()));
            Response response = Client.getResponse();

            // String owner_id = response.getMessage();
            String data = response.getMessage();
            musicBandArrayList = new ArrayList<>();
            for (String input : data.split("\n")) {
                // System.out.println(input);
                // System.out.println(input);
                Map<String, String> keyValueMap = new HashMap<>();
                String[] parts = input.split("\" ");
                for (String part : parts) {
                    String[] keyValue = part.split("=");
                    keyValueMap.put(keyValue[0].trim(), keyValue[1].replace("\"", "").trim());
                }
                tableModel.addRow(new Object[]{
                        keyValueMap.get("id"),
                        keyValueMap.get("name"),
                        keyValueMap.get("owner_id"),
                        keyValueMap.get("coordinates_x"),
                        keyValueMap.get("coordinates_y"),
                        keyValueMap.get("creationDate"),
                        keyValueMap.get("numberOfParticipants"),
                        keyValueMap.get("albumsCount"),
                        keyValueMap.get("genre"),
                        keyValueMap.get("label_sales")
                });}
        } catch (Exception e){

        }

        // Поле поиска
        searchField = new JTextField();
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText().toLowerCase();
                ((TableRowSorter) table.getRowSorter()).setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
            }

        });


        // Меню (оставьте как есть)
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menu");
        homeMenuItem = new JMenuItem("Main");
        mapMenuItem = new JMenuItem("Map");
        teamsMenuItem = new JMenuItem("Commands");

        // Обработчики меню (оставьте как есть)
        homeMenuItem.addActionListener(e -> {
            // Действия для пункта "Главная"
            //  System.out.println("Главная");
        });
        mapMenuItem.addActionListener(e -> {
            frame.setVisible(false);
            // Действия для пункта "Карта"
            new MapFrame();
        });
        teamsMenuItem.addActionListener(e -> {
            // Действия для пункта "Команды"
            // new CommandWindow();
        });

        fileMenu.add(homeMenuItem);
        fileMenu.add(mapMenuItem);
        fileMenu.add(teamsMenuItem);
        menuBar.add(fileMenu);

        // Кнопки
        createButton = new JButton("Create");
        editButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        editField = new JTextField(10);
        deleteField = new JTextField(10);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(editField);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteField);

        // Панель с таблицей, полем поиска и кнопками
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.add(searchField, BorderLayout.NORTH);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);






        deleteButton.addActionListener(e -> {
            try {
                Client.sendRequest(new Request("remove_by_id " + deleteField.getText(), null, Client.getLogin(), Client.getPassword()));
                Response response = Client.getResponse();
            } catch (Exception exception){

            }

        });


        JComboBox<String> languageBox = new JComboBox<>(new String[]{"Русский", "Română", "Svenska", "Español (Ecuador)"});
        languageBox.setBackground(new Color(49, 70, 142));
        languageBox.setForeground(Color.BLACK);
        languageBox.setSelectedItem(Client.getLanguage());
        switchLanguage(Client.getLanguage());
        menuBar.add(languageBox);
        // Добавление элементов в фрейм
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);

        Thread checker = new Thread(this::checkUpdates);
        checker.start();

        editButton.addActionListener(e -> {
            try {
                ArrayList<MusicBand> musicBandCopy = (ArrayList<MusicBand>) MusicBandWindow.getMusicBandArrayList().clone();
                for (MusicBand musicBand : musicBandCopy) {
                    if (musicBand.getOwner_id() == Client.getUserId() && musicBand.getId() == Integer.parseInt(editField.getText())){
                        checker.stop();
                        new UpdateWindow(musicBand);
                        frame.setVisible(false);
                    }
                }
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            // checker.start();
        });

        // Обработчики кнопок
        createButton.addActionListener(e -> {
            checker.stop();
            frame.setVisible(false);
            new CreateWindow();
        });

        frame.setBackground(new Color(49, 70, 142));
        menuBar.setBackground(new Color(49, 70, 142));
        menuBar.setForeground(Color.BLACK);
        fileMenu.setForeground(Color.BLACK);

        buttonPanel.setBackground(new Color(49, 70, 142));
        tablePanel.setBackground(new Color(49, 70, 142));

        createButton.setBackground(new Color(49, 70, 142));
        createButton.setForeground(Color.BLACK);

        editButton.setBackground(new Color(49, 70, 142));
        editButton.setForeground(Color.BLACK);

        deleteButton.setBackground(new Color(49, 70, 142));
        deleteButton.setForeground(Color.BLACK);

        // Отображение окна
        frame.setVisible(true);

        languageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) languageBox.getSelectedItem();
                Client.setLanguage(selectedItem);
                switchLanguage((String) languageBox.getSelectedItem());
            }
        });
    }

    private void switchLanguage(String language) {
        fileMenu.setText(LocalizedResources.localizedResources.get(language).get("menu"));
        homeMenuItem.setText(LocalizedResources.localizedResources.get(language).get("main"));
        mapMenuItem.setText(LocalizedResources.localizedResources.get(language).get("map"));
        teamsMenuItem.setText(LocalizedResources.localizedResources.get(language).get("commands"));

        createButton.setText(LocalizedResources.localizedResources.get(Client.getLanguage()).get("create"));
        editButton.setText(LocalizedResources.localizedResources.get(Client.getLanguage()).get("update"));
        deleteButton.setText(LocalizedResources.localizedResources.get(Client.getLanguage()).get("delete"));


        table.getColumnModel().getColumn(1).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("name"));
        table.getColumnModel().getColumn(2).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("ownerId"));
        table.getColumnModel().getColumn(5).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("creationDate"));
        table.getColumnModel().getColumn(6).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("numberOfParticipants"));
        table.getColumnModel().getColumn(7).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("albumsCount"));
        table.getColumnModel().getColumn(8).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("genre"));
        table.getColumnModel().getColumn(9).setHeaderValue(LocalizedResources.localizedResources.get(Client.getLanguage()).get("labelSales"));


        // Обновляем таблицу, чтобы изменения вступили в силу
        table.getTableHeader().repaint();
    }

    private synchronized void checkUpdates() {
        while (true) {
            try {
                Thread.sleep(5000);
                // CollectionManager.setList(DataBaseManager.getDataFromDatabase());
                tableModel.setRowCount(0);
                // Заполнение таблицы данными (замените этот пример данными из вашей БД)
                Client.sendRequest(new Request("show", null, Client.getLogin(), Client.getPassword()));
                Response response = Client.getResponse();

                // String owner_id = response.getMessage();
                String data = response.getMessage();

                if (data.contains("Сначала войдите")){
                    frame.setVisible(false);
                    wait();
                    new LoginRegisterWindow();
                }

                musicBandArrayList = new ArrayList<>();
                for (String input : data.split("\n")) {
                    // System.out.println(input);
                    // System.out.println(input);
                    Map<String, String> keyValueMap = new HashMap<>();
                    String[] parts = input.split("\" ");
                    for (String part : parts) {
                        String[] keyValue = part.split("=");
                        keyValueMap.put(keyValue[0].trim(), keyValue[1].replace("\"", "").trim());
                    }
                    tableModel.addRow(new Object[]{
                            keyValueMap.get("id"),
                            keyValueMap.get("name"),
                            keyValueMap.get("owner_id"),
                            keyValueMap.get("coordinates_x"),
                            keyValueMap.get("coordinates_y"),
                            keyValueMap.get("creationDate"),
                            keyValueMap.get("numberOfParticipants"),
                            keyValueMap.get("albumsCount"),
                            keyValueMap.get("genre"),
                            keyValueMap.get("label_sales")
                    });
                    MusicBand musicBand = new MusicBand();
                    musicBand.setId(Integer.valueOf(keyValueMap.get("id")));
                    musicBand.setOwner_id(Integer.valueOf(keyValueMap.get("owner_id")));
                    musicBand.setName(keyValueMap.get("name"));
                    musicBand.setNumberOfParticipants(Integer.valueOf(keyValueMap.get("numberOfParticipants")));
                    musicBand.setCoordinates(new Coordinates(Float.parseFloat(keyValueMap.get("coordinates_x")), Float.parseFloat(keyValueMap.get("coordinates_y"))));
                    musicBand.setCreationDate(LocalDateTime.now());
                    musicBand.setGenre(MusicGenre.valueOf(keyValueMap.get("genre")));
                    musicBand.setLabel(new Label(Integer.parseInt(keyValueMap.get("label_sales"))));
                    musicBand.setAlbumsCount(Integer.valueOf(keyValueMap.get("albumsCount")));
                    musicBandArrayList.add(musicBand);
                    musicBands.put(keyValueMap.get("name"), musicBand);
                }

                table.repaint();
                Thread.sleep(4000);

            } catch (Exception e){

                System.out.println(e.getMessage());
            }
        }
    }

    public static ArrayList<MusicBand> getMusicBandArrayList() {
        return musicBandArrayList;
    }
}