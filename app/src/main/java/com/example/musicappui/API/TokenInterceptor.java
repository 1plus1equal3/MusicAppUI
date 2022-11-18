package com.example.musicappui.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer BQAc0juJ65wJGM7U-1ax60TxVwRiQVLcg6-HsbZ8aSft8nuAbMgpwojueGR4RpM9vvSmAvvq4kuD3Kaz9tyNVPNaRdzWsZldS0sffD5QKlWGHaDECrM")
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(request);
    }
}
