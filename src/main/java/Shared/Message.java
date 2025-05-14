package Shared;

import java.io.Serializable;

public class Message implements Serializable {
    public int type; // e.g., 0 = login, 1 = sendMessage
    public String sender;
    public String content;    // message text or file name or ...


    public Message(){

    }
    public Message(int type, String sender, String content) {
        this.type = type;
        this.sender = sender;
        this.content = content;
    }

    @Override
    public String toString(){
        return sender + ": " + content;
    }
}
