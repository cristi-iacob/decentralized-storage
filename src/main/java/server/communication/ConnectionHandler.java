package server.communication;

public interface ConnectionHandler {
    void startConnection();
    void sendMessage(String message);
    String receiveMessage();
}
