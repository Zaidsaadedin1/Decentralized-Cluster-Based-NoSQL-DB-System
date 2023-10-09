package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientsSocket {
    private Socket clientsSocket;
    private HandleClientCommunications communications;
    private HandleClientCommunicationsWithNewVm newVm;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;
    private int port;

    public ClientsSocket(int port) throws IOException {
        try {
           this.port = port;
            InetAddress addr = InetAddress.getByName("172.21.0.4");
            clientsSocket = new Socket(addr, port);
           reader = new BufferedReader(new InputStreamReader(clientsSocket.getInputStream()));
           writer = new PrintWriter(clientsSocket.getOutputStream(), true);
           System.out.println("Connected to the server on port " + port);
           scanner = new Scanner(System.in);
           communications = new HandleClientCommunications(reader, writer, scanner);
           communications.setClientsSocket(this); // Set the reference to this ClientsSocket instance
           communications.startCommunication();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeClientServer() {
        try {
            if (clientsSocket != null) {
                clientsSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectClient(String ip) {
        try {
            closeClientServer();
            System.out.println("IPP " + ip);
            InetAddress addr = InetAddress.getByName( ip.split(":")[0]);
            clientsSocket = new Socket(addr, Integer.parseInt(ip.split(":")[1]));
            reader = new BufferedReader(new InputStreamReader(clientsSocket.getInputStream()));
            writer = new PrintWriter(clientsSocket.getOutputStream(), true);

            System.out.println("Redirected to the new server.");
            System.out.println("Connected to the server on port " + ip);
            scanner = new Scanner(System.in);
            newVm = new HandleClientCommunicationsWithNewVm(reader, writer, scanner);
            newVm.setClientsSocket(this); // Set the reference to this ClientsSocket instance
            newVm.startCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
