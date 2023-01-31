package no.hvl.dat110.messaging.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageUtils;
import no.hvl.dat110.messaging.MessagingClient;
import no.hvl.dat110.messaging.MessagingServer;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestMessaging {
    @Test
    public void test() {
        byte[] clientSent = {1, 2, 3, 4, 5};

        AtomicBoolean failure = new AtomicBoolean(false);

        Thread serverThread = new Thread(() -> {
            MessagingServer server = null;

            try {
                System.out.println("Messaging server - start");

                server = new MessagingServer(MessageUtils.MESSAGINGPORT);
                MessageConnection connection = server.accept();

                Message request = connection.receive();

                byte[] serverReceived = request.getData();
                Message reply = new Message(serverReceived);

                connection.send(reply);
                connection.close();

                assertArrayEquals(clientSent, serverReceived);
            } catch (Exception e) {
                e.printStackTrace();
                failure.set(true);
            } finally {
                server.stop();

                System.out.println("Messaging server - stop");
            }

        });

        Thread clientThread = new Thread(() -> {
            try {
                System.out.println("Messaging client - start");

                MessagingClient client = new MessagingClient(MessageUtils.MESSAGINGHOST, MessageUtils.MESSAGINGPORT);
                MessageConnection connection = client.connect();
                Message message1 = new Message(clientSent);

                connection.send(message1);

                Message message2 = connection.receive();
                byte[] clientReceived = message2.getData();

                connection.close();

                System.out.println("Messaging client - stop");

                assertArrayEquals(clientSent, clientReceived);
            } catch (Exception e) {
                e.printStackTrace();
                failure.set(true);
            }
        });

        try {
            serverThread.start();
            clientThread.start();

            serverThread.join();
            clientThread.join();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            if (failure.get()) {
                fail();
            }
        }

    }
}
