package com.example.musicappui.API.ApiSpotify.model_for_spotify_songs;

import com.example.musicappui.API.model_for_candy_ad.Songs;

public class Track extends Songs {
    private final long duration_ms;
    private final String id;
    private final String name;
    private final String uri;
    private final Artist[] artists;
    private final String preview_url;
    private final Album album;

    public Track(long duration_ms, String id, String name, String uri, Artist[] artists, String preview_url, Album album) {
        this.duration_ms = duration_ms;
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.artists = artists;
        this.preview_url = preview_url;
        this.album = album;
    }

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

    public String getPreview_url() {
        return preview_url;
    }

    public Album getAlbum() {
        return album;
    }
}
