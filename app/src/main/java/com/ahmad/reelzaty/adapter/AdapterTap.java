package com.ahmad.reelzaty.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

    public class AdapterTap extends FragmentStateAdapter {

        ArrayList<Fragment>list=new ArrayList<>();
        ArrayList<String>namefr=new ArrayList<>();

    public AdapterTap(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public AdapterTap(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterTap(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
public void add(Fragment fragment,String name){
        list.add(fragment);
        namefr.add(name);
}

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
    public int getItemCount() {
        return list.size();
    }
    public CharSequence title(int position){
        return namefr.get(position);
    }
}
