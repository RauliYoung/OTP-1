package com.example.opt_1.model;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * DAO class represents CRUD operations of application
 *
 */
public class DAO implements IDAO {

    /**
     * A variable for the database instance
     */
    private FirebaseFirestore databaseInstance = FirebaseFirestore.getInstance();
    /**
     * A variable for the database auth instance
     */
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    /**
     * A variable for the user instance
     */
    private User userInstance = User.getInstance();
    /**
     * A list variable for the exercise group results
     *
     */
    private Map<String,ArrayList<Double>> groupResults = null;
    /**
     * A variable for participants count
     */
    private int emailCount = 0;
    /**
     * A variable for sum of exercise time for the day
     */
    private double exerciseTime = 0;
    /**
     * A variable for sum of exercise meters for the day
     */
    private double exerciseInMeters = 0;

    /**
     * Method adds a new exercise data to the database
     * This method is called by controller after the user has stopped the activity
     * @param newExercise The exercise data to be added to the database
     */
    @Override
    public void addNewExerciseToDatabase(Exercise newExercise) {
        databaseInstance.collection("users").whereEqualTo("email", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = databaseInstance.collection("users").document(document.getId());
                        CollectionReference userExercise = userRef.collection("exercises");
                        userExercise.add(newExercise);
                        retrieveExercises();
                    }
                }
            }
        });
    }
    /**
     * Method retrieves the user exercises from database and updates the user's instance
     */
    private void retrieveExercises(){
        Query usersExercises = databaseInstance.collection("users").whereEqualTo("email", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
        usersExercises.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(handleTaskQS(task)){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        databaseInstance.collection("users/" + document.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    /**
     * Method adds a new user to the database
     * @param user A Hashmap containing username, email, first name and last name
     * @param password The password provided in the registration form
     * @param callbacks Callback verifies that the user has been created
     */
    @Override
    public void createUser(Map<String,String> user, String password, CRUDCallbacks callbacks) {
        firebaseAuth.createUserWithEmailAndPassword((String) Objects.requireNonNull(user.get("email")), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(handleTaskAuth(task)){
                    if (task.isSuccessful()) {
                        databaseInstance.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                firebaseAuth.signOut();
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
                        System.out.println("Something went wrong while creating new user " + task.getException());
                    }
                }
            }
        });
    }

    /**
     * Method deletes a user and associated data from the database
     */
    @Override
    public void removeUser() {
        try {
            DocumentReference docRefGroup = databaseInstance.collection("groups").document(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()));

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
            Query usersExercises = databaseInstance.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail());
            usersExercises.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(handleTaskQS(task)){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                             databaseInstance.collection("users/" + document.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(handleTaskQS(task)){
                                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                                            databaseInstance.collection("users/" + document.getId() +"/exercises").document(snapshot.getId()).delete();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });

            Query usersCollectionRef = databaseInstance.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail());
            usersCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (handleTaskQS(task)) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DocumentReference userRef = databaseInstance.collection("users").document(document.getId());
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
            firebaseAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    /**
     * Method verifies login credentials and updates the user's password
     * @param oldPassword The old password provided in the main page form
     * @param newPassword The new password provided in the main page form
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()), oldPassword);

        try {
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Password has been changed");
                                databaseInstance.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (handleTaskQS(task)) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                DocumentReference userRef = databaseInstance.collection("users").document(document.getId());
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
        }catch (NullPointerException err){
            err.printStackTrace();
        }
    }

    /**
     * Method checks if new username is already in use
     * @param newUsername The new username provided in the main page form
     * @param callbacks callbacks ensures that the username check has been processed
     */
    @Override
    public void checksIfUsernameExist(String newUsername, CRUDCallbacks callbacks) {
        Query usernames = databaseInstance.collection("users").whereEqualTo("username", newUsername);
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

    /**
     * Method is called after checksIfUsernameExist has been passed successful and changes the username to new one
     * @param username The username provided in the main page form
     * @param callbacks callbacks ensures that the username has been changed
     */
    private void changeUsername(String username, CRUDCallbacks callbacks){
        System.out.println("changeUsername()");
        databaseInstance.collection("users").whereEqualTo("email", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference userRef = databaseInstance.collection("users").document(document.getId());
                        userRef.update("username", username);
                        userInstance.setUsername(username);
                        callbacks.onSucceed();

                    }
                }
            }
        });
    }

    /**
     * Method attempts to login with user email and password in Firebase auth, and if it passed
     * method sets the user into instance and changes the view to main view
     * @param email The user's email from login page form
     * @param password The user's password from the login page form
     * @param callbacks callbacks ensures that the login has been processed
     */
    @Override
    public void loginUser(String email, String password, CRUDCallbacks callbacks) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(handleTaskAuth(task)){
                        System.out.println(firebaseAuth.getCurrentUser()+" LOGGED IN");
                        databaseInstance.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (handleTaskQS(task)) {
                                    Map<String,Object> user = new HashMap<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        user = document.getData();
                                    }
                                    User userInstance = User.getInstance();
                                    userInstance.setFirstName((String) user.get("firstName"));
                                    userInstance.setLastName((String) user.get("lastName"));
                                    userInstance.setUsername((String) user.get("username"));
                                    userInstance.setEmail((String) user.get("email"));
                                    userInstance.setGroup((String) user.get("group"));
                                    boolean userInGroup = Boolean.parseBoolean((String) Objects.requireNonNull(user.get("userInGroup")));
                                    userInstance.setUserInGroup(userInGroup);
                                    System.out.println("User instance: " + userInstance);
                                    retrieveExercises();
                                    callbacks.onSucceed();
                                }else{
                                    callbacks.onFailure();
                                }
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callbacks.onFailure();
                }
            });
        }catch (IllegalArgumentException err){
            err.printStackTrace();
            callbacks.onFailure();
        }
    }

    /**
     * Method creates and adds a new group to the database
     * @param groupName User-given name for the group from group page form
     */
    @Override
    public void addNewGroupIntoDatabase(String groupName) {
        String email = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        Group newGroup = new Group();
        databaseInstance.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                addUserToTheGroup(userInstance.getEmail(), new CRUDCallbacks() {
                                    @Override
                                    public void onSucceed() {
                                        System.out.println("New group and owner updated");
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
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

    /**
     * Method adds the user to a specific exercise group by group owner's email
     * @param groupOwnerEmail The email of the group owner, obtained from the group page form
     * @param callbacks callbacks ensures that the user has been added group
     */
    @Override
    public void addUserToTheGroup(String groupOwnerEmail , CRUDCallbacks callbacks) {
        DocumentReference docRef = databaseInstance.collection("groups").document(groupOwnerEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       databaseInstance.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (handleTaskQS(task)) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        databaseInstance.collection("users").document(document.getId()).update("userInGroup","true","group",groupOwnerEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    docRef.update("groupOfUserEmails", FieldValue.arrayUnion(userInstance.getEmail())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                databaseInstance.collection("users").document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if(task.isSuccessful()){
                                                                            DocumentSnapshot user = task.getResult();
                                                                            boolean userInGroup = Boolean.parseBoolean((String) Objects.requireNonNull(user.getData()).get("userInGroup"));
                                                                            userInstance.setUserInGroup(userInGroup);
                                                                            callbacks.onSucceed();
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        e.printStackTrace();
                                                                        callbacks.onFailure();
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

    /**
     * Gets the exercise results
     * @return Returns a HashMap containing results
     */
    @Override
    public Map<String, ArrayList<Double>> getGroupResults() {
        return groupResults;
    }

    /**
     * Method attempts to fetch a group by name from database
     * @param groupOwnerEmail The email of the group owner, obtained from the group page form
     * @param controllerCallback controllerCallbacks ensures that the group has been found
     */
    @Override
    public void fetchGroupFromDatabase(String groupOwnerEmail, CRUDCallbacks controllerCallback){
        groupResults = new HashMap<>();
        DocumentReference joinedGroupRef = databaseInstance.collection("groups").document(groupOwnerEmail);
        joinedGroupRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(handleTaskDS(task)){
                    DocumentSnapshot document = task.getResult();
                    Group group = document.toObject(Group.class);
                    try{
                        if(group.getGroupOfUserEmails() != null){
                            fetchGroupParticipants(group, new CRUDCallbacks() {
                                @Override
                                public void onSucceed() {
                                    System.out.println("Testi: " + groupResults);
                                    controllerCallback.onSucceed();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * Method attempts to fetch all users of the group from the database
     * @param group The group information
     * @param secondCallback secondCallback ensures that the group members are found
     */
    private void fetchGroupParticipants(Group group, CRUDCallbacks secondCallback) {

        for(String email : group.getGroupOfUserEmails()) {
            databaseInstance.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (handleTaskQS(task)) {
                        for (QueryDocumentSnapshot q : task.getResult()) {
                            Map<String, Object> user = q.getData();

                            fetchExerciseResults(q,user, new CRUDCallbacks() {
                                @Override
                                public void onSucceed() {
                                    emailCount++;
                                    exerciseTime = 0;
                                    exerciseInMeters = 0;
                                    if(emailCount == group.getGroupOfUserEmails().size()){
                                        secondCallback.onSucceed();
                                        emailCount = 0;
                                    }
                                }
                                @Override
                                public void onFailure() {}
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * Method attempts to fetch all exercises of particular user from the database
     * @param queryDocumentSnapshot A user reference document from the database
     * @param user The user information
     * @param callbacks callbacks ensures that all the exercises has been retrieved
     */
    private void fetchExerciseResults(QueryDocumentSnapshot queryDocumentSnapshot, Map<String, Object> user, CRUDCallbacks callbacks) {
        ArrayList<Double> exerciseResults = new ArrayList<>();
        databaseInstance.collection("users/" + queryDocumentSnapshot.getId() + "/exercises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (handleTaskQS(task)) {
                    for (QueryDocumentSnapshot queryRes : task.getResult()){
                        LocalDateTime date = LocalDateTime.now();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        if(Objects.equals(queryRes.get("exerciseDate"), date.format(format))){
                            exerciseTime += Double.parseDouble(String.valueOf(Objects.requireNonNull(queryRes.get("exerciseTime"))));
                            exerciseInMeters += Double.parseDouble(String.valueOf(Objects.requireNonNull(queryRes.get("exerciseInMeters"))));
                        }
                    }
                    //Index 0 = sum of exercise times
                    exerciseResults.add(exerciseTime);
                    //Index 1 = sum of exercise meters
                    exerciseResults.add(exerciseInMeters);
                    System.out.println("EXERLIST" + exerciseResults);
                    groupResults.put((String) user.get("username"),exerciseResults);
                    callbacks.onSucceed();
                }
            }
        });
    }

    /**
     * Method removes the user from the group by group owner email
     * @param groupOwnerEmail The email of the group owner, obtained from the group page form
     */
    @Override
    public void removeUserFromTheGroup(String groupOwnerEmail) {
        DocumentReference docRef = databaseInstance.collection("groups").document(groupOwnerEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        databaseInstance.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    /**
     * Method creates a new group and sets the group ID to be as the user's email
     * @param group The group information
     * @param callback callback ensures that the user has been removed from the group
     */
    @Override
    public void createNewGroup(Group group, CRUDCallbacks callback) {
        DocumentReference docRef = databaseInstance.collection("groups").document(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
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

    /**
     * Method checks if a specific task has been completed
     * @param task The task or action to be checked for completion
     * @return boolean value
     */
    public Boolean handleTaskQS(Task<QuerySnapshot> task) {
        return task.isSuccessful();
    }
    /**
     * Method checks if a specific task has been completed
     * @param task The task or action to be checked for completion
     * @return boolean value
     */
    public Boolean handleTaskDS(Task<DocumentSnapshot> task) {
        return task.isSuccessful();
    }
    /**
     * Method checks if a specific task has been completed
     * @param task The task or action to be checked for completion
     * @return boolean value
     */
    public Boolean handleTaskAuth(Task<AuthResult> task){
        return task.isSuccessful();
    }
    }