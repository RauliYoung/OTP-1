package com.example.opt_1.model;

import static org.junit.Assert.*;

import com.example.opt_1.R;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private IModel user;
    @Before
    public void initializeUser(){
        user = new User("testi","testi","testi","testi4@testi.com","testitesti6",null);
    }
    @Test
    public void getFirstName() {

    }

    @Test
    public void setFirstName() {
    }

    @Test
    public void getLastName() {
    }

    @Test
    public void setLastName() {
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void setUsername() {
    }

    @Test
    public void getEmail() {
    }

    @Test
    public void setEmail() {
    }

    @Test
    public void getPassword() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void processLogin() {
    }

    @Test
    public void getRegisterInfo() {
    }
}