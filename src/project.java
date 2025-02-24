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
        // userController.addUser(new UserModel(123,19, "Leap"));
        // userController.addUser(new UserModel(222, 19, "Ann"));
        // userController.addUser(new UserModel(223, 20, "Rith"));
        int option = 0;
        int choice = 0;
        while (choice == 0) {
            System.out.println("1.Register: ");
            System.out.println("2.Login: ");
            System.out.print("Option: ");
            choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                userController.registerAccount();
            } else if (choice == 2) {
                userController.loginAccount();
            } else {
                System.out.println("Invalid choice!");
            }
        }
        userController.ReadUsersData();
        // System.out.print("Your name: "); //example: Leap
        // String myName = scan.nextLine();
        // System.out.print("Password: ");
        // String myPass = scan.nextLine();
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
                    userController.searchUser();
                    break;
                case 3:
                    int chatOption = 0;
                    System.out.print("Who do you want to chat to? \nEnter username: ");
                    String userToChat = scan.nextLine();
                    while (chatOption != 4) {
                        chatController.displayChatMenu();
                        chatOption = scan.nextInt();
                        System.out.println();
                        chatController.checkFile(userToChat, userController.getUserName());
                        if (chatOption == 1) {
                            chatController.sendMessage(userController.getUserName());
                        } else if (chatOption == 2) {
                            chatController.viewChatHistory();
                        } else if (chatOption == 3) {
                            chatController.deleteChatHistory();
                        } else if (chatOption == 4) {
                            System.out.println("Exit chat");
                        } else {
                            System.out.println("Invalid option");
                        }
                        System.out.println();
                    }
                    break;
                case 4:
                    userController.banUser();
                    break;
                case 5:
                    userController.displayAllBannedUsers();
                    userController.unbanUser();
                    break;
                case 6:
                    userController.storeUserData();
                    break;
                default:
                    break;
            }
        }
        scan.close();
    }
}
