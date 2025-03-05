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
        user = new UserModel(0, "hi", 0, "a", "a");
        userList = new ArrayList<>();
    }

    
    boolean registerAccount() {
        int userIndex = 1;
        int age;
        System.out.print("\nName: ");
        String name = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        try {
            System.out.print("Age: ");
            age = input.nextInt();
            input.nextLine();
            if (age <= 0 || age > 100) {
                System.out.println("Invalid age");
                return false;
            }
            user.setUserName(name);
            user.setAge(age);
            user.setPassword(password);
            user.setBannedUsers("None");
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
            user.setUserID((userIndex));
            return true;
        } catch (Exception e) {
            System.out.println("Invalid age input");
            return false;
        }
    }

    boolean loginAccount() {
        String name;
        String password;
        String data;
        System.out.print("\nEnter Username: ");
        name = input.nextLine();
        System.out.print("Enter Password: ");
        password = input.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader("UserData.csv"));
            while ((data = br.readLine()) != null) {
                String[] pieces = data.split(",");
                if ((name.equals(pieces[1])) && (password.equals(pieces[3]))) {
                    user.setUserID(Integer.parseInt(pieces[0]));
                    user.setUserName(pieces[1]);
                    user.setAge(Integer.parseInt(pieces[2]));
                    user.setPassword(pieces[3]);
                    user.setBannedUserList(pieces[4]);
                    user.setBannedUsers(pieces[4]);
                    System.out.println("Login successfully");
                    return true;
                }
            }
            br.close();
            System.out.println("Failed to login (Incorrect username or password)");
            return false;
        } catch (Exception e) {
            System.out.println("You can't login to your account!");
            return false;
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
                                new UserModel(Integer.parseInt(pieces[0]), pieces[1], Integer.parseInt(pieces[2]),
                                        pieces[3], pieces[4]));
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Unable to read");
        }
    }

    void displayAllUsers() {
        System.out.println("\nUsers: ");
        for (UserModel u : userList) {
            userView.displayUser(u);
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

    void banUser() {
        try {
            System.out.println("\n=====BLOCK USER=====");
            System.out.print("Enter userID to ban the user: ");
            int blockUserID = input.nextInt();
            input.nextLine();
            for (UserModel u : userList) {
                if (u.getUserID() == blockUserID) {
                    user.addBannedUser(blockUserID);
                    if (user.findBannedUserID(blockUserID) == false) {
                        System.out.println("Successfully blocked " + u.getUserName());
                        if (user.getBannedUsers().equals("None")) {
                            user.setBannedUsers(Integer.toString(blockUserID));
                        } else {
                            user.setBannedUsers((user.getBannedUsers() + " " + Integer.toString(blockUserID)));
                        }
                    } else {
                        System.out.println("You already block " + u.getUserName());
                    }
                    return;
                }
            }
            System.out.println("Incorrect userID");
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }
    }

    void displayAllBannedUsers() {
        System.out.println("Blocked user:");
        for (int i : user.bannedUsersList) {
            for (UserModel u : userList) {
                if (u.getUserID() == i) {
                    userView.displayUser(u);
                }
            }
        }
    }

    void unbanUser() {
        try {
            System.out.println("\n=====UNBLOCK USER=====");
            System.out.print("Enter userID to unblock the user: ");
            int unbannedUserID = input.nextInt();
            input.nextLine();
            if (!user.bannedUsersList.isEmpty()) {
                if (user.findBannedUserID(unbannedUserID)) {
                    user.removeBannedUser(unbannedUserID);
                    System.out.println("Successfully unblocked the user");
                } else {
                    System.out.println("Incorrect user ID");
                }
                return;
            } else {
                System.out.println("You haven't block anyone yet!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
    }

    void storeUserData() {
        File file = new File("UserData.csv");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("UserData.csv", false));
            if (userList.get(userList.size() - 1).getUserID() < user.getUserID()) {
                userList.add(user);
            } else {
                userList.add(user.getUserID() - 1, user);
            }
            for (UserModel u : userList) {
                bw.write(u.getUserID() + "," + u.getUserName() + "," + u.getUserAge() + "," + u.getPassword() + ","
                        + u.getBannedUsers() + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    boolean checkExistingUser(String name) {
        for (UserModel u : userList) {
            if (u.getUserName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    String getUserName() {
        return user.getUserName();
    }

}
