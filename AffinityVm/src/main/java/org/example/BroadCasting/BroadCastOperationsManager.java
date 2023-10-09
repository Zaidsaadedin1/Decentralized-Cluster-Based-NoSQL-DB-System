package org.example.BroadCasting;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.JasonManager;
import org.example.NoSqlApplications.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BroadCastOperationsManager {
    private final BufferedReader reader;
    private final JasonManager jasonManager;
    private List<ServerHandler> listOfClients; // List of connected clients(servers)

    public BroadCastOperationsManager(BufferedReader reader, JasonManager jasonManager) {
        this.reader = reader;
        this.jasonManager = jasonManager;
        this.listOfClients = new ArrayList<>();
    }

    public void setListOfClients(List<ServerHandler> listOfClients) {
        this.listOfClients = listOfClients;
    }

    public String handelSendOperation() throws IOException {
        System.out.println("received");
        return reader.readLine();
    }

    public synchronized void receiveOperation() throws IOException, InterruptedException {
        List<Application> oldData;
        String newJson;
        String json = handelSendOperation();
        Gson gson = new Gson();
        Type applicationListType = new TypeToken<ArrayList<Application>>() {}.getType();
        List<Application> receivedJson = gson.fromJson(json, applicationListType);
        if (json != null && !json.isEmpty() && !receivedJson.equals(jasonManager.getRegisteredApplications())) {
            try {
                Thread.sleep(200);
                // Synchronize access to the critical section across all clients using the shared lock

                // Merge the new data into the existing data, avoiding duplicates.
                oldData = jasonManager.getRegisteredApplications();
                int receivedDataSize = receivedJson.size();
                int oldDataSize = oldData.size();
                if (oldDataSize < receivedDataSize) {
                    for (Application app : receivedJson) {
                        if (!oldData.contains(app)) {
                            oldData.add(app);
                        }
                    }
                    System.out.println("data added");
                } else if (oldDataSize > receivedDataSize) {
                    oldData.removeIf(app -> !receivedJson.contains(app));
                    System.out.println("data removed");
                }
                jasonManager.setRegisteredApplications(oldData);
                jasonManager.saveRegisteredDatabases();
                newJson = gson.toJson(oldData);

                // Broadcast the updated JSON data to all connected clients (outside the synchronized block)
                broadcastJsonData(newJson);
                Thread.sleep(5000);
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing JSON: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Broadcast JSON data to all connected clients
    private void broadcastJsonData(String jsonData) {

        if (listOfClients != null) {
            for (ServerHandler client : listOfClients) {
                if (client != null && client.getWriter() != null) {
                    client.getWriter().println(jsonData);
                }
            }
        }
        System.out.println(jsonData);
    }
}

