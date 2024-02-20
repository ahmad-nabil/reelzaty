package com.ahmad.reelzaty.informationUser;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricManager;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmad.reelzaty.Home;
import com.ahmad.reelzaty.informationUser.Database.FirebaseAuthentication;
import com.ahmad.reelzaty.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
public class Login extends Fragment {
FragmentLoginBinding loginBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate layout
        loginBinding=FragmentLoginBinding.inflate(inflater,container,false);
    //login btn
       loginBinding.login.setOnClickListener(this::CheckLogin);
       //rest password btn we create dialoge
loginBinding.restbtn.setOnClickListener(v->{
    AlertDialog.Builder dialog=new AlertDialog.Builder(requireContext());
    EditText editText=new EditText(requireContext());
    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    dialog.setTitle("rest password");
    dialog.setCustomTitle(editText);
    dialog.setPositiveButton("rest", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String Email=editText.getText().toString();
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(Email).addOnSuccessListener(v->{
                Toast.makeText(requireContext(), "email rest password sent", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(requireContext(), "your Email not in our database", Toast.LENGTH_SHORT).show();

                }
            });
        }
    });
    dialog.show();
});
        return loginBinding.getRoot();

    }

    private void CheckLogin(View loginBtn) {
        if (TextUtils.isEmpty(loginBinding.Emailogin.getText())||
                TextUtils.isEmpty(loginBinding.passwordLogin.getText())){
            Toast.makeText(requireContext() , "Please provide email and password", Toast.LENGTH_SHORT).show();
        Addnotes();
        }else {
            FirebaseAuthentication authentication=new FirebaseAuthentication();
          String email=loginBinding.Emailogin.getText().toString();
          String password=loginBinding.passwordLogin.getText().toString();
            authentication.Login(email,password).addOnSuccessListener(command -> {
                startActivity(new Intent(requireContext(), Home.class));
            requireActivity().finish();
            }).addOnFailureListener(v->{
                Toast.makeText(requireContext(), "check your email or password", Toast.LENGTH_SHORT).show();
                loginBinding.passwordLogin.setError("");
                loginBinding.Emailogin.setError("");
            });
        }
    }

    private void Addnotes() {
        if (TextUtils.isEmpty(loginBinding.Emailogin.getText())){
            loginBinding.Emailogin.setError("");

        }
        if (TextUtils.isEmpty(loginBinding.passwordLogin.getText())){
            loginBinding.passwordLogin.setError("");
        }
    }

}