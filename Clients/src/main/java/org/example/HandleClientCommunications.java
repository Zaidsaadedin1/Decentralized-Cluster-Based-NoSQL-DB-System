package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class HandleClientCommunications {
    private BufferedReader reader;
    private PrintWriter writer;
    private final Scanner scanner;
    private ClientsSocket clientsSocket;

    public HandleClientCommunications(BufferedReader reader, PrintWriter writer, Scanner scanner) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
    }
    public void setClientsSocket(ClientsSocket clientsSocket) {
        this.clientsSocket = clientsSocket;
    }

    public void startCommunication() throws IOException {
        String userMenuChoice;
        boolean running=true;
        while (running) {
            userMenuChoice = handleMenu(); // Send the menu options to the server

            switch (userMenuChoice) {
                case "1":
                    // User chose registration
                    handleRegistration();
                    break;
                case "2":
                    // User chose login
                    handleLogin();
                    break;
                case "3":
                    // User chose registration
                    handleRegistration();
                    break;
                case "4":
                    // User chose login
                    handleAdminLogin();
                    break;
                case "5":
                    handleDisconnecting();
                    running=false;
                default:
                    handleWrongChoice();
                    break;
            }
        }
    }



    private String handleMenu() throws IOException {
        String menuReader;
        for (int i = 0; i < 7; i++) {
            menuReader = reader.readLine();
            System.out.println(menuReader);
        }

        String userMenuChoice = scanner.nextLine();
        writer.println(userMenuChoice);
        return userMenuChoice;
    }

    public void handleRegistration() {
        String registrationReader;
        try {
            registrationReader = reader.readLine();
            System.out.println(registrationReader);

            for (int i = 0; i < 3; i++) {
                registrationReader = reader.readLine();
                System.out.println(registrationReader);
                String clientInput = scanner.nextLine();
                writer.println(clientInput);
            }

            String response = reader.readLine();
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogin() {
        String loginReader;
        try {
            loginReader = reader.readLine();
            System.out.println(loginReader);

            for (int i = 0; i < 2; i++) {
                loginReader = reader.readLine();
                System.out.println(loginReader);
                String clientInput = scanner.nextLine();
                writer.println(clientInput);
            }

            // Extract the new port value from the server's response
            String response = reader.readLine();
            if (response.equals("true")) {
                loginReader = reader.readLine();
                System.out.println(loginReader);

                loginReader = reader.readLine();
                System.out.println(loginReader);

                loginReader = reader.readLine();
                System.out.println(loginReader);
                clientsSocket.redirectClient(loginReader);
            }else{
                loginReader = reader.readLine();
                System.out.println(loginReader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleDisconnecting() {
        String disconnectingReader;
        try {
            disconnectingReader = reader.readLine();
            System.out.println(disconnectingReader);
            clientsSocket.closeClientServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleWrongChoice(){
        String wrongChoice;
        try {
            wrongChoice=reader.readLine();
            System.out.println(wrongChoice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    private void handleAdminLogin() {
        String loginReader;
        try {
            loginReader = reader.readLine();
            System.out.println(loginReader);

            for (int i = 0; i < 2; i++) {
                loginReader = reader.readLine();
                System.out.println(loginReader);
                String clientInput = scanner.nextLine();
                writer.println(clientInput);
            }

            String response = reader.readLine();
            loginReader = reader.readLine();
            System.out.println(loginReader);
            if ("true".equals(response)) {
                handelAdminMenuOperations();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String handelAdminMenu() throws IOException {
        String menuReader;
        for (int i = 0; i < 6; i++) {
            menuReader = reader.readLine();
            System.out.println(menuReader);
        }

        String userMenuChoice = scanner.nextLine();
        writer.println(userMenuChoice);
        return userMenuChoice;
    }
    public void handelAdminMenuOperations(){
        try {
            boolean connecting=true;
            while (connecting) {
                String userChoice = handelAdminMenu();
                switch (userChoice) {
                    case "1":
                        handelBlockClient();
                        break;
                    case "2":
                        handelModifyClientPasswordAndUsername();
                        break;
                    case "3":
                        handelListAllClients();
                        break;
                    case "4":
                        clientsSocket.closeClientServer();
                        connecting=false;
                        break;
                    default:
                        writer.println("Invalid request.");
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handelModifyClientPasswordAndUsername() throws IOException {
        handelListAllClients();

        String messageReader=reader.readLine();
        System.out.println(messageReader);
        String clientIdReader=scanner.nextLine();
        writer.println(clientIdReader);

        String isClientAvailable=reader.readLine();
        if (isClientAvailable.equals("true")){
            messageReader=reader.readLine();
            System.out.println(messageReader);
            String clientUserNameReader=scanner.nextLine();
            writer.println(clientUserNameReader);

            messageReader=reader.readLine();
            System.out.println(messageReader);
            String clientPasswordReader=scanner.nextLine();
            writer.println(clientPasswordReader);

            String isClientModified=reader.readLine();
            if (isClientModified    .equals("true")){
                messageReader=reader.readLine();
                System.out.println(messageReader);
            }else {
                messageReader=reader.readLine();
                System.out.println(messageReader);
            }
        }else {
            messageReader=reader.readLine();
            System.out.println(messageReader);
        }
    }

    public void handelBlockClient() throws IOException {
        handelListAllClients();
        String messageReader=reader.readLine();
        System.out.println(messageReader);
        String clientIdReader=scanner.nextLine();
        writer.println(clientIdReader);

        String isClientAvailable=reader.readLine();
        if (isClientAvailable.equals("true")){
            String isClientRemoved=reader.readLine();
            if (isClientRemoved.equals("true")){
                messageReader=reader.readLine();
                System.out.println(messageReader);
            }else {
                messageReader=reader.readLine();
                System.out.println(messageReader);
            }
        }else {
            messageReader=reader.readLine();
            System.out.println(messageReader);
        }
    }
    public void handelListAllClients() throws IOException {
        int size= Integer.parseInt(reader.readLine());
        String messageReader=reader.readLine();
        System.out.println(messageReader);
        if (size!=0){
            for (int i=0;i<size;i++){
                messageReader=reader.readLine();
                System.out.println(messageReader);
                messageReader=reader.readLine();
                System.out.println(messageReader);
                messageReader=reader.readLine();
                System.out.println(messageReader);
                messageReader=reader.readLine();
                System.out.println(messageReader);
                messageReader=reader.readLine();
                System.out.println(messageReader);
            }
        }else {
            messageReader=reader.readLine();
            System.out.println(messageReader);
        }
    }

}
