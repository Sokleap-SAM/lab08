import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "192.168.242.159"; // Replace with your server's IP
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
            System.out.println("\r\n" + //
                    "                         /$$                                                     /$$                                                                   /$$                   /$$    \r\n"
                    + //
                    "                        | $$                                                    | $$                                                                  | $$                  | $$    \r\n"
                    + //
                    " /$$  /$$  /$$  /$$$$$$ | $$  /$$$$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$        /$$$$$$    /$$$$$$         /$$$$$$  /$$   /$$  /$$$$$$         /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$  \r\n"
                    + //
                    "| $$ | $$ | $$ /$$__  $$| $$ /$$_____/ /$$__  $$| $$_  $$_  $$ /$$__  $$      |_  $$_/   /$$__  $$       /$$__  $$| $$  | $$ /$$__  $$       /$$_____/| $$__  $$ |____  $$|_  $$_/  \r\n"
                    + //
                    "| $$ | $$ | $$| $$$$$$$$| $$| $$      | $$  \\ $$| $$ \\ $$ \\ $$| $$$$$$$$        | $$    | $$  \\ $$      | $$  \\ $$| $$  | $$| $$  \\__/      | $$      | $$  \\ $$  /$$$$$$$  | $$    \r\n"
                    + //
                    "| $$ | $$ | $$| $$_____/| $$| $$      | $$  | $$| $$ | $$ | $$| $$_____/        | $$ /$$| $$  | $$      | $$  | $$| $$  | $$| $$            | $$      | $$  | $$ /$$__  $$  | $$ /$$\r\n"
                    + //
                    "|  $$$$$/$$$$/|  $$$$$$$| $$|  $$$$$$$|  $$$$$$/| $$ | $$ | $$|  $$$$$$$        |  $$$$/|  $$$$$$/      |  $$$$$$/|  $$$$$$/| $$            |  $$$$$$$| $$  | $$|  $$$$$$$  |  $$$$/\r\n"
                    + //
                    " \\_____/\\___/  \\_______/|__/ \\_______/ \\______/ |__/ |__/ |__/ \\_______/         \\___/   \\______/        \\______/  \\______/ |__/             \\_______/|__/  |__/ \\_______/   \\___/  \r\n"
                    + //
                    "                                                                                                                                                                                    \r\n"
                    + //
                    "                                                                                                                                                                                    \r\n"
                    + //
                    "                                                                                                                                                                                    \r\n"
                    + //
                    "");
            // Display menu
            System.out.println("\n╔═══════════════════════╗");
            System.out.println("║       MAIN MENU       ║");
            System.out.println("╠═══════════════════════╣");
            System.out.println("║ 1.   Register         ║");
            System.out.println("║ 2.   Login            ║");
            System.out.println("║ 3.   Exit             ║");
            System.out.println("╚═══════════════════════╝");
            System.out.print("Option: ");
            if (scanner.hasNextInt()) {
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
                    System.out.println("\r\n" + //
                    " ________ __                            __                                                   ______                                              __                                                                                               __                             \r\n" + //
                    "/        /  |                          /  |                                                 /      \\                                            /  |                                                                                             /  |                            \r\n" + //
                    "$$$$$$$$/$$ |____    ______   _______  $$ |   __        __    __   ______   __    __       /$$$$$$  |_____    ______         __    __   _______ $$/  _______    ______          ______   __    __   ______          _______  __    __   _______ _$$ |_     ______   _____  ____  \r\n" + //
                    "   $$ |  $$      \\  /      \\ /       \\ $$ |  /  |      /  |  /  | /      \\ /  |  /  |      $$ |_ $$/      \\  /      \\       /  |  /  | /       |/  |/       \\  /      \\        /      \\ /  |  /  | /      \\        /       |/  |  /  | /       / $$   |   /      \\ /     \\/    \\ \r\n" + //
                    "   $$ |  $$$$$$$  | $$$$$$  |$$$$$$$  |$$ |_/$$/       $$ |  $$ |/$$$$$$  |$$ |  $$ |      $$   | /$$$$$$  |/$$$$$$  |      $$ |  $$ |/$$$$$$$/ $$ |$$$$$$$  |/$$$$$$  |      /$$$$$$  |$$ |  $$ |/$$$$$$  |      /$$$$$$$/ $$ |  $$ |/$$$$$$$/$$$$$$/   /$$$$$$  |$$$$$$ $$$$  |\r\n" + //
                    "   $$ |  $$ |  $$ | /    $$ |$$ |  $$ |$$   $$<        $$ |  $$ |$$ |  $$ |$$ |  $$ |      $$$$/  $$ |  $$ |$$ |  $$/       $$ |  $$ |$$      \\ $$ |$$ |  $$ |$$ |  $$ |      $$ |  $$ |$$ |  $$ |$$ |  $$/       $$      \\ $$ |  $$ |$$      \\  $$ | __ $$    $$ |$$ | $$ | $$ |\r\n" + //
                    "   $$ |  $$ |  $$ |/$$$$$$$ |$$ |  $$ |$$$$$$  \\       $$ \\__$$ |$$ \\__$$ |$$ \\__$$ |      $$ |   $$ \\__$$ |$$ |            $$ \\__$$ | $$$$$$  |$$ |$$ |  $$ |$$ \\__$$ |      $$ \\__$$ |$$ \\__$$ |$$ |             $$$$$$  |$$ \\__$$ | $$$$$$  | $$ |/  |$$$$$$$$/ $$ | $$ | $$ |\r\n" + //
                    "   $$ |  $$ |  $$ |$$    $$ |$$ |  $$ |$$ | $$  |      $$    $$ |$$    $$/ $$    $$/       $$ |   $$    $$/ $$ |            $$    $$/ /     $$/ $$ |$$ |  $$ |$$    $$ |      $$    $$/ $$    $$/ $$ |            /     $$/ $$    $$ |/     $$/  $$  $$/ $$       |$$ | $$ | $$ |\r\n" + //
                    "   $$/   $$/   $$/  $$$$$$$/ $$/   $$/ $$/   $$/        $$$$$$$ | $$$$$$/   $$$$$$/        $$/     $$$$$$/  $$/              $$$$$$/  $$$$$$$/  $$/ $$/   $$/  $$$$$$$ |       $$$$$$/   $$$$$$/  $$/             $$$$$$$/   $$$$$$$ |$$$$$$$/    $$$$/   $$$$$$$/ $$/  $$/  $$/ \r\n" + //
                    "                                                       /  \\__$$ |                                                                                             /  \\__$$ |                                                    /  \\__$$ |                                           \r\n" + //
                    "                                                       $$    $$/                                                                                              $$    $$/                                                     $$    $$/                                            \r\n" + //
                    "                                                        $$$$$$/                                                                                                $$$$$$/                                                       $$$$$$/                                             \r\n" + //
                    "");
                        System.exit(0);
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
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
                this.clientName = username.toLowerCase();
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║              HELP GUIDE               ║");
                System.out.println("╠═══════════════════════════════════════╣");
                System.out.println("║ Command              │ Description    ║");
                System.out.println("╠══════════════════════╪════════════════╣");
                System.out.println("║ /chat <user> <msg>   │ Send a message ║");
                System.out.println("║ /block <user>        │ Block a user   ║");
                System.out.println("║ /unblock <user>      │ Unblock a user ║");
                System.out.println("║ /history <user>      │ View history   ║");
                System.out.println("║ /help                │ Show this menu ║");
                System.out.println("║ /logout              │ back to menu   ║");
                System.out.println("╚═══════════════════════════════════════╝");
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
                    if(parts.length < 3){
                        System.out.println("Invalid command!");
                    }
                    else{
                        String recipient = parts[1];
                        String message = parts[2];
                        out.println("PRIVATE_MSG:" + recipient + ":" + message);
                    }
                } else if (input.startsWith("/block ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println("Invalid command!");
                    }
                    else{
                        String blockedClient = parts[1];
                        out.println("/block " + blockedClient);
                    }
                } else if (input.startsWith("/unblock ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println("Invalid command!");
                    }
                    else{
                        String unblockedClient = parts[1];
                        out.println("/unblock " + unblockedClient);
                    }
                } else if (input.startsWith("/history ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println("Invalid command!");
                    }
                    else{
                        String otherUser = parts[1];
                        out.println("/history " + otherUser);
                    }
                } else if (input.equals("/help")) {
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║              HELP GUIDE               ║");
                    System.out.println("╠═══════════════════════════════════════╣");
                    System.out.println("║ Command              │ Description    ║");
                    System.out.println("╠══════════════════════╪════════════════╣");
                    System.out.println("║ /chat <user> <msg>   │ Send a message ║");
                    System.out.println("║ /block <user>        │ Block a user   ║");
                    System.out.println("║ /unblock <user>      │ Unblock a user ║");
                    System.out.println("║ /history <user>      │ View history   ║");
                    System.out.println("║ /help                │ Show this menu ║");
                    System.out.println("║ /logout              │ back to menu   ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                } else if (input.equals("/logout")) {
                    System.out.println("Logging out...");
                    logout();
                    return;
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
                } else if (message.startsWith("CHAT_HISTORY:")) {
                    System.out.println("════════════════════════════════════════════");
                    System.out.println("Chat history:\n" + message.substring(13));
                    System.out.println("════════════════════════════════════════════");
                } 
            }
        } catch (IOException e) {
            System.out.println("Disconnected from the server.");
        }
    }

    private void logout() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Close the socket
            }
        } catch (IOException e) {
            System.out.println("Error closing the socket.");
        }
        start(); // Return to the main menu
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}