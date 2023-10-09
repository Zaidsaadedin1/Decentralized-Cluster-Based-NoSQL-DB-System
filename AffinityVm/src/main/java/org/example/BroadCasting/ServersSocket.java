package org.example.BroadCasting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServersSocket {
    private static ServersSocket instance;  // Singleton instance
    private final ServerSocket vmServer;
    private List<ServerHandler> listOfServers;
    private static final Object sharedLock = new Object();
    private volatile boolean lockAvailable = true; // Added a boolean lock variable

    private ServersSocket() throws IOException {
        vmServer = new java.net.ServerSocket(1017);
        System.out.println("Server started. Listening on port " + 1017 + " At AFFINITY VM");
        listOfServers = new ArrayList<>();
        startListening();
    }

    public void startListening() {
        while (true) {
            try {
                Socket serverSocket = vmServer.accept();
                System.out.println("Server connected");

                ServerHandler serverHandler = new ServerHandler(serverSocket, sharedLock, this); // Pass ServersSocket to ServerHandler
                listOfServers.add(serverHandler);

                // Start a new thread to handle the client
                Thread clientThread = new Thread(serverHandler);
                clientThread.start();

                // Pass the list of client sockets to each ServerHandler
                serverHandler.setListOfClients(listOfServers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Singleton getInstance method
    public static ServersSocket getInstance() throws IOException {
        if (instance == null) {
            instance = new ServersSocket();
        }
        return instance;
    }

    // Method to check if the lock is available
    public boolean isLockAvailable() {
        return lockAvailable;
    }

    // Method to acquire the lock
    public void acquireLock() {
        lockAvailable = false;
    }

    // Method to release the lock
    public void releaseLock() {
        lockAvailable = true;
    }
}
