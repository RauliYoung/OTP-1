package com.example.opt_1.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

public class DAO implements IDAO{

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private FirebaseAuth auth = FirebaseAuth.getInstance();
   private FirebaseUser fireUser = auth.getCurrentUser();

    @Override
    public void createUser(User user){
        auth.createUserWithEmailAndPassword(user.getEmail().trim(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                        System.out.println("AUTHUSERCREATED "+ task.getResult());

                    }

                });

//     Query qs = db.collection("users").whereEqualTo("email",user.getEmail());
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            System.out.println(document.getData() + "TÄSSÄ DATAA");
//                        }
//                    } else {
//                        System.out.println("EIHÄN TÄSTÄ TULLUT MITÄÄN");
//                    }
//                });

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
    public void updateData() {

    }

//Example for putting users to collection.
     /*   db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e))*/;
}