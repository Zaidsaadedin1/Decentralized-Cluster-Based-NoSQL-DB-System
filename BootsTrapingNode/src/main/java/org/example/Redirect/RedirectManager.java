package org.example.Redirect;

import org.example.Client.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedirectManager {
    private final String jsonFilePath = "/app/config/registered_clients.json"; // Updated path inside the container
    //private final String jsonFilePath = "C:\\Users\\zaids\\Desktop\\Capson\\BootsTrapingNode\\registered_clients.json"; // Path to your JSON file

    ArrayList<String> vmIPs = new ArrayList<>(Arrays.asList("172.21.0.6:1011","172.21.0.3:1012","172.21.0.9:1013"));
    public String redirectClient(String username,String password) {
        int clientID = 0;
        List<Client> registeredClients = loadRegisteredClientsFromJson();

        for (Client client : registeredClients) {
            if (client.getUserName().equals(username) && client.getPassword().equals(password)) {
                clientID = (int) client.getId();
            }
        }
        int redirectIndex = clientID % vmIPs.size();
        String redirect =vmIPs.get(redirectIndex);
        return redirect;
    }

    private List<Client> loadRegisteredClientsFromJson() {
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
