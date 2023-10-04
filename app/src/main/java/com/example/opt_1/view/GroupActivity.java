package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.model.CRUDCallbacks;


public class GroupActivity extends Fragment {
    private Button addGroupBtn, joinGroupBtn, leaveGroupBtn;
    private EditText groupNameInput;
    private TextView  usernameTextField;
    private Controller controller;
    private View view;
    private String groupName;
    private User2 userInstance;

    public GroupActivity() {
        controller = new Controller();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group_activity_test, container, false);
        userInstance = User2.getInstance();
        //Set button references
        addGroupBtn = view.findViewById(R.id.addGroupButton);
        joinGroupBtn = view.findViewById(R.id.joinGroupButton);
        //Set input references
        groupNameInput = view.findViewById(R.id.groupNameInput);
        groupNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if(focused){
                    groupNameInput.setText("");

                }
                if(!focused){
                    if(groupNameInput.getText().length() == 0){
                        groupNameInput.setText("Insert group name");
                    }else{
                        groupName = groupNameInput.getText().toString();
                    }

                }
            }
        });
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupName = groupNameInput.getText().toString();
                controller.createNewGroup(groupName);
            }
        });
        if(userInstance.isUserInGroup()){
            controller.fecthGroupResults();
        }
        System.out.println("User in group: " + userInstance.isUserInGroup());

        joinGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupOwnerEmail = groupNameInput.getText().toString();
                controller.joinToGroup(groupOwnerEmail);
                Intent intent = new Intent(getActivity(), GroupFragment.class);
                startActivity(intent);
            }
        });
        return view;
    }
}