package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.model_for_candy_ad.SongItem;

import java.util.ArrayList;

public interface SongsFragCallback {
    void cherries(int num);

    void onAdapterSetUp(ArrayList<SongItem> items);
}
