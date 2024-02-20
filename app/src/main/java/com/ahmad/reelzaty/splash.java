package com.ahmad.reelzaty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ahmad.reelzaty.CustomObjects.SplashObjects;
import com.ahmad.reelzaty.adapter.AdapterSplash;
import com.ahmad.reelzaty.informationUser.AccountData;
import com.ahmad.reelzaty.informationUser.AuthManager;
import com.ahmad.reelzaty.databinding.SplashBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class splash extends AppCompatActivity {
    SplashBinding splashBinding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = SplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());
        String[]permission={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.USE_BIOMETRIC,Manifest.permission.CAMERA};
requestPermissions(permission,1);
        //resources for splash content
        ArrayList<SplashObjects> SplashRes = new ArrayList<>();
        SplashRes.add(new SplashObjects(R.drawable.sharevideo, "Share your video"));
        SplashRes.add(new SplashObjects(R.drawable.nolimits, "without limits"));
        SplashRes.add(new SplashObjects(R.drawable.letsgo, "lets start our trips"));
        //initialize View pager and  Adapter
        ViewPager2 viewsplash = new ViewPager2(splash.this);
        AdapterSplash adapterSplash = new AdapterSplash(SplashRes, this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
        viewsplash.setLayoutParams(layoutParams);
        viewsplash.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewsplash.setUserInputEnabled(false);
        viewsplash.setAdapter(adapterSplash);
        splashBinding.splash.addView(viewsplash);
        //add button
        Button nextbtn = new Button(splash.this);
        nextbtn.setText("next");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
layoutParams2.setMargins(50,50,50,50);
nextbtn.setLayoutParams(layoutParams2);
        splashBinding.splash.addView(nextbtn);
        //swipe between pages using btn
        nextbtn.setOnClickListener(v -> {
            int pos = viewsplash.getCurrentItem();
            switch (pos) {
                case 0:
                    viewsplash.setCurrentItem(1);
                    break;
                case 1:
                    viewsplash.setCurrentItem(2);
                    break;
                case 2:
                    startActivity(new Intent(splash.this, AuthManager.class));
                    finish();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allpermission ;
        if (requestCode==1){
           if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
    allpermission = true;
}else {
         allpermission=false;
           }
           if (!allpermission){
if (shouldShowRequestPermissionRationale()){
    Showrational();
}
           }
       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean shouldShowRequestPermissionRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.USE_BIOMETRIC)||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)||
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA);

    }

public void Showrational(){
    new MaterialAlertDialogBuilder(this).setTitle("Permission Required")
            .setMessage("It seems like you have denied the required permissions. Please grant the permissions.").setPositiveButton("ok", (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }).show();
}
}