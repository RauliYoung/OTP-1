package com.example.opt_1.view;

import android.os.Bundle;
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


}
