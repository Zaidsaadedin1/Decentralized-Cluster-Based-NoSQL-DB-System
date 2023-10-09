package org.example.Admin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AdminLoginManager {
    private final String jsonFilePath = "/app/config/registered_admins.json"; // Updated path inside the container

    public boolean verifyLogin(String username, String password) {
        List<Admin> registeredAdmins = loadRegisteredAdminsFromJson();

        for (Admin admin : registeredAdmins) {
            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
                return true; // Login successful
            }
        }

        return false; // Login failed
    }

    private List<Admin> loadRegisteredAdminsFromJson() {
        try (Reader reader = new FileReader(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);
            List<Admin> registeredAdmins = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String userName = jsonObject.getString("userName");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");
                registeredAdmins.add(new Admin(id, userName, email, password));
            }
            return registeredAdmins;
        } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception appropriately (e.g., logging the error)
        return new ArrayList<>(); // Return an empty list if there's an error reading the JSON file
    }
    }
}
