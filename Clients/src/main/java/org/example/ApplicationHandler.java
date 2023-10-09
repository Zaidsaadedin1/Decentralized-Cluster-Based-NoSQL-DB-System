package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public class ApplicationHandler {
        private final BufferedReader reader;
        private final PrintWriter writer;
        private final Scanner scanner;

        public ApplicationHandler(BufferedReader reader, PrintWriter writer, Scanner scanner) {
            this.reader = reader;
            this.writer = writer;
            this.scanner = scanner;
        }

        public void handleCreateApplication() throws IOException {
            String readerMessage;
            try {
                handelListApplicationsNames();
                handelSelectApplication();

                readerMessage = reader.readLine();
                if (readerMessage.equals("true")) {
                    readerMessage = reader.readLine();
                    System.out.println(readerMessage);
                } else {
                    readerMessage = reader.readLine();
                    System.out.println(readerMessage);
                }

            } catch (IOException e) {
                readerMessage = reader.readLine();
                System.out.println(readerMessage);
                throw new RuntimeException(e);
            }
        }

        public void handleDeleteApplication() throws IOException {
            String reader1;
            try {
                handelListApplicationsNames();
                handelSelectApplication();

                reader1=reader.readLine();
                if (reader1.equals("true")) {
                    reader1=reader.readLine();
                    System.out.println(reader1);
                } else {
                    reader1=reader.readLine();
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

        public void handelDisplayDataBasesInApplication() {
            try {
                String readerMessage = reader.readLine();
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

        public void handelSelectApplication() throws IOException {
            String MessageReader=reader.readLine();
            System.out.println(MessageReader);
            String selectedDatabase=scanner.nextLine();
            writer.println(selectedDatabase);

        }


    public void handelListApplicationsNames() throws IOException {

        int listOfApplications= Integer.parseInt(reader.readLine());
        String MessageReader=reader.readLine();
        System.out.println(MessageReader);
        for (int i=0;i<listOfApplications;i++){
            MessageReader=reader.readLine();
            System.out.println(MessageReader);
        }

    }

    }


