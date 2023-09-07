package com.example.opt_1.model;
import com.google.firebase.firestore.FirebaseFirestore;

public class DAO implements IDAO{


    @Override
    public void createData(int userid){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    @Override
    public void updateData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}

//Example for putting users to collection.
     /*   db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e))*/;