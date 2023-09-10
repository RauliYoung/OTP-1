package com.example.opt_1.model;

import com.google.android.gms.tasks.Task;

public interface IDAO {
    void createUser(User user) ;
    void loginUser(String email, String password);
    Boolean getRegisterErrorCheck();
    void updateData();
}
