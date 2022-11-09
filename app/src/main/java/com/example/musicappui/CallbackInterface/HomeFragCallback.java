package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.model_for_candy_ad.SongRow;

import java.util.List;

public interface HomeFragCallback {
    void onAdapterSetUp(List<SongRow> items);
}
