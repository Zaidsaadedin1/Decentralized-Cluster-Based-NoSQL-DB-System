    package org.example;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Scanner;

    public class TableHandler {
        private final BufferedReader reader;
        private final PrintWriter writer;
        private final Scanner scanner;
        private DataBaseHandler dataBaseHandler;
        private ApplicationHandler applicationHandler;

        public TableHandler(BufferedReader reader, PrintWriter writer, Scanner scanner,DataBaseHandler dataBaseHandler,ApplicationHandler applicationHandler) {
            this.reader = reader;
            this.writer = writer;
            this.scanner = scanner;
            this.dataBaseHandler=dataBaseHandler;
            this.applicationHandler=applicationHandler;
        }

        public void handleCreateTable() throws IOException {
            // Implement logic to create a table
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
                        selectTable();
                        String isTableValid = reader.readLine();
                        if (isTableValid.equals("false")) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        } else {
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
                messageReader = reader.readLine();
                System.out.println(messageReader);
                throw new RuntimeException(e);
            }
        }

        public void handleDeleteTable() throws IOException {
            // Implement logic to delete a table
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
                        selectTable();
                        String isTableValid = reader.readLine();
                        if (isTableValid.equals("true")) {
                            messageReader = reader.readLine();
                            System.out.println(messageReader);
                        } else {
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
                messageReader = reader.readLine();
                System.out.println(messageReader);
                throw new RuntimeException(e);
            }
        }

        public void selectTable() throws IOException {

            String messageReader = reader.readLine();
            System.out.println(messageReader);
            String selectedTable=scanner.nextLine();
            writer.println(selectedTable);
        }
    }
