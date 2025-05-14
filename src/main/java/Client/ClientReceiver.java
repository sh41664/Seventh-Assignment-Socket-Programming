package Client;


public class ClientReceiver implements Runnable {
    // TODO: Declare a variable to hold the input stream from the socket
    public ClientReceiver() {
        // TODO: Modify this constructor to receive either a Socket or an InputStream as a parameter
        // TODO: Initialize the input stream variable using the received parameter
    }

    @Override
    public void run() {
        try {
            while (true) {
                //TODO: Listen for new messages from server
                //TODO: print the  new message in CLI
            }
        } catch (Exception e) {

        }
    }

}
