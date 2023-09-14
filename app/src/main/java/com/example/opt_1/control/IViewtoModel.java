package com.example.opt_1.control;

import com.example.opt_1.model.RegistrationCallBack;

public interface IViewtoModel {
    void setLoginInformation(String emailInput,String password);
    void userLogin();
    void startActivity();
    void stopActivity();
    void setRegisterInformation(String firstName, String lastName, String username, String password, String email, RegistrationCallBack callback);
}
