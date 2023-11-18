package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private TextView usernameTextField;
    private EditText oldPasswordInput,newPasswordInput,newUsernameInput;
    private Controller controller;
    private View v;
    private Button logOutButton,removeUserBtn,changePasswordBtn,changeUsernameBtn;

    private String oldPassword,newPassword, newUsername, oldUsername;
    private User userInstance;

    public HomeFragment(){
        controller = new Controller();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userInstance = User.getInstance();
        v = inflater.inflate(R.layout.fragment_home,container,false);
        logOutButton = (Button) v.findViewById(R.id.logOut_Button);
        removeUserBtn = v.findViewById(R.id.removeUserButton);
        changePasswordBtn = v.findViewById(R.id.changePasswordBtn);
        changeUsernameBtn = v.findViewById(R.id.changeUsernameBtn);

        usernameTextField = v.findViewById(R.id.usernameTextField);
        oldPasswordInput = v.findViewById(R.id.oldPasswordInputField);
        newPasswordInput = v.findViewById(R.id.newPasswordInputField);
        newUsernameInput = v.findViewById(R.id.newUsernameInputField);
        usernameTextField = v.findViewById(R.id.usernameTextField);

        try {
            usernameTextField.setText(userInstance.getUsername());
        }catch (NullPointerException e){
            usernameTextField.setText("NULL");
        }


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        removeUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.removeUser();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
            }
        });
        oldPasswordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean focused) {
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
                        oldUsername = userInstance.getUsername();
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
                    controller.changeUsername(newUsername, new CRUDCallbacks() {
                        @Override
                        public void onSucceed() {
                            usernameTextField.setText(userInstance.getUsername());
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }
            }
        });

        return v;

    }
}
