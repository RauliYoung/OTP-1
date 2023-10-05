package com.example.opt_1.model;

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
    void addUserToTheGroup(String groupOwnerEmail);
    void removeUserFromTheGroup(String groupOwnerEmail);
    Boolean getRegisterErrorCheck();
    void updateData();
    void createNewGroup(Group group, CRUDCallbacks callback);
    ArrayList<Map<String,ArrayList<Double>>> fetchGroupFromDatabase(String groupOwnerEmail);
    FirebaseFirestore getDatabase();
    FirebaseAuth getUser();
}
