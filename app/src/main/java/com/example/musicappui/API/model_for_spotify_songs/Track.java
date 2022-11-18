package com.example.musicappui.API.model_for_spotify_songs;

public class Track {
    private long duration_ms;
    private String id;
    private String name;
    private String uri;
    private Artist[] artists;
    public long getDuration_ms() {
        return duration_ms;
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

    public Artist[] getArtists() {
        return artists;
    }
}
