import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatModel {

    File file;
    boolean fileStatus;
    String filePath;
    public void checkFile(String userName, String myName){
        fileStatus = false;
        filePath = myName + "_" + userName + ".txt";
        file = new File(filePath);
        if(file.exists()){
            fileStatus = true;
        }

    }

    public void sendMessage(String text){
        if(fileStatus == true){
            try {
                BufferedWriter fw = new BufferedWriter(new FileWriter(filePath, true)); 
                fw.write(text + "\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("Unable to send message!");
            }
        }
        else{
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false)); 
                writer.write(text + "\n");
                writer.close();
            } catch (Exception e) {
                System.out.println("Unable to send message!");
            }
        }
    }

    public void viewChatHistory(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String data;
            while((data = reader.readLine())!= null){
                System.out.println(data);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Unable to view chat history");
        }
    }

    public void deleteChatHistory(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to delete chat history!");
        }
    }
}
