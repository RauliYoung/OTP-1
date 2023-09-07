package com.example.opt_1.control;


import com.example.opt_1.model.DAO;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.IModel;
import com.example.opt_1.model.User;
import com.example.opt_1.model.UserTest;
import com.google.firebase.auth.FirebaseAuth;

public class Controller implements IModeltoView,IViewtoModel{

    private IModel model = new User();
    private IDAO database = new DAO();

    @Override
    public void userLogintestback() {

    }

    @Override
    public void getLoginInfo(String username, String password) {
        model.processLogin(username,password);
    }

    @Override
    public void setLoginInformation(String usernameInput, String password) {

    }

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email) {
        System.out.println(firstName + " " + lastName + " " + username + " "+ password + " "+ email);
        database.createUser(new User(firstName,lastName,username,password,email));
    }
}
