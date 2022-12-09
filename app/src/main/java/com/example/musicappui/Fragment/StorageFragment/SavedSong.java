package com.example.musicappui.Fragment.StorageFragment;

public class SavedSong {
    private final String id;
    private final String album;
    private final String artist;
    private final String title;
    private final String path;

    public SavedSong(String id, String album, String artist, String title, String path) {
        this.id = id;
        this.album = album;
        this.artist = artist;
        this.title = title;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }


    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }
}
