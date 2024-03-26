package com.group2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Artist;
import com.group2.pop4u_app.R;

import java.util.List;

public class ArtistVerticalListAdapter extends RecyclerView.Adapter<ArtistVerticalListAdapter.ViewHolder> {
    Activity activity;
    List<Artist> artistList;

    public ArtistVerticalListAdapter(Activity activity, List<Artist> artistList) {
        this.activity = activity;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.large_artist_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvArtistAvatar.setImageResource(artistList.get(position).getArtistAvatar());
        holder.txtArtistName.setText(artistList.get(position).getArtistName());
        holder.txtArtistYearDebut.setText(artistList.get(position).getArtistYearDebut());
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtArtistName, txtArtistYearDebut;
        ImageView imvArtistAvatar;

        public ViewHolder(View view) {
            super(view);
            txtArtistName = (TextView) view.findViewById(R.id.txtArtistName);
            txtArtistYearDebut = (TextView) view.findViewById(R.id.txtArtistYearDebut);
            imvArtistAvatar = (ImageView) view.findViewById(R.id.imvArtistAvatar);
        }
    }


}
