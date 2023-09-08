package com.example.opt_1.model;

public interface IDAO {
    void createUser(User user) ;
    void loginUser(String email, String password);
    void updateData();
}
