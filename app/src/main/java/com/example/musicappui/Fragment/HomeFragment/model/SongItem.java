package com.example.musicappui.Fragment.HomeFragment.model;

import java.io.Serializable;

public class SongItem implements Serializable {
    private long id;
    private String title;
    private String permalink;
    private String artworkUrl;

    public SongItem() {
    }

    public SongItem(String title, String artworkUrl) {
        this.title = title;
        this.artworkUrl = artworkUrl;
    }

    public SongItem(long id, String title, String SC_link) {
        this.id = id;
        this.title = title;
        this.permalink = SC_link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getImageUrl() {
        return artworkUrl;
    }

    public void setImageUrl(String imageUrl) {
        artworkUrl = imageUrl;
    }
}

