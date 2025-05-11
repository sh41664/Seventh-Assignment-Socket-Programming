package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    //TODO?????? CopyOnWriteArrayList
    public static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server running on port 12345...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            //...
        }
    }
}
