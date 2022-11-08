package com.example.musicappui.Fragment.HomeFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicappui.CallbackInterface.HomeFragCallback;
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.SongItem;
import com.example.musicappui.MainActivity;
import com.example.musicappui.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeFragCallback {

    private RecyclerView candyList;

    //Array contains song's info
    public ArrayList<SongItem> candies = new ArrayList<>();

    //Fragment methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setHomeFragAdapterSetUp(this);
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
    }

//CallBack override methods
    @Override
    public void AdapterSetUp(ArrayList<SongItem> items) {
        candies = items;
        CandyListAdapter candyListAdapter = new CandyListAdapter(candyList.getContext(), candies);
        candyList.setAdapter(candyListAdapter);
        candyList.setLayoutManager(new LinearLayoutManager(candyList.getContext()));
    }

}

//Adapter for main RecyclerView
class CandyListAdapter extends RecyclerView.Adapter<CandyListAdapter.RowViewHolder> {

    /* private List<Candy> candyList = new ArrayList<>();*/
    private final Context context;
    private final ArrayList<SongItem> candies;

    public CandyListAdapter(Context context, ArrayList<SongItem> candies) {
        this.context = context;
        this.candies = candies;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        bindCandyAd(holder, position);
    }

    public void bindCandyAd(RowViewHolder holder, int position) {
        CandyFavorAdapter candyFavorAdapter;
        String candyBrand;
        switch (position) {
            case 0:
                candyBrand = "Favorites";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 1:
                candyBrand = "Artists";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 2:
                candyBrand = "Albums";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 3:
                candyBrand = "Saved";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            default:
                candyBrand = null;
                candyFavorAdapter = null;
        }
        holder.candyBrand.setText(candyBrand);
        holder.candyAd.setAdapter(candyFavorAdapter);
        LinearLayoutManager candyLayout = new LinearLayoutManager(context);
        candyLayout.setOrientation(RecyclerView.HORIZONTAL);
        holder.candyAd.setLayoutManager(candyLayout);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class RowViewHolder extends RecyclerView.ViewHolder {

        private final TextView candyBrand;
        private final TextView seeAll;
        private final RecyclerView candyAd;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            candyBrand = itemView.findViewById(R.id.candy_brand);
            candyAd = itemView.findViewById(R.id.recyclerView);
            seeAll = itemView.findViewById(R.id.see_all);
        }

    }

}

//Sub RecyclerView Adapter
class CandyFavorAdapter extends RecyclerView.Adapter<CandyFavorAdapter.ViewHolder> {

    private final ArrayList<SongItem> candies;
    private final Context context;

    public CandyFavorAdapter(ArrayList<SongItem> candies, Context context) {
        this.candies = candies;
        this.context = context;
    }

    @NonNull
    @Override
    public CandyFavorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_favor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandyFavorAdapter.ViewHolder holder, int position) {
        Log.d("Image: ", "Loaded");
        Glide.with(context).load(candies.get(position).getImageUrl()).into(holder.candyShell);
        holder.candyShellDescription.setText(candies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return candies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView candyShell;
        private final TextView candyShellDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            candyShell = itemView.findViewById(R.id.candy_shell);
            candyShellDescription = itemView.findViewById(R.id.candy_shell_description);
/*
            candyShell.setBackgroundResource(R.drawable.roundborder);
*/
        }
    }

}

