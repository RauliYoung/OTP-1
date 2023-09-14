package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.opt_1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_Page extends AppCompatActivity  implements BottomNavigationView.OnItemSelectedListener {
    private Button logOutButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        logOutButton = findViewById(R.id.logOut_Button);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Page.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }
    ActivityFragment activityFrag = new ActivityFragment();
    GroupFragment groupFrag = new GroupFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = 0;

        if (item.getItemId() == R.id.groups){
            id = 1;
        }else if(item.getItemId() == R.id.activity){
            id = 2;
        }

        switch (id) {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment,groupFrag)
                        .commit();
                return true;

            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, activityFrag)
                        .commit();
                return true;
        }
        return false;
    }

    }
