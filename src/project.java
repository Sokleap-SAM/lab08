import java.util.Scanner;

public class project {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChatController chatController = new ChatController();
        UserController userController = new UserController();
        int option = 0;
        int choice;
        userController.loadUsersData();
        while (true) {
            System.out.println("\r\n" + //
                                " /$$      /$$           /$$                                                     /$$                                                                                       /$$           /$$                                     /$$ /$$          \r\n" + //
                                "| $$  /$ | $$          | $$                                                    | $$                                                                                      |__/          | $$                                    | $$|__/          \r\n" + //
                                "| $$ /$$$| $$  /$$$$$$ | $$  /$$$$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$        /$$$$$$    /$$$$$$         /$$$$$$  /$$   /$$  /$$$$$$         /$$$$$$$  /$$$$$$   /$$$$$$$ /$$  /$$$$$$ | $$       /$$$$$$/$$$$   /$$$$$$   /$$$$$$$ /$$  /$$$$$$ \r\n" + //
                                "| $$/$$ $$ $$ /$$__  $$| $$ /$$_____/ /$$__  $$| $$_  $$_  $$ /$$__  $$      |_  $$_/   /$$__  $$       /$$__  $$| $$  | $$ /$$__  $$       /$$_____/ /$$__  $$ /$$_____/| $$ |____  $$| $$      | $$_  $$_  $$ /$$__  $$ /$$__  $$| $$ |____  $$\r\n" + //
                                "| $$$$_  $$$$| $$$$$$$$| $$| $$      | $$  \\ $$| $$ \\ $$ \\ $$| $$$$$$$$        | $$    | $$  \\ $$      | $$  \\ $$| $$  | $$| $$  \\__/      |  $$$$$$ | $$  \\ $$| $$      | $$  /$$$$$$$| $$      | $$ \\ $$ \\ $$| $$$$$$$$| $$  | $$| $$  /$$$$$$$\r\n" + //
                                "| $$$/ \\  $$$| $$_____/| $$| $$      | $$  | $$| $$ | $$ | $$| $$_____/        | $$ /$$| $$  | $$      | $$  | $$| $$  | $$| $$             \\____  $$| $$  | $$| $$      | $$ /$$__  $$| $$      | $$ | $$ | $$| $$_____/| $$  | $$| $$ /$$__  $$\r\n" + //
                                "| $$/   \\  $$|  $$$$$$$| $$|  $$$$$$$|  $$$$$$/| $$ | $$ | $$|  $$$$$$$        |  $$$$/|  $$$$$$/      |  $$$$$$/|  $$$$$$/| $$             /$$$$$$$/|  $$$$$$/|  $$$$$$$| $$|  $$$$$$$| $$      | $$ | $$ | $$|  $$$$$$$|  $$$$$$$| $$|  $$$$$$$\r\n" + //
                                "|__/     \\__/ \\_______/|__/ \\_______/ \\______/ |__/ |__/ |__/ \\_______/         \\___/   \\______/        \\______/  \\______/ |__/            |_______/  \\______/  \\_______/|__/ \\_______/|__/      |__/ |__/ |__/ \\_______/ \\_______/|__/ \\_______/\r\n" + //
                                "                                                                                                                                                                                                                                                 \r\n" + //
                                "");
            System.out.println("\n1.Register: ");
            System.out.println("2.Login: ");
            System.out.print("Option: ");
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                scan.nextLine();
                if (choice == 1) {
                    if (userController.registerAccount()) {
                        break;
                    }
                } else if (choice == 2) {
                    if (userController.loginAccount()) {
                        break;
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scan.next();
            }
        }
        while (option != 6) {
            System.out.println("\n╔═══════════════════════╗");
            System.out.println("║       MAIN MENU       ║");
            System.out.println("╠═══════════════════════╣");
            System.out.println("║ 1.   List Users       ║");
            System.out.println("║ 2.   Search User      ║");
            System.out.println("║ 3.   Open Chat        ║");
            System.out.println("║ 4.   Block User       ║");
            System.out.println("║ 5.   Unblock User     ║");
            System.out.println("║ 6.   Exit             ║");
            System.out.println("╚═══════════════════════╝");
            System.out.print("  Select an option: ");

            if (scan.hasNextInt()) {
                option = scan.nextInt();
                scan.nextLine();
                System.out.print("\033c");
                switch (option) {
                    case 1:
                        userController.displayAllUsers();
                        System.out.println("\n\nPress enter to continue! ");
                        scan.nextLine();
                        System.out.print("\033c");
                        break;
                    case 2:
                        userController.searchUser();
                        System.out.println("\n\nPress enter to continue! ");
                        scan.nextLine();
                        System.out.print("\033c");
                        scan.nextLine();
                        break;
                    case 3:
                        System.out.print("\nWho do you want to chat to?\nEnter his/her username: ");
                        String userToChat = scan.nextLine();
                        if (userController.checkExistingUserAndNotBlockedUser(userToChat)) {
                            int chatOption = 0;
                            while (chatOption != 4) {
                                chatController.checkFile(userToChat.toLowerCase(), userController.getUserName().toLowerCase());
                                chatController.displayChatMenu();
                                if (scan.hasNextInt()) {
                                    chatOption = scan.nextInt();
                                    scan.nextLine();
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
                                } else {
                                    System.out.println("Invalid input! Please enter a number.");
                                    scan.next();
                                }
                            }
                        }
                        System.out.println("\n\nPress enter to continue! ");
                        scan.nextLine();
                        System.out.print("\033c");
                        break;
                    case 4:
                        userController.blockUser();
                        System.out.println("\n\nPress enter to continue! ");
                        scan.nextLine();
                        System.out.print("\033c");
                        break;
                    case 5:
                        userController.unblockUser();
                        System.out.println("\n\nPress enter to continue! ");
                        scan.nextLine();
                        System.out.print("\033c");
                        break;
                    case 6:
                        System.out.println("Exiting!");
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scan.next();
            }
        }
        scan.close();
    }
}