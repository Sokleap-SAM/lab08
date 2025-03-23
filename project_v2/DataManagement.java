import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManagement {
    public static String getChatHistory(String user1, String user2) {
        String fileName1 = user1.toLowerCase() + "_" + user2.toLowerCase() + ".txt";
        String fileName2 = user2.toLowerCase() + "_" + user1.toLowerCase() + ".txt";
        StringBuilder history = new StringBuilder();

        try {
            File file1 = new File(fileName1);
            File file2 = new File(fileName2);
            if (file1.exists()) {
                // Read from file1
                try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        history.append(line + ":::");
                    }
                }
            } else if (file2.exists()) {
                // Read from file2
                try (BufferedReader reader = new BufferedReader(new FileReader(file2))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        history.append(line + ":::");
                    }
                }
            } else {
                history.append("INFO:No chat history found.");
            }
        } catch (IOException e) {
            history.append("ERROR:Error reading chat history.");
        }
        return history.toString();
    }

    public static void saveChatHistory(String sender, String recipient, String message) {
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

}
