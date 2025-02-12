import java.util.Scanner;

class Menu{
    void displayMenu(){
        System.out.println("======menu======");
        System.out.println("1. List users");
        System.out.println("2. Search user");
        System.out.println("3. Open chat");
        System.out.println("4. View chat history");
        System.out.println("5. Remove chat history");
        System.out.println("6. Ban user");
        System.out.println("7. Unban user");
        System.out.println("8. Exit");
        System.out.println("Option: ");
    }
}
public class project {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Menu menu = new Menu();
        UserAndChatController controller = new UserAndChatController();
        controller.addUser(new User(123, 19, "Leap"));
        controller.addUser(new User(124, 19, "Ann"));
        menu.displayMenu();
        int option = scan.nextInt();
        scan.nextLine();
        switch (option) {
            case 1:
                controller.displayAllUsers();
                break;
        
            default:
                break;
        }
    }
}
