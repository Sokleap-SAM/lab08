import java.util.ArrayList;
import java.util.List;

public class UserController {
    List <User> userList = new ArrayList<>();

    void addUser(User user){
        userList.add(user);
    }

    void displayAllUsers(){
        //display all user from list
        //option chat, view history, ban and unban available
    }
    void searchUser(){
        //search by id
        //search by name
        //display all users based on search
        //option chat, view history, ban and unban available
    }
    void banUser(){
        //call chat class
        //show user that in chat
        //select to ban user
    }
    void unbanUser(){
        //call chat class
        //show user that you ban
        //select to unban user
    }
}
