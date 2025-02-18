import java.util.Scanner;

class Menu {
    void displayMenu() {
        System.out.println("======menu======");
        System.out.println("1. List users");
        System.out.println("2. Search user");
        System.out.println("3. Open chat");
        System.out.println("4. Ban user");
        System.out.println("5. Unban user");
        System.out.println("6. Exit");
        System.out.print("Option: ");
    }
}

public class project {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChatController chatController = new ChatController();
        UserController userController = new UserController();
        userController.addUser(new UserModel("Ann"));
        userController.addUser(new UserModel("Heng"));
        int option = 0;
        System.out.println("Your name: "); //example: Leap
        String myName = scan.nextLine();
        while (option != 6) {
            Menu menu = new Menu();
            menu.displayMenu();
            option = scan.nextInt();
            scan.nextLine();
            System.out.println();
            switch (option) {
                case 1:
                    userController.displayAllUsers();
                    break;
                case 2:
                case 3:
                    int chatOption = 0;
                    System.out.print("Enter username: ");
                    String UserToChat = scan.nextLine();
                    // user.search
                    while (chatOption != 4) {
                        chatController.displayChatMenu();
                        System.out.println();
                        chatOption = scan.nextInt();
                        chatController.checkFile(UserToChat, myName);
                        if (chatOption == 1) {
                            chatController.sendMessage(myName);
                        } else if (chatOption == 2) {
                            chatController.viewChatHistory();
                        } else if (chatOption == 3) {
                            chatController.deleteChatHistory();
                        } else if (chatOption == 4) {
                            System.out.println("Exit chat");
                        } else {
                            System.out.println("Invalid option");
                        }
                    }
                    break;
                case 4:
                case 5:
                case 6:
                default:
                    break;
            }
        }
        scan.close();
    }
}
