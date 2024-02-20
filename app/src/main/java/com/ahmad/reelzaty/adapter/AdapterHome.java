package com.ahmad.reelzaty.adapter;

import android.content.Context;
import android.media.session.MediaController;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.reelzaty.CustomObjects.AddressVideoObject;
import com.ahmad.reelzaty.databinding.VideopostLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.videoHolde> {
 ArrayList<AddressVideoObject> list;
 Context context;

    public AdapterHome(ArrayList<AddressVideoObject> stringArrayList, Context context) {
        this.list = stringArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterHome.videoHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideopostLayoutBinding binding=VideopostLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new videoHolde(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.videoHolde holder, int position) {
        holder.getvideo(list.get(position).videoUrl);
        Glide.with(context).load(list.get(position).imguser).into(holder.binding.imageView);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class videoHolde extends RecyclerView.ViewHolder{
        VideopostLayoutBinding binding;
    final ExoPlayer player;

        public videoHolde(VideopostLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            player=new SimpleExoPlayer.Builder(context.getApplicationContext()).build();
            binding.videoView.setPlayer(player);

        }
        public void getvideo(String urlvideo){
            if (urlvideo != null && !urlvideo.isEmpty()) {
                MediaItem mediaItem = MediaItem.fromUri(Uri.parse(urlvideo));
                player.setMediaItem(mediaItem);
                player.prepare();
            }

        }
    }
}
