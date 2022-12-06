package com.example.musicappui.CallbackInterface;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_albums.Albums;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.Artists;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;


import java.util.List;

public interface HomeFragCallback {
    void onAdapterSetUp(List<Track> tracks, List<Artists> artists, List<Albums> albums);
}
