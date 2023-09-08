package com.example.opt_1.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;
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

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        //LoginPage Fields
        usernameField = findViewById(R.id.loginUserNameInput);
        passwordField = findViewById(R.id.loginPasswordInput);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getLoginInfo(usernameField.getText().toString(), passwordField.getText().toString());
               authlistener = new FirebaseAuth.AuthStateListener(){
                    @Override
                    public  void  onAuthStateChanged(FirebaseAuth firebaseAuth){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user!=null && firebaseAuth.getCurrentUser()!=null){
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
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setLoginInformation(usernameField.getText().toString(),passwordField.getText().toString());
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}
