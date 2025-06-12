package Server;

import Shared.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream in ;
    private DataOutputStream out ;
    private BufferedInputStream bin ;
    private BufferedOutputStream bout ;
    private List<ClientHandler> allClients;
    private String username;


    public ClientHandler(Socket socket, List<ClientHandler> allClients) {

        this.socket = socket;
        this.allClients = allClients;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            bin = new BufferedInputStream(socket.getInputStream());
            bout = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            while (true) {
                String message = in.readUTF();
                System.out.println(message);
                Message newMessage = mapper.readValue(message, Message.class);
                int type = newMessage.getType();
                switch (type) {
                    case 0:
                        handleLogin(newMessage.getUsername(), newMessage.getPassword());
                        break;
                    case 1:
                        String sender = newMessage.getSender();
                        String content = newMessage.getContent();
                        broadcast(sender+": "+content);
                        break;
                    case 3:
                        String fileName = newMessage.getFileName();
                        int fileSize = newMessage.getFileSize();
                        receiveFile(fileName, fileSize);
                        break;
                    case 4:
                        sendFileList();
                        break;
                    case 6:
                        String requestedFileName = newMessage.getFileName();
                        sendFile(requestedFileName);
                        break;
                }
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }


    private void sendMessage(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void broadcast(String msg) throws IOException {
        for(ClientHandler client: allClients) {
            if(!client.equals(this)) {
                client.sendMessage(msg);
            }
        }
    }

    private void sendFileList(){
        String filesList = "";
        ObjectMapper mapper = new ObjectMapper();
        File folder = new File("src\\main\\resources\\Server\\Files");
        File[] listOfFiles = folder.listFiles();

        // list available files
        System.out.println("sending list of files . . .");
        for (int i = 0; i < listOfFiles.length; i++) {
            filesList += ((i + 1) + ". " + listOfFiles[i].getName() + ",");
        }

        System.out.println(filesList);

        Message fileListMessage = new Message(5,"Server", filesList);
        try {
            out.writeUTF(mapper.writeValueAsString(fileListMessage));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void sendFile(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src\\main\\resources\\Server\\Files\\"+fileName);

        Message fileMetaData = new Message(3,"Server", fileName+"/"+file.length());
        try {
            FileInputStream fis = new FileInputStream(file);
            out.writeUTF(mapper.writeValueAsString(fileMetaData));
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bout.write(buffer, 0, bytesRead);
            }
            bout.flush();
            System.out.println("file Sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void receiveFile(String filename, int fileLength)
    {
        File savedFile = new File("src\\main\\resources\\Server\\Files\\"+filename);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalRead=0;
            while (totalRead < fileLength &&
                    (bytesRead = bin.read(buffer, 0, (int) Math.min(buffer.length, fileLength - totalRead))) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            System.out.println(filename + " received");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void handleLogin(String username, String password) throws IOException, ClassNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        boolean accepted = Server.authenticate(username, password);
        Message response;
        if(accepted){
            this.username = username;
            response = new Message(2,"Server","approved");
        }else{
            response = new Message(2,"Server","rejected");
        }
        out.writeUTF(mapper.writeValueAsString(response));
        out.flush();
    }

}