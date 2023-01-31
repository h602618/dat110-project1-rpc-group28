package no.hvl.dat110.messaging;


import java.io.IOException;
import java.net.Socket;

public class MessagingClient {
    // name/IP address of the messaging server
    private final String server;

    // server port on which the messaging server is listening
    private final int port;

    public MessagingClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public MessageConnection connect() {
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(server, port);
        } catch (IOException e) {
            System.out.println("TCP MessagingClient:");
            e.printStackTrace();
            System.exit(1);
        }

        return new MessageConnection(clientSocket);
    }
}
