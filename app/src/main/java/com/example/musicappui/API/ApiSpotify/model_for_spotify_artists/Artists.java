package com.example.musicappui.API.ApiSpotify.model_for_spotify_artists;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Images;
import com.example.musicappui.API.model_for_candy_ad.Songs;

public class Artists extends Songs {
    private final String id;
    private final Images[] images;
    private final String name;

    public Artists(String id, Images[] images, String name) {
        this.id = id;
        this.images = images;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Images[] getImages() {
        return images;
    }

    public String getName() {
        return name;
    }
}
