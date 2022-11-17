package com.example.musicappui.API;

import com.example.musicappui.API.model_for_candy_ad.ResponseBody;
import com.example.musicappui.API.model_for_candy_taste.Candy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    /*String BASE_URL = "https://jsonplaceholder.typicode.com/";*/
    String BASE_URL = "https://soundcloud-scraper.p.rapidapi.com/";
    /*@GET("posts")*/


    @Headers(
            {
                    "X-RapidAPI-Key: 70a3427f36msh0218d959eaefacfp144c93jsn9aca26548970",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })
    @GET("v1/playlist/tracks")
    Call<ResponseBody> getCandyAd(@Query("playlist") String playlist, @Query("limit") int limit);

    @Headers(
            {
                    "X-RapidAPI-Key: 70a3427f36msh0218d959eaefacfp144c93jsn9aca26548970",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })
    @GET("v1/track/metadata")
    Call<Candy> getCandyTaste(@Query("track") long id);

}
