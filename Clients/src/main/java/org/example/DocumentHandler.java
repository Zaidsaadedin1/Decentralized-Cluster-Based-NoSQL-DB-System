package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DocumentHandler {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Scanner scanner;
    private DataBaseHandler dataBaseHandler;
    private TableHandler tableHandler;
    private ApplicationHandler applicationHandler;

    public DocumentHandler(BufferedReader reader, PrintWriter writer, Scanner scanner,DataBaseHandler dataBaseHandler,TableHandler tableHandler,ApplicationHandler applicationHandler) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
        this.dataBaseHandler=dataBaseHandler;
        this.tableHandler=tableHandler;
        this.applicationHandler=applicationHandler;
    }
    public void handleAddDocument() throws IOException {
        String messageReader;
        try {
            applicationHandler.handelListApplicationsNames();
            applicationHandler.handelSelectApplication();
            messageReader=reader.readLine();
            if (messageReader.equals("true")) {
                applicationHandler.handelDisplayDataBasesInApplication();
                dataBaseHandler.handelSelectDataBase();
                String isUsed = reader.readLine();
                if (isUsed.equals("true")) {
                    dataBaseHandler.handelDisplayTablesInDatabase();
                    tableHandler.selectTable();
                    String isTableValid = reader.readLine();
                    if (isTableValid.equals("false")) {
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    } else {
                        displayAllDocumentsInTable();

                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                        int numberOfFields;
                        try {
                            numberOfFields = Integer.parseInt(scanner.nextLine());
                            writer.println(numberOfFields);
                        } catch (NumberFormatException e) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            return; // Return from the method to avoid proceeding with invalid input.
                        }



                        for (int i = 0; i < numberOfFields; i++) {

                            messageReader = reader.readLine();
                            System.out.println(messageReader);

                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            String columnName = scanner.nextLine();
                            writer.println(columnName);

                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            String columnValue = scanner.nextLine();
                            writer.println(columnValue);
                        }
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    }
                } else {
                    messageReader = reader.readLine();
                    System.out.println(messageReader);
                }
            }else {
                messageReader = reader.readLine();
                System.out.println(messageReader);
            }

        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }



    public void handleReadDocument() throws IOException {
            String messageReader;
            try {
                applicationHandler.handelListApplicationsNames();
                applicationHandler.handelSelectApplication();
                messageReader=reader.readLine();
                if (messageReader.equals("true")) {
                    applicationHandler.handelDisplayDataBasesInApplication();
                    dataBaseHandler.handelSelectDataBase();
                    String isUsed = reader.readLine();
                    if (isUsed.equals("true")) {
                        dataBaseHandler.handelDisplayTablesInDatabase();
                        tableHandler.selectTable();
                        String isTableValid = reader.readLine();
                        if (isTableValid.equals("false")) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        } else {
                            handelChoseHowToDisplayDocumentsInTable();
                        }
                    } else {
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    }
                }else {
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleUpdateDocument() throws IOException {
        String messageReader;
        try {
            applicationHandler.handelListApplicationsNames();
            applicationHandler.handelSelectApplication();
            messageReader=reader.readLine();
            if (messageReader.equals("true")) {
                applicationHandler.handelDisplayDataBasesInApplication();
                dataBaseHandler.handelSelectDataBase();
                String isUsed = reader.readLine();
                if (isUsed.equals("true")) {
                    dataBaseHandler.handelDisplayTablesInDatabase();
                    tableHandler.selectTable();
                    String isTableValid = reader.readLine();
                    if (isTableValid.equals("false")) {
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    } else {
                        handelChoseHowToDisplayDocumentsInTable();
                        messageReader = reader.readLine();
                        System.out.println(messageReader);

                        int documentID;
                        try {
                            documentID = Integer.parseInt(scanner.nextLine());
                            writer.println(documentID);
                        } catch (NumberFormatException e) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            return; // Return from the method to avoid proceeding with invalid input.
                        }


                        String isDocumentAvailable = reader.readLine();
                        if (isDocumentAvailable.equals("true")) {


                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            String numberOfFields = scanner.nextLine();
                            writer.println(numberOfFields);


                            for (int i = 0; i < Integer.parseInt(numberOfFields); i++) {
                                messageReader = reader.readLine();
                                System.out.println(messageReader);
                                messageReader = reader.readLine();
                                System.out.println(messageReader);
                                String columnName = scanner.nextLine();
                                writer.println(columnName);

                                messageReader = reader.readLine();
                                System.out.println(messageReader);

                                String columnValue = scanner.nextLine();
                                writer.println(columnValue);
                            }
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        } else {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        }
                    }
                } else {
                    messageReader = reader.readLine();
                    System.out.println(messageReader);
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void handleDeleteDocument() throws IOException {
        String messageReader;
        try {
            applicationHandler.handelListApplicationsNames();
            applicationHandler.handelSelectApplication();
            messageReader=reader.readLine();
            if (messageReader.equals("true")) {
                applicationHandler.handelDisplayDataBasesInApplication();
                dataBaseHandler.handelSelectDataBase();
                String isUsed = reader.readLine();
                if (isUsed.equals("true")) {
                    dataBaseHandler.handelDisplayTablesInDatabase();
                    tableHandler.selectTable();
                    String isTableValid = reader.readLine();
                    if (isTableValid.equals("false")) {
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                    } else {
                        handelChoseHowToDisplayDocumentsInTable();
                        messageReader = reader.readLine();
                        System.out.println(messageReader);
                        int documentID;
                        try {
                            documentID = Integer.parseInt(scanner.nextLine());
                            writer.println(documentID);
                        } catch (NumberFormatException e) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                            return; // Return from the method to avoid proceeding with invalid input.
                        }

                        String isDocumentDeleted = reader.readLine();
                        if (isDocumentDeleted.equals("true")) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        } else {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        }
                    }
                } else {
                    messageReader = reader.readLine();
                    System.out.println(messageReader);
                }
            }else {
                messageReader = reader.readLine();
                System.out.println(messageReader);
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    public void handelChoseHowToDisplayDocumentsInTable() throws IOException {
        String userChoice = handelGetUserChoice();

        if (userChoice.equals("Y")){
            handelDisplayDocumentsByID();
        }else if (userChoice.equals("N")){
            displayAllDocumentsInTable();
        }else{
            String messageReader = reader.readLine();
            System.out.println(messageReader);
        }
    }
    private String handelGetUserChoice() throws IOException {
        String messageReader = reader.readLine();
        System.out.println(messageReader);
        String userChoice=scanner.nextLine();
        writer.println(userChoice);
        return userChoice;
    }
    private void handelDisplayDocumentsByID() throws IOException {
        String messageReader = reader.readLine();
        System.out.println(messageReader);
        String documentId=scanner.nextLine();
        writer.println(documentId);

        String isDocumentEmpty=reader.readLine();
        if (isDocumentEmpty.equals("true")){
            messageReader = reader.readLine();
            System.out.println(messageReader);
        }else {
            messageReader = reader.readLine();
            System.out.println(messageReader);
            messageReader = reader.readLine();
            System.out.println(messageReader);
        }
    }

    private void displayAllDocumentsInTable() throws IOException {
        String messageReader;
        String checkIfDocumentIsEmpty=reader.readLine();
        if (checkIfDocumentIsEmpty.equals("true")){
            messageReader = reader.readLine();
            System.out.println(messageReader);
        }else {
          handelDisplayDocuments();
        }
    }
    private void handelDisplayDocuments() throws IOException {
        String messageReader = reader.readLine();
        System.out.println(messageReader);
            int sizeOfDocumentsInTable = Integer.parseInt(reader.readLine());
            for (int i=0;i<sizeOfDocumentsInTable;i++){
                messageReader = reader.readLine();
                System.out.println(messageReader);
                messageReader = reader.readLine();
                System.out.println(messageReader);
                messageReader = reader.readLine();
                System.out.println(messageReader);
                messageReader = reader.readLine();
                System.out.println(messageReader);
                messageReader = reader.readLine();
                System.out.println(messageReader);
            }
        }

    }

