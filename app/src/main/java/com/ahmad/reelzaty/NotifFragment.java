package com.ahmad.reelzaty;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ahmad.reelzaty.CustomObjects.NotficationCustomObject;
import com.ahmad.reelzaty.adapter.AdapterNotfication;
import com.ahmad.reelzaty.databinding.FragmentNotficationBinding;
import com.ahmad.reelzaty.databinding.NoNotficBinding;
import com.ahmad.reelzaty.informationUser.Database.RealtimeDatabase;

import java.util.ArrayList;
import java.util.function.Consumer;

public class NotifFragment extends DialogFragment {
    FragmentNotficationBinding notficationBinding;
    RealtimeDatabase realtimeDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
notficationBinding=FragmentNotficationBinding.inflate(inflater,container,false);
getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
realtimeDatabase=new RealtimeDatabase();
if (isAdded()){
    showNotfication();
}
        notficationBinding.dismiss.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});
        return notficationBinding.getRoot();
    }

    private void showNotfication() {
        loadingScren();
        realtimeDatabase.getNotfications(new Consumer<ArrayList<NotficationCustomObject>>() {
            @Override
            public void accept(ArrayList<NotficationCustomObject> notficationCustomObjects) {
                notficationBinding.notifyLiner.removeAllViews();
                AdapterNotfication notfication=new AdapterNotfication(notficationCustomObjects,requireContext());
                RecyclerView RvNotfication=new RecyclerView(requireContext());
                RvNotfication.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                RvNotfication.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
RvNotfication.setAdapter(notfication);
notficationBinding.notifyLiner.addView(RvNotfication);
            }
        }, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
if (!aBoolean){
    notficationBinding.notifyLiner.removeAllViews();
    NoNotficBinding binding=NoNotficBinding.inflate(getLayoutInflater());
notficationBinding.notifyLiner.addView(binding.getRoot());
}
            }
        });
    }
    private void loadingScren() {
        notficationBinding.notifyLiner.removeAllViews();
        ProgressBar progressBar=new ProgressBar(requireContext(), null,android.R.attr.progressBarStyle);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50,50,50,50);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(layoutParams);
        notficationBinding.notifyLiner.addView(progressBar);
    }
}