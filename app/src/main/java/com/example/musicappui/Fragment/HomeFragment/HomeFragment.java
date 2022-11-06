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
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.ResponseBody;
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.SongItem;
import com.example.musicappui.R;
import com.example.musicappui.API.RetrofitClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static RecyclerView candyList;

    //Array contains song's info
    public static ArrayList<SongItem> candies = new ArrayList<>();

    //Call API and set up main RecyclerView adapter
    /*public void APICall() {
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
                        candies.add(i, new SongItem(body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getImageUrl()));
                    }
                    Log.e("Image url of " + i + " song is: ", candies.get(i).getImageUrl());
                }
                candyListAdapter = new CandyListAdapter(getContext(), candies);
                candyList.setAdapter(candyListAdapter);
                candyList.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Fail call: ", t.getMessage());
            }
        });
    }*/

    public static void AdapterSetUp(ArrayList<SongItem> items) {
        candies = items;
        CandyListAdapter candyListAdapter = new CandyListAdapter(candyList.getContext(), candies);
        candyList.setAdapter(candyListAdapter);
        candyList.setLayoutManager(new LinearLayoutManager(candyList.getContext()));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //API is only called 1 time during fragment creation
        /*APICall();*/
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
        /*switch (position) {
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
        }*/

        bindCandyAd(holder, position);

    }

    public void bindCandyAd(RowViewHolder holder, int position){
        CandyFavorAdapter candyFavorAdapter;
        String candyBrand;
        switch (position){
            case 0:
                candyBrand  = "Favorites";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 1:
                candyBrand  = "Artists";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 2:
                candyBrand  = "Albums";
                //Replace candies with favorite//artists//albums//saved
                candyFavorAdapter = new CandyFavorAdapter(candies, context);
                break;
            case 3:
                candyBrand  = "Saved";
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

/*public void submitData(List<Candy> list){
        candyList.clear();
        candyList.addAll(list);
        notifyDataSetChanged();
    }*/

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

