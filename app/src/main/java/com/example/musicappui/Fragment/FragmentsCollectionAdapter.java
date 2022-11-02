package com.example.musicappui.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentsCollectionAdapter extends FragmentStateAdapter {
    public FragmentsCollectionAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SongsFragment();
            case 2:
                return new ArtistsFragment();
            case 3:
                return new AlbumFragment();
            case 4:
                return new StorageFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
