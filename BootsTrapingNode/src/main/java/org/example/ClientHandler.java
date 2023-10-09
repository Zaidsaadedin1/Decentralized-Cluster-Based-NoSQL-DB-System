package org.example;

import org.example.Admin.AdminLoginManager;
import org.example.Admin.AdminRegistrationManager;
import org.example.Client.Client;
import org.example.Client.ClientLoginManager;
import org.example.Client.ClientRegistrationManager;
import org.example.Redirect.RedirectManager;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final ClientRegistrationManager clientRegistrationManager;
    private final ClientLoginManager clientLoginManager;
    private final AdminRegistrationManager adminRegistrationManager;
    private final AdminLoginManager adminLoginManager;
    private final RedirectManager redirectManager;

    public ClientHandler(Socket clientSocket, ClientRegistrationManager clientRegistrationManager, ClientLoginManager clientLoginManager, RedirectManager redirectManager,AdminLoginManager adminLoginManager,AdminRegistrationManager adminRegistrationManager) {
        this.clientSocket = clientSocket;
        this.clientRegistrationManager = clientRegistrationManager;
        this.clientLoginManager = clientLoginManager;
        this.adminRegistrationManager=adminRegistrationManager;
        this.adminLoginManager=adminLoginManager;
        this.redirectManager = redirectManager;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                String userMenuChoice = sendMenuOptions(); // Send the menu options to the client

                switch (userMenuChoice) {
                    case "1":
                        handleClientRegistration();
                        break;
                    case "2":
                        handleClientLogin();
                        break;
                    case "3":
                        handleAdminRegistration();
                        break;
                    case "4":
                        handleAdminLogin();
                        break;
                    case "5":
                        writer.println("Goodbye!");
                        break;
                    default:
                        writer.println("Invalid choice. Please choose a valid option.");
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


    // Add a method to send the menu options to the client
    private String sendMenuOptions() {
        String userMenuChoice;
        try {
        writer.println("Welcome to the registration and login system.");
        writer.println("Choose an option:");
        writer.println("1. Client Registration");
        writer.println("2. Client Login");
        writer.println("3. Admin Registration");
        writer.println("4. Admin Login");
        writer.println("5. Exit");

        userMenuChoice=reader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userMenuChoice;
    }
    public void handleClientRegistration() {
        try {
            writer.println("Client Registration Process");
            writer.println("Enter your username:");
            String username = reader.readLine();
            writer.println("Enter your email:");
            String email = reader.readLine();
            writer.println("Enter your password:");
            String password = reader.readLine();

            // Call the registrationManager to register the client
            String registrationMessage = clientRegistrationManager.registerNewClient(username, email, password);
            writer.println(registrationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClientLogin() {
        try {
            writer.println("Client Login Process");
            writer.println("Enter your username:");
            String username = reader.readLine();
            writer.println("Enter your password:");
            String password = reader.readLine();

            boolean loginMessage = clientLoginManager.verifyLogin(username,password);
            writer.println(loginMessage);
            if (loginMessage) {
                writer.println("Login successful!");
                writer.println("Wait, we will redirect you!");
                String port = redirectManager.redirectClient(username, password);

                writer.println(port);
            } else {
                writer.println("Login failed. Invalid credentials.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleAdminRegistration() {
        try {
            writer.println("Admin Registration Process");
            writer.println("Enter your username:");
            String username = reader.readLine();
            writer.println("Enter your email:");
            String email = reader.readLine();
            writer.println("Enter your password:");
            String password = reader.readLine();

            // Call the registrationManager to register the client
            String registrationMessage = adminRegistrationManager.registerNewAdmin(username, email, password);
            writer.println(registrationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAdminLogin() {
        try {
            writer.println("Admin Login Process");
            writer.println("Enter your username:");
            String username = reader.readLine();
            writer.println("Enter your password:");
            String password = reader.readLine();

            boolean loginMessage = adminLoginManager.verifyLogin(username,password);
            writer.println(loginMessage);
            if (loginMessage) {
                writer.println("Login successful!");
                adminMenuOperations();

            } else {
                writer.println("Login failed. Invalid credentials.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String adminMenu(){
        String userMenuChoice;
        try {
            writer.println("Welcome.");
            writer.println("Choose an option:");
            writer.println("1. Block client");
            writer.println("2. Modify client password and username");
            writer.println("3. List all clients");
            writer.println("4. Exit");

            userMenuChoice=reader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userMenuChoice;
    }
    public void adminMenuOperations(){
        try {
            boolean connecting=true;
            while (connecting) {
                String userChoice = adminMenu();
                switch (userChoice) {
                    case "1":
                        blockClient();
                        break;
                    case "2":
                        modifyClientPasswordAndUsername();
                        break;
                    case "3":
                        listAllClients();
                        break;
                    case "4":
                        clientSocket.close();
                        connecting=false;
                        break;
                    default:
                        writer.println("Invalid request.");
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void modifyClientPasswordAndUsername() throws IOException {
        listAllClients();
        writer.println("Please chose a client username you would like to Block");
        String clientUsernameReader=reader.readLine();

        boolean isAvailable=clientRegistrationManager.isClientAvailable(clientUsernameReader);
        writer.println(isAvailable);
        if (isAvailable) {
                writer.println("Enter the new Client username");
                String clientUserNameReader=reader.readLine();


                writer.println("Enter the new Client username");
                String clientPasswordReader=reader.readLine();

                boolean isModified=clientRegistrationManager.modifyClient(clientUsernameReader,clientUserNameReader,clientPasswordReader);
                writer.println(isModified);
                if (isModified){
                    writer.println("client modified");
                }else {
                    writer.println("failed to modify the client");
                }

        } else {
            writer.println("There is no clients available ");
        }
    }

    public void blockClient() throws IOException {
        listAllClients();
        writer.println("Please chose a client username you would like to Block");
        String clientUsernameReader=reader.readLine();
        boolean isAvailable=clientRegistrationManager.isClientAvailable(clientUsernameReader);
        writer.println(isAvailable);

        if (isAvailable) {
                boolean isRemoved=clientRegistrationManager.removeClient(clientUsernameReader);
                writer.println(isRemoved);
                if (isRemoved){
                    writer.println("client removed");
                }else {
                    writer.println("client not removed");
                }

            } else {
            writer.println("There is no clients available ");
        }
    }
    public void listAllClients() {
        List<Client> listOfClients = clientLoginManager.loadRegisteredClientsFromJson();
        writer.println(listOfClients.size());
        writer.println("List of clients");
        if (listOfClients.size() != 0) {
            for (Client client : listOfClients) {
                writer.println("----------------------------------------------------");
                writer.println("client email :"+client.getEmail());
                writer.println("client username :"+client.getUserName());
                writer.println("client password :"+client.getPassword());
                writer.println("----------------------------------------------------");
            }
        } else {
            writer.println("There is no clients available ");
        }
    }

}
