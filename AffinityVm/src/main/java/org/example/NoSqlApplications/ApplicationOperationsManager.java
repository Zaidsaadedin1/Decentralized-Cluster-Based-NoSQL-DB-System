package org.example.NoSqlApplications;

import org.example.JasonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ApplicationOperationsManager {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final JasonManager jsonManager;

    public ApplicationOperationsManager(BufferedReader reader, PrintWriter writer, JasonManager jsonManager) {
        this.reader = reader;
        this.writer = writer;
        this.jsonManager = jsonManager;
    }


    public void createApplication() {
        try {
            listApplicationsNames();
            String currentApplication = selectApplication();

            boolean result = jsonManager.addNewApplication(currentApplication);
            writer.println(result);
            if (result) {
                writer.println("Application '" + currentApplication + "' created successfully.");
            } else {
                writer.println("Application '" + currentApplication + "' already exists ");
            }
        } catch (IOException e) {
            writer.println("Error while creating the Application.");
            e.printStackTrace();
        }
    }

    public void deleteApplication() {
        try {
            listApplicationsNames();
            String currentApplication = selectApplication();

            boolean result = jsonManager.removeApplicationByName(currentApplication);
            writer.println(result);
            if (result) {
                writer.println("Application '" + currentApplication + "' removed successfully.");
            } else {
                writer.println("Application '" + currentApplication + "' not found ");
            }
        } catch (IOException e) {
            writer.println("Error while deleting the Application.");
            e.printStackTrace();
        }
    }

    public void displayDataBasesInApplication(String appName) throws IOException {
        writer.println("List of DataBases in the '" + appName + "' Application:");
        List<String> databases = jsonManager.getAllDataBasesInApplication(appName);

        boolean valid = !databases.isEmpty();
        writer.println(valid);
        if (valid) {
            int size = databases.size();
            writer.println(size);

            for (String tableName : databases) {
                writer.println(tableName);
            }
        } else {
            writer.println(appName + " does not contain any DataBases.");
        }
    }

    public String selectApplication() throws IOException {
        writer.println("Please select the Application by entering the Application name:");
        String currentApplication = reader.readLine();
        return currentApplication;
    }

    public void listApplicationsNames() {
        List<Application> applicationNames = jsonManager.loadRegisteredDatabases();

        int size = applicationNames.size();
        writer.println(size);
        writer.println("List of Applications :");
        for (Application appName : applicationNames) {
            writer.println(appName.getAppName());
        }
    }


}


