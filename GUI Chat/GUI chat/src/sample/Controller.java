package sample;


import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    DataOutputStream dos;
    Socket socket;
    ObjectInputStream ois;

    @FXML
    TextArea textAreaU;
    @FXML
    TextArea textArea;
    @FXML
    Button connectBtn;
    @FXML
    TextArea userListTextArea;

    @FXML
    private void send() {
        try {
            String text = textAreaU.getText();
            textAreaU.clear();
            textAreaU.requestFocus();
            textAreaU.setWrapText(true);
            textArea.setWrapText(true);
            textArea.appendText(times() + text + "\n");
            dos.writeUTF(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void connect() {
        try {
            connectBtn.setText("Отключиться от сервера");
            connectBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    disConnect();
                }
            });
            socket = new Socket("127.0.0.1", 9300); // Создаём сокет, для подключения к серверу
            dos = new DataOutputStream(socket.getOutputStream()); //выходящий поток out
            ois = new ObjectInputStream(socket.getInputStream());// входящий объект in
            Thread thread = new Thread(new Runnable() { // Создаём поток, для приёма сообщений от сервера
                @Override
                public void run() {
                    String response;
                    while (true) {
                        try {
                            response = ois.readObject().toString(); // Принимаем сообщение от сервера
                            System.out.println(response);
                            if (response.startsWith("**userList**")) {
                                String[] usersName = response.split("//"); //**userList**//user1//user2//user3
                                userListTextArea.setText("");
                                for (String name : usersName) {
                                    userListTextArea.appendText(name + "\n");
                                }
                            } else
                                textArea.appendText(response + "\n"); //Печатаем на консоль принятое сообщение от сервера
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } catch (IOException e) {
            // e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Сервер не обнаружен, перезапустите приложение!");
            alert.showAndWait();
            System.exit(0);
        }
    }

        @FXML
        private void disConnect () {
            try {
                socket.close();
                dos.close();
                ois.close();
                dos.flush(); // чистим
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String times () {
            Date date;
            String dtime;
            SimpleDateFormat df;
            date = new Date(); // текущая дата
            df = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
            dtime = df.format(date); // время
            return dtime = "[" + dtime + "] ";
        }
}







