package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VmOne {
    private static VmOne instance;  // Singleton instance
    private final ServerSocket vmServer;
    private final List<ClientHandler> clientHandlers;


    private VmOne() throws IOException {
        vmServer = new ServerSocket(1011);
        System.out.println("Server started. Listening on port " + 1011 + " At VM one");
        clientHandlers = new ArrayList<>();
        startListening();
    }

    // Singleton getInstance method
    public static VmOne getInstance() throws IOException {
        if (instance == null) {
            instance = new VmOne();
        }
        return instance;
    }

    public void startListening() {
        while (true) {
            try {
                Socket clientSocket = vmServer.accept();
                System.out.println("Client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
