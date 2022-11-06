package com.example.musicappui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicappui.API.RetrofitClient;
import com.example.musicappui.Fragment.FragmentsCollectionAdapter;
import com.example.musicappui.Fragment.HomeFragment.HomeFragment;
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.ResponseBody;
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.SongItem;
import com.example.musicappui.Fragment.SongsFragment.CherriesNum;
import com.example.musicappui.Fragment.SongsFragment.SongsFragment;
import com.example.musicappui.Fragment.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentsCollectionAdapter adapter;
    static ArrayList<SongItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Call API
        APICall();
        //Get view's ids
        Toolbar toolbar = findViewById(R.id.toolbar);
        viewPager2 = findViewById(R.id.vp2);
        tabLayout = findViewById(R.id.tab);

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
            switch (position){
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){
            //Do search things here
            Toast.makeText(getApplicationContext(), "Search something...", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Call API and set up main RecyclerView adapter
    public void APICall() {
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
                                body.getTracks().getItems()[i].getPublisher(), body.getTracks().getItems()[i].getDurationText()));
                        Log.d("Song: ", items.get(i).getTitle());
                    }
                }
                HomeFragment.AdapterSetUp(items);
                SongsFragment.AdapterSetUp(items);

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Fail call: ", t.getMessage());
            }
        });
    }
}