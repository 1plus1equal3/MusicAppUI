package com.example.musicappui.API.model_for_candy_ad;

import com.google.gson.annotations.SerializedName;

public class ResponseBody {
    @SerializedName(("tracks"))
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }
}
