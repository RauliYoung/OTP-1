package com.example.opt_1.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
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

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DAO implements IDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User2 userInstance = User2.getInstance();

    private Map<String,ArrayList<Double>> groupExercisesSumList;

    private boolean taskResult;


    @Override
    public void addNewExerciseToDatabase(Exercise newExercise) {
        db.collection("users").whereEqualTo("email", Objects.requireNonNull(auth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = db.collection("users").document(document.getId());
                        CollectionReference userExercise = userRef.collection("exercises");
                        userExercise.add(newExercise);
                        retrieveExercises();
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
                                        ArrayList<Map<String,Object>> userInstanceArrayList = userInstance.getExercises();
                                        userInstanceArrayList.add(snapshot.getData());
                                        userInstance.setExercises(userInstanceArrayList);
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
    public void createUser2(Map<String,String> user, String password, CRUDCallbacks callbacks) {
        auth.createUserWithEmailAndPassword((String) Objects.requireNonNull(user.get("email")), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(handleTaskAuth(task)){
                    if (task.isSuccessful()) {
                        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                System.out.println("New User: " + documentReference.getId());
                                System.out.println("New User Success");
                                auth.signOut();
                                callbacks.onSucceed();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                callbacks.onFailure();
                            }
                        });
                    } else {
                        System.out.println("Something went wrong " + task.getException());
                    }
                }
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
                                    System.out.println("Poistettu user collection");
                                }
                            });
                        }
                    }
                }
            });
            auth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    System.out.println("Poistettu auth");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Ei poistettu auth");
                }
            });
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
    public void checkIfUsernameExist(String newUsername, CRUDCallbacks callbacks) {
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
                        changeUsername(newUsername, callbacks);
                    }
                }
            }
        });
    }



    private void changeUsername(String username, CRUDCallbacks callbacks){
        System.out.println("changeUsername()");
        db.collection("users").whereEqualTo("email", Objects.requireNonNull(auth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = db.collection("users").document(document.getId());
                        userRef.update("username", username);
                        userInstance.setUsername(username);
                        callbacks.onSucceed();

                    }
                }
            }
        });
    }
    @Override
    public void loginUser(String email, String password, CRUDCallbacks callbacks) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(handleTaskAuth(task)){
                    if(auth.getCurrentUser() != null){
                        System.out.println(auth.getCurrentUser()+" LOGGED IN");
                            db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (handleTaskQS(task)) {
                                        Map<String,Object> user = new HashMap<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            user = document.getData();
                                        }
                                        User2 userInstance = User2.getInstance();
                                        userInstance.setFirstName((String) user.get("firstName"));
                                        userInstance.setLastName((String) user.get("lastName"));
                                        userInstance.setUsername((String) user.get("username"));
                                        userInstance.setEmail((String) user.get("email"));
                                        boolean userInGroup = Boolean.parseBoolean((String) Objects.requireNonNull(user.get("userInGroup")));
                                        userInstance.setUserInGroup(userInGroup);
                                        System.out.println(userInstance);
                                        callbacks.onSucceed();
                                        retrieveExercises();
                                    }else{
                                        callbacks.onFailure();
                                    }
                                }
                            });
                    }
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
                            newGroup.getGroupOfUserEmails().add(userInstance.getEmail());
                            newGroup.setGroupOwner(userInstance.getUsername());
                            newGroup.setGroupName(groupName);
                        }
                        createNewGroup(newGroup, new CRUDCallbacks() {
                            @Override
                            public void onSucceed() {
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
                                        System.out.println("Document add user ID: " + document.getId());
                                        db.collection("users").document(document.getId()).update("userInGroup","true").addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    System.out.println("User in group: true");
                                                    docRef.update("groupOfUserEmails", FieldValue.arrayUnion(userInstance.getEmail())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                db.collection("users").document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if(task.isSuccessful()){
                                                                            DocumentSnapshot user = task.getResult();
                                                                            boolean userInGroup = Boolean.parseBoolean((String) Objects.requireNonNull(user.getData()).get("userInGroup"));
                                                                            userInstance.setUserInGroup(userInGroup);
                                                                            fetchGroupFromDatabase(docRef);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    private void fetchGroupFromDatabase(DocumentReference joinedGroupRef){
        joinedGroupRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(handleTaskDS(task)){
                    DocumentSnapshot document = task.getResult();
                    System.out.println("Group document: " + document);
                    Group group = document.toObject(Group.class);
                    System.out.println("Group: " + group);
                    if(group.getGroupOfUserEmails() != null){
                        for(String email : group.getGroupOfUserEmails()){
                            db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(handleTaskQS(task)){
                                        for(QueryDocumentSnapshot q : task.getResult()){
                                            Map<String,Object> user = q.getData();
                                            System.out.println("Username: " +  (String) user.get("username"));
                                            db.collection("users/" + q.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    double exerciseTime = 0;
                                                    double exerciseInMeters = 0;
                                                    for(QueryDocumentSnapshot q : task.getResult()){
                                                        Map<String,Object> usersExercises = q.getData();
                                                        LocalDateTime date = LocalDateTime.now();
                                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                                        if(Objects.equals(usersExercises.get("exerciseDate"), date.format(format))){
                                                            exerciseTime += Double.parseDouble(String.valueOf(Objects.requireNonNull(usersExercises.get("exerciseTime"))));
                                                            exerciseInMeters += Double.parseDouble(String.valueOf(Objects.requireNonNull(usersExercises.get("exerciseInMeters"))));
                                                        }
                                                    }
                                                    ArrayList<Double> resultList = new ArrayList<Double>();
                                                    resultList.add(exerciseTime);
                                                    resultList.add(exerciseInMeters);
                                                    Map<String,ArrayList<Double>> userResults = new HashMap<>();
                                                    userResults.put((String) user.get("username"),resultList);
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
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
                                        docRef.update("groupOfUserEmails", FieldValue.arrayRemove(userInstance.getEmail()));
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
                        callback.onSucceed();
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
    public Boolean handleTaskAuth(Task<AuthResult> task){
        if(task.isSuccessful()){
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