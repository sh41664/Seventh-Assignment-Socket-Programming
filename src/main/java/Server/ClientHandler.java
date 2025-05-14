package Server;

import Shared.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.io.*;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private List<ClientHandler> allClients;
    private String username;

    public ClientHandler() {
        //TODO: consider adding necessary parameters to the constructor
        //TODO: initialize in and out
    }

    @Override
    public void run() {
        try {
            while (true) {
                //TODO: READ MESSAGE
                //TODO: Process Message
            }
        } catch (Exception e) {

        } finally {
            //TODO: Update the clients list in Server
        }
    }


    private void sendMessage(String msg){
        //TODO: send the message (chat) to the client
    }
    private void broadcast(String msg) throws IOException {
        //TODO: send the message to every other user currently in the chat room
        //TODO: update the chat history in the server
    }

    private void receiveFile(String filename)
    {
        //TODO: receive the file that is being uploaded and put it ina byte array
        //TODO: after the upload is done, save it using saveUploadedFile
    }
    private void saveUploadedFile(String filename, byte[] data) throws IOException {
        //TODO: save the file in the Server Resources folder
    }

    private void handleLogin(String username, String password) throws IOException, ClassNotFoundException {
        //TODO: use Server.login method to authenticate
        //TODO: send appropriate response to user
    }

}
