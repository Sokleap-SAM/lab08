import java.util.Scanner;

public class ChatController {
    Scanner scan = new Scanner(System.in);
    ChatModel chatModel = new ChatModel();

    public void displayChatMenu() {
        ChatView.displayMenu();
    }

    public void checkFile(String userName, String myName){
        chatModel.checkFile(userName, myName);
    }

    public void sendMessage(String myName) {
        System.out.print("Your text: ");
        String text = scan.nextLine();
        chatModel.sendMessage(text, myName);
    }

    public void viewChatHistory() {
        chatModel.viewChatHistory();
    }

    public void deleteChatHistory() {
        chatModel.deleteChatHistory();
    }
}
