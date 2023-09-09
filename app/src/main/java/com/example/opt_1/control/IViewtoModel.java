package com.example.opt_1.control;

public interface IViewtoModel {
    void setLoginInformation(String emailInput,String password);
    void userLogin();

    void setRegisterInformation(String firstName,String lastName,String username,String password,String email);
}
