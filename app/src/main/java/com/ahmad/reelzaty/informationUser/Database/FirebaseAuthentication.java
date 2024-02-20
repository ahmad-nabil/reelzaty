package com.ahmad.reelzaty.informationUser.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseAuthentication {
    FirebaseAuth authentication;

    public FirebaseAuthentication() {
        this.authentication = FirebaseAuth.getInstance();
    }
public Task <AuthResult>RegisterUser(String Email,String Password){
return authentication.createUserWithEmailAndPassword(Email,Password);
}
public Task <AuthResult>Login(String Email,String Password){
    return authentication.signInWithEmailAndPassword(Email,Password);


    }
    public Task <Void>restpassword(String Email){
        return authentication.sendPasswordResetEmail(Email);
    }
public void Signout(){
        authentication.signOut();
}
public Task<Void>UpdateEmail(String Email){
    return authentication.getCurrentUser().updateEmail(Email);

}
    public Task<Void>Updatepassword(String password){
        return authentication.getCurrentUser().updatePassword(password);
    }
   public FirebaseUser user(){
        return authentication.getCurrentUser();
   }

    public void getUserData( MutableLiveData<UserInfo> userData) {
FirestoreDatabase database=new FirestoreDatabase();
database.getUser(user().getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
if (task.isSuccessful()){
DocumentSnapshot documentSnapshot=task.getResult();
if (documentSnapshot!=null){
    UserInfo user=documentSnapshot.toObject(UserInfo.class);
    userData.setValue(user);
}
}
    }
});
    }
    public void getUserNameAndImg(Consumer<UserInfo>userInfoConsumer) {
        FirestoreDatabase database=new FirestoreDatabase();
        ArrayList<String> userData=new ArrayList<>();
        database.getUser(user().getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (documentSnapshot!=null){
                        UserInfo user=documentSnapshot.toObject(UserInfo.class);
                        userInfoConsumer.accept(user);
                    }
                }
            }
        });
    }
    public void getAllUsers(Consumer<ArrayList<UserInfo>>userInfoConsumer) {
        FirestoreDatabase database=new FirestoreDatabase();
        ArrayList<UserInfo> userData=new ArrayList<>();
database.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
            userData.add(snapshot.toObject(UserInfo.class));
        }
        userInfoConsumer.accept(userData);
    }
});
    }
}
