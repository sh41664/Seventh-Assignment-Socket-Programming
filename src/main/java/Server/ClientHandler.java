package Server;

import Shared.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.io.*;
public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private List<ClientHandler> allClients;
    private String username;

    public ClientHandler() {
        // consider adding necessary parameters to the constructor
        // initialize in and out

    }

    @Override
    public void run() {
        try {
            while (true) {
                //READ MESSAGE
                //Process Message
            }
        } catch (Exception e) {

        } finally {
            //Update all clients
        }
    }

    private void broadcast(Message msg) throws IOException {

    }

    private void saveUploadedFile(String filename, byte[] data) throws IOException {

    }

}
