
package org.example.system;
import java.net.*;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.ReadFileException;
import org.example.exceptions.RootException;
import org.example.filelogic.ReaderXML;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.DataBaseManager;
import org.example.recources.MusicBand;
import org.example.recources.Request;
import org.example.recources.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static org.example.system.Main.LOGGER;

public class Server {
    private int port;
    private DatagramChannel datagramChannel;
    private static String dataPath;
    private HashSet<String> regestered_users = new HashSet<>();

    // Используем ExecutorService для чтения запросов и обработки
    private ExecutorService readPool = Executors.newFixedThreadPool(10); // Например, 10 потоков для чтения запросов
    private ExecutorService processPool = Executors.newFixedThreadPool(10); // 10 потоков для обработки запросов

    // Используем ForkJoinPool для отправки ответов
    private ForkJoinPool sendPool = new ForkJoinPool();

    public Server(int serverPort, String path) throws IOException, RootException, ReadFileException {
        dataPath = path;
        CollectionManager.setMusicBandLinkedHashMap(DataBaseManager.getDataFromDatabase());

        port = serverPort;
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        new CommandManager();
    }

    public void start() {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(50000);
                InetSocketAddress clientAddress = (InetSocketAddress) datagramChannel.receive(buffer);
                buffer.flip();
                LOGGER.info("Получен новый запрос " + clientAddress);

                // Подготовим задачу для чтения запроса и добавим её в readPool
                readPool.submit(() -> {
                    try {
                        Request request = getRequest(buffer);

                        // Теперь обработаем запрос в другом пуле потоков
                        processPool.submit(() -> {
                            try {
                                Response response;
                                if (regestered_users.contains(request.getLogin()) ||
                                        request.getMessage().contains("login") ||
                                        request.getMessage().contains("reg")) {

                                    response = execute(request);
                                    if (response.getMessage().contains("welcome!")) {
                                        regestered_users.add(request.getLogin());
                                    }
                                } else {
                                    response = new Response("Сначала войдите или зарегистрируйтесь");
                                }
                                // Отправим ответ с использованием ForkJoinPool
                                sendPool.execute(() -> {
                                    try {
                                        sendResponse(response, clientAddress);
                                    } catch (IOException e) {
                                        LOGGER.warn("Ошибка отправки ответа: " + e.getMessage());
                                    }
                                });
                            } catch (Exception e) {
                                LOGGER.warn("Ошибка обработки запроса: " + e.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        LOGGER.warn("Ошибка чтения запроса: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                LOGGER.warn("Ошибка получения запроса: " + e.getMessage());
            }
        }
    }

    public Request getRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Request) oi.readObject();
    }

    public Response execute(Request request) throws NoCommandException {
        return new Response(CommandManager.startExecuting(request));
    }

    public void sendResponse(Response response, InetSocketAddress clientAddress) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        datagramChannel.send(buffer, clientAddress);
    }

    // При завершении работы сервера следует правильно завершить работу всех пулов потоков
    public void stopServer() {
        readPool.shutdown();
        processPool.shutdown();
        sendPool.shutdown();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static void setDataPath(String dataPath) {
        Server.dataPath = dataPath;
    }
}