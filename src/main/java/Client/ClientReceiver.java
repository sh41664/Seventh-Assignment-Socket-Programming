package Client;


import java.io.DataInputStream;


public class ClientReceiver implements Runnable {
    DataInputStream dis;
    public ClientReceiver(DataInputStream dis) {
        this.dis = dis;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String msg = dis.readUTF();
                System.out.println(msg);
            }
        } catch (Exception e) {

        }
    }

}