package com.ahmad.reelzaty.informationUser;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.informationUser.Database.UserInfo;

public class AccModel extends ViewModel {
    MutableLiveData<UserInfo> data=new MutableLiveData<>();
    FirebaseAuthentication authManager = new FirebaseAuthentication();

    public void fetchData(){
        authManager.getUserData(data);
    }
    public MutableLiveData<UserInfo> getData() {
        if (data.getValue()==null){
            fetchData();
        }
        return data;
    }
}
