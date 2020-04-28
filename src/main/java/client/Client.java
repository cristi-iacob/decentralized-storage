package client;

import server.communication.ConnectionHandler;
import server.communication.SocketConnectionHandler;

public class Client {
    private ConnectionHandler connectionHandler;

    public Client() {
        connectionHandler = new SocketConnectionHandler("localhost", 7777);
    }

    public void sendMessage(String message) {
        connectionHandler.sendMessage(message);
    }

    public  String readMessage() {
        return connectionHandler.receiveMessage();
    }
}
