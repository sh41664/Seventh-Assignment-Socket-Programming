package Client;

import java.io.ObjectInputStream;

public class ClientReceiver implements Runnable {
    private ObjectInputStream in;

    public ClientReceiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {

    }

}
