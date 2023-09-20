package com.example.opt_1.control;

import com.google.firebase.firestore.auth.User;

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
}
