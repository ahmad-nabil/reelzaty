package com.ahmad.reelzaty.informationUser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmad.reelzaty.Home;
import com.ahmad.reelzaty.R;
import com.ahmad.reelzaty.databinding.ActivityAccountDataBinding;
import com.ahmad.reelzaty.databinding.DialogUpdateAvatarBinding;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.informationUser.Database.FirestoreDatabase;
import com.ahmad.reelzaty.informationUser.Database.UserInfo;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AccountData extends AppCompatActivity {
ActivityAccountDataBinding AccDataBinding;
AccModel dataModel;
String UrlImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize Live model Component
        AccDataBinding= DataBindingUtil.setContentView(this,R.layout.activity_account_data);
        dataModel=new ViewModelProvider(this).get(AccModel.class);
        AccDataBinding.setModelAcc(dataModel);
        AccDataBinding.setLifecycleOwner(this);
        //get src Img
        dataModel.getData().observe(this, userData -> {
            UrlImg=userData.UriImg;
            Glide.with(AccountData.this).load(userData.UriImg).into(AccDataBinding.avtarAccData);
        });
        checkBiometricAvailability();
    AccDataBinding.changeimg.setOnClickListener(this::dialogUpdateImg);
    AccDataBinding.save.setOnClickListener(this::saveData);
AccDataBinding.materialToolbar.setNavigationOnClickListener(v->{
    startActivity(new Intent(AccountData.this, Home.class));
    finish();
});
    }

    private void checkBiometricAvailability() {
        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL|
                BiometricManager.Authenticators.BIOMETRIC_WEAK)){
            case BiometricManager.BIOMETRIC_SUCCESS:
                showBiometricBtn();
                break;

        }
    }



    private void showBiometricBtn() {
        ImageView imageView=new ImageView(this);
        Glide.with(this).load(R.drawable.baseline_fingerprint_24).into(imageView);
imageView.setClickable(true);
AccDataBinding.Linear.addView(imageView);
imageView.setOnClickListener(this::showBioAuthencation);
    }

    private void showBioAuthencation(View view) {
        BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("biometric authencation").
                setDescription("Please authenticate with your biometrics to continue")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .setNegativeButtonText("dismiss").build();
        BiometricPrompt biometricPrompt=new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
AccDataBinding.save.performClick();
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(AccountData.this, "Authentication failed try button save", Toast.LENGTH_SHORT).show();
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }

    private void saveData(View saveDataBtn) {
        HashMap<String,Object>map=new HashMap<>();
        if (TextUtils.isEmpty(AccDataBinding.NameAcc.getText())||
                TextUtils.isEmpty(AccDataBinding.EmailEtProfile.getText())||
                TextUtils.isEmpty(AccDataBinding.passwordEtprofile.getText())||
                TextUtils.isEmpty(AccDataBinding.phoneNumberProfile.getText())){
            Toast.makeText(this,  "fill ALL Value", Toast.LENGTH_SHORT).show();
            AddNotes();
        }else {
           String name=AccDataBinding.NameAcc.getText().toString();
           String email=AccDataBinding.EmailEtProfile.getText().toString();
           String password=AccDataBinding.passwordEtprofile.getText().toString();
           String phone=AccDataBinding.phoneNumberProfile.getText().toString();
            map.put("EmailUser",email);
            map.put("UriImg",UrlImg);
            map.put("nameUser",name);
map.put("passwordUser",password);
map.put("phoneUser",phone);
            FirebaseAuthentication authentication=new FirebaseAuthentication();
            authentication.user().updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    authentication.user().updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirestoreDatabase database=new FirestoreDatabase();
                            database.UpdateUser(authentication.user().getUid(),map);
                        }
                    });
                }
            });
        }
    }

    private void AddNotes() {
        if (TextUtils.isEmpty(AccDataBinding.NameAcc.getText())){
            AccDataBinding.NameAcc.setError("");
        }   if (TextUtils.isEmpty(AccDataBinding.EmailEtProfile.getText())){
            AccDataBinding.EmailEtProfile.setError("");
        }   if (TextUtils.isEmpty(AccDataBinding.passwordEtprofile.getText())){
            AccDataBinding.passwordEtprofile.setError("");
        }   if (TextUtils.isEmpty(AccDataBinding.phoneNumberProfile.getText())){
            AccDataBinding.phoneNumberProfile.setError("");
        }




    }

    private void dialogUpdateImg(View changImgBtn) {
        DialogUpdateAvatarBinding updateAvatarBinding=DialogUpdateAvatarBinding.inflate(getLayoutInflater());
        Dialog chooseImg=new Dialog(this);
        chooseImg.setContentView(updateAvatarBinding.getRoot());
        chooseImg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseImg.show();
        updateAvatarBinding.fromGallery.setOnClickListener(v->{
           Intent openGAL   =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       imgpicker.launch(openGAL);
        });
        updateAvatarBinding.usingCam.setOnClickListener(v->{
            Intent openCaM=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgpicker.launch(openCaM);

        });
    }

ActivityResultLauncher<Intent>imgpicker=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
if (result.getResultCode()==RESULT_OK){
    Uri uri=result.getData().getData();
    if (uri != null) {
        Glide.with(AccountData.this).load(uri).into(AccDataBinding.avtarAccData);
uploadimg(uri);
    }else {
Bundle bundle=result.getData().getExtras();
if (bundle!=null){
    Bitmap bit= (Bitmap) bundle.get("data");
    Glide.with(AccountData.this).load(bit).into(AccDataBinding.avtarAccData);
    uploadimg(ConverttoUri(AccountData.this,bit));


}
    }
}
    }
});

    private void uploadimg(Uri uri) {
        String file="usersImg"+"/"+System.currentTimeMillis()+".png";
        StorageReference reference= FirebaseStorage.getInstance().getReference().child(file);
        reference.putFile(uri).addOnSuccessListener(taskSnapshot ->{
            reference.getDownloadUrl().addOnSuccessListener(v->{
                UrlImg=v.toString();
            });
        });


    }

    private Uri ConverttoUri(Context context, Bitmap bit) {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        String path=MediaStore.Images.Media.insertImage(context.getContentResolver(),bit,"title",null);
        return Uri.parse(path);
    }


}