package com.example.opt_1.control;

import com.example.opt_1.model.DAO;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.IModel;
import com.example.opt_1.model.User;
import com.google.android.gms.tasks.Task;

public class Controller implements IModeltoView,IViewtoModel{

    private IModel model = new User();
    private IDAO database = new DAO();

    private String loginInfoUsername;
    private String loginInfoPassword;

    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername,loginInfoPassword);
    }

    @Override
    public void getLoginInfo() {

    }

    @Override
    public Boolean getRegisterInfo() {
        System.out.println("REGISTERERRORCHECK" + database.getRegisterErrorCheck());

        return database.getRegisterErrorCheck();
    }

    @Override
    public void setLoginInformation(String usernameInput, String passwordInput) {
        loginInfoPassword = passwordInput;
        loginInfoUsername = usernameInput;
    }

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email) {
        database.createUser(new User(firstName,lastName,username,email,password));
    }
}
