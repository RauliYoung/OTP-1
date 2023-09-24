package com.example.opt_1.control;

import com.example.opt_1.view.ActivityFragment;
import com.example.opt_1.model.CRUDCallbacks;


public interface IViewtoModel {
    void setLoginInformation(String emailInput,String password);
    void userLogin();
    void changePassword(String oldPassword, String newPassword);
    void changeUsername(String newUsername);
    void startActivity(ActivityFragment fragment);
    void removeUser();
    void stopActivity();
    void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback);
    void createNewGroup(String groupName);
    void joinToGroup(String groupOwnerEmail);
    void leaveFromGroup(String groupOwnerEmail);
}
