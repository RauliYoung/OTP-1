package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;

public class Main_Page extends AppCompatActivity {
private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        logOutButton = findViewById(R.id.logOut_Button);


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Page.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

}
