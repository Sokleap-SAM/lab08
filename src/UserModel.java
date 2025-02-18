public class UserModel {
    int userID, age;
    String name;
    Boolean statusBanned = false;
    UserModel(int userID, int age, String name){
        this.userID = userID;
        this.age = age;
        this.name = name;
    }
    void setStatusBanned(boolean statusBanned){
        this.statusBanned = statusBanned;
    }
    int getUserID(){
        return userID;
    }
    int getUserAge(){
        return age;
    }
    String getUserName(){
        return name;
    }
}
