package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AffinityVm {
    private static AffinityVm instance;  // Singleton instance
    private final ServerSocket vmServer;
    private List<ClientHandler> clientHandlers;


    private AffinityVm() throws IOException {
        vmServer = new ServerSocket(1013);
        System.out.println("Server started. Listening on port " + 1013 + " At Affinity Vm");
        clientHandlers = new ArrayList<>();
        startListening();
    }

    // Singleton getInstance method
    public static AffinityVm getInstance() throws IOException {
        if (instance == null) {
            instance = new AffinityVm();
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
