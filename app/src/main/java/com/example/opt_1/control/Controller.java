package com.example.opt_1.control;

import com.example.opt_1.model.DAO;
import com.example.opt_1.model.Group;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.IGroup;
import com.example.opt_1.model.IUser;
import com.example.opt_1.model.RegistrationCallBack;
import com.example.opt_1.model.User;

public class Controller implements IModeltoView,IViewtoModel{

    private IDAO database = new DAO();

    private String loginInfoUsername;
    private String loginInfoPassword;

    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername,loginInfoPassword);
    }

    @Override
    public void startActivity() {
        System.out.println("Activity Starting!");
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
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, RegistrationCallBack callback) {
        database.createUser(new User(firstName,lastName,username,email,password,callback),callback);
    }

    @Override
    public void makeNewGroup(String groupName) {


    }

}
