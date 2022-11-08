package com.example.musicappui.Fragment.ArtistsFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicappui.API.model_for_candy_ad.SongItem;
import com.example.musicappui.CallbackInterface.ArtistsFragCallback;
import com.example.musicappui.MainActivity;
import com.example.musicappui.R;
import java.util.ArrayList;

public class ArtistsFragment extends Fragment implements ArtistsFragCallback {

    RecyclerView cakeList;
    Spinner sortCake;
    TextView cakeNum;
    ArrayList<SongItem> cakes;

    //Fragment methods
    @Override
    public void onCreate(Bundle saveInstanceState){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null){
            mainActivity.setArtistsFragCallback(this);
        }
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment, container, false);
        cakeList = view.findViewById(R.id.cake_list);
        sortCake = view.findViewById(R.id.sort_cake);
        cakeNum = view.findViewById(R.id.cake_number);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //Doing some stuffs here
        super.onViewCreated(view, savedInstanceState);
    }

    //Override Callback methods
    @Override
    public void cakes(int num) {
        cakeNum.setText(num + " artists");
    }

    @Override
    public void AdapterSetUp(ArrayList<SongItem> items) {
        cakes = items;
        CakeListAdapter cakeListAdapter = new CakeListAdapter(getContext(), cakes);
        cakeList.setAdapter(cakeListAdapter);
        cakeList.setLayoutManager(new GridLayoutManager(cakeList.getContext(), 2));
    }

}

class CakeListAdapter extends RecyclerView.Adapter<CakeListAdapter.CakeViewHolder>{

    private final Context context;
    private final ArrayList<SongItem> cakes;

    public CakeListAdapter(Context context, ArrayList<SongItem> cakes) {
        this.context = context;
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public CakeListAdapter.CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CakeListAdapter.CakeViewHolder(LayoutInflater.from(context).inflate(R.layout.cake_favor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CakeListAdapter.CakeViewHolder holder, int position) {
        Glide.with(context).load(cakes.get(position).getImageUrl()).into(holder.cakeImage);
        holder.cakeName.setText(cakes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    static class CakeViewHolder extends RecyclerView.ViewHolder{

        private final ImageView cakeImage;
        private final TextView cakeName;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            cakeImage = itemView.findViewById(R.id.cake_image);
            cakeName = itemView.findViewById(R.id.cake_name);
        }
    }
}
