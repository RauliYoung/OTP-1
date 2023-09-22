package com.example.opt_1.control;
import com.example.opt_1.model.User;

public class CurrentUserInstance {
    private static CurrentUserInstance INSTANCE = null;
    private User currentUser;
    private CurrentUserInstance(){
    }

    public static CurrentUserInstance getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new CurrentUserInstance();
        }
        return INSTANCE;
    }

    public void setCurrentUser(User currentUser) {
        if(this.currentUser == null) {
            this.currentUser = currentUser;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
