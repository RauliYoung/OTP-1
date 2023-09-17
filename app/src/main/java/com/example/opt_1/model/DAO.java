package com.example.opt_1.model;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;

public class DAO implements IDAO{

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private FirebaseAuth auth = FirebaseAuth.getInstance();

   private boolean taskResult;
    @Override
    public void createUser(User user, CRUDCallbacks callback) {

        auth.createUserWithEmailAndPassword(user.getEmail().trim(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthInvalidCredentialsException err) {
                            System.out.println("BAD EMAIL FOR AUTH ACC");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }finally {
                            callback.onFailure();
                        }

                    } else {
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    callback.onSucceed(task.isSuccessful());
                                    System.out.println("Is signed in: " + auth.getCurrentUser().getEmail());
                                    auth.signOut();
                                    System.out.println("Is signed in: " + auth.getCurrentUser());
                                })
                                .addOnFailureListener(e -> callback.onFailure());
                    }

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
    public void addNewGroupToDatabase(String groupName) {
        String email = auth.getCurrentUser().getEmail();
        Group newGroup = new Group();
        db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(handleTask(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()){
                            User groupOwner = document.toObject(User.class);
                            newGroup.getGroup().add(groupOwner);
                            newGroup.setGroupOwner(groupOwner.getUsername());
                            newGroup.setGroupName(groupName);
                            System.out.println("Controller: " + document);
                        }
                        createNewGroup(newGroup, new CRUDCallbacks() {
                            @Override
                            public void onSucceed(boolean success) {
                                System.out.println("DAO Added a new group");
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
    public void addUserToTheGroup(String groupOwnerEmail) {
        DocumentReference docRef = db.collection("groups").document(groupOwnerEmail);
        System.out.println("Controller: " + docRef.getId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       db.collection("users").whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (handleTask(task)) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User currentUser = document.toObject(User.class);
                                        docRef.update("group", FieldValue.arrayUnion(currentUser));
                                    }
                                }
                            }
                        });
                    }
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
    public void createNewGroup(Group group, CRUDCallbacks callback) {
        DocumentReference docRef = db.collection("groups").document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getEmail()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Löytyy: " + document.getId());
                        callback.onFailure();
                    } else {
                        docRef.set(group, SetOptions.merge());
                        callback.onSucceed(true);
                    }
                } else {
                    System.out.println("Err: " + task.getException());
                }
            }
        });
    }

    @Override
    public FirebaseFirestore getDatabase() {
        return db;
    }

    @Override
    public Boolean handleTask(Task<QuerySnapshot> task) {
        if (task.isSuccessful()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public FirebaseAuth getUser() {
        return auth;
    }
}