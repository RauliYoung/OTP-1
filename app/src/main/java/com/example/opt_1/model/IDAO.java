package com.example.opt_1.model;

import com.example.opt_1.control.Controller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.Map;

public interface IDAO {
    void addNewExerciseToDatabase(Exercise exercise);
    void createUser2(Map<String, String> user, String password, CRUDCallbacks callbacks);
    void removeUser();
    void changePassword(String oldPassword, String newPassword);
    void checkIfUsernameExist(String newUsername, CRUDCallbacks callbacks);
    void loginUser(String email, String password, CRUDCallbacks callbacks);
    void addNewGroupToDatabase(String groupName);
    void addUserToTheGroup(String groupOwnerEmail, CRUDCallbacks callbacks);
    void fetchGroupFromDatabase(String groupOwnerEmail,CRUDCallbacks controllerCallback);
    void removeUserFromTheGroup(String groupOwnerEmail);
    Boolean getRegisterErrorCheck();
    void createNewGroup(Group group, CRUDCallbacks callback);
     Map<String, ArrayList<Double>> getGroupResults();
    
    FirebaseFirestore getDatabase();
    FirebaseAuth getUser();
}
