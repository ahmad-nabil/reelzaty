package com.ahmad.reelzaty.informationUser.Database;

import androidx.annotation.NonNull;

import com.ahmad.reelzaty.CustomObjects.AddressVideoObject;
import com.ahmad.reelzaty.CustomObjects.NotficationCustomObject;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class RealtimeDatabase {
    FirebaseDatabase firebaseDatabase;
FirebaseAuthentication authentication;
    public RealtimeDatabase() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        authentication=new FirebaseAuthentication();

    }
    public Task<Void>AddVideo(String Url){
        DatabaseReference reference= firebaseDatabase.getReference();
        String uid=authentication.user().getUid();
        return reference.child("videos").child(uid).child(String.valueOf(System.currentTimeMillis())).setValue(Url);
    }
    public void AddallVideo(String Url){
        DatabaseReference reference= firebaseDatabase.getReference();
        String uid=authentication.user().getUid();
        AddressVideoObject videoObject=new AddressVideoObject();
        authentication.getUserNameAndImg(new Consumer<UserInfo>() {
            @Override
            public void accept(UserInfo userInfo) {
                videoObject.imguser=userInfo.UriImg;
                videoObject.nameuser=userInfo.nameUser;
                videoObject.videoUrl=Url;
                reference.child("videos").child("allvideo").child(uid).child(String.valueOf(System.currentTimeMillis())).setValue(videoObject);
            }
        });

    }

    public Task<Void>Addusers(){
        DatabaseReference reference= firebaseDatabase.getReference();
        String uid=authentication.user().getUid();
        return reference.child("users").setValue(uid);
    }
    public Task<Void>AddNotfications(NotficationCustomObject customObject){
        DatabaseReference reference= firebaseDatabase.getReference();

        String uid=authentication.user().getUid();
if (uid!=null){
    return reference.child("notification").child(uid).child(String.valueOf(System.currentTimeMillis())).setValue(customObject);
}else {
    return null;
}}

        public void getNotfications(final Consumer<ArrayList<NotficationCustomObject>>listConsumer,final Consumer<Boolean>gotvalues){
            DatabaseReference reference= firebaseDatabase.getReference();
            String uid=authentication.user().getUid();
            ArrayList<NotficationCustomObject>notifilist=new ArrayList<>();

            reference.child("notification").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot s:snapshot.getChildren()) {
                            notifilist.add(s.getValue(NotficationCustomObject.class));
                        }
                        listConsumer.accept(notifilist);
                        gotvalues.accept(true);
                    }else {
                        gotvalues.accept(false);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
    public void getvideos(final Consumer<ArrayList<AddressVideoObject>> successValue,final Consumer<Boolean> gotvalue){
        DatabaseReference reference= firebaseDatabase.getReference();
        ArrayList<AddressVideoObject>list=new ArrayList<>();
        reference.child("videos").child("allvideo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists()){
                 for (DataSnapshot user:snapshot.getChildren()) {
                     for (DataSnapshot videoSnapshot : user.getChildren()) {
                         list.add(videoSnapshot.getValue(AddressVideoObject.class));
                     }
                 }
                 successValue.accept(list);
                 gotvalue.accept(true);
             }else {
                 gotvalue.accept(false);
             }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getvideosUser(final Consumer<ArrayList<String>> successValue,final Consumer<Boolean> gotvalue){
        DatabaseReference reference= firebaseDatabase.getReference();
        String uid=authentication.user().getUid();
        ArrayList<String>list=new ArrayList<>();
        reference.child("videos").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&&snapshot.getValue()!=null){
                    for (DataSnapshot s:snapshot.getChildren()) {
                        list.add(s.getValue(String.class));
                    }
                    successValue.accept(list);
                    gotvalue.accept(true);
                }else {
                    gotvalue.accept(false);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
