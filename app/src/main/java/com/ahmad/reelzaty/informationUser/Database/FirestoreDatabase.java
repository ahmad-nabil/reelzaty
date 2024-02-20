package com.ahmad.reelzaty.informationUser.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class FirestoreDatabase {
    FirebaseFirestore firestore;
    public  FirestoreDatabase(){
        firestore=FirebaseFirestore.getInstance();
    }
    public Task<Void>AddUser(String Uid, UserInfo data){
        return firestore.collection("users").document(Uid).set(data);
    }
    public Task<Void>UpdateUser(String Uid, HashMap <String ,Object>data){
        return firestore.collection("users").document(Uid).update(data);
    }

    public Task<DocumentSnapshot>getUser(String Uid){
        return firestore.collection("users").document(Uid).get();
    }
    public Task<QuerySnapshot>getAllUsers(){
        return firestore.collection("users").get();
    }
}
