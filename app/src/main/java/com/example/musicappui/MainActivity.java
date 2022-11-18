package com.example.musicappui;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.musicappui.API.RetrofitClient;
import com.example.musicappui.API.model_for_candy_ad.ResponseBody;
import com.example.musicappui.API.model_for_candy_ad.SongItem;
import com.example.musicappui.API.model_for_candy_ad.SongRow;
import com.example.musicappui.API.model_for_candy_ad.SongRowViewType;
import com.example.musicappui.API.model_for_candy_taste.Candy;
import com.example.musicappui.CallbackInterface.ArtistsFragCallback;
import com.example.musicappui.CallbackInterface.HomeFragCallback;
import com.example.musicappui.CallbackInterface.SongsFragCallback;
import com.example.musicappui.Fragment.FragmentsCollectionAdapter;
import com.example.musicappui.Fragment.ZoomOutPageTransformer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentsCollectionAdapter adapter;
    ExoPlayer player;
    PlayerControlView controller;
    Handler handler = new Handler();
    Runnable runnable;

    //UI player Views
    /*LinearLayout controllerClick;*/
    ConstraintLayout uiLayout;
    ImageButton uiBackBtn, play_pauseBtn, playerBackBtn, playerForwardBtn, playerSkipPreviousBtn, playerSkipNextBtn;
    ImageView songImage;
    TextView songName, artistName, timeStartProgress, timeEndProgress;
    ProgressBar songProgress;

    public ExoPlayer getPlayer() {
        return player;
    }

    public PlayerControlView getController() {
        return controller;
    }

    //Instance for Callback Interface
    HomeFragCallback homeFragCallback;
    SongsFragCallback songsFragCallback;
    ArtistsFragCallback artistsFragCallback;
    private ProgressBar progressBar;

    //Setter method for Callback
    public void setHomeFragAdapterSetUp(HomeFragCallback homeFragCallback) {
        this.homeFragCallback = homeFragCallback;
    }

    public void setSongsFragCallback(SongsFragCallback songsFragCallback) {
        this.songsFragCallback = songsFragCallback;
    }

    public void setArtistsFragCallback(ArtistsFragCallback artistsFragCallback) {
        this.artistsFragCallback = artistsFragCallback;
    }

    //MainActivity methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get view's ids
        Toolbar toolbar = findViewById(R.id.toolbar);
        viewPager2 = findViewById(R.id.vp2);
        tabLayout = findViewById(R.id.tab);
        progressBar = findViewById(R.id.loading);
        controller = findViewById(R.id.controller);

        //Set up toolbar
        toolbar.setTitle("  A music app");
        toolbar.setTitleMarginStart(10);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        //Set up viewPager
        adapter = new FragmentsCollectionAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Songs");
                    break;
                case 2:
                    tab.setText("Artists");
                    break;
                case 3:
                    tab.setText("Albums");
                    break;
                case 4:
                    tab.setText("Saved");
                    break;
                default:
                    break;
            }
        }).attach();

        //Call API
        APICall();

        //Set up UI player
        setUpMusicPlayerUI();

        //Set up player and controller
        playerSetUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            //Do search things here
            Toast.makeText(getApplicationContext(), "Search something...", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Call API and set up main RecyclerView adapter
    public void APICall() {
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<SongItem> items = new ArrayList<>();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getCandyAd("https://soundcloud.com/edsheeran/sets/tour-edition-1", 10);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) return;
                ResponseBody body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] UrlImage array!

                for (int i = 0; i < 10; i++) {
                    if (body != null) {
                        items.add(i, new SongItem(body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getImageUrl(),
                                body.getTracks().getItems()[i].getPublisher(), body.getTracks().getItems()[i].getDurationText(),
                                body.getTracks().getItems()[i].getId()));
                        Log.d("Song: ", items.get(i).getTitle());
                    }
                }

                List<SongRow> rows = new ArrayList<>();
                rows.add(new SongRow(0, "Favorites", items, SongRowViewType.CIRCLE));
                rows.add(new SongRow(1, "Artists", items, SongRowViewType.RECTANGLE));
                rows.add(new SongRow(2, "Albums", items, SongRowViewType.RECTANGLE));
                rows.add(new SongRow(3, "Saved", items, SongRowViewType.CIRCLE));

                progressBar.setVisibility(View.GONE);
                homeFragCallback.onAdapterSetUp(rows);
                songsFragCallback.cherries(items.size());
                songsFragCallback.onAdapterSetUp(items);

                APICallForArtist();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //API call for artists and artist's images
    public void APICallForArtist() {
        Log.e("Artists: ", "Get some artists");
        ArrayList<SongItem> items = new ArrayList<>();
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().getCandyAd("https://soundcloud.com/c-minh-446017979/sets/us-uk", 20);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) return;
                ResponseBody body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] UrlImage array!
                for (int i = 0; i < 20; i++) {
                    if (body != null) {
                        items.add(i, new SongItem(body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getImageUrl(),
                                body.getTracks().getItems()[i].getPublisher(), body.getTracks().getItems()[i].getDurationText(),
                                body.getTracks().getItems()[i].getId()));
                        Log.d("Song: ", items.get(i).getTitle());
                    }
                }
                artistsFragCallback.cakes(items.size());
                artistsFragCallback.onAdapterSetUp(items);

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Fail call: ", t.getMessage());
            }
        });
    }

    //Set up exoplayer music player
    public void playerSetUp() {
        player = new ExoPlayer.Builder(this).build();
        controller.setPlayer(player);
        controller.setOnClickListener(v -> {
            Log.e("UI player", "Clicked");
            uiLayout.setVisibility(View.VISIBLE);
        });
    }

    //Prepare song for player
    public void prepareSongFromUrl(long id, SongItem song) {
        //Call api to retrieve song url using its id
        Call<Candy> call = RetrofitClient.getInstance().getApi().getCandyTaste(id);
        if(runnable!=null) handler.removeCallbacks(runnable);
        runnable = () -> call.enqueue(new Callback<Candy>() {
            @Override
            public void onResponse(@NonNull Call<Candy> call1, @NonNull Response<Candy> response) {
                if (!response.isSuccessful()) Log.e("Message", "No song was found!");
                else Log.e("Message", "Response successfully");
                Candy candy = response.body();
                //Add url to mediaItem
                String url = null;
                if (candy != null) {
                    Log.e("Url", candy.getCandyTastes()[0].getUrl());
                    url = candy.getCandyTastes()[0].getUrl();
                }
                //Set up song with MediaItem
                Uri uri = Uri.parse(url);
                MediaItem item = MediaItem.fromUri(uri);
                //Add MediaItem to Exoplayer player
                player.addMediaItem(item);
                if(player.hasNextMediaItem()) {
                    player.seekToNextMediaItem();
                }
                player.prepare();
                controller.show();
                player.setPlayWhenReady(true);
                player.getPlaybackState();
                //Set up UI player layout
                getSongData(song);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        controller.setProgressUpdateListener((position, bufferedPosition)
                                -> songProgress.setProgress((int) position /(int) player.getDuration()));
                        handler.postDelayed(this, 1000);
                    }
                }, 1000);


            }

            @Override
            public void onFailure(@NonNull Call<Candy> call1, @NonNull Throwable t) {
                Log.e("Get song", "Fail!");
                Log.e("Error", t.getMessage());
            }
        });
        handler.postDelayed(runnable, 2000);
    }

    public void setUpMusicPlayerUI(){
        //Bind view's ids
        uiLayout = findViewById(R.id.ui_layout);
        uiBackBtn = findViewById(R.id.ui_back_btn);
        play_pauseBtn = findViewById(R.id.play_pause_btn);
        playerBackBtn = findViewById(R.id.back_btn);
        playerForwardBtn = findViewById(R.id.forward_btn);
        playerSkipPreviousBtn = findViewById(R.id.skip_previous_btn);
        playerSkipNextBtn = findViewById(R.id.skip_next_btn);
        songImage = findViewById(R.id.song_image);
        songName = findViewById(R.id.song_name);
        artistName = findViewById(R.id.artist_name);
        timeStartProgress = findViewById(R.id.time_start_progress);
        timeEndProgress = findViewById(R.id.time_end_progress);
        songProgress = findViewById(R.id.progressBar);

        //UI back Btn
        uiBackBtn.setOnClickListener(v -> {
            controller.show();
            uiLayout.setVisibility(View.GONE);
        });
    }

    public void getSongData(SongItem song){
        Glide.with(this).load(song.getImageUrl()).centerCrop().into(songImage);
        songName.setText(song.getTitle());
        artistName.setText(song.getPublisher().getArtist());
    }
}