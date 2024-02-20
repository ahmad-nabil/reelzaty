package com.ahmad.reelzaty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.reelzaty.databinding.SearcrecycleviewBinding;
import com.ahmad.reelzaty.informationUser.Database.UserInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.searchHolder> {
    ArrayList<UserInfo>list;
    Context context;

    public AdapterSearch(ArrayList<UserInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSearch.searchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearcrecycleviewBinding binding=SearcrecycleviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new searchHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.searchHolder holder, int position) {
        Glide.with(context).load(list.get(position).UriImg).into(holder.binding.imageViewSearchview);
        holder.binding.nameuyserSearchview.setText(list.get(position).nameUser);
        holder.binding.phonusersearchview.setText(list.get(position).phoneUser);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class searchHolder extends RecyclerView.ViewHolder{
        SearcrecycleviewBinding binding;
        public searchHolder(@NonNull   SearcrecycleviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
