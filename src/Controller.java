import java.util.ArrayList;
import java.util.List;

class UserAndChatController{
    List <User> userList = new ArrayList<>();

    void addUser(User user){
        userList.add(user);
    }

    void displayAllUsers(){
        for (User user : userList) {
            user.display();
        }
        //display all user from list
        //option chat, view history, ban and unban available
    }
    void searchUser(){
        //search by id
        //search by name
        //display all users based on search
        //option chat, view history, ban and unban available
    }
    void openChat(){
        //call chat class
        //show user that in chat (u chat before or search)
        //select to chat (cant select char that ban)
        //store data in file after each chat
    }
    void viewChatHistory(){
        //call chat class
        //show user that in chat
        //select to view history
        //view history
    }
    void deleteChatHistory(){
        //call chat class
        //show user that you delete chat history
        //select to delete history
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
    void exit(){
        //display text
    }

    public void addUser(int i, int j, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }
}