package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HandleClientCommunicationsWithNewVm {
    private BufferedReader reader;
    private PrintWriter writer;
    private final Scanner scanner;
    private ClientsSocket clientsSocket;

    public HandleClientCommunicationsWithNewVm(BufferedReader reader, PrintWriter writer, Scanner scanner) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
    }

    public void setClientsSocket(ClientsSocket clientsSocket) {
        this.clientsSocket = clientsSocket;
    }



    public void startCommunication() throws IOException {
        ApplicationHandler applicationHandler=new ApplicationHandler(reader,writer,scanner);
        DataBaseHandler dataBaseHandler=new DataBaseHandler(reader,writer,scanner,applicationHandler);
        TableHandler tableHandler=new TableHandler(reader,writer,scanner,dataBaseHandler,applicationHandler);
        DocumentHandler documentHandler=new DocumentHandler(reader,writer,scanner,dataBaseHandler,tableHandler,applicationHandler);
        String reader1;
        String userMenuChoice;
        try{
        boolean running = true;
        while (running) {
            userMenuChoice = handleMenu(); // Send the menu options to the server
            switch (userMenuChoice) {
                case "1":
                    applicationHandler.handleCreateApplication();
                    break;
                case "2":
                    applicationHandler.handleDeleteApplication();
                    break;
                case "3":
                    dataBaseHandler.handleCreateDatabase();
                    break;
                case "4":
                    dataBaseHandler.handleDeleteDatabase();
                    break;
                case "5":
                    tableHandler.handleCreateTable();
                    break;
                case "6":
                    tableHandler.handleDeleteTable();
                    break;
                case "7":
                    documentHandler.handleAddDocument();
                    break;
                case "8":
                    documentHandler.handleReadDocument();
                    break;
                case "9":
                    documentHandler.handleUpdateDocument();
                    break;
                case "10":
                    documentHandler.handleDeleteDocument();
                    break;
                default:
                    reader1=reader.readLine();
                    System.out.println(reader1);
                    break;
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
        }


    private String handleMenu() throws IOException {
        String userMenuChoice;
        try {
            for (int i = 0; i < 13; i++) {
                String menuReader = reader.readLine();
                System.out.println(menuReader);
            }

            userMenuChoice = scanner.nextLine(); // Read the user's menu choice
            writer.println(userMenuChoice);     // Send the choice to the client
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userMenuChoice;
    }


}
