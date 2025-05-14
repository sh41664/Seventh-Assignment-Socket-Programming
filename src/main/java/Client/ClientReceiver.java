package Client;

import Shared.Message;

import java.io.ObjectInputStream;

public class ClientReceiver implements Runnable {
    private ObjectInputStream in;

    public ClientReceiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //TODO: Listen for new messages from server
                //TODO: print the  new message in CLI
                Message recived_message = new Message();
            }
        } catch (Exception e) {

        }
    }

}
