package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.CurrentUserInstance;

import java.util.Objects;


public class GroupActivityTest extends Fragment {
    private Button addGroupBtn, joinGroupBtn, leaveGroupBtn, removeUserBtn, changePasswordBtn, changeUsernameBtn;
    private EditText groupNameInput, oldPasswordInput,newPasswordInput, newUsernameInput;
    private TextView  usernameTextField;
    private Controller controller;
    private View view;
    private String groupName, oldPassword,newPassword, newUsername, oldUsername;
    private CurrentUserInstance userInstance = CurrentUserInstance.getINSTANCE();
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
        addGroupBtn = view.findViewById(R.id.addGroupButton);
        joinGroupBtn = view.findViewById(R.id.joinGroupButton);
        leaveGroupBtn = view.findViewById(R.id.leaveGroupButton);
        removeUserBtn = view.findViewById(R.id.removeUserButton);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        changeUsernameBtn = view.findViewById(R.id.changeUsernameBtn);
        //Set input references
        groupNameInput = view.findViewById(R.id.groupNameInput);
        usernameTextField = view.findViewById(R.id.usernameTextField);
        oldPasswordInput = view.findViewById(R.id.oldPasswordInputField);
        newPasswordInput = view.findViewById(R.id.newPasswordInputField);
        newUsernameInput = view.findViewById(R.id.newUsernameInputField);
        usernameTextField.setText(userInstance.getCurrentUser().getUsername());

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
        joinGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupOwnerEmail = groupNameInput.getText().toString();
                controller.joinToGroup(groupOwnerEmail);
            }
        });
        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupOwnerEmail = groupNameInput.getText().toString();
                controller.leaveFromGroup(groupOwnerEmail);
            }
        });

        removeUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.removeUser();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
            }
        });

        oldPasswordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                oldPasswordInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        oldPassword = oldPasswordInput.getText().toString();
                        System.out.println("User old password: " + oldPassword);
                    }
                });
                if (!focused) {
                    if (oldPasswordInput.getText().length() == 0) {
                        oldPasswordInput.setText(oldPasswordInput.getHint());
                    } else {
                        oldPassword = oldPasswordInput.getText().toString();
                    }
                }
            }
        });
        newPasswordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                newPasswordInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        newPassword = newPasswordInput.getText().toString();
                        System.out.println("User new password: " + newPassword);
                    }
                });
                if (!focused) {
                    if (newPasswordInput.getText().length() == 0) {
                        newPasswordInput.setText(newPasswordInput.getHint());
                    }
                }
            }
        });
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Old password: " + oldPassword + " New password " + newPassword);
                controller.changePassword(oldPassword,newPassword);
            }
        });
        newUsernameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                newUsernameInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        oldPassword = userInstance.getCurrentUser().getUsername();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        newUsername = newUsernameInput.getText().toString();
                        System.out.println("New username: " + newUsername);
                    }
                });
                if (!focused) {
                    if (newUsernameInput.getText().length() == 0) {
                        newUsernameInput.setText(newUsernameInput.getHint());
                    }
                }
            }
        });
        changeUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(newUsername, oldUsername)){
                    controller.changeUsername(newUsername);
                }
            }
        });
        return view;
    }
}