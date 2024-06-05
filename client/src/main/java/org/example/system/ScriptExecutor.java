package org.example.system;

import org.example.exceptions.RootException;
import org.example.exceptions.WrongArgumentException;
import org.example.recources.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

import static org.example.system.Client.login;
import static org.example.system.Client.password;

public class ScriptExecutor {
    private static Stack<File> st = new Stack<>();

    public static void execute(String inputLine, Client client) throws RootException, IOException, ClassNotFoundException, InterruptedException {
        if (inputLine.split(" ").length != 2) {
            return;
        }
        String path = inputLine.split(" ")[1];
        File file = new File(path);
        if (!file.canRead()) {
            throw new RootException("Возникли проблемы с файлом");
        }
        if (st.isEmpty()) {
            st.add(file);
        }
        String line;
        st.add(file);
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String command;
        String[] data = new String[6];
        while ((line = br.readLine()) != null) {
            command = line.split(" ")[0];
            if (command.equals("add") || command.contains("update_by_id")) {
                for (int n = 0; n < 6; n++) {
                    if ((line = br.readLine()) != null) {
                        data[n] = line;
                    }
                }
                try {
                    Validator.isNotNull(data[0]);
                    Validator.XisCorrect(data[1].split(" ")[0]);
                    Validator.YisCorrect(data[1].split(" ")[1]);
                    Validator.isNotNullZero(data[2]);
                    Validator.isNotNullZero(data[3]);
                    Validator.isNotNullZero(data[5]);

                    MusicBand m1 = new MusicBand(data[0], new Coordinates(Float.parseFloat(data[1].split(" ")[0]),
                            Float.parseFloat(data[1].split(" ")[1])), Integer.parseInt(data[2]),
                            Integer.parseInt(data[3]),
                            MusicGenre.valueOf(data[4]), new Label(Integer.parseInt(data[5])));
                    client.sendRequest(new Request(command, m1, login, password));
                    Thread.sleep(200);
                    Response response = client.getResponse();
                    System.out.println(response.getMessage());

                } catch (WrongArgumentException e) {
                    System.out.println("Некорректные данные");
                } catch (InterruptedException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (line.contains("execute_script")) {
                    File file_new = new File(line.split(" ")[1]);
                    if (!file_new.canRead()) {
                        throw new RootException("Возникли проблемы с файлом");
                    }
                    if (st.contains(file_new)) {
                        System.out.println("Рекурсия в файл " + file.getName() + " была пропущена");
                    } else {
                        ScriptExecutor.execute(line, client);
                    }
                } else {
                    client.sendRequest(new Request(command, null, login, password));
                    Thread.sleep(200);
                    Response response = client.getResponse();
                    System.out.println(response.getMessage());
                }
            }
        }
        st.pop();
    }
}
