package com.example.opt_1.model;
import java.util.ArrayList;
import java.util.Map;

/**
 * The IDAO interface defines the methods for the CRUD operations
 */
public interface IDAO {
    void addNewExerciseToDatabase(Exercise exercise);
    void createUser(Map<String, String> user, String password, CRUDCallbacks callbacks);
    void removeUser();
    void changePassword(String oldPassword, String newPassword);
    void checksIfUsernameExist(String newUsername, CRUDCallbacks callbacks);
    void loginUser(String email, String password, CRUDCallbacks callbacks);
    void addNewGroupIntoDatabase(String groupName);
    void addUserToTheGroup(String groupOwnerEmail, CRUDCallbacks callbacks);
    void fetchGroupFromDatabase(String groupOwnerEmail,CRUDCallbacks controllerCallback);
    void removeUserFromTheGroup(String groupOwnerEmail);
    void createNewGroup(Group group, CRUDCallbacks callback);
    Map<String, ArrayList<Double>> getGroupResults();

}
