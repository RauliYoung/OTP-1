package com.example.opt_1.control;

import com.example.opt_1.model.RegistrationCallBack;
import com.example.opt_1.view.ActivityFragment;

public interface IViewtoModel {
    void setLoginInformation(String emailInput,String password);
    void userLogin();
    void startActivity(ActivityFragment fragment);
    void stopActivity();
    void setRegisterInformation(String firstName, String lastName, String username, String password, String email, RegistrationCallBack callback);
}
