package com.example.opt_1.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public interface IDAO {
    void createUser(User user, RegistrationCallBack callBack) ;
    void loginUser(String email, String password);
    Boolean getRegisterErrorCheck();
    void updateData();
    void createNewGroup(Group group);
    FirebaseFirestore getDatabase();

    FirebaseAuth getUser();
}
