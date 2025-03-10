import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    UserModel user;
    List<UserModel> userList;
    UserView userView = new UserView();
    Scanner input = new Scanner(System.in);

    UserController() {
        userList = new ArrayList<>();
    }

    boolean registerAccount() {
        int userID = 1;
        int age;
        System.out.print("\nName: ");
        String name = input.nextLine();
        if (checkExistingUser(name)) {
            System.out.println("Username already exist! Please choose a different username");
            return false;
        }
        System.out.print("Password: ");
        String password = input.nextLine();
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long");
            return false;
        }
        while (true) {
            System.out.print("Age: ");
            if (input.hasNextInt()) {
                age = input.nextInt();
                input.nextLine();
                if (age < 8 || age > 100) {
                    System.out.println("Invalid age! Age must be between 8 and 100.");
                    continue;
                }
                if (!(new File("UserData.csv")).exists()) {
                    System.out.println("\nYou are the first user!");
                }
                userID = userID + userList.size();
                System.out.println("Successfully registered an account");
                user = new UserModel(userID, name, age, password, "None");
                user.setBlockedUserList();
                userList.add(user);
                return true;
            } else {
                System.out.println("Please enter age in number!");
                input.next();
            }
        }
    }

    boolean loginAccount() {
        String name;
        String password;
        System.out.print("\nEnter Username: ");
        name = input.nextLine();
        System.out.print("Enter Password: ");
        password = input.nextLine();
        for (UserModel u : userList) {
            if ((name.equals(u.getUserName())) && password.equals(u.password)) {
                user = new UserModel(u.getUserID(), u.getUserName(), u.getUserAge(), u.getPassword(),
                        u.getBlockedUsers());
                user.setBlockedUserList();
                System.out.println("Login successfully");
                return true;
            }
        }
        System.out.println("Failed to login (Incorrect username or password)");
        return false;
    }

    void loadUsersData() {
        try {
            if ((new File("UserData.csv")).exists()) {
                String data;
                BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
                br.readLine();
                while ((data = br.readLine()) != null) {
                    String[] pieces = data.split(",");
                    userList.add(
                            new UserModel(Integer.parseInt(pieces[0]), pieces[1], Integer.parseInt(pieces[2]),
                                    pieces[3], pieces[4]));
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Unable to load datas due to server error");
        }
    }

    void displayAllUsers() {
        System.out.println("\nUsers: ");
        for (UserModel u : userList) {
            userView.displayUser(u);
            if (user.getUserID() == u.getUserID()) {
                System.out.print(" (ME)");
            }
            System.out.println("");
        }
    }

    void searchUser() {
        System.out.println("\n=====SEARCH USER=====");
        System.out.println("1. Search by userID");
        System.out.println("2. Search by Username");
        System.out.print("Answer: ");
        int ans = input.nextInt();
        input.nextLine();

        boolean found = false;

        if (ans == 1) {
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
        } else if (ans == 2) {
            System.out.print("Enter name: ");
            String name = input.nextLine().toLowerCase();

            for (UserModel u : userList) {
                if (u.name.equalsIgnoreCase(name)) {
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

    void blockUser() {
        System.out.println("\n=====BLOCK USER=====");
        System.out.print("Enter username to block the user: ");
        String name = input.nextLine();
        int id = 0;
        for (UserModel u : userList) {
            if (u.getUserName().equals(name)) {
                id = u.getUserID();
                break;
            }
        }
        if(id == 0){
            System.out.println("Incorrect userName!");
            return;
        }
        for (int ID : user.getBlockedUsersList()) {
            if (ID == id) {
                System.out.println("You already blocked " + name);
                return;
            }
        }
        user.addblockedUser(id);
        System.out.println("Successfully blocked " + name);
        storeUserData();
    }

    void unblockUser() {
        if (displayAllBlockedUsers()) {
            int id = 0;
            System.out.println("\n=====UNBLOCK USER=====");
            System.out.print("Enter username to unblock the user: ");
            String name = input.nextLine();
            for (UserModel u : userList) {
                if (u.getUserName().equals(name)) {
                    id = u.getUserID();
                    break;
                }
            }
            if(id == 0){
                System.out.println("Incorrect userName!");
                return;
            }
            for (int ID : user.getBlockedUsersList()) {
                if (ID == id) {
                    System.out.println("Successfully unblocked " + name);
                    user.removeblockedUser(ID);
                    storeUserData();
                    return;
                }
            }
            System.out.println("You haven't ban " + name + " yet");
        }
    }

    boolean displayAllBlockedUsers() {
        if (user.getBlockedUsersList().isEmpty()) {
            System.out.println("\nYou haven't block anyone yet!");
            return false;
        } else {
            System.out.println("\nBlocked User:");
            for (int id : user.getBlockedUsersList()) {
                for (UserModel u : userList) {
                    if (u.getUserID() == id) {
                        userView.displayUser(u);
                        System.out.println();
                    }
                }
            }
            return true;
        }
    }

    void storeUserData() {
        try {
            if (!user.getBlockedUsersList().isEmpty()) {
                user.setBlockedUsers(user.convertBlockedUserListToString());
            } else {
                user.setBlockedUsers("None");
            }
            userList.set(user.getUserID() - 1, user);
            BufferedWriter bw = new BufferedWriter(new FileWriter("UserData.csv", false));
            bw.write("ID, Name, Age, Password, BlockedUserID\n");
            for (UserModel u : userList) {
                bw.write(u.getUserID() + "," + u.getUserName() + "," + u.getUserAge() + ","
                        + u.getPassword() + "," + u.getBlockedUsers() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Unable to store data due to server error");
        }
    }

    boolean checkExistingUser(String name) {
        for (UserModel u : userList) {
            if (u.getUserName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    boolean checkExistingUserAndNotBlockedUser(String name){
        int id = 0;
        for (UserModel u : userList) {
            if (u.getUserName().equalsIgnoreCase(name)) {
                id = u.getUserID();
            }
        }
        if(id == 0){
            System.out.println("Incorrect username");
            return false;
        }
        for(int ID: user.getBlockedUsersList()){
            if(id == ID){
                return true;
            }
        }
        System.out.println("You blocked " + name + " ! Please unblocked him to access chat with him");
        return false;
    }

    String getUserName() {
        return user.getUserName();
    }

}
