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
        user = new UserModel(0, 0, "hi");
        userList = new ArrayList<>();
    }

    // void addUser(UserModel user) {
    // userList.add(user);
    // }

    void displayAllUsers() {
        // Display all users from the list
        for (UserModel u : userList) {
            userView.displayUser(u);
        }
    }

    void ReadUsersData() {
        try {
            if ((new File("UserData.csv")).exists()) {
                String data;
                BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
                while ((data = br.readLine()) != null) {
                    String[] pieces = data.split(",");
                    if (user.getUserID() != Integer.parseInt(pieces[0])) {
                        userList.add(
                                new UserModel(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[2]), pieces[1]));
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Unable to read");
        }
    }

    // void displayAllUsers() {
    // try {
    // String data;
    // BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
    // while ((data = br.readLine()) != null) {
    // String[] pieces = data.split(",");
    // if (user.getUserID() != Integer.parseInt(pieces[0])) {
    // System.out.println("ID: " + pieces[0] + " Name: " + pieces[1] + " Age:" +
    // pieces[2]);
    // }
    // }
    // br.close();
    // } catch (Exception e) {
    // System.out.println("Can't display all users");
    // }
    // }

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
        System.out.println("Welcome to ban option");
        System.out.print("Enter ID to ban user: ");
        int bannedUserID = input.nextInt();
        input.nextLine();
        if (user.findBannedUserID(bannedUserID)) {
            user.addBannedUser(bannedUserID);
            System.out.println("Successfully banned the user");
        } else {
            System.out.println("Unable to banned the user (Incorrect user ID)");
        }
    }

    void unbanUser() {
        System.out.println("Welcome to unban option");
        System.out.print("Enter ID to unban user: ");
        int unbannedUserID = input.nextInt();
        input.nextLine();
        if (user.findBannedUserID(unbannedUserID)) {
            user.removeBannedUser(unbannedUserID);
            System.out.println("Successfully unbanned the user");
        } else {
            System.out.println("Unable to banned the user (Incorrect user ID)");
        }
    }

    void displayAllBannedUsers() {
        for (UserModel u : userList) {
            if (user.findBannedUserID(u.userID)) {
                userView.displayUser(u);
            }
        }
    }
    // void displayAllBannedUsers() {
    // try {
    // String data;
    // BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
    // while ((data = br.readLine()) != null) {
    // String[] pieces = data.split(",");
    // if (user.findBannedUserID(Integer.parseInt(pieces[0]))) {
    // System.out.println("ID: " + pieces[0] + " Name: " + pieces[1] + " Age:" +
    // pieces[3]);
    // }
    // }
    // br.close();
    // } catch (Exception e) {
    // System.out.println("Can't display all banned users!");
    // }
    // }

    void registerAccount() {
        int userIndex = 1;
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        System.out.print("Age: ");
        int age = input.nextInt();
        user.setUserName(name);
        user.setAge(age);
        user.setPassword(password);
        try {
            String data;
            if ((new File("UserData.csv")).exists()) {
                BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
                while ((data = br.readLine()) != null) {
                    userIndex++;
                }
                br.close();
            } else {
                System.out.println("You are the first user!");
            }
        } catch (Exception e) {
            System.out.println("You can't register an account");
        }
        user.setUserID((userIndex));
    }

    void loginAccount() {
        int id;
        String password;
        boolean loginStatus = false;
        System.out.print("Enter ID: ");
        id = input.nextInt();
        input.nextLine();
        System.out.print("Enter Password: ");
        password = input.nextLine();
        try {
            String data;
            BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
            while ((data = br.readLine()) != null) {
                String[] pieces = data.split(",");
                if ((id == (Integer.parseInt(pieces[0]))) && (password.equals(pieces[3]))) {
                    user.setUserID(Integer.parseInt(pieces[0]));
                    user.setUserName(pieces[1]);
                    user.setAge(Integer.parseInt(pieces[2]));
                    user.setPassword(pieces[3]);
                    user.setBannedUserList(pieces[4]);
                    loginStatus = true;
                    // user.setLoginStatus(true);
                    System.out.println("Login successfully");
                    break;
                }
            }
            br.close();
            user.setLoginStatus(loginStatus);
            // if(loginStatus == true){
            //     user.setLoginStatus(loginStatus);
            // }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Can't Login!" + e);
        }
    }

    void storeUserData() {
        if (user.getLoginStatus() == false) {
            File file = new File("UserData.csv");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("UserData.csv", true));
                bw.write(
                        user.getUserID() + "," + user.getUserName() + "," + user.getUserAge() + "," + user.getPassword()
                                + ",");
                for (int u : user.bannedUsersList) {
                    if (user.bannedUsersList.isEmpty()) {
                        bw.write("None");
                    }
                    bw.write(u + " ");
                }
                bw.write("\n");
                bw.close();
            } catch (Exception e) {
                System.out.println("Can't Store Your Data!");
            }
        }
    }

    String getUserName() {
        return user.getUserName();
    }

    // void readUserData(){
    // try {
    // String data;
    // BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
    // while ((data = br.readLine()) != null) {
    // String[] pieces = data.split(",");
    // user.setUserID(Integer.parseInt(pieces[0]));
    // user.setUserName(pieces[1]);
    // user.setAge(Integer.parseInt(pieces[2]));
    // user.setPassword(pieces[3]);
    // user.setBannedUserList(pieces[4]);
    // }
    // br.close();
    // } catch (Exception e) {
    // System.out.println("Can't Login");
    // }
    // }

}
