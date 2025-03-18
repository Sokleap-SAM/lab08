import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "192.168.0.154"; // Replace with your server's IP
    private static final int PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ChatClient() {
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerUser(scanner);
                    break;
                case "2":
                    if (loginUser(scanner)) {
                        enterChat();
                        return; // Exit the menu and start chatting
                    }
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void registerUser(Scanner scanner) {
        try {
            socket = new Socket(SERVER_IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.print("Enter a username (will be converted to lowercase): ");
            String username = scanner.nextLine();
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            out.println("REGISTER");
            out.println(username);
            out.println(password);

            String response = in.readLine();
            if (response.startsWith("SUCCESS:")) {
                System.out.println(response.substring(8));
            } else if (response.startsWith("ERROR:")) {
                System.out.println(response.substring(6));
            }

            // Close the socket after registration
            socket.close();
        } catch (IOException e) {
            System.out.println("Error connecting to the server. Please try again.");
        }
    }

    private boolean loginUser(Scanner scanner) {
        try {
            socket = new Socket(SERVER_IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            out.println("LOGIN");
            out.println(username);
            out.println(password);

            String response = in.readLine();
            if (response.startsWith("SUCCESS:")) {
                System.out.println(response.substring(8));
                this.clientName = username.toLowerCase(); // Store username in lowercase
                return true; // Login successful
            } else if (response.startsWith("ERROR:")) {
                System.out.println(response.substring(6));
                // Close the socket if login fails
                socket.close();
                return false; // Login failed
            }
        } catch (IOException e) {
            System.out.println("Error connecting to the server. Please try again.");
        }
        return false;
    }

    private void enterChat() {
        try {
            // Start receiving messages in a separate thread
            new Thread(this::receiveMessages).start();

            // Handle chat commands
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                if (input.startsWith("/chat ")) {
                    String[] parts = input.split(" ", 3);
                    String recipient = parts[1];
                    String message = parts[2];
                    out.println("PRIVATE_MSG:" + recipient + ":" + message);
                } else if (input.startsWith("/block ")) {
                    String[] parts = input.split(" ", 2);
                    String blockedClient = parts[1];
                    out.println("/block " + blockedClient);
                } else if (input.startsWith("/unblock ")) {
                    String[] parts = input.split(" ", 2);
                    String unblockedClient = parts[1];
                    out.println("/unblock " + unblockedClient);
                } else if (input.startsWith("/history ")) {
                    String[] parts = input.split(" ", 2);
                    String otherUser = parts[1];
                    out.println("/history " + otherUser);
                } else if (input.equals("/help")) {
                    out.println("/help");
                } else {
                    System.out.println("Please use '/help' for more info!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in chat session. Disconnecting...");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing the socket.");
            }
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("CLIENT_LIST:")) {
                    System.out.println("Connected clients: " + message.substring(12));
                } else if (message.startsWith("PRIVATE_MSG:")) {
                    String[] parts = message.split(":", 3);
                    String sender = parts[1];
                    String msg = parts[2];
                    System.out.println("[Private from " + sender + "]: " + msg);
                } else if (message.startsWith("SUCCESS:")) {
                    System.out.println(message.substring(8));
                } else if (message.startsWith("INFO:")) {
                    System.out.println(message.substring(5));
                } else if (message.startsWith("ERROR:")) {
                    System.out.println(message.substring(6));
                }
            }
        } catch (IOException e) {
            System.out.println("Disconnected from the server.");
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}