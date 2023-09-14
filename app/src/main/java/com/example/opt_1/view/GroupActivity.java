package com.example.opt_1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;

public class GroupActivity extends AppCompatActivity {

    private Button addGroup;
    private EditText groupNameInputfield;
    private Controller controller;
    private Boolean isActivated = false;
    private String groupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        controller = new Controller();

        addGroup = findViewById(R.id.addGroupButton);
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
        });addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupName = groupNameInputfield.getText().toString();
                controller.makeNewGroup(groupName);
            }
        });
    }
}