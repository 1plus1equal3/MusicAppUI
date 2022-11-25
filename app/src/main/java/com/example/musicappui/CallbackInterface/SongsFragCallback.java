package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;

import java.util.ArrayList;

public interface SongsFragCallback {
    void cherries(int num);

    void onAdapterSetUp(ArrayList<Track> items);
}
