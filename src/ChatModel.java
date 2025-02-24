import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ChatModel {

    File file;
    boolean fileStatus;
    String filePath;

    public void checkFile(String userName, String myName) {
        fileStatus = false;
        filePath = myName + "_" + userName + ".txt";
        file = new File(filePath);
        if (file.exists()) {
            fileStatus = true;
            return;
        }
        filePath = userName + "_" + myName + ".txt";
        file = new File(filePath);
        if (file.exists()) {
            fileStatus = true;
            return;
        }
        fileStatus = false;

    }

    public void sendMessage(String text, String myName) {
        if (fileStatus == true) {
            try {
                BufferedWriter fw = new BufferedWriter(new FileWriter(filePath, true));
                fw.write(myName + ":   " + text + "\n");
                fw.close();
                System.out.println("Successfully send message!");
            } catch (Exception e) {
                System.out.println("Unable to send message!\n");
            }
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(myName + ":   " + text + "\n");
                writer.close();
                System.out.println("Successfully send message!");
            } catch (Exception e) {
                System.out.println("Unable to send message!\n");
            }
        }
    }

    public void viewChatHistory() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String data;
            System.out.println("Chat history: ");
            while ((data = reader.readLine()) != null) {
                System.out.println(data);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Unable to view chat history!");
        }
    }

    public void deleteChatHistory() {
        if (file.delete()) {
            System.out.println("Successfully delete chat history!");
        } else {
            System.out.println("Unable to delete history");
        }
    }

    public String getFilePath(){
        return filePath;
    }
}
