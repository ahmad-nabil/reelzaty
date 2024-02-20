package com.ahmad.reelzaty.informationUser;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmad.reelzaty.Home;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.informationUser.Database.FirestoreDatabase;
import com.ahmad.reelzaty.informationUser.Database.UserInfo;
import com.ahmad.reelzaty.databinding.DialogUpdateAvatarBinding;
import com.ahmad.reelzaty.databinding.FragmentSignupBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicReference;


public class Signup extends Fragment {
    FragmentSignupBinding signupBinding;
    Uri ImgUri;
    String Url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate
        signupBinding=FragmentSignupBinding.inflate(inflater,container,false);
        signupBinding.updateavtar.setOnClickListener(this::updateAvatar);
        //signup btn listener
signupBinding.signupbtn.setOnClickListener(v->{
    CheckData();
});
        return signupBinding.getRoot();
    }
    private void CheckData() {
if (ImgUri==null){
    Toast.makeText(requireContext(), "upload img please", Toast.LENGTH_SHORT).show();
}else {
    CheckFileds();

}
    }

    private void CheckFileds() {
   String name,email,password,phone;
        if (TextUtils.isEmpty(signupBinding.EmailET.getText())||
           TextUtils.isEmpty(signupBinding.fullnameET.getText())||
           TextUtils.isEmpty(signupBinding.passwordET.getText())||
           TextUtils.isEmpty(signupBinding.phoneET.getText())){
       Toast.makeText(requireContext(),  "fill ALL Value", Toast.LENGTH_SHORT).show();
   AddNotes();
   }else {
            if (signupBinding.passwordET.getText().toString().length()>4){
                name=signupBinding.fullnameET.getText().toString();
                email=signupBinding.EmailET.getText().toString();
                password=signupBinding.passwordET.getText().toString();
                phone=signupBinding.phoneET.getText().toString();
                UserInfo data=new UserInfo();
                data.nameUser=name;
                data.EmailUser=email;
                data.passwordUser=password;
                data.phoneUser=phone;


                    data.UriImg=ImgUri.toString();
                    FirebaseAuthentication authentication=new FirebaseAuthentication();
                    authentication.RegisterUser(email,password).addOnSuccessListener(v->{
                        FirestoreDatabase firestoreDatabase=new FirestoreDatabase();
                        firestoreDatabase.AddUser(authentication.user().getUid(),data);
                        startActivity(new Intent(requireContext(), Home.class));
                        requireActivity().finish();



                });
            }else {
                Toast.makeText(requireContext(),  "check password filed", Toast.LENGTH_SHORT).show();
                signupBinding.passwordET.setError("less than 5 character");
            }
        }

    }

    private void AddNotes() {
        if (TextUtils.isEmpty(signupBinding.fullnameET.getText())){
            signupBinding.fullnameET.setError("");
        }
        if (TextUtils.isEmpty(signupBinding.passwordET.getText())){
            signupBinding.passwordET.setError("");
        }
        if (TextUtils.isEmpty(signupBinding.EmailET.getText())){
            signupBinding.EmailET.setError("");
        }
        if (TextUtils.isEmpty(signupBinding.phoneET.getText())){
            signupBinding.phoneET.setError("");
        }


    }

    private void updateAvatar(View view) {
        DialogUpdateAvatarBinding updateAvatarBinding=DialogUpdateAvatarBinding.inflate(getLayoutInflater());
        Dialog chooseImg=new Dialog(requireContext());
        chooseImg.setContentView(updateAvatarBinding.getRoot());
        chooseImg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseImg.show();
      updateAvatarBinding.usingCam.setOnClickListener(v->{
          Intent openCam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          ImgPicker.launch(openCam);

      });
      updateAvatarBinding.fromGallery.setOnClickListener(v->{
          Intent openGallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          ImgPicker.launch(openGallery);
      });
    }
    ActivityResultLauncher<Intent>ImgPicker=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                ImgUri=result.getData().getData();
if (ImgUri!=null){
    Glide.with(requireContext()).load(ImgUri).into(signupBinding.updateavtar);
    UploadImgToserver(ImgUri);
}else {
    Bundle bundle=result.getData().getExtras();
    if (bundle!=null){
        Bitmap bit= (Bitmap) bundle.get("data");
        Glide.with(requireContext()).load(bit).into(signupBinding.updateavtar);
        UploadImgToserver(ConvertToUri(requireContext(),bit));
    }
}


            }

        }
    });

    private Uri ConvertToUri(Context context,Bitmap bit) {
        ByteArrayOutputStream byteArrayStream=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG,100,byteArrayStream);
        String path=MediaStore.Images.Media.insertImage(context.getContentResolver(),bit,"title",null);
ImgUri=Uri.parse(path);
return ImgUri;
    }

private void UploadImgToserver(Uri imgUri){
        String file="usersImg"+"/"+System.currentTimeMillis()+".png";
        StorageReference reference= FirebaseStorage.getInstance().getReference().child(file);
    reference.putFile(imgUri).addOnSuccessListener(taskSnapshot ->{
        reference.getDownloadUrl().addOnSuccessListener(v->{
            Url=v.toString();
        });
            });

}
}