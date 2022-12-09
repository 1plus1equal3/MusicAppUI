package com.example.musicappui.API.ApiSpotify.model_for_spotify_songs;

public class Album {
    private final Images[] images;
    private String id;

    public Album(Images[] images) {
        this.images = images;
    }

    public Images[] getImages() {
        return images;
    }

    public String getId() {
        return id;
    }
}
