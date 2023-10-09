package org.example.Table;

import org.example.DataBase.DatabaseOperationsManager;
import org.example.JasonManager;
import org.example.NoSqlApplications.ApplicationOperationsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TableOperationsManager {
    private BufferedReader reader;
    private PrintWriter writer;
    private JasonManager jsonManager;
    private DatabaseOperationsManager databaseOperationsManager;
    private ApplicationOperationsManager applicationOperationsManager;

    public TableOperationsManager(BufferedReader reader, PrintWriter writer, DatabaseOperationsManager databaseOperationsManager, ApplicationOperationsManager applicationOperationsManager, JasonManager jsonManager) {
        this.reader = reader;
        this.writer = writer;
        this.jsonManager = jsonManager;
        this.databaseOperationsManager = databaseOperationsManager;
        this.applicationOperationsManager = applicationOperationsManager;
    }

    public void createTable() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();
            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);

            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = databaseOperationsManager.selectDataBase();


                isUsed = jsonManager.isDatabaseNameUsed(currentApp, currentDatabase);
                writer.println(isUsed);
                if (isUsed) {
                    databaseOperationsManager.displayTablesInDatabase(currentDatabase, currentApp);
                    String tableName;
                    tableName = selectTable();
                    boolean valid = jsonManager.addTableToDatabase(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (valid) {
                        writer.println("Table '" + tableName + "' created successfully.");
                    } else {
                        writer.println("Failed to create table '" + tableName + "'. Table already Avalible.");
                    }
                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }

            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException e) {
            writer.println("Error while creating the table.");
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();
            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);

            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = databaseOperationsManager.selectDataBase();
                isUsed = jsonManager.isDatabaseNameUsed(currentApp, currentDatabase);
                writer.println(isUsed);

                if (isUsed) {
                    databaseOperationsManager.displayTablesInDatabase(currentDatabase, currentApp);
                    String tableName = selectTable();
                    boolean valid = jsonManager.deleteTableFromDatabase(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (!valid) {
                        writer.println("Table '" + tableName + "' not found.");
                    } else {
                        writer.println("Table '" + tableName + "' deleted successfully.");
                    }
                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException e) {
            writer.println("Error while deleting the table.");
            e.printStackTrace();
        }
    }

    public String selectTable() throws IOException {
        writer.println("Type the table name you want by entering the table name :");
        return reader.readLine();
    }


}
