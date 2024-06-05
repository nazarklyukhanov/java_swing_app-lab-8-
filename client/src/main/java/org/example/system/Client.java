package org.example.system;

import org.example.exceptions.RootException;
import org.example.recources.MusicBand;
import org.example.recources.Request;
import org.example.recources.Response;
import org.example.recources.generators.BandGenerator;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client {
    private static DatagramSocket socket;
    private static InetSocketAddress serverAddress;
    public static String login;
    public static String password;
    public static int userId;
    private static String language = "Русский";

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Client.language = language;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Client.userId = userId;
    }

    public Client(String address, int port) throws IOException {
        socket = new DatagramSocket();
        serverAddress = new InetSocketAddress(address, port);
        socket.connect(serverAddress);
    }

    public void start() {
        System.out.println("Добро пожаловать!");
        Scanner sc = new Scanner(System.in);
        while (true){
            try {
                String input = sc.nextLine();
                MusicBand musicBand = null;
                if (input.contains("login") || input.contains("reg")){
                    login=input.split(" ")[1];
                    password=input.split(" ")[2];
                }
                if (input.equals("add") || input.contains("update_by_id") || input.contains("add_if_max")){
                    musicBand = BandGenerator.generateBand();
                    sendRequest(new Request(input, musicBand, login, password));
                    Thread.sleep(200);
                    Response response = getResponse();
                    System.out.println(response.getMessage());
                } else if (input.contains("execute_script")){
                    ScriptExecutor.execute(input, this);
                } else{
                    sendRequest(new Request(input, musicBand, login, password));
                    Thread.sleep(200);
                    Response response = getResponse();
                    System.out.println(response.getMessage());
                }
            } catch (IOException e){
                System.out.println("Server is not available");
            } catch (ClassNotFoundException e){
                System.out.println("Something wrong with Response");
            } catch (InterruptedException ignored){

            } catch (RootException e) {
                System.out.println("You do not have enough rules");
            }

        }
    }

    public static void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);

        DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), serverAddress);
        socket.send(sendPacket);
    }

    public static Response getResponse() throws ClassNotFoundException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(50000);
        DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.array().length);
        socket.receive(packet);
        buffer.flip();
        ByteArrayInputStream bi = new ByteArrayInputStream(packet.getData());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Response) oi.readObject();
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    public static void setPassword(String password) {
        Client.password = password;
    }
}
