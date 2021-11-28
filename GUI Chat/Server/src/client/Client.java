package client;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Client {
    //static Send send;


    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9300); // Создаём сокет, для подключения к серверу Socket("45.141.79.138",8188)
            System.out.println(times() + "пользователь успешно подключен");
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream()); //исходящий поток
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); //входящий поток

            Thread thread = new Thread(new Runnable() { // Создаём поток, для приёма сообщений от сервера
                @Override
                public void run() {

                    while (true) {
                        try {String response;
                            response = String.valueOf(ois.readObject());// Принимаем сообщение от сервера
                            System.out.println("сервер прислал: " + response); //Печатаем на консоль принятое сообщение от сервера
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                    }
                }
            });
            thread.start();
            Scanner scanner = new Scanner(System.in);
            String request;
            while (true) {
                request = scanner.nextLine(); // Ждём сообщение от пользователя (из консоли)
                 oos.writeUTF(request); // Отправляем сообщение из консоли на сервер

                if (request.equals("stop")) {
                    System.out.println("stop" + "\n");
                    socket.close();
                    oos.close();
                    oos.flush(); // чистим
                    System.exit(0);
                }
            }
        } catch (IOException e) {
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
