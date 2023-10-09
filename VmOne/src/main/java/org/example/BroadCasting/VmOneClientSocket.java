package org.example.BroadCasting;

import org.example.JasonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class VmOneClientSocket {

    private Socket clientsSocket;
    private BufferedReader reader;
    private PrintWriter writer;
   private ServerHandler serverHandler;


    public VmOneClientSocket() throws IOException {
        try {
            InetAddress addr = InetAddress.getByName("172.21.0.9");
            clientsSocket = new Socket(addr, 1017);
            reader = new BufferedReader(new InputStreamReader(clientsSocket.getInputStream()));
            writer = new PrintWriter(clientsSocket.getOutputStream(), true);
            System.out.println("VM ONE Connected to the server on port :1017 AFFINITY VM"  );
            serverHandler = new ServerHandler(reader, writer);
            serverHandler.setClientsSocket(clientsSocket);
            serverHandler.startCommunication();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
