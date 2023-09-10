package com.example.opt_1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {
    private Button registerButton;
    private Controller controller;

    private EditText firstNameField,lastNameField,userNameField,passwordField,emailField;

    private String firstName,lastName,userName,password,email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        setContentView(R.layout.register_page);
        registerButton = findViewById(R.id.registerPageRegisterButton);
        firstNameField = findViewById(R.id.registerFirstName);
        lastNameField = findViewById(R.id.registerLastName);
        userNameField = findViewById(R.id.registerUsernameInput);
        passwordField = findViewById(R.id.registerPasswordInput);
        emailField = findViewById(R.id.registerEmailInput);

    registerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firstName = firstNameField.getText().toString();
            lastName = lastNameField.getText().toString();
            userName = userNameField.getText().toString();
            password = passwordField.getText().toString();
            email = emailField.getText().toString();

            if (checkValidCharacterLenght(firstName, lastName, userName) && checkPasswordLength(password)) {
                controller.setRegisterInformation(firstName, lastName, userName, password, email);

                if (controller.getRegisterInfo()) {
                    Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                    startActivity(intent);
                } else {
                    System.out.println("EMAIL IS IN A WRONG FORMAT");
                }

            } else {
                System.out.println("INVALID INPUTS/MAX LENGHT CHARACTERS IN FIELD 11");
            }
        }
    });
    }
    public Boolean checkValidCharacterLenght(String firstname,String lastname,String username){

        int min = 2;
        int max = 11;

        if (
                firstname.length() > min && firstname.length() < max ||
                lastname.length() > min && lastname.length() < max ||
                username.length() > min && username.length() < max)
        {
            return true;
        }else{
            return false;
        }
    }
    public Boolean checkPasswordLength(String password){
        int minpass = 5;
        int maxpass = 11;

        if (password.length() > minpass && password.length() < maxpass){
            return true;
        }else{
            return  false;
        }
    }
}
