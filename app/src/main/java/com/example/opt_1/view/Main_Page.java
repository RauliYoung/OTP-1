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
    BottomNavigationView bottomNavigationView;
    ActivityFragment activityFrag;
    GroupFragment groupFrag;
    HomeFragment homeFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        homeFrag = new HomeFragment();
        groupFrag = new GroupFragment();
        activityFrag = new ActivityFragment();

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.groups){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment,groupFrag)
                    .commit();
            return true;
        }else if(item.getItemId() == R.id.activity){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, activityFrag)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFrag  )
                    .commit();
            return true;
        }
        return false;

    }

    }
