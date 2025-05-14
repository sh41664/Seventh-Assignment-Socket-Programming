package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Shared.User;
public class Server {
    private static final User[] users = {
            new User("user1", "1234"),
            new User("user2", "1234"),
            new User("user3", "1234"),
            new User("user4", "1234"),
            new User("user5", "1234"),
    };

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //TODO: start server socket

        //TODO: accept clients and create a thread for each
        //TODO: update the list of clients
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
