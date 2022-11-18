package com.example.musicappui.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyClient {
    private static SpotifyClient client = null;
    private final ApiSpotify apiSpotify;

    public SpotifyClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSpotify.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiSpotify = retrofit.create(ApiSpotify.class);
    }

    public static synchronized SpotifyClient getInstance(){
        if(client==null) client = new SpotifyClient();
        return client;
    }

    public ApiSpotify getApiSpotify() {return apiSpotify;}
}
