package org.example;

import java.io.IOException;

public class RunBootStrappingServer {
    public static void main(String[] args) {
        try {
            BootStrappingServer bootStrappingServer = new BootStrappingServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
