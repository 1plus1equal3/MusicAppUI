package com.example.musicappui.API.ApiSpotify;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class Interceptor implements okhttp3.Interceptor {

    SharedPreferences preferences;

    public Interceptor(Context context) {
        preferences = context.getSharedPreferences("TokenInfo", MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
/*
        boolean hasToken = Objects.equals(chain.request().header("IncludeInterceptor"), "true");
*/
        String type = preferences.getString("type", null);
        String token = preferences.getString("token", null);
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", type + " " + token)
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(request);
    }
}
