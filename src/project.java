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
                    System.out.print("Who do you want to chat to? \nEnter username: ");
                    String userToChat = scan.nextLine();
                    if (userController.checkExistingUser(userToChat)) {
                        int chatOption = 0;
                        while (chatOption != 4) {
                            try {
                                chatController.checkFile(userToChat, userController.getUserName());
                                chatController.displayChatMenu();
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
                            } catch (Exception e) {
                                System.out.println("Invalid input");
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
                    userController.storeUserDatas();
                    break;
                default:
                    break;
            }
        }
        scan.close();
    }
}
