package com.example.opt_1.control;
import android.widget.TextView;

import com.example.opt_1.model.DAO;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.ILocationTracker;
import com.example.opt_1.model.LocationTracker;
import com.example.opt_1.model.User;
import com.example.opt_1.view.ActivityFragment;

public class Controller implements IModeltoView,IViewtoModel {

    private IDAO database = new DAO();

    private LocationTracker locationTracker;

    private String loginInfoUsername;
    private String loginInfoPassword;

    private TextView textViewData;
    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername, loginInfoPassword);
    }

    @Override
    public void removeUser() {
        database.removeUser();
    }

    @Override
    public synchronized void startActivity(ActivityFragment fragment,TextView data) {
        System.out.println("DATATEXTVIEW" + data);
        this.textViewData = data;
        System.out.println("Activity Starts!");
        locationTracker = new LocationTracker();
        locationTracker.setLocation(fragment,this);
        locationTracker.start();

    }


    @Override
    public synchronized void stopActivity() {
        locationTracker.setActive(false);
        System.out.println("Activity Stopping!");
    }

    @Override
    public void getLoginInfo() {

    }

    @Override
    public Boolean getRegisterInfo() {
        return database.getRegisterErrorCheck();
    }

    @Override
    public void getTravelledDistanceModel() {
        textViewData.setText(String.valueOf(locationTracker.getTravelledDistance()));
    }

    @Override
    public void setLoginInformation(String usernameInput, String passwordInput) {
        loginInfoPassword = passwordInput;
        loginInfoUsername = usernameInput;
    }

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback) {
        database.createUser(new User(firstName, lastName, username, email, password, callback), callback);
    }

    @Override
    public void createNewGroup(String groupName) {
        database.addNewGroupToDatabase(groupName);
    }

    @Override
    public void joinToGroup(String groupOwnerEmail) {
        database.addUserToTheGroup(groupOwnerEmail);
    }

    @Override
    public void leaveFromGroup(String groupOwnerEmail) {
        database.removeUserFromTheGroup(groupOwnerEmail);
    }
}