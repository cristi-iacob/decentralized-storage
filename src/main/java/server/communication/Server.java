package server.communication;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(7777);

            while (true) {
                Socket s = null;
                s = ss.accept();
                System.out.println("client connected");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                Thread t = new Thread(new ClientHandler(dis, dos, s));
                t.start();
            }
        } catch (Exception e) {

        }
    }
}
