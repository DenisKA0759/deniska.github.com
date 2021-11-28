
package server;



import java.text.SimpleDateFormat;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


public class Server {
    static ArrayList<User> users = new ArrayList<>();
    static String userName = "";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9300); // Создаёи серверный сокет
            System.out.println(times() + "Сервер запущен");
            while (true) { // бесконечный цикл для ожидания подключения клиентов
                System.out.println(times() + "Ожидаю подключения клиентов...");
                Socket socket = serverSocket.accept(); // Ожидаем подключения клиента
                User currentUser = new User(socket);
                users.add(currentUser);
                DataInputStream dis = new DataInputStream(socket.getInputStream()); // Поток ввода
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); // Поток вывода
                currentUser.setOos(oos);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testName(currentUser, dis, users);
                            currentUser.setUserName(userName);
                            currentUser.setUserName2(userName);
                            sendUserList(); //Отправляем обновление списка пользователей
                            System.out.println(times() + currentUser.getUserName() + " добро пожаловать на сервер!");
                            currentUser.getOos().writeObject(times() + currentUser.getUserName() + " добро пожаловать на сервер!");
                            while (true) {
                                String request;
                                request = dis.readUTF(); // Принимает сообщение от клиента
                                if (request.startsWith("@")){
                                    testPrivate(currentUser, dis,request, users,b);
                                    request=this.request2; }
                                System.out.println(times() + currentUser.getUserName() + " прислал: " + request);
                                for (User user : users) { // Перебираем клиентов которые подключенны в настоящий момент
                                    if (currentUser != user) {
                                        user.getOos().writeObject(times() + currentUser.getUserName() + ": " + request); // Рассылает принятое сообщение всем клиентам
                                    }
                                }
                            }
                        } catch (IOException e) {
                            // e.printStackTrace();
                            try {

                                for (User user : users) {  // Перебираем клиентов которые подключенны в настоящий момент
                                    try {
                                        user.getOos().writeObject("Пользователь " + currentUser.getUserName() + " покинул чат"); // Рассылает принятое сообщение всем клиентам
                                        System.out.println(times() + "Пользователь " + currentUser.getUserName() + " покинул чат");
                                        users.remove(currentUser);
                                        socket.close(); // Удаление сокета, когда клиент отключился
                                    } catch (IOException exception) {
                                        //exception.printStackTrace();
                                        System.out.println(times() + "Пользователь " + currentUser.getUserName() + " покинул чат");
                                        users.remove(currentUser);
                                        socket.close(); // Удаление сокета, когда клиент отключился
                                    }
                                }
                            } catch (Exception ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        sendUserList(); //Отправляем обновление списка пользователей
                    }

                    byte b=0; String request2="";
                    // проверка на приватные сообщения
                    public String testPrivate (User currentUser, DataInputStream dis,String request,ArrayList<User> users,byte b) throws IOException {
                        if (b==3) { request = dis.readUTF();}
                        if (!request.startsWith("@")) { request2 = request;}
                        if (request.equals("@")) { //проверка на приватные сообщения
                            currentUser.getOos().writeObject(times() + "Введите: @имя");
                            b = 3;testPrivate(currentUser, dis, request, users, b);}
                        for (User user : users) {
                            if (request.equals("@" + user.getUserName2())) {
                                currentUser.getOos().writeObject(times() + "Введите приватное сообщение для" + user.getUserName());
                                request = dis.readUTF();
                                user.getOos().writeObject(times() + currentUser.getUserName() + "прислал приват: " + request);
                                System.out.println(times() + currentUser.getUserName()
                                        + " прислал приват для " + user.getUserName() + request);
                                b = 3; testPrivate(currentUser, dis, request, users, b); }  b = 2; }
                        if (b == 2 && request.startsWith("@")) {
                            currentUser.getOos().writeObject(times() + "Такого пользователя нет, проверьте имя");
                            b = 3; testPrivate(currentUser, dis, request, users, b); }
                        return request2;
                    }

                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void sendUserList() {
        String usersName = "**userList**";
        for (User user : users) {
            usersName += "//" + user.getUserName(); // **userList**//user1//user2//user3
        }
        for (User user : users) {
            try {
                user.getOos().writeObject(usersName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String times() {
        Date date;
        String dtime;
        SimpleDateFormat df;
        date = new Date(); // текущая дата
        df = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
        dtime = df.format(date); // время
        return dtime = "[" + dtime + "] ";
    }


    private static User testName(User currentUser, DataInputStream dis, ArrayList<User> users) throws IOException {

        currentUser.getOos().writeObject(times() + "Введите имя: ");
        userName = dis.readUTF();
        if (userName.startsWith("/") || userName.startsWith(" ") ||
                userName.startsWith("@") || userName.equals("")) {
            currentUser.getOos().writeObject("Неправильный тип имени, повторите попытку");
            testName(currentUser, dis, users);
        }
        if (users.size() > 1) {
            for (User user : users)
                if (userName.equals(user.getUserName2())) {
                    currentUser.getOos().writeObject("Неправильный тип имени, повторите попытку");
                    testName(currentUser, dis, users);
                }
        }
        return currentUser;
    }
}



