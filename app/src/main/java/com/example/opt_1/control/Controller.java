package com.example.opt_1.control;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.opt_1.model.DAO;
import com.example.opt_1.model.Group;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.IGroup;
import com.example.opt_1.model.IUser;
import com.example.opt_1.model.RegistrationCallBack;
import com.example.opt_1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Controller implements IModeltoView,IViewtoModel {

    private IDAO database = new DAO();

    private String loginInfoUsername;
    private String loginInfoPassword;

    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername, loginInfoPassword);
    }

    @Override
    public void startActivity() {
        System.out.println("Activity Starting!");
    }

    @Override
    public void stopActivity() {
        System.out.println("Activity Stopping!");
    }

    @Override
    public void getLoginInfo() {

    }

    @Override
    public Boolean getRegisterInfo() {
        return database.getRegisterErrorCheck();
    }

    @Override
    public void setLoginInformation(String usernameInput, String passwordInput) {
        loginInfoPassword = passwordInput;
        loginInfoUsername = usernameInput;
    }

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, RegistrationCallBack callback) {
        database.createUser(new User(firstName, lastName, username, email, password, callback), callback);
    }

    @Override
    public void makeNewGroup(String groupName) {

        String email = database.getUser().getCurrentUser().getEmail();
        Group voittaja = new Group();
        database.getDatabase().collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User groupOwner = document.toObject(User.class);
                        //TODO Take this user to group owner
                        voittaja.getGroup().add(groupOwner);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}