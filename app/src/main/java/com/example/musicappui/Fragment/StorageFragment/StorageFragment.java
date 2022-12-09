package com.example.musicappui.Fragment.StorageFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicappui.MainActivity;
import com.example.musicappui.QuerySavedSongs;
import com.example.musicappui.R;

import java.util.ArrayList;

public class StorageFragment extends Fragment {

    Spinner sortTea;
    TextView teaNum;
    RecyclerView teaList;


    //Fragment methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        QuerySavedSongs querySavedSongs = new QuerySavedSongs(getActivity());
        querySavedSongs.start();
        ArrayList<SavedSong> songs = querySavedSongs.getSongs();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if(querySavedSongs.isAlive())
                    handler.post(this);
                else {
                    teaNum.setText(songs.size() + " songs");
                    TeaListAdapter adapter = new TeaListAdapter(getContext(), songs);
                    teaList.setAdapter(adapter);
                    teaList.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage, container, false);
        //Get view's ids
        sortTea = view.findViewById(R.id.sort_tea);
        teaNum = view.findViewById(R.id.tea_number);
        teaList = view.findViewById(R.id.tea_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Doing some stuffs here
        super.onViewCreated(view, savedInstanceState);
    }

}

class TeaListAdapter extends RecyclerView.Adapter<TeaListAdapter.TeaViewHolder> {

    Context context;
    ArrayList<SavedSong> teas;

    public TeaListAdapter(Context context, ArrayList<SavedSong> teas) {
        this.context = context;
        this.teas = teas;
    }

    @NonNull
    @Override
    public TeaListAdapter.TeaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeaViewHolder holder, int position) {
        holder.teaName.setText(teas.get(position).getTitle());
        holder.teaArtist.setText(teas.get(position).getArtist());
        holder.teaItem.setOnClickListener(v -> ((MainActivity) holder.teaItem.getContext()).prepareSongFromUrl(null, teas.get(position)));

    }

    @Override
    public int getItemCount() {
        return teas.size();
    }

    static class TeaViewHolder extends RecyclerView.ViewHolder {

        TextView teaName, teaArtist;
        ConstraintLayout teaItem;
        ImageView teaImage;

        public TeaViewHolder(@NonNull View itemView) {
            super(itemView);
            //Get item's ids
            teaImage = itemView.findViewById(R.id.tea_image);
            teaItem = itemView.findViewById(R.id.tea_item);
            teaName = itemView.findViewById(R.id.tea_name);
            teaArtist = itemView.findViewById(R.id.tea_artist);
        }
    }
}
