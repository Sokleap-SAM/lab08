import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "192.168.204.48"; // Replace with your server's IP
    private static final int PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    String resetColorCode = "\u001B[0m";
    String yellowColorCode = "\u001B[33m";
    String blueColorCode = "\u001B[34m";
    String pinkColorCode = "\u001B[35m";
    String redColorCode = "\u001B[31m";
    String greenColorCode = "\u001B[32m";

    public ChatClient() {

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
    System.out.println(pinkColorCode + 
        "                         /$$                                                     /$$                                                                   /$$                   /$$    \r\n"
        + "                        | $$                                                    | $$                                                                  | $$                  | $$    \r\n"
        + " /$$  /$$  /$$  /$$$$$$ | $$  /$$$$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$        /$$$$$$    /$$$$$$         /$$$$$$  /$$   /$$  /$$$$$$         /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$  \r\n"
        + "| $$ | $$ | $$ /$$__  $$| $$ /$$_____/ /$$__  $$| $$_  $$_  $$ /$$__  $$      |_  $$_/   /$$__  $$       /$$__  $$| $$  | $$ /$$__  $$       /$$_____/| $$__  $$ |____  $$|_  $$_/  \r\n"
        + "| $$ | $$ | $$| $$$$$$$$| $$| $$      | $$  \\ $$| $$ \\ $$ \\ $$| $$$$$$$$        | $$    | $$  \\ $$      | $$  \\ $$| $$  | $$| $$  \\__/      | $$      | $$  \\ $$  /$$$$$$$  | $$    \r\n"
        + "| $$ | $$ | $$| $$_____/| $$| $$      | $$  | $$| $$ | $$ | $$| $$_____/        | $$ /$$| $$  | $$      | $$  | $$| $$  | $$| $$            | $$      | $$  | $$ /$$__  $$  | $$ /$$\r\n"
        + "|  $$$$$/$$$$/|  $$$$$$$| $$|  $$$$$$$|  $$$$$$/| $$ | $$ | $$|  $$$$$$$        |  $$$$/|  $$$$$$/      |  $$$$$$/|  $$$$$$/| $$            |  $$$$$$$| $$  | $$|  $$$$$$$  |  $$$$/\r\n"
        + " \\_____/\\___/  \\_______/|__/ \\_______/ \\______/ |__/ |__/ |__/ \\_______/         \\___/   \\______/        \\______/  \\______/ |__/             \\_______/|__/  |__/ \\_______/   \\___/  \r\n"
        + "                                                                                                                                                                                    \r\n"
        + resetColorCode);
            // Display menu
            System.out.println(blueColorCode + "\n╔═══════════════════════╗");
            System.out.println("║       MAIN MENU       ║");
            System.out.println("╠═══════════════════════╣");
            System.out.println("║ 1.   Register         ║");
            System.out.println("║ 2.   Login            ║");
            System.out.println("║ 3.   Exit             ║");
            System.out.println("╚═══════════════════════╝" + resetColorCode);
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
                        }
                        break;
                    case "3":
                    System.out.println(pinkColorCode +
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
                    "" + resetColorCode);
                        System.exit(0);
                        return;
                    default:
                        System.out.println(redColorCode + "Invalid option. Please try again!" + resetColorCode);
                        break;
                }
            } else {
                System.out.println(redColorCode + "Invalid input! Please enter a number!" + resetColorCode);
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
                System.out.println(greenColorCode + response.substring(8));
            } else if (response.startsWith("ERROR:")) {
                System.out.println(resetColorCode + response.substring(6) + resetColorCode);
            }

            // Close the socket after registration
            socket.close();
        } catch (IOException e) {
            System.out.println(resetColorCode + "Error connecting to the server! Please try again!" +  resetColorCode);
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
                System.out.println(greenColorCode + response.substring(8) + resetColorCode);
                System.out.println(blueColorCode+"╔═══════════════════════════════════════╗");
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
                System.out.println("╚═══════════════════════════════════════╝" + resetColorCode);
                return true; // Login successful
            } else if (response.startsWith("ERROR:")) {
                System.out.println(redColorCode + response.substring(6) + redColorCode);
                // Close the socket if login fails
                socket.close();
                return false; 
            }
        } catch (IOException e) {
            System.out.println(redColorCode + "Error connecting to the server. Please try again." + resetColorCode);
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
                if (input.startsWith("/chat")) {
                    String[] parts = input.split(" ", 3);
                    if(parts.length < 3){
                        System.out.println(yellowColorCode + "Invalid command! Please use '/help' for more info!" + resetColorCode);
                    }
                    else{
                        String recipient = parts[1];
                        String message = parts[2];
                        out.println("/chat:" + recipient + ":" + message);
                    }
                } else if (input.startsWith("/block ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println(yellowColorCode + "Invalid command! Please use '/help' for more info!" + resetColorCode);
                    }
                    else{
                        String blockedClient = parts[1];
                        out.println("/block:" + blockedClient);
                    }
                } else if (input.startsWith("/unblock ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println(yellowColorCode + "Invalid command! Please use '/help' for more info!" + resetColorCode);
                    }
                    else{
                        String unblockedClient = parts[1];
                        out.println("/unblock:" + unblockedClient);
                    }
                } else if (input.startsWith("/history ")) {
                    String[] parts = input.split(" ", 2);
                    if(parts.length < 2){
                        System.out.println(yellowColorCode + "Invalid command! Please use '/help' for more info!" + resetColorCode);
                    }
                    else{
                        String otherUser = parts[1];
                        out.println("/history:" + otherUser);
                    }
                } else if (input.equals("/help")) {
                    System.out.println(blueColorCode+"╔═══════════════════════════════════════╗");
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
                    System.out.println("╚═══════════════════════════════════════╝"+ resetColorCode);
                } else if (input.equals("/logout")) {
                    System.out.println(blueColorCode + "Logging out..." + resetColorCode);
                    socket.close();
                    return;
                } else {
                    System.out.println(yellowColorCode + "Invalid command! Please use '/help' for more info!" + resetColorCode);
                }
            }
        } catch (Exception e) {
            System.out.println(redColorCode + "Error in chat session. Disconnecting..." + resetColorCode);
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
                    System.out.println(pinkColorCode + "[Private from " + sender + "]: " + msg + resetColorCode);
                } else if (message.startsWith("SUCCESS:")) {
                    System.out.println(greenColorCode + "Info: " + message.substring(8) + " Successfully" + resetColorCode);
                } else if (message.startsWith("INFO:")) {
                    System.out.println(yellowColorCode + "Info: " + message.substring(5) + resetColorCode);
                } else if (message.startsWith("ERROR:")) {
                    System.out.println(redColorCode + "Error: " + message.substring(6));
                } else if (message.startsWith("CHAT_HISTORY:")) {
                    if(message.substring(13).startsWith("INFO")){
                        System.out.println("Info: " + message.substring(17));
                    }
                    System.out.println(blueColorCode +"════════════════════════════════════════════"+ resetColorCode);
                    System.out.println(pinkColorCode + "Chat history:" + pinkColorCode);
                    
                    String[] parts = message.substring(13).split(":::");
                    for(int i = 0; i < parts.length; i++){
                        System.out.println(parts[i]);
                    }
                    System.out.println(blueColorCode +"════════════════════════════════════════════"+ resetColorCode);
                } 
            }
        } catch (IOException e) {
            System.out.println(redColorCode + "Disconnected from the server." + resetColorCode);
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}