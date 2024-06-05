package org.example.gui;

import org.example.gui.mapFrme.MapFrame;
import org.example.recources.*;
import org.example.recources.Label;
import org.example.system.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UpdateWindow extends JFrame{
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JTextField numberOfParticipantsField;
    private JTextField albumsCountField;
    private JTextField salesField;
    private JTextField locationXField;
    private JTextField locationYField;
    private JTextField locationNameField;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem homeMenuItem;
    private JMenuItem mapMenuItem;
    private JMenuItem teamsMenuItem;
    private JComboBox<String> generBox;
    private boolean menuCollapsed = false;

    public UpdateWindow(MusicBand musicBand) {
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
            new MapFrame();
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
        nameField.setText(musicBand.getName());
        panel.add(nameField);

        panel.add(new JLabel("X"));
        xField = new JTextField();
        xField.setText(String.valueOf(musicBand.getCoordinates().getX()));
        panel.add(xField);

        panel.add(new JLabel("Y"));
        yField = new JTextField();
        yField.setText(String.valueOf(musicBand.getCoordinates().getY()));
        panel.add(yField);

        panel.add(new JLabel("number of participants"));
        numberOfParticipantsField = new JTextField();
        numberOfParticipantsField.setText(String.valueOf(musicBand.getNumberOfParticipants()));
        panel.add(numberOfParticipantsField);

        panel.add(new JLabel("album's count"));
        albumsCountField = new JTextField();
        albumsCountField.setText(String.valueOf(musicBand.getAlbumsCount()));
        panel.add(albumsCountField);

        panel.add(new JLabel("Genre"));
        generBox = new JComboBox<>(new String[]{"PROGRESSIVE_ROCK", "JAZZ", "BLUES"});
        generBox.setSelectedItem(musicBand.getGenre().toString());
        panel.add(generBox);

        panel.add(new JLabel("label sales"));
        salesField = new JTextField();
        salesField.setText(musicBand.getLabel().getSales().toString());
        panel.add(salesField);

        add(menuBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        Button update = new Button("Update");
        update.addActionListener(e -> {
            try {
                MusicBand musicBand_new = new MusicBand();
                musicBand_new.setId(musicBand.getId());
                musicBand_new.setName(nameField.getText());
                musicBand_new.setNumberOfParticipants(Integer.valueOf(numberOfParticipantsField.getText()));
                musicBand_new.setCoordinates(new Coordinates(Float.parseFloat(xField.getText()), Float.parseFloat(yField.getText())));
                musicBand_new.setCreationDate(LocalDateTime.now());
                musicBand_new.setGenre(MusicGenre.valueOf(generBox.getSelectedItem().toString()));
                musicBand_new.setLabel(new Label(Integer.parseInt(salesField.getText())));
                musicBand_new.setAlbumsCount(Integer.valueOf(albumsCountField.getText()));

                Request request = new Request("update_by_id " + musicBand.getId(), musicBand_new, Client.login, Client.password);
                Client.sendRequest(request);
                Response response = Client.getResponse();
                setVisible(false);
                new MusicBandWindow();
            } catch (Exception exception) {
                showErrorDialog("Error: " + exception.getMessage());
            }

        });



        add(update, BorderLayout.SOUTH);

        setBackground(new Color(49, 70, 142));
        menuBar.setBackground(new Color(49, 70, 142));
        menuBar.setForeground(Color.BLACK);
        fileMenu.setForeground(Color.BLACK);
        update.setBackground(new Color(49, 70, 142));
        update.setForeground(Color.BLACK);

        setVisible(true);
    }

    public void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
