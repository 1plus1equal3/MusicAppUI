package com.example.musicappui;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.musicappui.API.ApiSpotify.ResponseForAccessToken;
import com.example.musicappui.API.ApiSpotify.SpotifyClient_0;
import com.example.musicappui.API.ApiSpotify.SpotifyClient_1;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.Artists;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.ArtistsResponse;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.RecommendSongs;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;
import com.example.musicappui.API.model_for_candy_ad.SongRow;
import com.example.musicappui.API.model_for_candy_ad.SongRowViewType;
import com.example.musicappui.API.model_for_candy_ad.Songs;
import com.example.musicappui.CallbackInterface.ArtistsFragCallback;
import com.example.musicappui.CallbackInterface.HomeFragCallback;
import com.example.musicappui.CallbackInterface.SongsFragCallback;
import com.example.musicappui.Fragment.FragmentsCollectionAdapter;
import com.example.musicappui.Fragment.ZoomOutPageTransformer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentsCollectionAdapter adapter;
    ExoPlayer player;
    PlayerControlView controller;
    ArrayList<Track> items = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<Artists> artists = new ArrayList<>();
    //Response for access token
    ResponseForAccessToken accessTokenResponse;

    //UI player Views
    /*LinearLayout controllerClick;*/ ConstraintLayout uiLayout;
    ImageButton uiBackBtn, play_pauseBtn, playerBackBtn, playerForwardBtn, playerSkipPreviousBtn, playerSkipNextBtn;
    ImageView songImage;
    TextView songName, artistName, timeStartProgress, timeEndProgress;
    ProgressBar songProgress;
    //Instance for Callback Interface
    HomeFragCallback homeFragCallback;
    SongsFragCallback songsFragCallback;
    ArtistsFragCallback artistsFragCallback;
    private ProgressBar progressBar;

    public ExoPlayer getPlayer() {
        return player;
    }

    public PlayerControlView getController() {
        return controller;
    }

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
        //Check permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            Log.d("Permission", "Granted");
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

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
        getAccessToken();

        //Set up UI player
        setUpMusicPlayerUI();

        //Set up player and controller
        playerSetUp();
    }

    //Permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            Log.d("Permission", "Granted");
        else Log.d("Permission", "Denied");
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

    //API call to get Access Token
    public void getAccessToken() {
        SharedPreferences preferences = getSharedPreferences("TokenInfo", MODE_PRIVATE);
        Call<ResponseForAccessToken> call = SpotifyClient_0.getInstance().getApiSpotify().getAccessToken("client_credentials");
        call.enqueue(new Callback<ResponseForAccessToken>() {
            @Override
            public void onResponse(@NonNull Call<ResponseForAccessToken> call, @NonNull Response<ResponseForAccessToken> response) {
                if (!response.isSuccessful()) return;
                accessTokenResponse = response.body();
                if (accessTokenResponse != null) {
                    Log.e("Access Token", accessTokenResponse.getAccess_token());
                    Log.e("Token Type", accessTokenResponse.getToken_type());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", accessTokenResponse.getAccess_token());
                    editor.putString("type", accessTokenResponse.getToken_type());
                    editor.apply();
                    APICall();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseForAccessToken> call, @NonNull Throwable t) {
                Log.d("Call", "Failed");
            }
        });
    }

    public void APICall() {
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<Songs> items = new ArrayList<>();
        Call<RecommendSongs> call = SpotifyClient_1.getInstance(this).getApiSpotify().getTracks(20);
        call.enqueue(new Callback<RecommendSongs>() {
            @Override
            public void onResponse(@NonNull Call<RecommendSongs> call, @NonNull Response<RecommendSongs> response) {
                if (!response.isSuccessful()) return;
                RecommendSongs body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] UrlImage array!

                for (int i = 0; i < 20; i++) {
                    if (body != null) {
                        Track track = body.getItems()[i].getTrack();
                        items.add(i, new Track(
                                track.getDuration_ms(),
                                track.getId(),
                                track.getName(),
                                track.getUri(),
                                track.getArtists(),
                                track.getPreview_url(),
                                track.getAlbum()
                        ));
                        ids.add(i, track.getId());
                        Log.d("Song: ", ((Track) items.get(i)).getName());
                    }
                }

                List<SongRow> rows = new ArrayList<>();
                rows.add(new SongRow(0, "Favorites", items, SongRowViewType.CIRCLE));
                APICallForArtist();
                rows.add(new SongRow(1, "Artists", artists, SongRowViewType.RECTANGLE));
                rows.add(new SongRow(2, "Albums", items, SongRowViewType.RECTANGLE));
                rows.add(new SongRow(3, "Saved", items, SongRowViewType.CIRCLE));

                progressBar.setVisibility(View.GONE);
                homeFragCallback.onAdapterSetUp(rows);
                songsFragCallback.cherries(items.size());
                songsFragCallback.onAdapterSetUp(items);

                APICallForArtist();
            }

            @Override
            public void onFailure(@NonNull Call<RecommendSongs> call, @NonNull Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //API call for artists and artist's images
    public void APICallForArtist() {
        Log.e("Artists: ", "Get some artists");
        String idsStr = ids.get(0);
        for(int i = 1; i < ids.size(); i++){
            idsStr = "," + ids.get(i);
        }
        Call<ArtistsResponse> call = SpotifyClient_1.getInstance(this).getApiSpotify().getArtists(idsStr);
        call.enqueue(new Callback<ArtistsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArtistsResponse> call, @NonNull Response<ArtistsResponse> response) {
                if(!response.isSuccessful()) return;
                ArtistsResponse artistsResponse = response.body();
                if(artistsResponse==null) return;
                for(int i = 0; i < artistsResponse.getArtists().length; i++){
                    artists.add(1, new Artists(
                            artistsResponse.getArtists()[i].getId(),
                            artistsResponse.getArtists()[i].getImages(),
                            artistsResponse.getArtists()[i].getName()
                            ));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArtistsResponse> call, @NonNull Throwable t) {
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
    public void prepareSongFromUrl(Track song) {
        Uri uri;
        MediaItem item;
        if(song.getPreview_url()==null) return;
        uri = Uri.parse(song.getPreview_url());
        item = new MediaItem.Builder()
                    .setUri(uri)
                    .setMediaId(song.getId())
                    .build();
        //Add MediaItem to Exoplayer player
        if(player.getMediaItemCount()<4) {
            player.addMediaItem(item);
            player.getMediaItemAt(player.getMediaItemCount() - 1);
        }
        else {
            player.removeMediaItem(player.getCurrentMediaItemIndex()-3);
            player.addMediaItem(item);
            player.getMediaItemAt(player.getMediaItemCount() - 1);
        }
        player.prepare();
        controller.show();

        //Set up UI player layout
        getSongData(song);

        //Set max progress
        player.addListener(new Player.Listener() {

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    songProgress.setMax((int) player.getDuration() / 1000);
                    Log.e("Song duration: ", String.valueOf(player.getDuration()));
                }
                if (playbackState == ExoPlayer.STATE_ENDED)
                    play_pauseBtn.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_baseline_play_circle_24));
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying)
                    play_pauseBtn.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_baseline_pause_circle_24));
                else
                    play_pauseBtn.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_baseline_play_circle_24));
            }

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                if (mediaItem != null) {
                    getSongData(findSong(mediaItem.mediaId));
                }
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
            }
        });

        //Progress update
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                songProgress.setProgress((int) player.getCurrentPosition() / 1000);
                long minuteStart = player.getCurrentPosition() / 60000;
                long secondStart = (player.getCurrentPosition() / 1000) % 60;
                long minuteEnd = (player.getDuration() - player.getCurrentPosition()) / 60000;
                long secondEnd = ((player.getDuration() - player.getCurrentPosition()) / 1000) % 60;
                setProgressStartTime(minuteStart, secondStart);
                setProgressEndTime(minuteEnd, secondEnd);
                handler1.post(this);
            }
        }, 1000);
    }

    //Set progress timeline
    public void setProgressStartTime(long minute, long second) {
        String minuteStr;
        String secondStr;
        if (minute < 10) minuteStr = "0" + minute;
        else minuteStr = "" + minute;
        if (second < 10) secondStr = "0" + second;
        else secondStr = "" + second;
        timeStartProgress.setText(minuteStr + ":" + secondStr);
    }

    public void setProgressEndTime(long minute, long second) {
        String minuteStr;
        String secondStr;
        if (minute < 10) minuteStr = "0" + minute;
        else minuteStr = "" + minute;
        if (second < 10) secondStr = "0" + second;
        else secondStr = "" + second;
        timeEndProgress.setText(minuteStr + ":" + secondStr);
    }

    //Find song using its id
    public Track findSong(String id){
        for(int i = 0; i < items.size(); i++){
            if(Objects.equals(id, items.get(i).getId())){
                return items.get(i);
            }
        }
        return null;
    }

    //Set up UI for player
    public void setUpMusicPlayerUI() {
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

    //Load song's image, name, artist
    public void getSongData(Track song) {
        Glide.with(this).load(song.getAlbum().getImages()[0].getUrl()).centerCrop().into(songImage);
        songName.setText(song.getName());
        artistName.setText(song.getArtists()[0].getName());
    }

    //Player's button onClick methods
    public void playBtn(View view) {
        if (!player.isPlaying()) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        } else {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    public void replayBtn(View view) {
        if (player.getCurrentPosition() >= 10000)
            player.seekTo(player.getCurrentPosition() - 10000);
        else player.seekTo(0);
    }

    public void forwardBtn(View view) {
        if (player.getCurrentPosition() < player.getDuration() - 10000)
            player.seekTo(player.getCurrentPosition() + 10000);
    }

    public void skipPreviousBtn(View view) {
        if (player.hasPreviousMediaItem()) player.seekToPreviousMediaItem();
        else player.seekTo(0);
    }

    public void skipNextBtn(View view) {
        if (player.hasNextMediaItem()) player.seekToNextMediaItem();
        else player.seekTo(player.getDuration());
    }

}