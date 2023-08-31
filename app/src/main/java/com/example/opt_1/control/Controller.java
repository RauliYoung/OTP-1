package com.example.opt_1.control;


import com.example.opt_1.model.DAO;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.IModel;
import com.example.opt_1.model.UserTest;

public class Controller implements IModeltoView,IViewtoModel{

    private IModel model = new UserTest();
    private IDAO database = new DAO();

    @Override
    public void userLogintestback() {

    }

    @Override
    public void getLoginInfo(String username, String password) {
        model.processLogin(username,password);
    }
}
