package org.example.Admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminRegistrationManager {
    private List<Admin> registeredAdmins;
    private final String jsonFilePath = "/app/config/registered_admins.json"; // Updated path inside the container

    // private final String jsonFilePath = "C:\\Users\\zaids\\Desktop\\Capson\\BootsTrapingNode\\registered_clients.json"; // Path to your JSON file
    private int latestId = 0; // Add a field to keep track of the latest ID

    public AdminRegistrationManager() {
        loadRegisteredAdmins(); // Load clients from the JSON file when the manager is initialized
        // Initialize the latestId based on the existing clients
        for (Admin admin : registeredAdmins) {
            if (admin.getId() > latestId) {
                latestId = (int) admin.getId();
            }
        }
    }

    // Method to register a new client and update the JSON file
    public String registerNewAdmin(String userName, String email, String password) {
        String errorMessage;

        // Check if the email or username is already used
        if (isEmailOrUserNameUsed(email, userName)) {
            return "Admin already registered with this email or username";
        }

        // Increment the latestId and assign it to the new client
        latestId++;
        Admin newAdmin = new Admin(latestId, userName, email, password);
        registeredAdmins.add(newAdmin);

        // Save the updated list of clients to the JSON file
        saveRegisteredAdmins();
        return "Admin registered successfully";
    }

    // Method to check if an email or username is already used
    private boolean isEmailOrUserNameUsed(String email, String userName) {
        for (Admin admin : registeredAdmins) {
            if (admin.getEmail().equals(email) || admin.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }


    private List<Admin> loadRegisteredAdmins() {
        try (Reader reader = new FileReader(jsonFilePath)) {
            Type AdminListType = new TypeToken<List<Admin>>() {}.getType();
            Gson gson = new Gson();
            registeredAdmins = gson.fromJson(reader, AdminListType);
            if (registeredAdmins == null) {
                registeredAdmins = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            registeredAdmins = new ArrayList<>();
        }
        return registeredAdmins;
    }
    public boolean saveRegisteredAdmins() {
        try {
            if (jsonFilePath == null) {
                System.err.println("jsonFilePath is null.");
                return false;
            }

            if (registeredAdmins == null) {
                System.err.println("registeredDatabases is null.");
                return false;
            }
            Writer writer = new FileWriter(jsonFilePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(registeredAdmins, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            // Handle the error here
            System.err.println("Error saving databases to JSON file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
