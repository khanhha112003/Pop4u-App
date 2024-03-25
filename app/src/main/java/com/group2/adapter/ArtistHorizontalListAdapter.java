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

public class ArtistHorizontalListAdapter extends RecyclerView.Adapter<ArtistHorizontalListAdapter.ViewHolder> {
    Activity activity;
    List<Artist> artistList;

    public ArtistHorizontalListAdapter(Activity activity, List<Artist> artistList) {
        this.activity = activity;
        this.artistList = artistList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtArtistName;
        ImageView imvArtistAvatar;

        public ViewHolder(View view) {
            super(view);

            txtArtistName = (TextView) view.findViewById(R.id.txtArtistName);
            imvArtistAvatar = (ImageView) view.findViewById(R.id.imvArtistAvatar);
        }
    }

    @NonNull
    @Override
    public ArtistHorizontalListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.artist_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHorizontalListAdapter.ViewHolder holder, int position) {
        holder.imvArtistAvatar.setImageResource(artistList.get(position).getArtistAvatar());
        holder.txtArtistName.setText(artistList.get(position).getArtistName());

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }
}
