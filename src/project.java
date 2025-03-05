import java.util.InputMismatchException;
import java.util.Scanner;

public class project {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChatController chatController = new ChatController();
        UserController userController = new UserController();
        int option = 0;
        int choice = 0;
        boolean registerAccount;
        boolean loginAccount;
        while (choice == 0) {
            try {
                System.out.println("1.Register: ");
                System.out.println("2.Login: ");
                System.out.print("Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println(choice);
                if (choice == 1) {
                    registerAccount = userController.registerAccount();
                    if (registerAccount == false) {
                        choice = 0;
                    }
                } else if (choice == 2) {
                    loginAccount = userController.loginAccount();
                    if (loginAccount == false) {
                        choice = 0;
                    }
                } else {
                    System.out.println("Inavlid choice!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                choice = 0;
            }
        }
        userController.ReadUsersData();
        while (option != 6) {
            System.out.println("\n======menu======");
            System.out.println("1. List users");
            System.out.println("2. Search user");
            System.out.println("3. Open chat");
            System.out.println("4. Ban user");
            System.out.println("5. Unban user");
            System.out.println("6. Exit");
            System.out.print("Option: ");
            option = scan.nextInt();
            scan.nextLine();
            switch (option) {
                case 1:
                    userController.displayAllUsers();
                    break;
                case 2:
                    userController.searchUser();
                    break;
                case 3:
                    System.out.print("Who do you want to chat to? \nEnter username: ");
                    String userToChat = scan.nextLine();
                    if (userController.checkExistingUser(userToChat)) {
                        int chatOption = 0;
                        while (chatOption != 4) {
                            chatController.checkFile(userToChat, userController.getUserName());
                            chatController.displayChatMenu();
                            chatOption = scan.nextInt();
                            scan.nextLine();
                            chatOption = scan.nextInt();
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
                        }
                    } else {
                        System.out.println("User Not Found");
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
