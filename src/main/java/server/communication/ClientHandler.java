package server.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(DataInputStream dis, DataOutputStream dos, Socket s) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
    }

    @Override
    public void run() {
        String received;
        String toreturn;

        while (true) {
            try {
                Thread.sleep(5000);
                received = dis.readUTF();

                System.out.println("received from client: " + received);
                dos.writeUTF("tralala");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
