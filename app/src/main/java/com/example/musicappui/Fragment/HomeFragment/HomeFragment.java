package com.example.musicappui.Fragment.HomeFragment;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicappui.API.model_for_candy_ad.SongItem;
import com.example.musicappui.API.model_for_candy_ad.SongRow;
import com.example.musicappui.API.model_for_candy_ad.SongRowViewType;
import com.example.musicappui.CallbackInterface.HomeFragCallback;
import com.example.musicappui.MainActivity;
import com.example.musicappui.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragCallback {

    //Array contains song's info
    private RecyclerView candyList;
    private CandyListAdapter candyListAdapter;

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

        candyListAdapter = new CandyListAdapter();
        candyList.setAdapter(candyListAdapter);
        candyList.setHasFixedSize(true);
    }

    //CallBack override methods
    @Override
    public void onAdapterSetUp(List<SongRow> items) {
        candyListAdapter.setDataList(items);
    }
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
            return new CircleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_circle_layout, parent, false));
        } else {
            return new RowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.candy_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RowViewHolder) {
            ((RowViewHolder) holder).bindCandyAd(candies.get(position));
        } else if (holder instanceof CircleViewHolder) {
            ((CircleViewHolder) holder).bindCandyAd(candies.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return candies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return candies.get(position).getViewType().ordinal();
    }

    public void setDataList(List<SongRow> list) {
        candies.clear();
        candies.addAll(list);
        notifyDataSetChanged();
    }

    static class RowViewHolder extends RecyclerView.ViewHolder {

        private final TextView candyBrand;
        private final TextView seeAll;
        private final RecyclerView candyAd;
        CandyFavorAdapter candyFavorAdapter;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            candyBrand = itemView.findViewById(R.id.candy_brand);
            candyAd = itemView.findViewById(R.id.recyclerView);
            seeAll = itemView.findViewById(R.id.see_all);
        }

        public void bindCandyAd(SongRow row) {
            candyBrand.setText(row.getTitle());
            candyFavorAdapter = new CandyFavorAdapter(row.getSongItems());
            candyAd.setAdapter(candyFavorAdapter);
        }
    }

    static class CircleViewHolder extends RecyclerView.ViewHolder {

        private final TextView candyBrand;
        private final TextView seeAll;
        private final RecyclerView candyAd;
        CandyFavorAdapter candyFavorAdapter;

        public CircleViewHolder(@NonNull View itemView) {
            super(itemView);
            candyBrand = itemView.findViewById(R.id.candy_brand);
            candyAd = itemView.findViewById(R.id.recyclerView);
            seeAll = itemView.findViewById(R.id.see_all);
        }

        public void bindCandyAd(SongRow row) {
            candyBrand.setText(row.getTitle());
            candyFavorAdapter = new CandyFavorAdapter(row.getSongItems());
            candyAd.setAdapter(candyFavorAdapter);
        }
    }
}

//Sub RecyclerView Adapter
class CandyFavorAdapter extends RecyclerView.Adapter<CandyFavorAdapter.ViewHolder> {

    private final List<SongItem> candies;

    public CandyFavorAdapter(List<SongItem> candies) {
        this.candies = candies;
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
        Glide.with(holder.itemView.getContext()).load(candies.get(position).getImageUrl()).circleCrop().into(holder.candyShell);
        holder.candyShellDescription.setText(candies.get(position).getTitle());
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
/*
            candyShell.setBackgroundResource(R.drawable.roundborder);
*/
        }
    }

}

