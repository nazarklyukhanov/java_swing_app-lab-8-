package org.example.system;


import org.example.gui.LoginRegisterWindow;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            new Thread(() -> client.start()).start();
            LoginRegisterWindow loginRegisterWindow = new LoginRegisterWindow();
            new Thread(() -> loginRegisterWindow.createAndShowGUI()).start();
        } catch (Exception e){
            System.out.println("Something wrong: " + e.getMessage());
        }
    }
}