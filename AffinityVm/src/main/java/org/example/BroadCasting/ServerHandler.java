package org.example.BroadCasting;

import org.example.JasonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerHandler implements Runnable {
    private BufferedReader reader;
    private PrintWriter writer;
    private List<ServerHandler> listOfClients;
    private final Object sharedLock;
    private final ServersSocket serversSocket;
    private BroadCastOperationsManager broadCastOperationsManager;
    private JasonManager jsonManager;
    public ServerHandler(Socket clientSocket, Object sharedLock, ServersSocket serversSocket) {
        this.sharedLock = sharedLock;
        this.serversSocket = serversSocket;
        this.jsonManager=new JasonManager();
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // Initialize the BroadCastOperationsManager here
            this.broadCastOperationsManager = new BroadCastOperationsManager(reader, jsonManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set the list of connected clients
    public void setListOfClients(List<ServerHandler> listOfClients) {
        this.listOfClients = listOfClients;
    }

    // Method to set the BroadCastOperationsManager
    public void setBroadCastOperationsManager(BroadCastOperationsManager broadCastOperationsManager) {
        this.broadCastOperationsManager = broadCastOperationsManager;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void run() {

        // Pass the list of clients to BroadCastOperationsManager
        broadCastOperationsManager.setListOfClients(listOfClients);
        try {
            while (true) {
                // Request access to perform operations
                requestAccessToLock();

                // Synchronize access to receiveOperation using the shared lock
                synchronized (sharedLock) {
                    broadCastOperationsManager.receiveOperation();
                }

                // Release the lock
                releaseLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to request access to the lock
    private void requestAccessToLock() {
        while (!serversSocket.isLockAvailable()) {
            // Lock is not available, keep waiting
            try {
                Thread.sleep(1000); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        serversSocket.acquireLock(); // Lock is available, acquire it
    }

    // Method to release the lock
    private void releaseLock() {
        serversSocket.releaseLock();
    }
}
