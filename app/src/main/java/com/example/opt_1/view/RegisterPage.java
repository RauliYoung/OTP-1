package com.example.opt_1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.opt_1.R;
public class RegisterPage extends AppCompatActivity {
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        registerButton = findViewById(R.id.registerPageRegisterButton);


    registerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
        }
    });
    }
}
