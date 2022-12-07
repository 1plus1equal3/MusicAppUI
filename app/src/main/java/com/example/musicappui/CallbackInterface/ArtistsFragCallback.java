package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.Artists;

import java.util.ArrayList;

public interface ArtistsFragCallback {
    void cakes(int num);

    void onAdapterSetUp(ArrayList<Artists> items);
}
