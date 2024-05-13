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
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistVerticalListAdapter extends RecyclerView.Adapter<ArtistVerticalListAdapter.ViewHolder> {
    Activity activity;
    List<Artist> artistList;
    OnClickListener onClickListener;

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
        Artist artist = artistList.get(position);
        String artistAvatar = artist.getArtistAvatar();
        holder.txtArtistName.setText(artist.getArtistName());
        holder.txtArtistYearDebut.setText("Ra mắt năm " + String.valueOf(artist.getArtistYearDebut()));
        Picasso.get()
                .load(artistAvatar)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .fit().centerCrop()
                .into(holder.imvArtistAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, artist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(int position, Artist artist);
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
