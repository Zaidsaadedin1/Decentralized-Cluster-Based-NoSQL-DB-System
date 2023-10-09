package org.example;

import java.io.IOException;

public class RunClients {

    public static void main(String[] args) {
        try {
            int port =1010;
            ClientsSocket clientsSocket1 = new ClientsSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
