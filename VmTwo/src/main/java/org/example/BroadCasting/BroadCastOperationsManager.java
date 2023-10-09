package org.example.BroadCasting;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.JasonManager;
import org.example.NoSqlApplications.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BroadCastOperationsManager {
    private BufferedReader reader;
    private PrintWriter writer;

    private JasonManager jasonManager;

    public BroadCastOperationsManager(BufferedReader reader, PrintWriter writer, JasonManager jasonManager) {
        this.reader = reader;
        this.writer = writer;
        this.jasonManager = jasonManager;
    }

    public void sendOperation() {
        List<Application> sentJson = jasonManager.getRegisteredApplications();
        Gson gson = new Gson();
        String json = gson.toJson(sentJson);
        writer.println(json);
    }


    public void receiveOperation() throws IOException {
        String json = reader.readLine();
        System.out.println(json);
        if (json != null && !json.isEmpty()) {
            try {
                Gson gson = new Gson();
                Type applicationListType = new TypeToken<ArrayList<Application>>() {}.getType();
                List<Application> receivedJson = gson.fromJson(json, applicationListType);
                jasonManager.setRegisteredApplications(receivedJson);
                jasonManager.saveRegisteredDatabases();
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing JSON: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
