package com.example.musicappui.Fragment.HomeFragment;

import android.os.Bundle;
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
import com.example.musicappui.API.ApiSpotify.model_for_spotify_albums.Albums;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_artists.Artists;
import com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track;
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
    }

    //CallBack override methods
    @Override
    public void onAdapterSetUp(List<Track> tracks, List<Artists> artists, List<Albums> albums) {
        candyListAdapter.setDataList(tracks, artists, albums);

    }


    //Adapter for main RecyclerView
    static class CandyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        /* private List<Candy> candyList = new ArrayList<>();*/
        private final List<Track> tracks = new ArrayList<>();
        private final List<Artists> artists =  new ArrayList<>();
        private final List<Albums> albums =  new ArrayList<>();
/*
        private final ArrayList<SavedSongs> savedSongs =  new ArrayList<>();
*/

        public CandyListAdapter() {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

           /*if (SongRowViewType.values()[viewType] == SongRowViewType.Artists) {
                return new ArtistsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_circle_layout, parent, false));
            } else if(SongRowViewType.values()[viewType] == SongRowViewType.FavoriteSongs) {
                return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
            } else if(SongRowViewType.values()[viewType] == SongRowViewType.Albums) {
                return new AlbumsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
            } else
                return null;*/

            switch (viewType){
                case 0:
                    Log.d("aa0", "0");
                    return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
                case 1:
                    Log.d("aa1", "1");
                    return new ArtistsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_circle_layout, parent, false));
                case 2:
                    Log.d("aa2", "2");
                    return new AlbumsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
                default:
                    return null;
            }
            }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
