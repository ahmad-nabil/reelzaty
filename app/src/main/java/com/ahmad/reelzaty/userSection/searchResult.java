package com.ahmad.reelzaty.userSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmad.reelzaty.Home;
import com.ahmad.reelzaty.R;
import com.ahmad.reelzaty.adapter.AdapterSearch;
import com.ahmad.reelzaty.databinding.ActivitySearchResultBinding;
import com.ahmad.reelzaty.informationUser.AccountData;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.informationUser.Database.UserInfo;

import java.util.ArrayList;
import java.util.function.Consumer;

public class searchResult extends AppCompatActivity {
String value;
FirebaseAuthentication authentication;
ActivitySearchResultBinding searchResultBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResultBinding=ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(searchResultBinding.getRoot());
        value=getIntent().getStringExtra("search");
        authentication=new FirebaseAuthentication();
        fetchSearch(value);
        searchResultBinding.barsearch.setNavigationOnClickListener(v->{
            startActivity(new Intent(searchResult.this, Home.class));
            finish();
        });
        searchResultBinding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
             fetchSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void fetchSearch(String value) {
        ArrayList<UserInfo>users=new ArrayList<>();
authentication.getAllUsers(new Consumer<ArrayList<UserInfo>>() {
    @Override
    public void accept(ArrayList<UserInfo> userInfos) {
        for (UserInfo userInfo:userInfos) {
            if (userInfo.nameUser.toLowerCase().contains(value.toLowerCase())){
                users.add(userInfo);
            }

        }
    if (users.size()>0){
        searchResultBinding.linearlayout.removeAllViews();
        RecyclerView recyclerView=new RecyclerView(searchResult.this);
        AdapterSearch search=new AdapterSearch(users,searchResult.this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(searchResult.this,LinearLayoutManager.VERTICAL,false));
   recyclerView.setAdapter(search);
   searchResultBinding.linearlayout.addView(recyclerView);
    }else {
        searchResultBinding.linearlayout.removeAllViews();
        View view= LayoutInflater.from(searchResult.this).inflate(R.layout.noresult,searchResultBinding.linearlayout,false);
        searchResultBinding.linearlayout.addView(view);
    }

    }

});
    }
}