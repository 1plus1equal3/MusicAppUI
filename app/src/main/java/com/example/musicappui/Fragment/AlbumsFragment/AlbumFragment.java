package com.example.musicappui.Fragment.AlbumsFragment;

import android.annotation.SuppressLint;
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
import com.example.musicappui.API.ApiSpotify.model_for_spotify_albums.Albums;
import com.example.musicappui.CallbackInterface.AlbumsFragCallback;
import com.example.musicappui.MainActivity;
import com.example.musicappui.R;

import java.util.ArrayList;

public class AlbumFragment extends Fragment implements AlbumsFragCallback {
    RecyclerView cheeseList;
    Spinner sortCheese;
    TextView cheeseNum;
    ArrayList<Albums> cheeses;

    //Fragment methods
    @Override
    public void onCreate(Bundle saveInstanceState){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null){
            mainActivity.setAlbumsFragCallback(this);
        }
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        cheeseList = view.findViewById(R.id.cheese_list);
        sortCheese = view.findViewById(R.id.sort_cheese);
        cheeseNum = view.findViewById(R.id.cheese_number);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //Doing some stuffs here
        super.onViewCreated(view, savedInstanceState);
    }


    //Override Callback methods
    @SuppressLint("SetTextI18n")
    @Override
    public void cheeses(int num) {
        cheeseNum.setText(num + " albums");
    }
    @Override
    public void onAdapterSetUp(ArrayList<Albums> items) {
       cheeses = items;
       CheeseListAdapter cheeseListAdapter = new CheeseListAdapter(items);
       cheeseList.setAdapter(cheeseListAdapter);
       cheeseList.setLayoutManager(new GridLayoutManager(cheeseList.getContext(), 2));
    }

}

class CheeseListAdapter extends RecyclerView.Adapter<CheeseListAdapter.CheeseViewHolder>{

    private final ArrayList<Albums> cakes;

    public CheeseListAdapter(ArrayList<Albums> cakes) {
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheeseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_favor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(cakes.get(position).getImages()[0].getUrl()).centerCrop().into(holder.cheeseImage);
        holder.cheeseName.setText(cakes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    static class CheeseViewHolder extends RecyclerView.ViewHolder{

        private final ImageView cheeseImage;
        private final TextView cheeseName;

        public CheeseViewHolder(@NonNull View itemView) {
            super(itemView);
            cheeseImage = itemView.findViewById(R.id.cake_image);
            cheeseName = itemView.findViewById(R.id.cake_name);
        }
    }
}

