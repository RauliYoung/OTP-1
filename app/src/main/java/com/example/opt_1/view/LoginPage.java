package com.example.opt_1.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.CurrentUserInstance;
import com.example.opt_1.control.IViewtoModel;
import com.example.opt_1.model.CRUDCallbacks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button registerButton;
    private IViewtoModel controller;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser fireUser = auth.getCurrentUser();
    FirebaseAuth.AuthStateListener authlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        setContentView(R.layout.login_page);
        if(auth.getCurrentUser() != null){
            auth.signOut();
            System.out.println("Login page user: " + auth.getCurrentUser());
        }
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        //LoginPage Fields
        usernameField = findViewById(R.id.loginUserNameInput);
        passwordField = findViewById(R.id.loginPasswordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }});
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });

    }
    private void userLogin(){
        controller.setLoginInformation(usernameField.getText().toString(), passwordField.getText().toString());
        controller.userLogin();
        authlistener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(CurrentUserInstance.getINSTANCE()!= null && user!=null && firebaseAuth.getCurrentUser()!=null){
                    System.out.println("USERLOGGED IN: " + user.getEmail());
                    Intent intent = new Intent(LoginPage.this, Main_Page.class);
                    startActivity(intent);
                    finish();
                }else{
                    System.out.println("WRONG USER OR PASS");
                }
            }
        };
        auth.addAuthStateListener(authlistener);

    }
}
