package com.example.opt_1.control;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.opt_1.model.DAO;
import com.example.opt_1.model.Group;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

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
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback) {
        database.createUser(new User(firstName, lastName, username, email, password, callback), callback);
    }

    @Override
    public void makeNewGroup(String groupName) {

        String email = database.getUser().getCurrentUser().getEmail();
        Group newGroup = new Group();
        database.getDatabase().collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(database.handleTask(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User groupOwner = document.toObject(User.class);
                        newGroup.getGroup().add(groupOwner);
                        newGroup.setGroupOwner(groupOwner.getUsername());
                        newGroup.setGroupName(groupName);

                        database.createNewGroup(newGroup, new CRUDCallbacks() {
                            @Override
                            public void onSucceed(boolean success) {
                                System.out.println("Added a new group");
                            }

                            @Override
                            public void onFailure() {
                                System.out.println("Error while creating a group");
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void joinToGroup(String groupOwnerEmail) {
        //Etsi firestoresta ryhmä email-osoitteella
        //Yritä liittyä ryhmään, jos käyttäjä ei ole jo ryhmässä.
        DocumentReference docRef = database.getDatabase().collection("groups").document(groupOwnerEmail);
        System.out.println("Controller: " + docRef.getId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Group group = document.toObject(Group.class);
                    }
                }
            }
        });
    }
}