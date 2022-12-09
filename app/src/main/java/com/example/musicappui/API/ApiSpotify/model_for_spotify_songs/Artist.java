package com.example.musicappui.API.ApiSpotify.model_for_spotify_songs;

public class Artist {
    private String id;
    private final String name;
    private String uri;

    public Artist(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
