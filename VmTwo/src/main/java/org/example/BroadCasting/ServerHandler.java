package org.example.BroadCasting;

import org.example.JasonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerHandler {
    private Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public ServerHandler(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }
    public void setClientsSocket(Socket socket) {
        this.socket = socket;
    }

    public void startCommunication() {
        JasonManager jsonManager = new JasonManager();
        BroadCastOperationsManager broadCastOperationsManager = new BroadCastOperationsManager(reader, writer, jsonManager);

        try {
            while (true) {
                broadCastOperationsManager.sendOperation();
                System.out.println("sent");
                broadCastOperationsManager.receiveOperation();
                System.out.println("received");
            }
        } catch (IOException e) {
            // Handle exceptions gracefully (e.g., log the error and close resources)
            e.printStackTrace();
        } finally {
            try {
                // Close the socket and any other resources when done
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
