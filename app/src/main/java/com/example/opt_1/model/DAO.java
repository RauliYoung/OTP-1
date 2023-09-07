package com.example.opt_1.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public class DAO implements IDAO{

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private FirebaseAuth auth = FirebaseAuth.getInstance();
   private FirebaseUser fireUser = auth.getCurrentUser();

    @Override
    public void createUser(User user){

     Query qs = db.collection("users").whereEqualTo("email",user.getEmail());
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println(document.getData() + "TÄSSÄ DATAA");
                        }
                    } else {
                        System.out.println("EIHÄN TÄSTÄ TULLUT MITÄÄN");
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