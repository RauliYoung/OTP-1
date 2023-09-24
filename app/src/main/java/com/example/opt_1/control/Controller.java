package com.example.opt_1.control;
import com.example.opt_1.model.DAO;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.ILocationTracker;
import com.example.opt_1.model.LocationTracker;
import com.example.opt_1.model.User;
import com.example.opt_1.view.ActivityFragment;

public class Controller implements IModeltoView,IViewtoModel {

    private IDAO database = new DAO();

    private ILocationTracker locationTracker = new LocationTracker();

    private String loginInfoUsername;
    private String loginInfoPassword;

    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername, loginInfoPassword);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        database.changePassword(oldPassword,newPassword);
    }

    @Override
    public void changeUsername(String newUsername) {
        database.checkIfUsernameExist(newUsername);
    }

    @Override
    public void removeUser() {
        database.removeUser();
    }

    @Override
    public void startActivity(ActivityFragment fragment) {
        System.out.println("Activity Starts!");
        locationTracker.getLocation(fragment);
    }


    @Override
    public void stopActivity() {
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