package com.example.opt_1.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

public class DAO implements IDAO{

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private FirebaseAuth auth = FirebaseAuth.getInstance();
   private FirebaseUser fireUser = auth.getCurrentUser();

   private boolean taskResult;
    @Override
    public void createUser(User user,RegistrationCallBack callback) {

        auth.createUserWithEmailAndPassword(user.getEmail().trim(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthInvalidCredentialsException err) {
                            System.out.println("BAD EMAIL FOR AUTH ACC");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                })
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                    }
                    callback.onRegistrationComplete(task.isSuccessful());
                });
}


    @Override
    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (auth.getCurrentUser() == null){
                            System.out.println("WRONG USERNAME/ AUTH NOT LOGGED IN!");
                        }else {
                            System.out.println(auth.getCurrentUser()+" LOGGED IN");
                        }
                    }

                });
    }
    @Override
    public Boolean getRegisterErrorCheck() {
        return taskResult;
    }
    @Override
    public void updateData() {

    }

    @Override
    public void createNewGroup(Group group) {
        group.setGroupOwner(auth.getCurrentUser().getEmail());
        CollectionReference groupRef = db.collection("groups");
        System.out.println("DAO group: " + group);
        db.collection("groups").document(auth.getCurrentUser().getEmail()).set(group, SetOptions.merge());
        //groupRef.add(group).addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()));
    }

    @Override
    public FirebaseFirestore getDatabase() {
        return db;
    }

    @Override
    public FirebaseAuth getUser() {
        return auth;
    }
}