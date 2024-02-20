package com.ahmad.reelzaty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.reelzaty.CustomObjects.NotficationCustomObject;
import com.ahmad.reelzaty.databinding.NotificationRvBinding;

import java.util.ArrayList;

public class AdapterNotfication extends RecyclerView.Adapter<AdapterNotfication.HolderNotifi> {
  ArrayList<NotficationCustomObject>list;
  Context context;

    public AdapterNotfication(ArrayList<NotficationCustomObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNotfication.HolderNotifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationRvBinding rvBinding=NotificationRvBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new HolderNotifi(rvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotfication.HolderNotifi holder, int position) {
        holder.rvBinding.bodynotfi.setText(list.get(position).getBody());
        holder.rvBinding.titleNotfi.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderNotifi extends RecyclerView.ViewHolder {
        NotificationRvBinding rvBinding;
        public HolderNotifi(@NonNull NotificationRvBinding rvBinding) {
            super(rvBinding.getRoot());
            this.rvBinding=rvBinding;
        }
    }
}
