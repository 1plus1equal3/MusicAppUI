package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;
import com.example.musicappui.API.model_for_candy_ad.Songs;

import java.util.ArrayList;

public interface SongsFragCallback {
    void cherries(int num);

    void onAdapterSetUp(ArrayList<Songs> items);
}
