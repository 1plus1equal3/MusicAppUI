package com.example.musicappui.Fragment.HomeFragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.Artists;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;
import com.example.musicappui.API.model_for_candy_ad.SongRow;
import com.example.musicappui.API.model_for_candy_ad.SongRowViewType;
import com.example.musicappui.CallbackInterface.HomeFragCallback;
import com.example.musicappui.MainActivity;
import com.example.musicappui.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragCallback {

    //Array contains song's info
    private RecyclerView candyList;
    private CandyListAdapter candyListAdapter;
    ExoPlayer player;
    PlayerControlView controller;
    MainActivity activity;


    //Fragment methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setHomeFragAdapterSetUp(this);
        }
        if (activity != null) {
            player = activity.getPlayer();
            controller = activity.getController();
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        candyList = view.findViewById(R.id.candy_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        candyListAdapter = new CandyListAdapter();
        candyList.setAdapter(candyListAdapter);
        candyList.setHasFixedSize(true);
    }

    //CallBack override methods
    @Override
    public void onAdapterSetUp(List<SongRow> items) {
        candyListAdapter.setDataList(items);
    }


    //Adapter for main RecyclerView
    class CandyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        /* private List<Candy> candyList = new ArrayList<>();*/
        private final ArrayList<SongRow> candies = new ArrayList<>();

        public CandyListAdapter() {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (SongRowViewType.values()[viewType] == SongRowViewType.CIRCLE) {
                return new ArtistsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_circle_layout, parent, false));
            } else {
                return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FavoritesViewHolder) {
                ((FavoritesViewHolder) holder).bindCandyAd(candies.get(position));
            } else if (holder instanceof ArtistsViewHolder) {
                ((ArtistsViewHolder) holder).bindCandyAd(candies.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return candies.size();
        }

        //Get ViewType of ViewHolder?
        @Override
        public int getItemViewType(int position) {
            return candies.get(position).getViewType().ordinal();
        }

        public void setDataList(List<SongRow> list) {
            candies.clear();
            candies.addAll(list);
            notifyDataSetChanged();
        }

        //ViewHolder for favourite songs
        class FavoritesViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;

            CandyFavorAdapter_1 candyFavorAdapter;

            public FavoritesViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(SongRow row) {
                candyBrand.setText(row.getTitle());
                List<Track> list = (List<Track>) (List<?>) row.getTracks();
                candyFavorAdapter = new CandyFavorAdapter_1(list);
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

        //ViewHolder for artists
         class ArtistsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_0 candyFavorAdapter;

            public ArtistsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(SongRow row) {
                candyBrand.setText(row.getTitle());
                List<Artists> list = (List<Artists>) (List<?>) row.getTracks();
                candyFavorAdapter = new CandyFavorAdapter_0(list);
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

        //ViewHolder for albums
        class AlbumsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_0 candyFavorAdapter;

            public AlbumsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(SongRow row) {
                candyBrand.setText(row.getTitle());
                candyFavorAdapter = new CandyFavorAdapter_0(row.getTracks());
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

        //ViewHolder for saved songs
        class SavedSongsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_0 candyFavorAdapter;

            public SavedSongsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(SongRow row) {
                candyBrand.setText(row.getTitle());
                candyFavorAdapter = new CandyFavorAdapter_0(row.getTracks());
                candyAd.setAdapter(candyFavorAdapter);
            }
        }
    }

    //Sub RecyclerView Adapter for Circle Images
//Music items can be played
    class CandyFavorAdapter_0 extends RecyclerView.Adapter<CandyFavorAdapter_0.ViewHolder> {

        private final List<com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track> candies;

        public CandyFavorAdapter_0(List<com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track> candies) {
            this.candies = candies;
        }

        @NonNull
        @Override
        public CandyFavorAdapter_0.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandyFavorAdapter_0.ViewHolder holder, int position) {
            Log.d("Image: ", "Loaded");
            Glide.with(holder.itemView.getContext()).load(candies.get(position).getAlbum().getImages()[0].getUrl()).circleCrop().into(holder.candyShell);
            holder.candyShellDescription.setText(candies.get(position).getName());
            //Prepare song here
            holder.candyItem.setOnClickListener(v -> {
                Log.e("Id", String.valueOf(candies.get(position).getId()));
                if(activity!=null) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> activity.prepareSongFromUrl(candies.get(position)), 1500);
                }
            });

        }

        @Override
        public int getItemCount() {
            return candies.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView candyShell;
            private final TextView candyShellDescription;
            private final LinearLayout candyItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                candyShell = itemView.findViewById(R.id.candy_shell);
                candyShellDescription = itemView.findViewById(R.id.candy_shell_description);
                candyItem = itemView.findViewById(R.id.candy_item);
            }
        }

    }

    //Sub RecyclerView Adapter for Rectangle Images
    static class CandyFavorAdapter_1 extends RecyclerView.Adapter<CandyFavorAdapter_1.ViewHolder> {

        private final List<com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track> candies;

        public CandyFavorAdapter_1(List<Track> candies) {
            this.candies = candies;
        }

        @NonNull
        @Override
        public CandyFavorAdapter_1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandyFavorAdapter_1.ViewHolder holder, int position) {
            Log.d("Image: ", "Loaded");
            Glide.with(holder.itemView.getContext()).load(candies.get(position).getAlbum().getImages()[0].getUrl()).centerCrop().into(holder.candyShell);
            holder.candyShellDescription.setText(candies.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return candies.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView candyShell;
            private final TextView candyShellDescription;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                candyShell = itemView.findViewById(R.id.candy_shell);
                candyShellDescription = itemView.findViewById(R.id.candy_shell_description);
            }
        }

    }
}

