package com.example.opt_1.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;


public class LoginPage extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private IViewtoModel controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        setContentView(R.layout.login_page);
        loginButton = findViewById(R.id.loginButton);
        usernameField = findViewById(R.id.loginUserNameInput);
        passwordField = findViewById(R.id.loginPasswordInput);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getLoginInfo(usernameField.getText().toString(),passwordField.getText().toString());
            }
        });

    }
}
