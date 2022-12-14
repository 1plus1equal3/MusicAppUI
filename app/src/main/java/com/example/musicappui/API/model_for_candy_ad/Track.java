package com.example.musicappui.API.model_for_candy_ad;

import java.io.Serializable;

public class Track implements Serializable {
    private long id;
    private String title;
    private String permalink;
    private String artworkUrl;
    private Publisher publisher;
    private long durationMs;
    private String durationText;


    public Track() {
    }


    public Track(String title, String artworkUrl) {
        this.title = title;
        this.artworkUrl = artworkUrl;
    }

    public Track(String title, String artworkUrl, Publisher publisher, String durationText, long id) {
        this.title = title;
        this.artworkUrl = artworkUrl;
        this.publisher = publisher;
        this.durationText = durationText;
        this.id = id;
    }

    public Track(String title, String artworkUrl, Publisher publisher, String durationText, long id, long durationMs) {
        this.title = title;
        this.artworkUrl = artworkUrl;
        this.publisher = publisher;
        this.durationText = durationText;
        this.id = id;
        this.durationMs = durationMs;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }
}
