import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserModel {
    int userID, age;
    String name;
    String password;
    String blockedUsers;
    ArrayList<Integer> blockedUsersList;

    UserModel(int userID, String name, int age, String password, String blockedUsers) {
        blockedUsersList = new ArrayList<>();
        this.userID = userID;
        this.age = age;
        this.name = name;
        this.password = password;
        this.blockedUsers = blockedUsers;
    }

    void setUserID(int id) {
        this.userID = id;
    }

    void setUserName(String name) {
        this.name = name;
    }

    void setPassword(String pass) {
        this.password = pass;
    }

    void setAge(int age) {
        this.age = age;
    }

    void setBlockedUsers(String blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    int getUserID() {
        return userID;
    }

    int getUserAge() {
        return age;
    }

    String getUserName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    String getBlockedUsers() {
        return blockedUsers;
    }

    void addblockedUser(int id) {
        blockedUsersList.add(id);
    }

    void removeblockedUser(int id) {
        blockedUsersList.remove(id - 1);
    }

    void setBlockedUserList() {
        if (!blockedUsers.equals("None")) {
            String[] blockedUser = blockedUsers.split(":");
            for (int i = 0; i < blockedUser.length; i++) {
                blockedUsersList.add(Integer.parseInt(blockedUser[i]));
            }
        }
    }

    ArrayList<Integer> getBlockedUsersList() {
        return blockedUsersList;
    }

    String convertBlockedUserListToString() {
        return blockedUsersList.stream().map(Object::toString).collect(Collectors.joining(":"));
    }
}
