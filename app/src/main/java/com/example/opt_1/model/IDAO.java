package com.example.opt_1.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public interface IDAO {
    void createUser(User user, CRUDCallbacks callBack) ;
    void loginUser(String email, String password);
    void addNewGroupToDatabase(String groupName);
    void addUserToTheGroup(String groupOwnerEmail);
    Boolean getRegisterErrorCheck();
    void updateData();
    void createNewGroup(Group group, CRUDCallbacks callback);
    FirebaseFirestore getDatabase();
    Boolean handleTask(Task<QuerySnapshot> task);
    FirebaseAuth getUser();
}
