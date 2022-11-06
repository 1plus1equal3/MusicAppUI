package com.example.musicappui.Fragment.SongsFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.musicappui.Fragment.HomeFragment.model_for_candy_ad.SongItem;
import com.example.musicappui.R;
import java.util.ArrayList;

public class SongsFragment extends Fragment{

    private static RecyclerView cherryList;
    Spinner sortCherry;
    TextView cherryNum;
    static ArrayList<SongItem> cherries;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs_fragment, container, false);
        //Get view's ids
        cherryList = view.findViewById(R.id.cherry_list);
        sortCherry = view.findViewById(R.id.sort_cherry);
        cherryNum = view.findViewById(R.id.cherry_number);
        //Populate cherry Arraylist

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Doing some stuffs here
/*        CherryListAdapter cherryListAdapter = new CherryListAdapter(getContext(), cherries);
        cherryList.setAdapter(cherryListAdapter);
        cherryList.setLayoutManager(new LinearLayoutManager(getContext()));*/
        super.onViewCreated(view, savedInstanceState);
    }

    public static void AdapterSetUp(ArrayList<SongItem> items) {
        cherries = items;
        Log.e("SongFragment: ", cherries.get(0).getPublisher().getArtist());
        CherryListAdapter cherryListAdapter = new CherryListAdapter(cherryList.getContext(), cherries);
        cherryList.setAdapter(cherryListAdapter);
        cherryList.setLayoutManager(new LinearLayoutManager(cherryList.getContext()));
    }
}

class CherryListAdapter extends RecyclerView.Adapter<CherryListAdapter.CherryViewHolder>{

    Context context;
    ArrayList<SongItem> cherries;

    public CherryListAdapter(Context context, ArrayList<SongItem> cherries) {
        this.context = context;
        this.cherries = cherries;
    }

    @NonNull
    @Override
    public CherryListAdapter.CherryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CherryViewHolder(LayoutInflater.from(context).inflate(R.layout.cherry_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CherryListAdapter.CherryViewHolder holder, int position) {
        Glide.with(context).load(cherries.get(position).getImageUrl()).into(holder.cherryImage);
        holder.cherryName.setText(cherries.get(position).getTitle());
        holder.cherryArtist.setText(cherries.get(position).getPublisher().getArtist());
        holder.cherryDuration.setText(cherries.get(position).getDurationText()+" ms");
    }

    @Override
    public int getItemCount() {
        return cherries.size();
    }

    static class CherryViewHolder extends RecyclerView.ViewHolder{

        ImageView cherryImage;
        TextView cherryName, cherryArtist, cherryDuration;

        public CherryViewHolder(@NonNull View itemView) {
            super(itemView);
            //Get item's ids
            cherryImage = itemView.findViewById(R.id.cherry_image);
            cherryName = itemView.findViewById(R.id.cherry_name);
            cherryArtist = itemView.findViewById(R.id.cherry_artist);
            cherryDuration = itemView.findViewById(R.id.cherry_duration);
        }
    }
}