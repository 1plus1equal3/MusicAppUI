package com.example.musicappui.API.ApiSpotify;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyClient_0 {
    private static SpotifyClient_0 client = null;
    private final ApiSpotify apiSpotify;

    public SpotifyClient_0() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSpotify.BASE_URL_FOR_TOKEN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiSpotify = retrofit.create(ApiSpotify.class);
    }

    public static SpotifyClient_0 getInstance() {
        if (client == null) client = new SpotifyClient_0();
        return client;
    }

    public ApiSpotify getApiSpotify() {
        return apiSpotify;
    }
}
