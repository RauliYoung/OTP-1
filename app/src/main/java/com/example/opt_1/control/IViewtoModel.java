package com.example.opt_1.control;

public interface IViewtoModel {
    void getLoginInfo(String username,String password);
    void setLoginInformation(String emailInput,String password);

    void setRegisterInformation(String firstName,String lastName,String username,String password,String email);
}
