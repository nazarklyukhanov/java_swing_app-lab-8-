package org.example.gui;

import org.example.recources.*;
import org.example.recources.Label;
import org.example.system.Client;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CreateWindow extends JFrame {
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JTextField numberOfParticipantsField;
    private JTextField albumsCountField;
    private JTextField salesField;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem homeMenuItem;
    private JMenuItem mapMenuItem;
    private JMenuItem teamsMenuItem;
    private JComboBox<String> generBox;
    private boolean menuCollapsed = false;

    public CreateWindow() {
        setTitle("App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Меню (оставьте как есть)
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menu");
        homeMenuItem = new JMenuItem("Main");
        mapMenuItem = new JMenuItem("Map");
        teamsMenuItem = new JMenuItem("Commands");

        // Обработчики меню (оставьте как есть)
        homeMenuItem.addActionListener(e -> {
            setVisible(false);
            new MusicBandWindow();
        });
        mapMenuItem.addActionListener(e -> {
            setVisible(false);
            // new MapFrame(CollectionManager.getList());
        });
        teamsMenuItem.addActionListener(e -> {
            // Действия для пункта "Команды"
            // System.out.println("Команды");
        });

        fileMenu.add(homeMenuItem);
        fileMenu.add(mapMenuItem);
        fileMenu.add(teamsMenuItem);
        menuBar.add(fileMenu);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        panel.setLayout(new GridLayout(10, 2, 10, 10));
        panel.add(new JLabel("name"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("X"));
        xField = new JTextField();
        panel.add(xField);

        panel.add(new JLabel("Y"));
        yField = new JTextField();
        panel.add(yField);

        panel.add(new JLabel("number of participants"));
        numberOfParticipantsField = new JTextField();
        panel.add(numberOfParticipantsField);

        panel.add(new JLabel("album's count"));
        albumsCountField = new JTextField();
        panel.add(albumsCountField);

        panel.add(new JLabel("Genre"));
        generBox = new JComboBox<>(new String[]{"PROGRESSIVE_ROCK", "JAZZ", "BLUES"});
        // hairColorField = new JTextField();
        panel.add(generBox);

        panel.add(new JLabel("label sales"));
        salesField = new JTextField();
        panel.add(salesField);

        add(menuBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        Button create = new Button("create");
        create.addActionListener(e -> {
            try {
                MusicBand musicBand = new MusicBand();
                musicBand.setName(nameField.getText());
                musicBand.setNumberOfParticipants(Integer.valueOf(numberOfParticipantsField.getText()));
                musicBand.setCoordinates(new Coordinates(Float.parseFloat(xField.getText()), Float.parseFloat(yField.getText())));
                musicBand.setCreationDate(LocalDateTime.now());
                musicBand.setGenre(MusicGenre.valueOf(generBox.getSelectedItem().toString()));
                musicBand.setLabel(new Label(Integer.parseInt(salesField.getText())));
                musicBand.setAlbumsCount(Integer.valueOf(albumsCountField.getText()));
                Request request = new Request("add", musicBand, Client.login, Client.password);
                Client.sendRequest(request);
                Response response = Client.getResponse();
                System.out.println(response.getMessage());
                setVisible(false);
                new MusicBandWindow();
            } catch (Exception exception) {
                showErrorDialog("Error: " + exception.getMessage());
            }

        });


        add(create, BorderLayout.SOUTH);

        setBackground(new java.awt.Color(49, 70, 142));
        menuBar.setBackground(new java.awt.Color(49, 70, 142));
        menuBar.setForeground(java.awt.Color.BLACK);
        fileMenu.setForeground(java.awt.Color.BLACK);
        create.setBackground(new java.awt.Color(49, 70, 142));
        create.setForeground(java.awt.Color.BLACK);

        setVisible(true);
    }

    public void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
