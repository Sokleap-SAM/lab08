import java.util.ArrayList;

public class UserModel {
    int userID, age;
    String name;
    String password;
    Boolean loginStatus = false;
    ArrayList<Integer> bannedUsersList = new ArrayList<>();

    UserModel(int userID, int age, String name) {
        this.userID = userID;
        this.age = age;
        this.name = name;
    }

    void addBannedUser(int id) {
        bannedUsersList.add(id);
    }

    void removeBannedUser(int id) {
        bannedUsersList.remove(id);
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

    // void setStatusBanned(boolean statusBanned) {
    //     this.statusBanned = statusBanned;
    // }

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

    void setLoginStatus(boolean loginStatus){
        this.loginStatus = loginStatus;
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

    boolean getLoginStatus(){
        return loginStatus;
    }
}
