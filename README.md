# Seventh Assignment: Socket Programming

## Table of contents
- [Important Note](#important-note)
- [Introduction](#introduction)
- [Objectives 🎯](#objectives-)
- [Theoretical Questions 📝](#theoretical-questions-)
- [Practical Questions 💻](#practical-questions-)
- [Bonus Tasks 🌟](#bonus-tasks-)
- [Evaluation 📊](#evaluation-)
- [Submission ⌛](#submission-)
- [Additional Resources 📚](#additional-resources-)


## Important Note:
When you're opening this project on your own system, please make sure to:

1. Go to **Settings/Preferences** > **Build, Execution, Deployment** > **Build Tools** > **Gradle**.

2. Under **Gradle settings**, change the **Gradle distribution** to:

- **Use local Gradle distribution**, and

- Set the **Gradle home** path to your own local Gradle installation directory.

If you don’t have Gradle installed locally, you can either:

- Install Gradle manually and configure the path, or

- Change the setting to **Use Gradle wrapper** instead.



## Introduction
Welcome to the Seventh Advanced Programming (AP) Assignment. This project is divided into two main sections:

1. **Theoretical Questions**: This section is designed to deepen your understanding of networking concepts in Java. You'll have to analyze one code block and answer questions about it.

2. **Practical Questions**: In this section, you'll get hands-on experience with socket-programming in Java.


## Objectives 🎯

- Reviewing the concepts of socket programming

- Creating a local server to handle incoming client requests

- Enabling clients to send messages in a group chat or download files from the server

- Designing a request-response mechanism for communication between client and server


## Theoretical Questions 📝
**Note: Please answer these questions in a Markdown file (Report.md) and place it in the root directory of your fork. Include code or screenshots where you see fit.**



### 1. Three Ways to Send a Login Message

```java  
  class LoginRequest implements Serializable {
    String username;
    String password;

    LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5050);

        LoginRequest loginRequest = new LoginRequest("user1", "pass123");
        // === Method 1: Plain String ===
        PrintWriter stringOut = new PrintWriter(socket.getOutputStream(), true);
        stringOut.println("LOGIN|" + loginRequest.username + "|" + loginRequest.password);

        // === Method 2: Serialized Object ===
        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.writeObject(loginRequest);

        // === Method 3: JSON ===
        Gson gson = new Gson();
        String json = gson.toJson(loginRequest);
        PrintWriter jsonOut = new PrintWriter(socket.getOutputStream(), true);
        jsonOut.println(json);

        socket.close();
    }
}
```  

**Note:** The server file is not provided as part of this project. You are encouraged to write your own server implementation for testing and running the application.
### **Questions:**
####  Method 1: **Plain String Format**

1. What are the pros and cons of using a plain string like `"LOGIN|user|pass"`?
2. How would you parse it, and what happens if the delimiter appears in the data?
3. Is this approach suitable for more complex or nested data?

---
#### Method 2:  **Serialized Java Object**

1. What’s the advantage of sending a full Java object?
2. Could this work with a non-Java client like Python?

---
#### Method 3: **JSON**

1. Why is JSON often preferred for communication between different systems?
2. Would this format work with servers or clients written in other languages?    

---

## Practical Questions 💻



### Chat

#### 📝 Task Description
---
In this task, you will complete the functionality for a basic command-line chat application. The application allows multiple users to join a shared chat room, send messages to each other, and receive messages in real time. Your work involves both the client-side and server-side logic needed to support live message exchange and communication flow.


#### 🛠 What You Need to Do

- Implement the logic for reading and processing incoming messages on the server side.
- Handle message sending from the server to an individual client.
- Broadcast messages from one user to all others and update the chat history.
- Ensure that the server updates its list of connected clients properly when a client disconnects.
- On the client side, implement the functionality to listen for and display new messages from the server.
- Start the client-side message receiver when a user enters the chat.
- Enable sending a message from the client to the server when the user types in the chat.
- Implement the exit mechanism so that when a user types `/exit`, the client disconnects cleanly from the server.



### File Upload / Download

#### 📝 Task Description
The application allows clients to upload files from their local directory to the server and download files stored on the server to their local folder. Each client has their own folder under `resources/Client/<username>`.

#### 🛠 What You Need to Do

##### Upload

- Let the user select a file from `resources/Client/<username>`.
- Send file metadata (name and length) to the server to indicate an upload is starting.
- Read the file as bytes and send it over the socket.
- On the server, implement `receiveFile()` to accept the file data and save it using `saveUploadedFile()`.
- Place some files in `resources/client/<username>/` for the client for testing.

##### Download

- Send a file list request to the server.
- On the server, implement `sendFileList()` to list and send back all files in `resources/Server/`.
- Let the client select a file from the list.
- Send a file download request from the client.
- Implement `sendFile()` on the server to send the selected file and its size.
- Receive the file on the client and save it to `resources/Client/<username>/`.
- Place some files in `resources/server/` for the server for the client for testing.

---

### 📬 About the `Message` Class (Optional)

The Message class defines a **structured format** for data exchanged between the **Client** and **Server**, such as login requests, chat messages, file operations, or other commands.

> **Note:**  
> This class is **not required** for socket communication.  
> It is provided as a **convenient option** for structured messaging between the client and server.  
> You may choose to use:
>
> - Java serialization,
>
> - JSON with a library like Jackson or Gson,
>
> - Or your own custom protocol (e.g., delimited strings).
>



---  



## Bonus Tasks 🌟

### Chat

**Graphical Visualization (UI)**
  
  - Implement a graphical user interface (GUI) for the chat application. The GUI should allow users to:
      
      - See a list of connected users.
          
      - Send and receive messages in real time.
          
      - Display chat messages in a scrolling window.
          
  - Implement a chat history to show previous messages, allowing users to scroll back and view past conversations.
          
    



## Evaluation 📊

Your work on this assignment will be evaluated based on:

- **Understanding of Networking Concepts**: Your ability to accurately answer the theoretical questions. Remember that the answers to the theoretical questions should be provided separately in a markdown file.

- **Code Quality**: Your code should be well-structured, readable, and efficient. Proper use of Java conventions, including variable naming, class structure, and comments, will also be considered.


- 🔒 Important Constraint
  The Server and Client modules must not reference each other directly in code.
  They communicate only through sockets, not through shared classes.
  However, both the server and client can reference the Shared folder to use common data structures, constants, or utilities.

- Total: 100 points
    - 🧠 Theoretical Questions – 20 points
    - 🧮 Practical Task 1 (Chat) – 40 points
    - 🏦 Practical Task 2 (File Upload Download) – 40 points
    - 🌟 Bonus Tasks – Up to 10 extra points

## Submission ⌛

1. Add your mentor as a contributor to the project.
2. Create a `develop` branch for implementing features.
3. Use Git for regular code commits.
4. Push your code and the answers file to the remote repository.
5. Submit a pull request to merge the `develop` branch with `main`.

The deadline for submitting your code is **Wednesday, May 21** (31st of Ordibehesht)

## Additional Resources 📚

For assistance with this assignment, you may refer to the following resources:

- [Java Socket Programming Client Server Messenger by WittCode](https://youtu.be/gchR3DpY-8Q?si=dSyRSnFmB6fLIpej)
- [Working with jackson](https://jenkov.com/tutorials/java-json/jackson-installation.html)
To learn about different input and output streams:
  - [InputStream](https://jenkov.com/tutorials/java-io/inputstream.html)
  - [OutputStream](https://jenkov.com/tutorials/java-io/outputstream.html)
  - [DataInputStream](https://jenkov.com/tutorials/java-io/datainputstream.html)
  - [DataOutputStream](https://jenkov.com/tutorials/java-io/dataoutputstream.html)
  - [FileInputStream](https://jenkov.com/tutorials/java-io/fileinputstream.html)
  - [FileOutputStream](https://jenkov.com/tutorials/java-io/fileoutputstream.html)
  - [PrintWriter](https://jenkov.com/tutorials/java-io/printwriter.html)
  

Also, you can find a wealth of knowledge from various YouTube courses. They can be a great source of learning. Alongside, joining discussions on forums and reading helpful documents can also be beneficial.
