package org.example.gui;


import org.example.recources.Request;
import org.example.recources.Response;
import org.example.system.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginRegisterWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Login/Register Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new CardLayout());

        // Создание панелей для входа и регистрации
        JPanel loginPanel = createLoginPanel(frame);
        JPanel registerPanel = createRegisterPanel(frame);

        // Добавление панелей в CardLayout
        frame.add(loginPanel, "login");
        frame.add(registerPanel, "register");

        // Отображение окна
        frame.setVisible(true);
    }

    private JPanel createLoginPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(49, 70, 142));

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(49, 70, 142));
        loginButton.setForeground(Color.BLACK);

        JButton switchToRegisterButton = new JButton("Switch to Register");
        switchToRegisterButton.setBackground(new Color(49, 70, 142));
        switchToRegisterButton.setForeground(Color.BLACK);

        // Устанавливаем одинаковый размер для кнопок
        Dimension buttonSize = new Dimension(150, 30);
        loginButton.setPreferredSize(buttonSize);
        switchToRegisterButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        panel.add(switchToRegisterButton, gbc);

        switchToRegisterButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "register");
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client.sendRequest(new Request("login " + userField.getText() + " " + new String(passField.getPassword()), null, userField.getText(), new String(passField.getPassword())));
                    Response response = Client.getResponse();
                    String[] rs = response.getMessage().split(" ");
                    if (rs[0].equals("welcome!")){
                        Client.setLogin(userField.getText());
                        Client.setPassword(new String(passField.getPassword()));
                        Client.setUserId(Integer.parseInt(rs[1]));
                        frame.setVisible(false);
                        new MusicBandWindow();
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return panel;
    }

    private JPanel createRegisterPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(49, 70, 142));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passField, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(49, 70, 142));
        registerButton.setForeground(Color.BLACK);

        JButton switchToLoginButton = new JButton("Switch to Login");
        switchToLoginButton.setBackground(new Color(49, 70, 142));
        switchToLoginButton.setForeground(Color.BLACK);


        Dimension buttonSize = new Dimension(150, 30);
        registerButton.setPreferredSize(buttonSize);
        switchToLoginButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(registerButton, gbc);

        gbc.gridy = 3;
        panel.add(switchToLoginButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client.sendRequest(new Request("reg " + userField.getText() + " " + new String(passField.getPassword()), null, userField.getText(), new String(passField.getPassword())));
                    Response response = Client.getResponse();
                    String[] rs = response.getMessage().split(" ");
                    if (rs[0].equals("welcome!")){
                        Client.setLogin(userField.getText());
                        Client.setPassword(new String(passField.getPassword()));
                        Client.setUserId(Integer.parseInt(rs[1]));
                        frame.setVisible(false);
                        new MusicBandWindow();
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        switchToLoginButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "login");
        });

        return panel;
    }

    public static void main(String[] arg){
        LoginRegisterWindow loginRegisterWindow = new LoginRegisterWindow();
        loginRegisterWindow.createAndShowGUI();
    }
}
