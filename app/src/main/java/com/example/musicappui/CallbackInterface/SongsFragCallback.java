package com.example.musicappui.CallbackInterface;

import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.SongItem;

import java.util.ArrayList;

public interface SongsFragCallback {
    void cherries(int num);

    void AdapterSetUp(ArrayList<SongItem> items);
}
