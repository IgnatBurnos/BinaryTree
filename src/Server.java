import java.io.*;
import java.net.*;

/**
 * A server class that handles client connections and performs operations on binary trees.
 */
public class Server {
    private BinaryTree<String> stringTree;
    private BinaryTree<Integer> integerTree;
    private BinaryTree<Double> doubleTree;

    /**
     * Constructs a new Server instance.
     * Initializes the binary trees for strings, integers, and doubles.
     */
    public Server() {
        stringTree = new BinaryTree<>();
        integerTree = new BinaryTree<>();
        doubleTree = new BinaryTree<>();
    }

    /**
     * Starts the server and listens on the specified port for client connections.
     *
     * @param port the port number to listen on
     */
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles communication with a client connected to the server.
     */
    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        /**
         * Constructs a new ClientHandler instance.
         *
         * @param socket the client socket
         */
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        /**
         * Runs the client handler thread.
         */
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client: " + inputLine);

                    String[] parts = inputLine.split(" ");
                    String operation = parts[0];

                    switch (operation) {
                        case "SEARCH":
                            handleSearch(parts[1], parts[2]);
                            break;
                        case "INSERT":
                            handleInsert(parts[1], parts[2]);
                            break;
                        case "DELETE":
                            handleDelete(parts[1], parts[2]);
                            break;
                        case "DRAW":
                            handleDraw(parts[1]);
                            break;
                        default:
                            out.println("Invalid operation");
                            break;
                    }
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Handles the SEARCH operation requested by the client.
         *
         * @param treeType the type of tree to search in (Integer, Double, or String)
         * @param value    the value to search for
         */
        private void handleSearch(String treeType, String value) {
            switch (treeType) {
                case "Integer":
                    boolean foundInt = integerTree.search(Integer.parseInt(value));
                    out.println(foundInt ? "Element found" : "Element not found");
                    break;
                case "Double":
                    boolean foundDouble = doubleTree.search(Double.parseDouble(value));
                    out.println(foundDouble ? "Element found" : "Element not found");
                    break;
                case "String":
                    boolean foundString = stringTree.search(value);
                    out.println(foundString ? "Element found" : "Element not found");
                    break;
                default:
                    out.println("Invalid tree type");
                    break;
            }
        }

        /**
         * Handles the INSERT operation requested by the client.
         *
         * @param treeType the type of tree to insert into (Integer, Double, or String)
         * @param value    the value to insert
         */
        private void handleInsert(String treeType, String value) {
            try {
                switch (treeType) {
                    case "Integer":
                        integerTree.insert(Integer.parseInt(value));
                        integerTree.draw();
                        out.println("Element inserted");
                        break;
                    case "Double":
                        doubleTree.insert(Double.parseDouble(value));
                        out.println("Element inserted");
                        break;
                    case "String":
                        stringTree.insert(value);
                        out.println("Element inserted");
                        break;
                    default:
                        out.println("Invalid tree type");
                        break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid value");
            }
        }

        /**
         * Handles the DELETE operation requested by the client.
         *
         * @param treeType the type of tree to delete from (Integer, Double, or String)
         * @param value    the value to delete
         */
        private void handleDelete(String treeType, String value) {
            try {
                switch (treeType) {
                    case "Integer":
                        integerTree.delete(Integer.parseInt(value));
                        out.println("Element deleted");
                        break;
                    case "Double":
                        doubleTree.delete(Double.parseDouble(value));
                        out.println("Element deleted");
                        break;
                    case "String":
                        stringTree.delete(value);
                        out.println("Element deleted");
                        break;
                    default:
                        out.println("Invalid tree type");
                        break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid value");
            }
        }

        /**
         * Handles the DRAW operation requested by the client.
         *
         * @param treeType the type of tree to draw (Integer, Double, or String)
         */
        private void handleDraw(String treeType) {
            switch (treeType) {
                case "Integer":
                    integerTree.draw();
                    break;
                case "Double":
                    doubleTree.draw();
                    break;
                case "String":
                    stringTree.draw();
                    break;
                default:
                    out.println("Invalid tree type");
                    break;
            }
            out.println("Draw completed");
        }
    }

    /**
     * The main method to start the server on the specified port.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start(4444);
    }
}
