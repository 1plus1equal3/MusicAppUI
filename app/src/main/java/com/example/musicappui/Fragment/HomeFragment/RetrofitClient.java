package com.example.musicappui.Fragment.HomeFragment;

import android.util.Log;

import com.example.musicappui.Fragment.HomeFragment.model.ResponseBody;
import com.example.musicappui.Fragment.HomeFragment.model.SongItem;

import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

interface Api {
    /*String BASE_URL = "https://jsonplaceholder.typicode.com/";*/
    String BASE_URL = "https://soundcloud-scraper.p.rapidapi.com/";
    /*@GET("posts")*/



    @Headers(
            {
                    "X-RapidAPI-Key: 4eaf7ff150msh5e6765b4d66afedp108270jsn0af9710bc796",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })
    @GET("v1/playlist/tracks")
    Call<ResponseBody> getSongId(@Query("playlist") String playlist, @Query("limit") int limit);
}

public class RetrofitClient {
    private static RetrofitClient client = null;
    private final Api api;

    private RetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        api = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (client == null) {
            client = new RetrofitClient();
        }
        return client;
    }

    public Api getApi() {
        return api;
    }
}

class ApiCaller extends Thread {

    public ArrayList<SongItem> songItems = new ArrayList<>();

    @Override
    public void run() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getSongId("https://soundcloud.com/edsheeran/sets/tour-edition-1", 10);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] imageurl array!
                for (int i = 0; i < 10; i++) {
                    songItems.add(i, new SongItem(body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getImageUrl()));
                    Log.e("Image url of " + i + " song is: ", songItems.get(i).getImageUrl());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
            }
        });
        super.run();
    }

    public ArrayList<SongItem> getSongItems() {
        return songItems;
    }

}
