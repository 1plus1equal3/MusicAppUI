package com.example.musicappui.API.ApiSpotify;

import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.ArtistsResponse;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.RecommendSongs;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSpotify {

    String BASE_URL_FOR_TOKEN = "https://accounts.spotify.com/";
    String BASE_URL_FOR_OTHERS = "https://api.spotify.com/";

    @Headers({
            "Authorization: Basic NTMwNGQ0NmMzZmFkNDYzNjkwMzkyNzgxMTdmNjNkZjM6OWE3M2Q3YWYyNzZmNDAzMGJkNDg5NDdmOGZlMTYzMmE=",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("api/token")
    Call<ResponseForAccessToken> getAccessToken(@Query("grant_type") String grant_type);

    @GET("v1/playlists/37i9dQZEVXbLdGSmz6xilI/tracks")
    Call<RecommendSongs> getTracks(@Query("limit") int limit);

    @GET("v1/artists")
    Call<ArtistsResponse> getArtists(@Query("ids") String ids);
}
