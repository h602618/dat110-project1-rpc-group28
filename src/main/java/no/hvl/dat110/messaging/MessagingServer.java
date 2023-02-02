package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;

public class MessagingServer {
    // server-side socket for accepting incoming TCP connections
    private ServerSocket welcomeSocket;

    public MessagingServer(int port) {
        try {
            this.welcomeSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Messaging server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public MessageConnection accept() {
        MessageConnection connection = null;

        try {
            connection = new MessageConnection(welcomeSocket.accept());
        } catch (IOException e) {
            System.out.println("TCP MessagingServer:");
            e.printStackTrace();
            System.exit(1);
        }

        return connection;
    }

    public void stop() {
        if (welcomeSocket != null) {
            try {
                welcomeSocket.close();
            } catch (IOException ex) {
                System.out.println("Messaging server: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}