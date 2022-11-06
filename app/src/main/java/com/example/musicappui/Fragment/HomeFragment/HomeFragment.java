package com.example.musicappui.Fragment.HomeFragment;

import android.annotation.SuppressLint;
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
import com.example.musicappui.Fragment.HomeFragment.model.ResponseBody;
import com.example.musicappui.Fragment.HomeFragment.model.SongItem;
import com.example.musicappui.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView candyList;
    ArrayList<SongItem> candies = new ArrayList<>();
    CandyListAdapter candyListAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getSongId("https://soundcloud.com/edsheeran/sets/tour-edition-1", 10);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) return;
                ResponseBody body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] imageurl array!
                for (int i = 0; i < 10; i++) {
                    candies.add(i, new SongItem(body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getImageUrl()));
                    Log.e("Image url of " + i + " song is: ", candies.get(i).getImageUrl());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
            }
        });
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
        //Doing some stuffs here
        candyListAdapter = new CandyListAdapter(getContext(), candies);
        candyList.setAdapter(candyListAdapter);
        candyList.setLayoutManager(new LinearLayoutManager(getContext()));
        super.onViewCreated(view, savedInstanceState);
    }

/*    @Override
    public void onResume() {
        candyFavorAdapter = candyListAdapter.getCandyFavorAdapter();
        if (candyFavorAdapter != null) {
            candyFavorAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }*/
}

//Adapter for list view
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
        switch (position) {
            case 0:
                holder.candyBrand.setText("Favorite");
                //Replace candies with favorite//artists//albums//saved
                CandyFavorAdapter candyFavorAdapter_0 = new CandyFavorAdapter(candies, context);
                holder.candyAd.setAdapter(candyFavorAdapter_0);
                LinearLayoutManager candyLayout_0 = new LinearLayoutManager(context);
                candyLayout_0.setOrientation(RecyclerView.HORIZONTAL);
                holder.candyAd.setLayoutManager(candyLayout_0);
                break;
            case 1:
                holder.candyBrand.setText("Artists");
                //Replace candies with favorite//artists//albums//saved
                CandyFavorAdapter candyFavorAdapter_1 = new CandyFavorAdapter(candies, context);
                holder.candyAd.setAdapter(candyFavorAdapter_1);
                LinearLayoutManager candyLayout_1 = new LinearLayoutManager(context);
                candyLayout_1.setOrientation(RecyclerView.HORIZONTAL);
                holder.candyAd.setLayoutManager(candyLayout_1);
                break;
            case 2:
                holder.candyBrand.setText("Albums");
                //Replace candies with favorite//artists//albums//saved
                CandyFavorAdapter candyFavorAdapter_2 = new CandyFavorAdapter(candies, context);
                holder.candyAd.setAdapter(candyFavorAdapter_2);
                LinearLayoutManager candyLayout_2 = new LinearLayoutManager(context);
                candyLayout_2.setOrientation(RecyclerView.HORIZONTAL);
                holder.candyAd.setLayoutManager(candyLayout_2);
                break;
            case 3:
                holder.candyBrand.setText("Saved");
                //Replace candies with favorite//artists//albums//saved
                CandyFavorAdapter candyFavorAdapter_3 = new CandyFavorAdapter(candies, context);
                holder.candyAd.setAdapter(candyFavorAdapter_3);
                LinearLayoutManager candyLayout_3 = new LinearLayoutManager(context);
                candyLayout_3.setOrientation(RecyclerView.HORIZONTAL);
                holder.candyAd.setLayoutManager(candyLayout_3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

/*    public void submitData(List<Candy> list){
        candyList.clear();
        candyList.addAll(list);
        notifyDataSetChanged();
    }*/

    class RowViewHolder extends RecyclerView.ViewHolder {

        private TextView candyBrand, seeAll;
        private RecyclerView candyAd;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            candyBrand = itemView.findViewById(R.id.candy_brand);
            candyAd = itemView.findViewById(R.id.recyclerView);
            seeAll = itemView.findViewById(R.id.see_all);
        }

    }

}

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
        }
    }

}
