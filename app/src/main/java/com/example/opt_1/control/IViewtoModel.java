package com.example.opt_1.control;

import android.widget.TextView;

import com.example.opt_1.view.ActivityFragment;
import com.example.opt_1.model.CRUDCallbacks;


public interface IViewtoModel {
    void setLoginInformation(String emailInput,String password);
    void userLogin(String email, String password, CRUDCallbacks callbacks);
    void startActivity(ActivityFragment fragment,TextView data,TextView timer);
    void changeUsername(String newUsername);
    void changePassword(String oldPassword, String newPassword);
    void removeUser();
    void stopActivity();
    void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback);
    void createNewGroup(String groupName);
    void joinToGroup(String groupOwnerEmail);
    void leaveFromGroup(String groupOwnerEmail);
    double caclulatePace(double activityLength);
}
