package org.example.Client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLoginManager {
    private final String jsonFilePath = "/app/config/registered_clients.json"; // Updated path inside the container

   //private final String jsonFilePath = "C:\\Users\\zaids\\Desktop\\Capson\\BootsTrapingNode\\registered_clients.json"; // Path to your JSON file

    public boolean verifyLogin(String username, String password) {
        List<Client> registeredClients = loadRegisteredClientsFromJson();

        for (Client client : registeredClients) {
            if (client.getUserName().equals(username) && client.getPassword().equals(password)) {
                return true; //"Login successful"
            }
        }

        return  false;//"Login failed"
    }

    public List<Client> loadRegisteredClientsFromJson() {
        try (Reader reader = new FileReader(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            // Convert JSON data to a list of Client objects (you'll need to create a suitable Client constructor)
            List<Client> registeredClients = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = (int) jsonObject.getDouble("id");
                String userName = jsonObject.getString("userName");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");
                registeredClients.add(new Client(id,userName, email, password));
            }

            return registeredClients;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., logging the error)
            return new ArrayList<>(); // Return an empty list if there's an error reading the JSON file
        }
    }
}
