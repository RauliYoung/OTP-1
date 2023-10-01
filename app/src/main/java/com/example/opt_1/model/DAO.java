package com.example.opt_1.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.opt_1.control.CurrentUserInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DAO implements IDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CurrentUserInstance userInstance = CurrentUserInstance.getINSTANCE();


    private boolean taskResult;


    @Override
    public void addNewExerciseToDatabase(Exercise newExercise) {
        db.collection("users").whereEqualTo("email", Objects.requireNonNull(auth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = db.collection("users").document(document.getId());
                        LocalDateTime date = LocalDateTime.now();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formatDateTime = date.format(format);
                        Map<String,Object> exercise = new HashMap<>();
                        exercise.put(formatDateTime,newExercise);
                        userRef.collection("exercises").add(exercise);


                    }
                }
            }
        });
    }
    private void retrieveExercises(){
        Query usersExercises = db.collection("users").whereEqualTo("email", auth.getCurrentUser().getEmail());
        usersExercises.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(handleTaskQS(task)){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("DAO USER: " + document.getId());
                        db.collection("users/" + document.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(handleTaskQS(task)){
                                    for (QueryDocumentSnapshot snapshot : task.getResult()){

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
                        } finally {
                            callback.onFailure();
                        }

                    } else {
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    callback.onSucceed(task.isSuccessful());
                                    auth.signOut();
                                })
                                .addOnFailureListener(e -> callback.onFailure());
                    }
                });
    }

    @Override
    public void removeUser() {
        try {
            DocumentReference docRefGroup = db.collection("groups").document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getEmail()));

            docRefGroup.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (handleTaskDS(task)) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            docRefGroup.delete();
                        } else {
                            System.out.println("Group doesn't exist");
                        }
                    } else {
                        System.out.println("Err: " + task.getException());
                    }
                }
            });
            Query usersExercises = db.collection("users").whereEqualTo("email", auth.getCurrentUser().getEmail());
            usersExercises.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(handleTaskQS(task)){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("DAO USER: " + document.getId());
                             db.collection("users/" + document.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(handleTaskQS(task)){
                                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                                            db.collection("users/" + document.getId() +"/exercises").document(snapshot.getId()).delete();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });

            Query usersCollectionRef = db.collection("users").whereEqualTo("email", auth.getCurrentUser().getEmail());
            usersCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (handleTaskQS(task)) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("DAO USER: " + document.getId());
                            DocumentReference userRef = db.collection("users").document(document.getId());
                            userRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("Poistettu?");
                                }
                            });
                        }
                    }
                }
            });
            auth.getCurrentUser().delete();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getEmail()), oldPassword);

        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Password has been changed");
                            db.collection("users").whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (handleTaskQS(task)) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            DocumentReference userRef = db.collection("users").document(document.getId());
                                            userRef.update("password", newPassword);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void checkIfUsernameExist(String newUsername) {
        Query usernames = db.collection("users").whereEqualTo("username", newUsername);
        usernames.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    if(!querySnapshot.isEmpty()){
                        System.out.println("Username is already on use");
                    }else {
                        System.out.println("Username is available to use");
                        changeUsername(newUsername);
                    }
                }
            }
        });
    }
    private void changeUsername(String username){
        System.out.println("changeUsername()");
        db.collection("users").whereEqualTo("email", Objects.requireNonNull(auth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = db.collection("users").document(document.getId());
                        userRef.update("username", username);
                        userInstance.getCurrentUser().setUsername(username);
                    }
                }
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
                            db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (handleTaskQS(task)) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            User currentUser = document.toObject(User.class);
                                            userInstance.setCurrentUser(currentUser);
                                        }
                                    }
                                }
                            });
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
                if(handleTaskQS(task)) {
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
                                if (handleTaskQS(task)) {
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
    public void removeUserFromTheGroup(String groupOwnerEmail) {
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
                                if (handleTaskQS(task)) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User currentUser = document.toObject(User.class);
                                        docRef.update("group", FieldValue.arrayRemove(currentUser));
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

    public Boolean handleTaskQS(Task<QuerySnapshot> task) {
        if (task.isSuccessful()){
            return true;
        }else{
            return false;
        }
    }
    public Boolean handleTaskDS(Task<DocumentSnapshot> task) {
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