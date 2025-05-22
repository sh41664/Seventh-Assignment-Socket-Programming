package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Shared.User;
public class Server {
    // Predefined users for authentication
    private static final User[] users = {
            new User("user1", "1234"),
            new User("user2", "1234"),
            new User("user3", "1234"),
            new User("user4", "1234"),
            new User("user5", "1234"),
    };

    // List of currently connected clients
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(2819);
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected");
            ClientHandler clientHandler = new ClientHandler(socket,clients);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public static boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}