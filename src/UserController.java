import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    List<UserModel> userList;
    UserView userView = new UserView();
    Scanner input = new Scanner(System.in);

    UserController() {
        userList = new ArrayList<>();
    }

    void addUser(UserModel user) {
        userList.add(user);
    }

    void displayAllUsers() {
        // Display all users from the list
        for (UserModel u : userList) {
            userView.displayUser(u);
        }
    }

    void searchUser() {
        System.out.println("Do you want to search by ID or name?");
        System.out.print("Answer: ");
        String ans = input.nextLine().toLowerCase();

        boolean found = false;

        // Check ID
        if (ans.equals("id")) {
            System.out.print("Enter ID: ");
            int id = input.nextInt();
            input.nextLine();

            for (UserModel u : userList) {
                if (u.userID == id) {
                    userView.displayUser(u);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("User not found");
            }
        } else if (ans.equals("name")) { // Check name
            System.out.print("Enter name: ");
            String name = input.nextLine().toLowerCase();

            for (UserModel u : userList) {
                if (u.name.equalsIgnoreCase(name)) { // Fixed name comparison
                    userView.displayUser(u);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("User not found");
            }

        } else {
            System.out.println("Wrong input!");
        }
    }

    void banUser() {
        // call chat class
        // show user that in chat
        // select to ban user
    }

    void unbanUser() {
        // call chat class
        // show user that you ban
        // select to unban user
    }

}
