package com.example.musicappui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicappui.Fragment.FragmentsCollectionAdapter;
import com.example.musicappui.Fragment.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentsCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}