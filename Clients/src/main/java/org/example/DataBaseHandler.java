package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataBaseHandler {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Scanner scanner;
    private ApplicationHandler applicationHandler;

    public DataBaseHandler(BufferedReader reader, PrintWriter writer, Scanner scanner,ApplicationHandler applicationHandler) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = scanner;
        this.applicationHandler=applicationHandler;
    }

    public void handleCreateDatabase() throws IOException {
        String readerMessage;
        try {
            applicationHandler.handelListApplicationsNames();
            applicationHandler.handelSelectApplication();
            readerMessage=reader.readLine();
            if (readerMessage.equals("true")) {
                applicationHandler.handelDisplayDataBasesInApplication();
                handelSelectDataBase();
                readerMessage = reader.readLine();
                if (readerMessage.equals("true")) {
                    readerMessage = reader.readLine();
                    System.out.println(readerMessage);
                } else {
                    readerMessage = reader.readLine();
                    System.out.println(readerMessage);
                }
            }else {
                readerMessage=reader.readLine();
                System.out.println(readerMessage);
            }
        } catch (IOException e) {
            readerMessage = reader.readLine();
            System.out.println(readerMessage);
            throw new RuntimeException(e);
        }
    }

    public void handleDeleteDatabase() throws IOException {
        String reader1;
        try {
            applicationHandler.handelListApplicationsNames();
            applicationHandler.handelSelectApplication();
            reader1=reader.readLine();
            if (reader1.equals("true")) {
                applicationHandler.handelDisplayDataBasesInApplication();
                handelSelectDataBase();

                reader1 = reader.readLine();
                if (reader1.equals("true")) {
                    reader1 = reader.readLine();
                    System.out.println(reader1);
                } else {
                    reader1 = reader.readLine();
                    System.out.println(reader1);
                }
            }else {
                reader1 = reader.readLine();
                System.out.println(reader1);
            }
        } catch (IOException e) {
            try {
                reader1=reader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(reader1);
            throw new RuntimeException(e);
        }
    }

    public void handelDisplayTablesInDatabase() {
        String readerMessage;
        try {
            readerMessage = reader.readLine();
            System.out.println(readerMessage);

                String checkTableIfEmpty = reader.readLine();
                if (checkTableIfEmpty.equals("true")) {
                    int sizeOfTable = Integer.parseInt(reader.readLine());

                    for (int i = 0; i < sizeOfTable; i++) {
                        String tableName = reader.readLine();
                        System.out.println(tableName);
                    }
                } else {
                    readerMessage = reader.readLine();
                    System.out.println(readerMessage);
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handelSelectDataBase() throws IOException {
        String MessageReader=reader.readLine();
        System.out.println(MessageReader);
        String selectedDatabase=scanner.nextLine();
        writer.println(selectedDatabase);

    }
}
