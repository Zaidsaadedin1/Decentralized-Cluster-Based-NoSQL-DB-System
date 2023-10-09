package org.example.DataBase;

import org.example.NoSqlApplications.ApplicationOperationsManager;
import org.example.JasonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DatabaseOperationsManager {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final JasonManager jsonManager;
    private final ApplicationOperationsManager applicationOperationsManager;


    public DatabaseOperationsManager(BufferedReader reader, PrintWriter writer, ApplicationOperationsManager applicationOperationsManager, JasonManager jsonManager) {
        this.reader = reader;
        this.writer = writer;
        this.jsonManager = jsonManager;
        this.applicationOperationsManager = applicationOperationsManager;
    }


    public void createDatabase() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();


            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);
            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = selectDataBase();
                boolean result = jsonManager.addNewDatabase(currentApp, currentDatabase);
                writer.println(result);
                if (result) {
                    writer.println("Database '" + currentDatabase + "' created successfully.");
                } else {
                    writer.println("Database '" + currentDatabase + "' already exists ");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException e) {
            writer.println("Error while creating the database.");
            e.printStackTrace();
        }
    }

    public void deleteDatabase() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();

            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);
            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = selectDataBase();

                boolean result = jsonManager.removeDatabaseByName(currentApp, currentDatabase);
                writer.println(result);
                if (result) {
                    writer.println("Database '" + currentDatabase + "' removed successfully.");
                } else {
                    writer.println("Database '" + currentDatabase + "' not found ");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException e) {
            writer.println("Error while deleting the database.");
            e.printStackTrace();
        }
    }

    public void displayTablesInDatabase(String currentDatabase, String currentApp) throws IOException {

        writer.println("List of tables in the '" + currentDatabase + "' database:");
        List<String> tables = jsonManager.getAllTablesInDatabase(currentApp, currentDatabase);

        boolean valid = !tables.isEmpty();
        writer.println(valid);
        if (valid) {
            int size = tables.size();
            writer.println(size);

            for (String tableName : tables) {
                writer.println(tableName);
            }
        } else {
            writer.println(currentDatabase + " does not contain any tables.");
        }
    }

    public String selectDataBase() throws IOException {
        writer.println("Please select the database by entering the database name:");
        String currentDatabase = reader.readLine();
        return currentDatabase;
    }


}
