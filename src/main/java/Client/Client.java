package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static String username;
    public static void main(String[] args) throws Exception {
        //TODO: create a socket
        try (Socket socket = new Socket("localhost", 12345)) {
            //TODO: Initialize in and out


            Scanner scanner = new Scanner(System.in);

            // --- LOGIN PHASE ---
            System.out.println("===== Welcome to CS Music Room =====");


            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();


                sendLoginRequest(username, password);

                //TODO: get login response
                //TODO: manage login
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
                        //TODO: close the socket
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

    private static void sendLoginRequest(String username, String password) {
        //TODO: send the request
    }
    private static void enterChat(Scanner scanner) throws IOException {
        System.out.print("You have entered the chat ");

    }

    private static void sendChatMessage(String message) throws IOException {
        //TODO: send the chat message
    }

    private static void uploadFile(Scanner scanner) throws IOException {
        File userFolder = new File("resources/Client/" + username);
        if (!userFolder.exists() || !userFolder.isDirectory()) {
            System.out.println("No uploadable files found in " + userFolder.getPath());
            return;
        }

        File[] files = userFolder.listFiles((dir, name) -> !new File(dir, name).isDirectory());
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

        //TODO: send a message informing the server that a file is going to be uploaded
        //TODO: send the file in bytes
    }

    private static void requestDownload(Scanner scanner) throws IOException {
        //TODO: send request to get file names
        //TODO: show the names and let the user to select one
        //TODO: download the file into the specific folder of the user in the resources folder
    }
}
