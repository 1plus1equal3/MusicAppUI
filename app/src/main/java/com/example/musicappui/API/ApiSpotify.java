package com.example.musicappui.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSpotify {

    String BASE_URL = "https://accounts.spotify.com/";

    @POST("api/token")
    Call<ResponseForAccessToken> getAccessToken(@Query("grant_type") String grant_type);

    @Headers({
            "Authorization: Bearer BQAc0juJ65wJGM7U-1ax60TxVwRiQVLcg6-HsbZ8aSft8nuAbMgpwojueGR4RpM9vvSmAvvq4kuD3Kaz9tyNVPNaRdzWsZldS0sffD5QKlWGHaDECrM",
            "Content-Type: application/json"
    })
    @GET("v1/playlists/37i9dQZEVXbLdGSmz6xilI/tracks")
    Call<ResponseForAccessToken> getTracks(@Query("limit") String limit);

}
