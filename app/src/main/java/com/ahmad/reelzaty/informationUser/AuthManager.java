package com.ahmad.reelzaty.informationUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ahmad.reelzaty.userSection.profile;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.adapter.AdapterTap;
import com.ahmad.reelzaty.databinding.ActivityAuthManagerBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class AuthManager extends AppCompatActivity {

ActivityAuthManagerBinding authManagerBinding;
AdapterTap adaptertap;

    @Override
    protected void onStart() {
        FirebaseAuthentication authentication=new FirebaseAuthentication();
        if (authentication.user()!=null){
            startActivity(new Intent(this, profile.class));
            finish();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authManagerBinding=ActivityAuthManagerBinding.inflate(getLayoutInflater());
        setContentView(authManagerBinding.getRoot());
        adaptertap=new AdapterTap(getSupportFragmentManager(),getLifecycle());
        Login login=new Login();
        Signup signup=new Signup();
adaptertap.add(login,"Login");
adaptertap.add(signup,"signup");
authManagerBinding.vpagerAuth.setAdapter(adaptertap);
        new TabLayoutMediator(authManagerBinding.authTap,authManagerBinding.vpagerAuth,(tab, position) -> tab.setText(adaptertap.title(position))).attach();
    }
}