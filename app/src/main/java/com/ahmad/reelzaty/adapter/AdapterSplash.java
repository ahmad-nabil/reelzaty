package com.ahmad.reelzaty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.reelzaty.CustomObjects.SplashObjects;
import com.ahmad.reelzaty.databinding.VpagerSplashBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterSplash extends RecyclerView.Adapter<AdapterSplash.Viewpager> {
    ArrayList<SplashObjects>list;

  Context context;

    public AdapterSplash(ArrayList<SplashObjects> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSplash.Viewpager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VpagerSplashBinding vpagerSplashBinding=VpagerSplashBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Viewpager(vpagerSplashBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSplash.Viewpager holder, int position) {
holder.vpagerSplashBinding.tvSplash.setText(list.get(position).getTxt());
Glide.with(context).load(list.get(position).getImgRes()).into(holder.vpagerSplashBinding.imgsplash);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewpager extends RecyclerView.ViewHolder {
        VpagerSplashBinding vpagerSplashBinding;
        public Viewpager(@NonNull   VpagerSplashBinding vpagerSplashBinding) {
            super(vpagerSplashBinding.getRoot());
           this.vpagerSplashBinding=vpagerSplashBinding;
        }
    }
}
