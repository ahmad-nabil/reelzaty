package com.ahmad.reelzaty.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.reelzaty.databinding.ProfileVideosBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.HolderProfile> {
 Context context;
 ArrayList <String>list;

    public AdapterProfile(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterProfile.HolderProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileVideosBinding videosBinding=ProfileVideosBinding.inflate(LayoutInflater.from(parent.getContext())
        ,parent,false);
        return new HolderProfile(videosBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProfile.HolderProfile holder, int position) {
holder.getvideo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderProfile extends RecyclerView.ViewHolder {
        ProfileVideosBinding videosBinding;
        final ExoPlayer exoPlayer;
        public HolderProfile(@NonNull   ProfileVideosBinding videosBinding) {
            super(videosBinding.getRoot());
            this.videosBinding=videosBinding;
            exoPlayer=new ExoPlayer.Builder(context.getApplicationContext()).build();
            videosBinding.videoViewProfile.setPlayer(exoPlayer);

        }
        public void getvideo(String urlvideo){
            MediaItem mediaItem=MediaItem.fromUri(Uri.parse(urlvideo));
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        }
    }
}