/*            if (holder instanceof FavoritesViewHolder) {
                ((FavoritesViewHolder) holder).bindCandyAd(tracks);
            } else if (holder instanceof ArtistsViewHolder) {
                ((ArtistsViewHolder) holder).bindCandyAd(artists.get(position));
            }*/

                    if (holder instanceof FavoritesViewHolder) {
                        ((FavoritesViewHolder) holder).bindCandyAd(tracks);
                    }
                    if (holder instanceof ArtistsViewHolder) {
                        ((ArtistsViewHolder) holder).bindCandyAd(artists);
                    }
                    if (holder instanceof AlbumsViewHolder) {
                        ((AlbumsViewHolder) holder).bindCandyAd(albums);
                    }
            }


        @Override
        public int getItemCount() {
            return 3;
        }

        //Get ViewType of ViewHolder?
        @Override
        public int getItemViewType(int position) {
      /*      switch (position){
                case 0:
                    Log.e("Song ", String.valueOf(SongRowViewType.FavoriteSongs.ordinal()));
                    return SongRowViewType.FavoriteSongs.ordinal();
                case 1:
                    Log.e("Song ", String.valueOf(SongRowViewType.FavoriteSongs.ordinal()));
                    return SongRowViewType.Artists.ordinal();
                case 2:
                    Log.e("Song ", String.valueOf(SongRowViewType.FavoriteSongs.ordinal()));
                    return SongRowViewType.Albums.ordinal();
                default:
                    Log.e("Song ", String.valueOf(SongRowViewType.FavoriteSongs.ordinal()));
                    return 0;
            }*/
            Log.e("Position: ", String.valueOf(position));
            return position;
        }

        public void setDataList(List<Track> tracksIn, List<Artists> artistsIn, List<Albums> albumsIn) {
            tracks.clear();
            tracks.addAll(tracksIn);
            artists.clear();
            artists.addAll(artistsIn);
            albums.clear();
            albums.addAll(albumsIn);
            notifyDataSetChanged();
        }

        //ViewHolder for favourite songs
        static class FavoritesViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;

            CandyFavorAdapter_FavoriteSongs candyFavorAdapter;

            public FavoritesViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(List<Track> tracks) {
                candyBrand.setText("Favorites");
                candyFavorAdapter = new CandyFavorAdapter_FavoriteSongs(tracks);
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

        //ViewHolder for artists
        static class ArtistsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_Artists candyFavorAdapter;

            public ArtistsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(List<Artists> artists) {
                candyBrand.setText("Artists");
                candyFavorAdapter = new CandyFavorAdapter_Artists(artists);
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

        //ViewHolder for albums
        static class AlbumsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_Albums candyFavorAdapter;

            public AlbumsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(List<Albums> albums) {
                candyBrand.setText("Albums");
                candyFavorAdapter = new CandyFavorAdapter_Albums(albums);
                candyAd.setAdapter(candyFavorAdapter);
            }
        }

/*        //ViewHolder for saved songs
        class SavedSongsViewHolder extends RecyclerView.ViewHolder {

            private final TextView candyBrand;
            private final TextView seeAll;
            private final RecyclerView candyAd;
            CandyFavorAdapter_Artists candyFavorAdapter;

            public SavedSongsViewHolder(@NonNull View itemView) {
                super(itemView);
                candyBrand = itemView.findViewById(R.id.candy_brand);
                candyAd = itemView.findViewById(R.id.recyclerView);
                seeAll = itemView.findViewById(R.id.see_all);
            }

            public void bindCandyAd(SongRow row) {
                candyBrand.setText(row.getTitle());
                candyFavorAdapter = new CandyFavorAdapter_Artists(row.getTracks());
                candyAd.setAdapter(candyFavorAdapter);
            }
        }*/
    }

    //Sub RecyclerView Adapter for Favorite Songs
    static class CandyFavorAdapter_FavoriteSongs extends RecyclerView.Adapter<CandyFavorAdapter_FavoriteSongs.ViewHolder> {

        private final List<com.example.musicappui.API.ApiSpotify.model_for_spotify_songs.Track> candies;

        public CandyFavorAdapter_FavoriteSongs(List<Track> candies) {
            this.candies = candies;
        }

        @NonNull
        @Override
        public CandyFavorAdapter_FavoriteSongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandyFavorAdapter_FavoriteSongs.ViewHolder holder, int position) {
            Log.d("Image: ", "Loaded");
            Glide.with(holder.itemView.getContext()).load(candies.get(position).getAlbum().getImages()[0].getUrl()).centerCrop().into(holder.candyShell);
            holder.candyShellDescription.setText(candies.get(position).getName());
            holder.candyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) holder.candyItem.getContext()).prepareSongFromUrl(candies.get(position));
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

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView candyShell;
            private final TextView candyShellDescription;
            private final LinearLayout candyItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                candyItem = itemView.findViewById(R.id.candy_item);
                candyShell = itemView.findViewById(R.id.candy_shell);
                candyShellDescription = itemView.findViewById(R.id.candy_shell_description);
            }
        }

    }

    //Sub RecyclerView Adapter for Artists
    static class CandyFavorAdapter_Artists extends RecyclerView.Adapter<CandyFavorAdapter_Artists.ViewHolder> {

        private final List<Artists> candies;

        public CandyFavorAdapter_Artists(List<Artists> candies) {
            this.candies = candies;
        }

        @NonNull
        @Override
        public CandyFavorAdapter_Artists.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandyFavorAdapter_Artists.ViewHolder holder, int position) {
            Log.d("Image for artist: ", "Loaded");
            Glide.with(holder.itemView.getContext()).load(candies.get(position).getImages()[0].getUrl()).circleCrop().into(holder.candyShell);
            holder.candyShellDescription.setText(candies.get(position).getName());
            //Open a new Fragment showing tracks related to this artist
            holder.candyItem.setOnClickListener(v -> {

            });

        }

        @Override
        public int getItemCount() {
            return candies.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {

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

    //Sub RecyclerView Adapter for Albums
    static class CandyFavorAdapter_Albums extends RecyclerView.Adapter<CandyFavorAdapter_Albums.ViewHolder> {
        private final List<Albums> candies;
        public CandyFavorAdapter_Albums(List<Albums> candies) {
            this.candies = candies;
        }
        @NonNull
        @Override
        public CandyFavorAdapter_Albums.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandyFavorAdapter_Albums.ViewHolder holder, int position) {
            Log.d("Image for album: ", "Loaded");
            Glide.with(holder.itemView.getContext()).load(candies.get(position).getImages()[0].getUrl()).centerCrop().into(holder.candyShell);
            holder.candyShellDescription.setText(candies.get(position).getName());
            //Open a new Fragment showing tracks related to this album
            holder.candyItem.setOnClickListener(v -> {

            });

        }

        @Override
        public int getItemCount() {
            return candies.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {

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


}

