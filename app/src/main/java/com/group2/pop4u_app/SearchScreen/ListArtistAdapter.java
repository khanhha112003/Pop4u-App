package com.group2.pop4u_app.SearchScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.pop4u_app.R;

import java.util.HashMap;
import java.util.List;

public class ListArtistAdapter extends RecyclerView.Adapter<ListArtistAdapter.ViewHolder> {

        Context context;
    List<HashMap<String, String>> listArtistData;

    ListArtistAdapter(Context context, List<HashMap<String, String>> listArtistData){
        this.context = context;
        this.listArtistData = listArtistData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_screen_list_artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.artistName.setText(listArtistData.get(position).get("artist_name"));
    }

    @Override
    public int getItemCount() {
        return listArtistData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistName;

        ImageView artistImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.artistName = (TextView) itemView.findViewById(R.id.txt_search_dashboard_item_artist_name);
            this.artistImage = (ImageView) itemView.findViewById(R.id.imv_search_dashboard_item_artist_image);
        }
    }
}