package org.example;

import org.example.DataBase.DatabaseOperationsManager;
import org.example.Document.DocumentOperationsManager;
import org.example.NoSqlApplications.ApplicationOperationsManager;
import org.example.Table.TableOperationsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String userChoice;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserChoice() {
        return userChoice;
    }

    @Override
    public void run() {
        // Initialize operation managers
        final JasonManager jasonManager = new JasonManager();
        ApplicationOperationsManager applicationOperationsManager = new ApplicationOperationsManager(reader, writer, jasonManager);
        DatabaseOperationsManager dbManager = new DatabaseOperationsManager(reader, writer, applicationOperationsManager, jasonManager);
        TableOperationsManager tableManager = new TableOperationsManager(reader, writer, dbManager, applicationOperationsManager, jasonManager);
        DocumentOperationsManager documentManager = new DocumentOperationsManager(reader, writer, applicationOperationsManager, dbManager, tableManager, jasonManager);

        try {
            while (true) {
                userChoice = sendMenu();
                switch (userChoice) {
                    case "1":
                        applicationOperationsManager.createApplication();
                        break;
                    case "2":
                        applicationOperationsManager.deleteApplication();
                        break;
                    case "3":
                        dbManager.createDatabase();
                        break;
                    case "4":
                        dbManager.deleteDatabase();
                        break;
                    case "5":
                        tableManager.createTable();
                        break;
                    case "6":
                        tableManager.deleteTable();
                        break;
                    case "7":
                        documentManager.addDocument();
                        break;
                    case "8":
                        documentManager.readDocument();
                        break;
                    case "9":
                        documentManager.updateDocument();
                        break;
                    case "10":
                        documentManager.deleteDocument();
                        break;
                    default:
                        writer.println("Invalid request.");
                        break;
                }
            }
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String sendMenu() {
        // Define the menu options and send them to the client
        userChoice = null; // Initialize to null in case of exceptions
        try {
            String menu = """
                    MENU:
                    Choose your operation
                    1. Create Application
                    2. Delete Application
                    3. Create Database
                    4. Delete Database
                    5. Create Table
                    6. Delete Table
                    7. Add Document
                    8. Read Document
                    9. Update Document
                    10. Delete Document
                    Enter the option number:""";
            writer.println(menu);
            userChoice = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userChoice;
    }

}
