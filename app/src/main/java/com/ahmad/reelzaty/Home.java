package com.ahmad.reelzaty;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ahmad.reelzaty.CustomObjects.AddressVideoObject;
import com.ahmad.reelzaty.informationUser.AuthManager;
import com.ahmad.reelzaty.adapter.AdapterHome;
import com.ahmad.reelzaty.databinding.ActivityHomeBinding;
import com.ahmad.reelzaty.informationUser.AccountData;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.informationUser.Database.RealtimeDatabase;
import com.ahmad.reelzaty.userSection.profile;
import com.ahmad.reelzaty.userSection.searchResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Home extends AppCompatActivity {
ActivityHomeBinding Homebinding;
Uri VideoUri;
FirebaseAuthentication authentication;
    RealtimeDatabase realtimeDatabase=new RealtimeDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Homebinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(Homebinding.getRoot());
        authentication=new FirebaseAuthentication();
        //notficAation btn
        Homebinding.notfibtn.setOnClickListener(v->{
            NotifFragment fragment=new NotifFragment();

            fragment.show(getSupportFragmentManager(),null);
        });
        //share btn
Homebinding.bar.setNavigationOnClickListener(v->{
share();
});
Homebinding.bottomNavigationView.setOnItemSelectedListener(this::ListernBottomNav);
//get videos for alll user
RetrieveVideos();
looding();
//search bar
        Homebinding.searchbarHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(Home.this, searchResult.class).putExtra("search",query));
               return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void RetrieveVideos() {
         realtimeDatabase.getvideos(new Consumer<ArrayList<AddressVideoObject>>() {
             @Override
             public void accept(ArrayList<AddressVideoObject> addressVideoObjects) {
                 AdapterHome home = new AdapterHome(addressVideoObjects, Home.this);
                 RecyclerView Rv = new RecyclerView(Home.this);
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                 Rv.setLayoutParams(layoutParams);
                 Rv.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.VERTICAL, false));
                 Rv.setAdapter(home);
                 Rv.setBackgroundColor(Color.BLACK);
                 Homebinding.liner.removeAllViews();
                 Homebinding.liner.addView(Rv);
             }
         }, new Consumer<Boolean>() {
             @Override
             public void accept(Boolean aBoolean) {
if (!aBoolean){

    Homebinding.liner.removeAllViews();
    View view=LayoutInflater.from(Home.this).inflate(R.layout.novideos_layout, Homebinding.liner,false);
    Homebinding.liner.addView(view);
}
             }
         });
    }

    private boolean ListernBottomNav(MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.AccData){
startActivity(new Intent(this, AccountData.class));
        }
        if (menuItem.getItemId()==R.id.logout){
            FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication();
            firebaseAuthentication.Signout();
            startActivity(new Intent(this, AuthManager.class));
            finish();
        }
        if (menuItem.getItemId()==R.id.sharevideo){
            PostVideo();
        }
        if (menuItem.getItemId()==R.id.profilebtn){
        startActivity(new Intent(this, profile.class));
            finish();
        }
return true;
    }

    private void share() {
        Intent send=new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT,"join to us download reelzaty from app store");
        send.setType("text/plain");
        Intent share=Intent.createChooser(send,null);
        startActivity(share);
    }

//pst video and upload it to database
    private void PostVideo() {
        Intent UploadVideo=new Intent();
        UploadVideo.setAction(Intent.ACTION_OPEN_DOCUMENT);
        UploadVideo.addCategory(Intent.CATEGORY_OPENABLE);
        UploadVideo.setType("video/*");
uploadvideo.launch(UploadVideo);
    }
    ActivityResultLauncher<Intent>uploadvideo=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                // Get the selected video URI
                VideoUri=o.getData().getData();
                // Upload the video to Firebase Storage
                UploadToStorage(VideoUri);
            }

        }

        private void UploadToStorage(Uri videoUri) {
            String uid=authentication.user().getUid();
            String filename=uid+"/"+System.currentTimeMillis()+uid+".mp4";
            StorageReference videoRef= FirebaseStorage.getInstance().getReference().child(filename);
            videoRef.putFile(videoUri).addOnSuccessListener(taskSnapshot -> {
                videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                realtimeDatabase.AddVideo(uri.toString());
                realtimeDatabase.AddallVideo(uri.toString());

});
            });
        }
    });
    public void looding(){
        Homebinding.liner.removeAllViews();
        ProgressBar progressBar=new ProgressBar(this,null, android.R.attr.progressBarStyle);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50,50,50,50);
    progressBar.setLayoutParams(layoutParams);
Homebinding.liner.setGravity(Gravity.CENTER);
Homebinding.liner.addView(progressBar);
    }
}