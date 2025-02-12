class User{
    int userID, age;
    String name;
    Boolean statusBanned = false;
    User(int userID, int age, String name){
        this.userID = userID;
        this.age = age;
        this.name = name;
    }
    void setStatusBanned(boolean statusBanned){
        this.statusBanned = statusBanned;
    }

}
class Chat{
    void sendChat(){

    }
    void receiveChat(){

    }
    void storeHistory(){

    }
    void viewHistory(){

    }
    void removeHistory(){

    }
}
