package com.example.musicappui.Fragment.HomeFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicappui.R;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ListView candyList;

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
        CandyListAdapter candyListAdapter = new CandyListAdapter(getContext());
        candyList.setAdapter(candyListAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
//Adapter for list view
class CandyListAdapter extends BaseAdapter{

    private final Context context;

    public CandyListAdapter( Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.candy_layout, null);
        }
        //get View's ids
        TextView candyBrand, seeAllBtn;
        RecyclerView candyFavor;
        candyBrand = convertView.findViewById(R.id.candy_brand);
        seeAllBtn = convertView.findViewById(R.id.see_all);
        candyFavor = convertView.findViewById(R.id.recyclerView);

        //Set main type for each item
        switch (position){
            case 0:
                candyBrand.setText("Favorite");
                break;
            case 1:
                candyBrand.setText("Artists");
                break;
            case 2:
                candyBrand.setText("Albums");
                break;
            case 3:
                candyBrand.setText("Saved");
                break;
        }
        return convertView;
    }
}

class CandyFavorAdapter extends RecyclerView.Adapter<CandyFavorAdapter.ViewHolder>{

    private final ArrayList<Candy> candies;

    public CandyFavorAdapter(ArrayList<Candy> candies) {
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

    }

    @Override
    public int getItemCount() {
        return candies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView candyShell;
        private TextView candyShellDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            candyShell = itemView.findViewById(R.id.candy_shell);
            candyShellDescription = itemView.findViewById(R.id.candy_shell_description);
        }
    }
}
