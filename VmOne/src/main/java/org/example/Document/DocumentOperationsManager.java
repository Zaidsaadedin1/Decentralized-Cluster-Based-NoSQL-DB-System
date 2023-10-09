package org.example.Document;

import org.example.NoSqlApplications.ApplicationOperationsManager;
import org.example.DataBase.DatabaseOperationsManager;
import org.example.JasonManager;
import org.example.Table.TableOperationsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DocumentOperationsManager {
    private BufferedReader reader;
    private PrintWriter writer;
    private JasonManager jsonManager;
    private DatabaseOperationsManager databaseOperationsManager;
    private TableOperationsManager tableOperationsManager;
    private ApplicationOperationsManager applicationOperationsManager;


    public DocumentOperationsManager(BufferedReader reader, PrintWriter writer, ApplicationOperationsManager applicationOperationsManager, DatabaseOperationsManager databaseOperationsManager, TableOperationsManager tableOperationsManager, JasonManager jasonManager) {
        this.reader = reader;
        this.writer = writer;
        this.jsonManager = jasonManager;
        this.databaseOperationsManager = databaseOperationsManager;
        this.tableOperationsManager = tableOperationsManager;
        this.applicationOperationsManager = applicationOperationsManager;

    }


    public void addDocument() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();
            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);
            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = databaseOperationsManager.selectDataBase();
                isUsed = jsonManager.isDatabaseNameUsed(currentApp, currentDatabase) && jsonManager.isDataBaseHaveTables(currentApp, currentDatabase);
                writer.println(isUsed);
                if (isUsed) {
                    databaseOperationsManager.displayTablesInDatabase(currentDatabase, currentApp);
                    String tableName = tableOperationsManager.selectTable();
                    boolean valid = jsonManager.isTableAvailable(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (!valid) {
                        writer.println("Table '" + tableName + "' not found.");
                    } else {
                        displayAllDocumentsInTable(currentApp, currentDatabase, tableName);
                        writer.println("How many fields (columns) would you like your document to have? Enter a number:");
                        int numberOfFields;
                        try {
                            numberOfFields = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException e) {
                            writer.println("Invalid input. Please enter a valid integer for the number of fields.");
                            return; // Return from the method to avoid proceeding with invalid input.
                        }
                        Map<String, String> document = new HashMap<>();
                        for (int i = 0; i < numberOfFields; i++) {
                            writer.println("Document field number :" + (i + 1));
                            writer.println("Please choose the column name for field :");
                            String columnName = reader.readLine();

                            writer.println("Please type the value you want to insert into " + columnName + ":");
                            String value = reader.readLine();
                            document.put(columnName, value);
                        }
                        jsonManager.addDocumentToTable(currentApp, currentDatabase, tableName, document);
                        writer.println("Document added successfully");
                    }
                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void readDocument() {
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
                    String tableName = tableOperationsManager.selectTable();
                    boolean valid = jsonManager.isTableAvailable(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (!valid) {
                        writer.println("Table '" + tableName + "' not found.");
                    } else {
                        choseHowToDisplayDocumentsInTable(currentApp, currentDatabase, tableName);
                    }
                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteDocument() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();
            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);
            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = databaseOperationsManager.selectDataBase();
                isUsed = jsonManager.isDatabaseNameUsed(currentApp, currentDatabase) && jsonManager.isDataBaseHaveTables(currentApp, currentDatabase);
                writer.println(isUsed);
                if (isUsed) {
                    databaseOperationsManager.displayTablesInDatabase(currentDatabase, currentApp);
                    String tableName = tableOperationsManager.selectTable();
                    boolean valid = jsonManager.isTableAvailable(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (!valid) {
                        writer.println("Table '" + tableName + "' not found.");
                    } else {
                        choseHowToDisplayDocumentsInTable(currentApp, currentDatabase, tableName);
                        writer.println("Please chose the document id you would like to delete");
                        int documentID;
                        try {
                            documentID = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException e) {
                            writer.println("Invalid input. Please enter a valid integer for the number of fields.");
                            return; // Return from the method to avoid proceeding with invalid input.
                        }
                        valid = jsonManager.deleteDocumentInTable(currentApp, currentDatabase, tableName, documentID);
                        writer.println(valid);
                        if (valid) {
                            writer.println("Document with ID " + documentID + " has been deleted successfully");
                        } else {
                            writer.println("failed to delete document with the ID :" + documentID);
                        }
                    }

                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }
            } else {
                writer.println("No APP found with this name");
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateDocument() {
        try {
            applicationOperationsManager.listApplicationsNames();
            String currentApp = applicationOperationsManager.selectApplication();
            boolean isUsed = jsonManager.isApplicationNameUsed(currentApp);
            writer.println(isUsed);
            if (isUsed) {
                applicationOperationsManager.displayDataBasesInApplication(currentApp);
                String currentDatabase = databaseOperationsManager.selectDataBase();
                isUsed = jsonManager.isDatabaseNameUsed(currentApp, currentDatabase) && jsonManager.isDataBaseHaveTables(currentApp, currentDatabase);
                writer.println(isUsed);
                if (isUsed) {
                    databaseOperationsManager.displayTablesInDatabase(currentDatabase, currentApp);
                    String tableName = tableOperationsManager.selectTable();
                    boolean valid = jsonManager.isTableAvailable(currentApp, currentDatabase, tableName);
                    writer.println(valid);
                    if (!valid) {
                        writer.println("Table '" + tableName + "' not found.");
                    } else {
                        choseHowToDisplayDocumentsInTable(currentApp, currentDatabase, tableName);
                        writer.println("PLease chose the Document that you want to update by entering its ID number:");
                        int documentID;
                        try {
                            documentID = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException e) {
                            writer.println("Invalid input. Please enter a valid integer for the number of fields.");
                            return; // Return from the method to avoid proceeding with invalid input.
                        }
                        Document checkDocument = jsonManager.reedDocumentsInTableByID(currentApp, currentDatabase, tableName, documentID);
                        boolean isDocumentAvailable = checkDocument != null;
                        writer.println(isDocumentAvailable);
                        if (isDocumentAvailable) {
                            synchronized (checkDocument) {
                                Map<String, String> document = new HashMap<>();
                                writer.println("How many fields (columns) would you like your document to have? Enter a number:");
                                int numberOfFields = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < numberOfFields; i++) {
                                    writer.println("Document field number :" + (i + 1));
                                    writer.println("Please choose the column name for field :");
                                    String columnName = reader.readLine();

                                    writer.println("Please type the value you want to insert into " + columnName + ":");
                                    String value = reader.readLine();
                                    document.put(columnName, value);
                                }
                                jsonManager.modifyDocumentInTable(currentApp, currentDatabase, tableName, documentID, document);
                                writer.println("Document has been Modified successfully");
                            }
                        } else {
                            writer.println("there is no document found with this id ");
                        }
                    }
                } else {
                    writer.println("DataBase with this name not found please type a dataBase that appear in the list!");
                }
            } else {
                writer.println("No APP found with this name");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void choseHowToDisplayDocumentsInTable(String appName, String databaseName, String tableName) throws IOException {
        String userChoice = getUserChoice();

        if (userChoice.equals("Y")) {
            displayDocumentById(appName, databaseName, tableName);
        } else if (userChoice.equals("N")) {
            displayAllDocumentsInTable(appName, databaseName, tableName);
        } else {
            writer.println("Wrong choice. Please choose (Y) for YES or (N) for NO.");
        }

    }

    private String getUserChoice() throws IOException {
        writer.println("Would you like to read documents by specific ID ? (Y/N)");
        String UserChoice = reader.readLine();
        return UserChoice;
    }

    private void displayDocumentById(String appName, String databaseName, String tableName) throws IOException {
        writer.println("Please enter the Document id you would like to search for:");
        int documentId = Integer.parseInt(reader.readLine());
        Document document = jsonManager.reedDocumentsInTableByID(appName, databaseName, tableName, documentId);
        boolean isEmpty = document == null;
        writer.println(isEmpty);
        if (isEmpty) {
            writer.println("There is no document with this id number");
        } else {
            writer.println(document.getKeys());
            writer.println(document.getValues());
        }

    }

    private void displayAllDocumentsInTable(String appName, String databaseName, String tableName) {
        // Retrieve all documents from the table
        List<String> Documents = jsonManager.getAllTablesInDatabase(appName, databaseName);
        boolean isTableHasDocuments = Documents.isEmpty();
        writer.println(isTableHasDocuments);
        if (isTableHasDocuments) {
            writer.println("this table dose not contain any documents");
        } else {
            Map<Integer, Document> documents = jsonManager.reedDocumentsInTable(appName, databaseName, tableName);
            displayDocuments(tableName, documents);
        }
    }

    private void displayDocuments(String tableName, Map<Integer, Document> documents) {
        writer.println("List of documents in the '" + tableName + "' table:");
        int size = documents.size();
        writer.println(size);

        for (Map.Entry<Integer, Document> entry : documents.entrySet()) {
            int documentId = entry.getKey();
            Document document = entry.getValue();

            writer.println("--------------------------------------------------------------------------");
            writer.println("Document ID: " + documentId);

            // Assuming that getKeys() and getValues() are methods in your Document class
            String keys = document.getKeys();
            String values = document.getValues();

            writer.println("Document Fields: " + keys);
            writer.println("Document Fields Values: " + values);
            writer.println("--------------------------------------------------------------------------");
        }
    }

}




