package com.example.opt_1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;

public class GroupActivity extends AppCompatActivity {

    private Button addGroup, joinGroup, leaveGroup, removeUser;
    private EditText groupNameInputfield;
    private Controller controller;
    private Boolean isActivated = false;
    private String groupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        //Init controller
        controller = new Controller();
        //Get button references
        addGroup = findViewById(R.id.addGroupButton);
        joinGroup = findViewById(R.id.joinGroupButton);
        leaveGroup = findViewById(R.id.addUserGroupButton);
        removeUser = findViewById(R.id.removeUserButton);
        //Get inputfield reference
        groupNameInputfield = findViewById(R.id.groupNameInput);
        groupNameInputfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if(focused){
                    groupNameInputfield.setText("");

                }
                if(!focused){
                    if(groupNameInputfield.getText().length() == 0){
                        groupNameInputfield.setText("Insert group name");
                    }else{
                        groupName = groupNameInputfield.getText().toString();
                    }

                }
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupName = groupNameInputfield.getText().toString();
                controller.createNewGroup(groupName);
            }
        });
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupOwnerEmail = groupNameInputfield.getText().toString();
                controller.joinToGroup(groupOwnerEmail);
            }
        });
        leaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupOwnerEmail = groupNameInputfield.getText().toString();
                controller.leaveFromGroup(groupOwnerEmail);
            }
        });

        removeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.removeUser();
                Intent intent = new Intent(GroupActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Suljetaan sovellus");
    }
}