package org.example.gui.mapFrme;


import org.example.gui.LocalizedResources;
import org.example.gui.MusicBandWindow;
import org.example.recources.*;
import org.example.recources.Label;
import org.example.system.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.example.gui.mapFrme.MapFrame.frame;


public class MapFrame extends JFrame {
    public static JFrame frame;
    private HashMap<Integer, MusicBand> persons = new HashMap<>();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem homeMenuItem;
    private JMenuItem mapMenuItem;
    private JMenuItem teamsMenuItem;
    private boolean menuCollapsed = false;
    private Canvas canvas;

    public MapFrame() {
        // resourceBundle = ResourceBundle.getBundle("LocalizedStrings", UiManager.getLocale());
        // Настройка окна
        frame = new JFrame("map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 500);
        frame.setLocationRelativeTo(null);

        // Создание панелей
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Создание кнопок
        JButton mainButton = new JButton("mainButton");
        JButton mapButton = new JButton("mapButton");
        JButton commandsButton = new JButton("commandButton");


        mainButton.setFocusable(false);
        mapButton.setFocusable(false);
        commandsButton.setFocusable(false);

        // Панель для кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(mainButton);
        buttonPanel.add(mapButton);
        buttonPanel.add(commandsButton);

        // persons = pl;
        canvas = new Canvas();
        // canvas.setPersons(persons);

        // frame.add(new JScrollPane(canvas));
        // MapView = new MapPanelView(organizations);
        canvas.setBackground(Color.GRAY);
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        centerPanel.add(canvas);

        // Добавление панелей в окно
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Меню (оставьте как есть)
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menu");
        homeMenuItem = new JMenuItem("Main");
        mapMenuItem = new JMenuItem("Map");
        teamsMenuItem = new JMenuItem("Commands");

        // Обработчики меню (оставьте как есть)
        homeMenuItem.addActionListener(e -> {
            frame.setVisible(false);
            new MusicBandWindow();
        });
        mapMenuItem.addActionListener(e -> {

        });
        teamsMenuItem.addActionListener(e -> {
            // Действия для пункта "Команды"
            System.out.println("Команды");
        });

        fileMenu.add(homeMenuItem);
        fileMenu.add(mapMenuItem);
        fileMenu.add(teamsMenuItem);
        menuBar.add(fileMenu);

        // Сворачивание/Развертывание меню (оставьте как есть)
        fileMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    menuCollapsed = !menuCollapsed;
                    if (menuCollapsed) {
                        fileMenu.setText("...");
                        fileMenu.removeAll();
                        fileMenu.add(new JMenuItem("Развернуть"));
                    } else {
                        fileMenu.setText("Меню");
                        fileMenu.removeAll();
                        fileMenu.add(homeMenuItem);
                        fileMenu.add(mapMenuItem);
                        fileMenu.add(teamsMenuItem);
                    }
                    frame.validate();
                }
            }
        });


        frame.setBackground(new Color(49, 70, 142));
        menuBar.setBackground(new Color(49, 70, 142));
        menuBar.setForeground(Color.WHITE);
        fileMenu.setForeground(Color.WHITE);
        canvas.setBackground(Color.WHITE);

        // Добавление элементов в фрейм
        frame.add(menuBar, BorderLayout.NORTH);

        Thread checker = new Thread(this::checkUpdates);
        checker.start();

        // Добавление MouseListener на MapPanelView
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<MusicBand> personsCopy = MusicBandWindow.getMusicBandArrayList();
                for (MusicBand person : personsCopy) {
                    // System.out.println(canvas.getCordX() + " " + canvas.getCordX());
                    if (canvas.getCordX() - person.getCoordinates().getX() <= 10 && -10 <= canvas.getCordX() - person.getCoordinates().getX() &&
                            canvas.getCordY() - person.getCoordinates().getY() <= 10 && -10 <= canvas.getCordY() - person.getCoordinates().getY()) {
                        MusicBandInfoWindow infoWindow = new MusicBandInfoWindow(person);
                        infoWindow.setVisible(true);
                    }
                }
            }
        });


        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                // new MainFrame();
            }
        });

        commandsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                // new CommandFrame();
            }
        });


        frame.setVisible(true); // Установите видимость в конце
    }

    // Метод для добавления содержимого в центральную панель

    private synchronized void checkUpdates() {
        while (true) {
            try {
                // Заполнение таблицы данными (замените этот пример данными из вашей БД)
                Client.sendRequest(new Request("show", null, Client.login, Client.password));
                Response response = Client.getResponse();

                // String owner_id = response.getMessage();
                String data = response.getMessage();
                MusicBandWindow.musicBandArrayList = new ArrayList<>();
                for (String input : data.split("\n")) {
                    // System.out.println(input);
                    // System.out.println(input);
                    Map<String, String> keyValueMap = new HashMap<>();
                    String[] parts = input.split("\" ");
                    for (String part : parts) {
                        String[] keyValue = part.split("=");
                        keyValueMap.put(keyValue[0].trim(), keyValue[1].replace("\"", "").trim());
                    }

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

                    MusicBandWindow.musicBandArrayList.add(musicBand);
                }
                // canvas.setPersons(CollectionManager.getList());
                canvas.repaint();
                Thread.sleep(7000);
                // repaint();
            } catch (Exception e) {

            }

        }
    }
}

class MusicBandInfoWindow extends JFrame {
    public MusicBandInfoWindow(MusicBand musicBand) {
        setTitle("Info");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        StringBuilder sb = new StringBuilder();
        Field[] fields = musicBand.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true); // Для доступа к private полям
                try {
                    if (!field.getName().equals("serialVersionUID")) {
                        sb.append(field.getName()).append(": ").append(field.get(musicBand)).append("\n");
                    }
                } catch (Exception e) {
                    sb.append(field.getName()).append(": ").append(field.get(musicBand)).append("\n");
                }
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append(": доступ запрещёнn");
            }
        }
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        // Создание прокручиваемой панели и добавление в неё текстовой области
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Создание панели и добавление прокручиваемой панели в центр
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);


        if (Client.getUserId() == musicBand.getOwner_id()) {
            JButton updateButton = new JButton("Update");
            updateButton.setBackground(new Color(49, 70, 142));
            updateButton.setForeground(Color.WHITE);
            panel.add(updateButton, BorderLayout.SOUTH);
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    setVisible(false);
                    // new UpdateWindow(person);
                }
            });
        }


        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }
}
