package server.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketConnectionHandler implements ConnectionHandler {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private InetAddress ip;
    private int port;

    public SocketConnectionHandler(String ip, int port) {
        try {
            this.ip = InetAddress.getByName(ip);
            this.port = port;
            startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startConnection() {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            this.dataOutputStream.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveMessage() {
        try {
            return dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
