import java.util.ArrayList;

public class UserModel {
    int userID, age;
    String name;
    String password;
    String bannedUsers;
    ArrayList<Integer> bannedUsersList;

    UserModel(int userID, String name, int age, String password, String bannedUsers) {
        bannedUsersList = new ArrayList<>();
        this.userID = userID;
        this.age = age;
        this.name = name;
        this.password = password;
        this.bannedUsers = bannedUsers;
    }

    void addBannedUser(int id) {
        bannedUsersList.add(id);
    }

    void removeBannedUser(int id) {
        bannedUsersList.remove(id-1);
    }

    void setBannedUserList(String bannedUsers){
        if(bannedUsers.equals("None")){
            return;
        }
        String[] bannedUser = bannedUsers.split(" ");
        for(String user: bannedUser){
            int bannedUserID = Integer.parseInt(user);
            bannedUsersList.add(bannedUserID);
        }
    }

    boolean findBannedUserID(int id){
        for(int userID: bannedUsersList){
            if(userID == id){
                return true;
            }
        }
        return false;
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

    void setBannedUsers(String bannedUsers){
        this.bannedUsers = bannedUsers;
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

    String getBannedUsers(){
        return bannedUsers;
    }
}
