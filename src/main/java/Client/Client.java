package Client;

import Shared.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static DataInputStream in;
    private static DataOutputStream out;
    private static BufferedInputStream inBuff;
    private static BufferedOutputStream outBuff;
    private static ObjectMapper mapper = new ObjectMapper();
    private static String username;

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 2819)) {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            inBuff = new BufferedInputStream(socket.getInputStream());
            outBuff = new BufferedOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            // --- LOGIN PHASE ---
            System.out.println("===== Welcome to CS Music Room =====");

            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                loggedIn = sendLoginRequest(username, password);
                if (loggedIn) {
                    System.out.println("Logged in");
                    Client.username = username;
                } else {
                    System.out.println("Not logged in");
                }
            }

            // --- ACTION MENU LOOP ---
            while (true) {
                printMenu();
                System.out.print("Enter choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> enterChat(scanner);
                    case "2" -> uploadFile(scanner);
                    case "3" -> requestDownload(scanner);
                    case "0" -> {
                        System.out.println("Exiting...");
                        in.close();
                        out.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Enter chat box");
        System.out.println("2. Upload a file");
        System.out.println("3. Download a file");
        System.out.println("0. Exit");
    }

    private static boolean sendLoginRequest(String username, String password) {
        boolean result = false;
        try {
            Message loginRequest = new Message(0, username, username + "/" + password);
            out.writeUTF(mapper.writeValueAsString(loginRequest));
            out.flush();
            String response = in.readUTF();
            Message serverResponse = mapper.readValue(response, Message.class);
            if (serverResponse.getType() == 2) {
                result = serverResponse.loginResult();
            } else {
                System.out.println("Invalid response");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static void enterChat(Scanner scanner) throws IOException {
        System.out.print("You have entered the chat ");
        Thread receiverThread = new Thread(new ClientReceiver(in));
        receiverThread.start();

        String messageString = "";
        while (!messageString.equalsIgnoreCase("/exit")) {
            messageString = scanner.nextLine();
            if (!messageString.equalsIgnoreCase("/exit")) {
                sendChatMessage(messageString);
            }
        }
        sendChatMessage(username + " left the chat");
        receiverThread.interrupt();
    }

    private static void sendChatMessage(String messageToSend) throws IOException {
        Message sendingMessage = new Message(1, username, messageToSend);
        out.writeUTF(mapper.writeValueAsString(sendingMessage));
        out.flush();
    }

    private static void uploadFile(Scanner scanner) throws IOException {
        File file = new File("src/main/resources/Client/" + username);
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files to upload.");
            return;
        }

        // Show available files
        System.out.println("Select a file to upload:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        System.out.print("Enter file number: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 0 || choice >= files.length) {
            System.out.println("Invalid choice.");
            return;
        }

        String fileName = files[choice].getName();
        long fileSize = files[choice].length();
        String metaDataString = fileName + "/" + fileSize;
        Message metaData = new Message(3, username, metaDataString);
        out.writeUTF(mapper.writeValueAsString(metaData));
        out.flush();

        System.out.println("Sending file : " + fileName);
        FileInputStream fileInputStream = new FileInputStream(files[choice]);
        byte[] buffer = new byte[1000];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outBuff.write(buffer, 0, bytesRead);
        }
        outBuff.flush();
    }

    private static void requestDownload(Scanner scanner) throws IOException {
        Message request = new Message(4, username, "/DFR");
        out.writeUTF(mapper.writeValueAsString(request));
        out.flush();

        String files = in.readUTF();
        boolean rightMessage = false;
        while (!rightMessage) {
            Message fileList = mapper.readValue(files, Message.class);
            if (fileList.getType() == 5) {
                List<String> filesList = fileList.getListOfFiles();
                for (String file : filesList) {
                    System.out.println(file);
                }
                System.out.print("Enter file number: ");
                int choice = scanner.nextInt() - 1;
                String fileName = filesList.get(choice).split(". ")[1];
                System.out.println(fileName);
                Message fileNameDownload = new Message(6, username, fileName);
                out.writeUTF(mapper.writeValueAsString(fileNameDownload));
                out.flush();

                boolean fileMeta = false;
                while (!fileMeta) {
                    String fileMetaJson = in.readUTF();
                    Message fileMetaMsg = mapper.readValue(fileMetaJson, Message.class);
                    if (fileMetaMsg.getType() == 3) {
                        fileMeta = true;
                        String receiveFileName = fileMetaMsg.getFileName();
                        long fileLength = fileMetaMsg.getFileSize();
                        File recieveFile = new File("src/main/resources/Client/" + username + "/" + receiveFileName);
                        FileOutputStream fileOutputStream = new FileOutputStream(recieveFile);
                        byte[] buffer = new byte[1000];
                        int bytesRead;
                        long totalRead = 0;
                        while (totalRead < fileLength &&
                                (bytesRead = inBuff.read(buffer, 0, (int) Math.min(buffer.length, fileLength - totalRead))) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                            totalRead += bytesRead;
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        System.out.println("file received");
                    }
                }
                rightMessage = true;
            }
        }
    }
}
