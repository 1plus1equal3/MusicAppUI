package com.example.musicappui.API.model_for_candy_ad;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;

import java.util.List;

public class SongRow {
    private final long id;
    private final String title;
    private final List<Songs> tracks;
    private final SongRowViewType viewType;

    public SongRow(long id, String title, List<Songs> tracks, SongRowViewType viewType) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.viewType = viewType;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SongRowViewType getViewType() {
        return viewType;
    }

    public List<Songs> getTracks() {
        return tracks;
    }

}

