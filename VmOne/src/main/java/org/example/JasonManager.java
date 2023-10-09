package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.DataBase.DataBase;
import org.example.Document.Document;
import org.example.NoSqlApplications.Application;
import org.example.Table.Table;
import java.io.*;
import java.util.*;

public class JasonManager {
    private final String jsonFilePath = "/app/config/DataBases.json";
    private List<Application> registeredApplications;

    public JasonManager() {
        try {
            registeredApplications = loadRegisteredDatabases(); // Initialize here
            if (registeredApplications == null) {
                registeredApplications = new ArrayList<>(); // Initialize if data loading failed
            }
        } catch (Exception e) {
            System.err.println("Error initializing JasonManager: " + e.getMessage());
            e.printStackTrace();
            registeredApplications = new ArrayList<>(); // Initialize if an exception occurs
        }
    }

    public List<Application> getRegisteredApplications() {
        return registeredApplications;
    }

    public void setRegisteredApplications(List<Application> registeredApplications) {
        this.registeredApplications = registeredApplications;
    }
    public List<Application> loadRegisteredDatabases() {
        try (Reader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            registeredApplications = gson.fromJson(reader, new TypeToken<List<Application>>() {}.getType());

            if (registeredApplications == null) {
                registeredApplications = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return registeredApplications;
    }

    public void saveRegisteredDatabases() {
        try {
            if (registeredApplications == null) {
                System.err.println("registeredDatabases is null.");
                return;
            }

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(registeredApplications);

            try (Writer writer = new FileWriter(jsonFilePath)) {
                writer.write(json);
            }

        } catch (IOException e) {
            System.err.println("Error saving databases to JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean addNewApplication(String appName) {
        // Check if the database name is already used
        if (isApplicationNameUsed(appName)) {
            return false;
        }

        Application application = new Application(appName);
        registeredApplications.add(application);
        saveRegisteredDatabases();
        return true;

    }

    public boolean removeApplicationByName(String appName) {
        if (!isApplicationNameUsed(appName)) {
            return false;
        }
        Application application = getApplicationByName(appName);
        registeredApplications.remove(application);
        saveRegisteredDatabases();
        return true;
    }

    public boolean isApplicationNameUsed(String appName) {
        return registeredApplications.stream()
                .filter(application -> application.getAppName() != null) // Filter out null database names
                .anyMatch(db -> db.getAppByName().equals(appName));
    }


    public Application getApplicationByName(String appName) {
        if (isApplicationNameUsed(appName)) {
            for (Application application : registeredApplications) {
                if (application.getAppByName().equals(appName)) return application;
            }
        }
        return null;
    }

    public List<String> getAllDataBasesInApplication(String appName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return new ArrayList<>();
        }
        List<String> dataBasesNames = new ArrayList<>();
        List<DataBase> dataBases = targetApp.getDataBases();

        for (DataBase dataBase : dataBases) {
            dataBasesNames.add(dataBase.getDatabaseName());
        }

        return dataBasesNames;
    }


    public boolean addNewDatabase(String appName, String databaseName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }

        DataBase targetDataBase = targetApp.getDataBaseByName(databaseName);
        if (targetDataBase != null) {
            return false;
        }

        targetApp.createDataBase(databaseName);
        saveRegisteredDatabases();
        return true;

    }

    public boolean removeDatabaseByName(String appName, String databaseName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }

        DataBase targetDataBase = targetApp.getDataBaseByName(databaseName);
        if (targetDataBase == null) {
            return false;
        }

        targetApp.deleteDataBase(databaseName);
        saveRegisteredDatabases();
        return true;
    }

    public boolean isDatabaseNameUsed(String appName, String databaseName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }

        DataBase targetDataBase = targetApp.getDataBaseByName(databaseName);
        return targetDataBase != null;
    }


    public boolean isDataBaseHaveTables(String appName, String databaseName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return false;
        }

        return !targetDatabase.getTables().isEmpty();
    }


    public boolean addTableToDatabase(String appName, String databaseName, String tableName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return false;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable != null) {
            return false;
        }

        targetDatabase.createTable(tableName);
        saveRegisteredDatabases();
        return true;
    }

    public boolean deleteTableFromDatabase(String appName, String databaseName, String tableName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return false;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable == null) {
            return false;
        }


        targetDatabase.deleteTable(tableName);
        saveRegisteredDatabases();
        return true;
    }


    public List<String> getAllTablesInDatabase(String appName, String databaseName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return new ArrayList<>();
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return new ArrayList<>();
        }

        if (targetDatabase.getTables() == null) {
            return new ArrayList<>(); // Return an empty list if the database is not found
        }

        List<String> tableNames = new ArrayList<>();
        List<Table> tables = targetDatabase.getTables();

        for (Table table : tables) {
            tableNames.add(table.getTableName());
        }

        return tableNames;
    }


    public boolean isTableAvailable(String appName, String databaseName, String tableName) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase != null) {
            Table table = targetDatabase.getTableByName(tableName);
            return table != null;
        }
        return false;
    }


    public void addDocumentToTable(String appName, String databaseName, String tableName, Map<String, String> documentData) throws IOException {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable == null) {
            return;
        }

        targetTable.addDocument(documentData);
        saveRegisteredDatabases();
    }

    public boolean deleteDocumentInTable(String appName, String databaseName, String tableName, int documentId) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return false;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return false;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable == null) {
            return false;
        }
        Document targetDocument = targetTable.getDocumentById(documentId);
        if (targetDocument == null) {
            return false;
        }
        targetTable.deleteDocument(documentId);
        saveRegisteredDatabases();
        return true;
    }


    public Map<Integer, Document> reedDocumentsInTable(String appName, String databaseName, String tableName) {
        Application targetApp = getApplicationByName(appName);
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        Table targetTable = targetDatabase.getTableByName(tableName);
        return targetTable.getAllDocuments();
    }

    public Document reedDocumentsInTableByID(String appName, String databaseName, String tableName, int id) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return null;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return null;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable == null) {
            return null;
        }

        return targetTable.getDocumentById(id);
    }


    public void modifyDocumentInTable(String appName, String databaseName, String tableName, int documentId, Map<String, String> newDocumentData) {
        Application targetApp = getApplicationByName(appName);
        if (targetApp == null) {
            return;
        }
        DataBase targetDatabase = targetApp.getDataBaseByName(databaseName);
        if (targetDatabase == null) {
            return;
        }
        Table targetTable = targetDatabase.getTableByName(tableName);
        if (targetTable == null) {
            return;
        }

        targetTable.modifyDocument(documentId, newDocumentData);
        saveRegisteredDatabases();
    }


}
