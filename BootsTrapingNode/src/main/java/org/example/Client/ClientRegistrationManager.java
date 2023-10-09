package org.example.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClientRegistrationManager {
    private List<Client> registeredClients;
    private final String jsonFilePath = "/app/config/registered_clients.json"; // Updated path inside the container

   // private final String jsonFilePath = "C:\\Users\\zaids\\Desktop\\Capson\\BootsTrapingNode\\registered_clients.json"; // Path to your JSON file
    private int latestId = 0; // Add a field to keep track of the latest ID

    public ClientRegistrationManager() {
        loadRegisteredClients(); // Load clients from the JSON file when the manager is initialized
        // Initialize the latestId based on the existing clients
        for (Client client : registeredClients) {
            if (client.getId() > latestId) {
                latestId = (int) client.getId();
            }
        }
    }

    // Method to register a new client and update the JSON file
    public String registerNewClient(String userName, String email, String password) {
        String errorMessage;

        // Check if the email or username is already used
        if (isEmailOrUserNameUsed(email, userName)) {
            errorMessage = "You can't register using this email or username, it's already used.";
            return errorMessage;
        }

        // Increment the latestId and assign it to the new client
        latestId++;
        Client newClient = new Client(latestId, userName, email, password);
        registeredClients.add(newClient);

        // Save the updated list of clients to the JSON file
        saveRegisteredClients();

        return "Registered successfully";
    }

    // Method to check if an email or username is already used
    private boolean isEmailOrUserNameUsed(String email, String userName) {
        for (Client client : registeredClients) {
            if (client.getEmail().equals(email) || client.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    // Method to load registered clients from the JSON file
//    private void loadRegisteredClients() {
//        try (Reader reader = new FileReader(jsonFilePath)) {
//            Gson gson = new Gson();
//            registeredClients = gson.fromJson(reader, ArrayList.class);
//            if (registeredClients == null) {
//                registeredClients = new ArrayList<>();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            registeredClients = new ArrayList<>();
//        }
//    }

    // Method to load registered clients from the JSON file
    private List<Client> loadRegisteredClients() {
        try (Reader reader = new FileReader(jsonFilePath)) {
            Type clientListType = new TypeToken<List<Client>>() {}.getType();
            Gson gson = new Gson();
            registeredClients = gson.fromJson(reader, clientListType);
            if (registeredClients == null) {
                registeredClients = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            registeredClients = new ArrayList<>();
        }
        return registeredClients;
    }

    public boolean saveRegisteredClients() {
        try {
            if (jsonFilePath == null) {
                System.err.println("jsonFilePath is null.");
                return false;
            }

            if (registeredClients == null) {
                System.err.println("registeredDatabases is null.");
                return false;
            }
            Writer writer = new FileWriter(jsonFilePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(registeredClients, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            // Handle the error here
            System.err.println("Error saving databases to JSON file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    public boolean isClientAvailable(String clientUsername){
        if (registeredClients.size() != 0) {
            for (Client client : registeredClients) {
                if (client.getUserName().equals(clientUsername)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeClient (String clientUsername) {
        boolean isAvailable = isClientAvailable(clientUsername);
        if (isAvailable) {
            for (Client client : registeredClients) {
                if (client.getUserName().equals(clientUsername)) {
                    registeredClients.remove(client);
                    saveRegisteredClients();
                    return true;
                }
            }
        }
        return false;
    }
    public boolean modifyClient (String oldClientName,String clientUsername,String password) {
        boolean isAvailable = isClientAvailable(oldClientName);
        if (isAvailable) {
            for (Client client : registeredClients) {
                if (client.getUserName().equals(oldClientName)) {
                    Client modifiedClient=new Client((int) client.getId(),clientUsername,client.getEmail(),password);
                    registeredClients.remove(client);
                    registeredClients.add(modifiedClient);
                    saveRegisteredClients();
                    return true;
                }
            }
        }
        return false;
    }

}
