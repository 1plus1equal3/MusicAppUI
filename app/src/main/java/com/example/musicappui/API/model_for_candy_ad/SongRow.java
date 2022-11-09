package com.example.musicappui.API.model_for_candy_ad;

import java.util.List;

public class SongRow {
    private long id;
    private String title;
    private List<SongItem> songItems;
    private SongRowViewType viewType;

    public SongRow(long id, String title, List<SongItem> songItems, SongRowViewType viewType) {
        this.id = id;
        this.title = title;
        this.songItems = songItems;
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

    public List<SongItem> getSongItems() {
        return songItems;
    }
}

