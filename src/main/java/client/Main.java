package client;


public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client();
            Thread.sleep(5000);
            client.sendMessage("qweqwe");
            Thread.sleep(5000);
            System.out.println(client.readMessage());
        } catch (Exception e) {

        }
    }
}
