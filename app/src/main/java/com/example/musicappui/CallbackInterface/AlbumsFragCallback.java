package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_albums.Albums;

import java.util.ArrayList;

public interface AlbumsFragCallback {
    void cheeses(int num);

    void onAdapterSetUp(ArrayList<Albums> items);
}
