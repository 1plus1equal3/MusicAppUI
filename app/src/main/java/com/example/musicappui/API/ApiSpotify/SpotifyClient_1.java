package com.example.musicappui.API.ApiSpotify;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyClient_1 {
    private static SpotifyClient_1 client = null;
    private final ApiSpotify apiSpotify;

    public SpotifyClient_1(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSpotify.BASE_URL_FOR_OTHERS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiSpotify = retrofit.create(ApiSpotify.class);
    }


    public static SpotifyClient_1 getInstance(Context context) {
        if (client == null) client = new SpotifyClient_1(context);
        return client;
    }

    public ApiSpotify getApiSpotify() {
        return apiSpotify;
    }
}
