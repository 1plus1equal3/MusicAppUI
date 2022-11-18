package com.example.musicappui.API.model_for_spotify_songs;

public class RecommendSongs {
private final Items[] items;

    public RecommendSongs(Items[] items) {
        this.items = items;
    }

    public Items[] getItems() {
        return items;
    }
}
