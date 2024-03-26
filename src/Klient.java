import java.net.*;
import java.io.*;

/**
 * A client class that connects to a server and communicates with it.
 */
public class Klient {

    /**
     * The main method that starts the client.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 4444);
            // Sending to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Receiving from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Console console = System.console();
            String text;

            do {
                text = console.readLine("Enter text: ");

                // Sending to the server
                out.println(text);
                // Receiving from the server
                System.out.println(in.readLine());

            } while (!text.equals("bye"));
            socket.close();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
