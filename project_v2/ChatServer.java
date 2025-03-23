import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> clients = new ArrayList<>();
    private static Map<String, String> registeredUsers = new HashMap<>(); // Stores username and password
    private static final String USER_DATA_FILE = "UserData.txt";
    private static final String USER_STATUS_FILE = "UserStatus.csv";

    public static void main(String[] args) {
        loadUserData(); // Load existing user data
        loadUserStatus(); // Load existing user status

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load user data from file
    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    registeredUsers.put(parts[0].toLowerCase(), parts[1]); // Store usernames in lowercase
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user data found. Starting fresh.");
        }
    }

    // Save user data to file
    private static void saveUserData(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(username.toLowerCase() + ":" + password); // Save usernames in lowercase
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isUserBlocked(String username, String blockedUser) {
        try {
            File file = new File(USER_STATUS_FILE);
            if (!file.exists()) {
                return false; // File doesn't exist, so no blocks
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(username)) {
                        // Check if the blockedUser is in the list
                        for (int i = 1; i < parts.length; i++) {
                            if (parts[i].equals(blockedUser)) {
                                return true; // User is blocked
                            }
                        }
                        break; // No need to check further lines for this user
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // User is not blocked
    }

    // Register a new user
    public static boolean registerUser(String username, String password) {
        String lowercaseUsername = username.toLowerCase(); // Convert username to lowercase
        if (registeredUsers.containsKey(lowercaseUsername)) {
            return false; // Username already exists (case-insensitive)
        }
        registeredUsers.put(lowercaseUsername, password);
        saveUserData(lowercaseUsername, password); // Save user data to file
        return true;
    }

    // Login a user
    public static boolean loginUser(String username, String password) {
        String lowercaseUsername = username.toLowerCase(); // Convert username to lowercase
        return registeredUsers.containsKey(lowercaseUsername)
                && registeredUsers.get(lowercaseUsername).equals(password);
    }

    // Load user status from file
    private static void loadUserStatus() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_STATUS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0];
                    for (int i = 1; i < parts.length; i++) {
                        ClientHandler client = getClientByUsername(username);
                        if (client != null) {
                            client.addblockClient(parts[i]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user status found. Starting fresh.");
        }
    }

    // Save user status to file
    private static void saveUserStatus(String username, String blockedUser, boolean block) {
        try {
            File file = new File(USER_STATUS_FILE);
            List<String> lines = new ArrayList<>();

            // Read existing lines
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
            }

            // Update or add the line for the user
            boolean userFound = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(username)) {
                    if (block) {
                        // Add the blocked user
                        lines.set(i, lines.get(i) + "," + blockedUser);
                    } else {
                        // Remove the blocked user
                        List<String> blockedUsers = new ArrayList<>(Arrays.asList(parts));
                        blockedUsers.remove(blockedUser);
                        lines.set(i, String.join(",", blockedUsers));
                    }
                    userFound = true;
                    break;
                }
            }

            // If the user was not found, add a new line
            if (!userFound && block) {
                lines.add(username + "," + blockedUser);
            }

            // Write updated lines back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save chat history to file
    private static void saveChatHistory(String sender, String recipient, String message) {
        String fileName1 = sender.toLowerCase() + "_" + recipient.toLowerCase() + ".txt";
        String fileName2 = recipient.toLowerCase() + "_" + sender.toLowerCase() + ".txt";

        // Check which file exists
        File file1 = new File(fileName1);
        File file2 = new File(fileName2);

        try {
            if (file1.exists()) {
                // Append to file1
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1, true))) {
                    writer.write(sender + ": " + message);
                    writer.newLine();
                }
            } else if (file2.exists()) {
                // Append to file2
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2, true))) {
                    writer.write(sender + ": " + message);
                    writer.newLine();
                }
            } else {
                // Create a new file (file1)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1, true))) {
                    writer.write(sender + ": " + message);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving chat history.");
        }
    }

    // Get client by username
    private static ClientHandler getClientByUsername(String username) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(username)) {
                return client;
            }
        }
        return null;
    }

    // Broadcast client list to all clients
    public static void broadcastClientList() {
        StringBuilder clientList = new StringBuilder("CLIENT_LIST:");
        for (ClientHandler client : clients) {
            clientList.append(client.getClientName()).append(",");
        }
        String message = clientList.toString();

        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    // Send private message
    public static void sendPrivateMessage(String sender, String recipient, String message) {
        if (sender.equals(recipient)) {
            notifySelfMessageError(sender);
            return;
        }

        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipient)) {
                if (!client.isBlocked(sender)) {
                    client.sendMessage("PRIVATE_MSG:" + sender + ":" + message);
                    saveChatHistory(sender, recipient, message); // Save chat history
                } else {
                    notifyBlockedMessage(sender, recipient);
                }
                break;
            }
        }
    }

    // Block a client
    public static void blockClient(String blocker, String blocked) {
        if (blocker.equals(blocked)) {
            notifySelfBlockError(blocker);
            return;
        }
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(blocker)) {
                client.addblockClient(blocked);
                client.sendMessage("SUCCESS:You have blocked " + blocked);
                saveUserStatus(blocker, blocked, true); // Save user status
                break;
            }
        }
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(blocked)) {
                client.sendMessage("INFO:" + blocker + " has blocked you.");
                break;
            }
        }
        return;
    }

    // Unblock a client
    public static void unblockClient(String unblocker, String unblocked) {
        if (unblocker.equals(unblocked)) {
            notifySelfBlockError(unblocker);
            return;
        } else if (isUserBlocked(unblocker, unblocked)) {
            for (ClientHandler client : clients) {
                if (client.getClientName().equals(unblocker)) {
                    client.removeblockClient(unblocked);
                    client.sendMessage("SUCCESS:You have unblocked " + unblocked);
                    saveUserStatus(unblocker, unblocked, false); // Update user status
                    break;
                }
            }
            for (ClientHandler client : clients) {
                if (client.getClientName().equals(unblocked)) {
                    client.sendMessage("INFO:" + unblocker + " has unblocked you.");
                    break;
                }
            }
            return;
        }
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(unblocker)) {
                client.sendMessage("INFO:You didn't block" + unblocked + " or " + unblocked + " is not exist");
            }
        }
    }

    // Notify blocked message
    public static void notifyBlockedMessage(String sender, String recipient) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(sender)) {
                client.sendMessage("ERROR:You are blocked by " + recipient);
                break;
            }
        }
    }

    // Notify self-message error
    public static void notifySelfMessageError(String clientName) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(clientName)) {
                client.sendMessage("ERROR:You cannot send a message to yourself.");
                break;
            }
        }
    }

    // Notify self-block error
    public static void notifySelfBlockError(String clientName) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(clientName)) {
                client.sendMessage("ERROR:You cannot block/unblock yourself.");
                break;
            }
        }
    }

    // Remove a client
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastClientList();
    }

    public static String getChatHistory(String user1, String user2) {
        String fileName1 = user1.toLowerCase() + "_" + user2.toLowerCase() + ".txt";
        String fileName2 = user2.toLowerCase() + "_" + user1.toLowerCase() + ".txt";
        StringBuilder history = new StringBuilder();

        try {
            File file1 = new File(fileName1);
            File file2 = new File(fileName2);

            if (file1.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        history.append(line).append("\n");
                    }
                    reader.close();
                }
            } else if (file2.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file2))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        history.append(line).append("\n");
                    }
                    reader.close();
                }
            } else {
                history.append("No chat history found.");
            }
        } catch (IOException e) {
            history.append("Error reading chat history.");
        }

        return history.toString();
    }

    // Client handler class
    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;
        private Set<String> blockedClients = new HashSet<>();

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public String getClientName() {
            return clientName;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void addblockClient(String clientName) {
            blockedClients.add(clientName);
        }

        public void removeblockClient(String clientName) {
            blockedClients.remove(clientName);
        }

        public boolean isBlocked(String clientName) {
            return blockedClients.contains(clientName);
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Handle registration or login
                String authType = in.readLine();
                if (authType.equals("REGISTER")) {
                    String username = in.readLine();
                    String password = in.readLine();
                    if (registerUser(username, password)) {
                        out.println("SUCCESS:Registration successful. Please login.");
                    } else {
                        out.println("ERROR:Username already exists.");
                        return;
                    }
                } else if (authType.equals("LOGIN")) {
                    String username = in.readLine();
                    String password = in.readLine();
                    if (loginUser(username, password)) {
                        out.println("SUCCESS:Login successful.");
                        this.clientName = username;
                        System.out.println("Client " + clientName + " has joined.");
                        broadcastClientList();
                    } else {
                        out.println("ERROR:Invalid username or password.");
                        return;
                    }
                } else {
                    out.println("ERROR:Invalid authentication type.");
                    return;
                }

                // Handle chat commands
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("PRIVATE_MSG:")) {
                        String[] parts = inputLine.split(":", 3);
                        String recipient = parts[1];
                        String message = parts[2];
                        sendPrivateMessage(clientName, recipient, message);
                    } else if (inputLine.startsWith("/block ")) {
                        String[] parts = inputLine.split(" ", 2);
                        String blockedClient = parts[1];
                        blockClient(clientName, blockedClient);
                    } else if (inputLine.startsWith("/unblock ")) {
                        String[] parts = inputLine.split(" ", 2);
                        String unblockedClient = parts[1];
                        unblockClient(clientName, unblockedClient);
                    } else if (inputLine.startsWith("/history ")) {
                        String[] parts = inputLine.split(" ", 2);
                        String otherUser = parts[1];
                        String history = getChatHistory(clientName, otherUser);
                        out.println("CHAT_HISTORY:" + history);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                removeClient(this);
                System.out.println("Client " + clientName + " has left.");
            }
        }
    }
}