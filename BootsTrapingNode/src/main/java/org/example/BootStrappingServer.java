    package org.example;

    import org.example.Admin.AdminLoginManager;
    import org.example.Admin.AdminRegistrationManager;
    import org.example.Client.ClientLoginManager;
    import org.example.Client.ClientRegistrationManager;
    import org.example.Redirect.RedirectManager;

    import java.io.IOException;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.util.ArrayList;
    import java.util.List;

    public class BootStrappingServer {
        private final ServerSocket bootStrappingServer;
        private final List<ClientHandler> clientHandlers;
        private final ClientRegistrationManager clientRegistrationManager;
        private final ClientLoginManager clientLoginManager;
        private final AdminRegistrationManager adminRegistrationManager;
        private final AdminLoginManager adminLoginManager;
        private final RedirectManager redirectManager;

        public BootStrappingServer() throws IOException {
            bootStrappingServer = new ServerSocket(1010);
            System.out.println("Server started. Listening on port " + 1010);
            clientHandlers = new ArrayList<>();
            clientRegistrationManager = new ClientRegistrationManager(); // Initialize the registrationManager
            clientLoginManager = new ClientLoginManager();
            adminRegistrationManager=new AdminRegistrationManager();
            adminLoginManager=new AdminLoginManager();
            redirectManager=new RedirectManager();
            startListening();
        }

        public void startListening() {
            while (true) {
                try {
                    Socket clientSocket = bootStrappingServer.accept();
                    System.out.println("Client connected");

                    // Create a new ClientHandler instance for each connected client
                    ClientHandler clientHandler = new ClientHandler(clientSocket, clientRegistrationManager, clientLoginManager,redirectManager,adminLoginManager,adminRegistrationManager);

                    // Add the client handler to the list
                    clientHandlers.add(clientHandler);

                    // Start the client handler in a separate thread
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

