package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.opt_1.R;
import com.example.opt_1.control.Controller;


public class GroupActivityTest extends Fragment {
    private Button addGroup, joinGroup, leaveGroup, removeUser;
    private EditText groupNameInputfield;
    private Controller controller;
    private View view;
    private String groupName;
    public GroupActivityTest() {
        controller = new Controller();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group_activity_test, container, false);
        //Set button references
        addGroup = view.findViewById(R.id.addGroupButton);
        joinGroup = view.findViewById(R.id.joinGroupButton);
        leaveGroup = view.findViewById(R.id.leaveGroupButton);
        removeUser = view.findViewById(R.id.removeUserButton);
        //Set text input field
        groupNameInputfield = view.findViewById(R.id.groupNameInput);
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
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
            }
        });
        return view;
    }
}