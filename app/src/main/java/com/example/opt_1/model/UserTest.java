package com.example.opt_1.model;


public class UserTest implements IModel {

    @Override
    public void processLogin(String username, String password) {
        System.out.println(username);
        System.out.println(password);
    }

}
