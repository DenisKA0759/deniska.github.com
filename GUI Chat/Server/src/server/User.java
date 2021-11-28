package server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class User {
    private Socket socket;
    private String userName;
    private String userName2;
    private UUID uuid;
    private ObjectOutputStream oos;

    public User(Socket socket) {
        this.socket = socket;
        this.userName = "<"+"Гость"+">";
        this.uuid = UUID.randomUUID();
        this.userName2 = userName;
    }

    public void setUserName(String userName) { this.userName = "<"+userName+">"; }
    public void setUserName2(String userName) { this.userName2 = userName; }
    public Socket getSocket() { return socket;}
    public String getUserName() { return userName;}
    public String getUserName2() { return userName2;}
    public UUID getUuid() { return uuid; }
    public ObjectOutputStream getOos() { return oos; }
    public void setOos(ObjectOutputStream oos) { this.oos = oos;}
}

