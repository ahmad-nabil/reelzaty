package com.ahmad.reelzaty.userSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ahmad.reelzaty.Home;
import com.ahmad.reelzaty.R;
import com.ahmad.reelzaty.adapter.AdapterProfile;
import com.ahmad.reelzaty.databinding.ActivityProfileBinding;
import com.ahmad.reelzaty.databinding.NovideosLayoutBinding;
import com.ahmad.reelzaty.informationUser.AccModel;
import com.ahmad.reelzaty.informationUser.Database.RealtimeDatabase;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.function.Consumer;

public class profile extends AppCompatActivity {
ActivityProfileBinding profileBinding;
AccModel accModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize Live model Component
        profileBinding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        accModel=new ViewModelProvider(this).get(AccModel.class);
        profileBinding.setLifecycleOwner(this);
        profileBinding.setModelprofile(accModel);
      //get img src
        accModel.getData().observe(this, userInfo -> Glide.with(profile.this).load(userInfo.UriImg).into(profileBinding.imageView4));
    CheckVideos();
    //home button
    profileBinding.barprofile.setNavigationOnClickListener(v->{
        startActivity(new Intent(profile.this, Home.class));
        finish();
    });
    }

    private void CheckVideos() {
        loadingScren();
        RealtimeDatabase realtimeDatabase=new RealtimeDatabase();
  realtimeDatabase.getvideosUser(new Consumer<ArrayList<String>>() {
      @Override
      public void accept(ArrayList<String> strings) {
          profileBinding.Frames.removeAllViews();
          AdapterProfile profile = new AdapterProfile(profile.this,strings);
          RecyclerView Rv = new RecyclerView(profile.this);
          LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
          Rv.setLayoutParams(layoutParams);
          Rv.setLayoutManager(new LinearLayoutManager(profile.this, LinearLayoutManager.VERTICAL, false));
          Rv.setAdapter(profile);
          profileBinding.Frames.addView(Rv);
      }
  }, new Consumer<Boolean>() {
      @Override
      public void accept(Boolean aBoolean) {
if (!aBoolean){
    profileBinding.Frames.removeAllViews();
    NovideosLayoutBinding binding=NovideosLayoutBinding.inflate(getLayoutInflater());
    profileBinding.Frames.addView(binding.getRoot());
}
      }
  });
    }

    private void loadingScren() {
        ProgressBar progressBar=new ProgressBar(this, null,android.R.attr.progressBarStyle);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParams.setMargins(50,50,50,50);
       layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(layoutParams);
        profileBinding.Frames.addView(progressBar);
    }
}