package com.group2.pop4u_app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.group2.adapter.ArtistVerticalListAdapter;
import com.group2.model.Artist;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityAllArtistBinding;

import java.util.ArrayList;

public class AllArtist extends AppCompatActivity {

    ActivityAllArtistBinding binding;

    ArtistVerticalListAdapter artistVerticalListAdapter;
    ArrayList<Artist> artistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllArtistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
    }

    private void loadData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        binding.rccAllArtist.setLayoutManager(gridLayoutManager);
        binding.rccAllArtist.addItemDecoration(itemDecoration);
        binding.rccAllArtist.setHasFixedSize(true);

        artistArrayList = new ArrayList<>();
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));

        artistVerticalListAdapter = new ArtistVerticalListAdapter(this, artistArrayList);
        binding.rccAllArtist.setAdapter(artistVerticalListAdapter);
    }
}