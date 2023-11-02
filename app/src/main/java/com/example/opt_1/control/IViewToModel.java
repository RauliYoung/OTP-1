package com.example.opt_1.control;
import android.widget.TextView;
import com.example.opt_1.view.ActivityFragment;
import com.example.opt_1.model.CRUDCallbacks;

import java.util.Map;

/**
 * ViewModel interface that declares all the necessary methods for data transfer between view and model via controller
 */
public interface IViewToModel {
    void userLogin(String email, String password, CRUDCallbacks callbacks);
    void startActivity(ActivityFragment fragment,TextView data,TextView timer);
    void changeUsername(String newUsername, CRUDCallbacks callbacks);
    void changePassword(String oldPassword, String newPassword);
    void removeUser();
    Map<String,Double> stopActivity();
    void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback);
    void createNewGroup(String groupName);
    void joinToGroup(String groupOwnerEmail);
    void leaveFromGroup(String groupOwnerEmail);
    double caclulatePace(double activityLength);
    void fecthGroupResults(String groupOwnerEmail,CRUDCallbacks callbacks);
}
