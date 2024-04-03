package com.group2.pop4u_app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

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
        setSupportActionBar(binding.tbrArtistList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_list_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.mnFilterProduct) {
            openFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilterDialog() {
        Dialog dialog = new Dialog(AllArtist.this);
        dialog.setContentView(R.layout.dialog_filter_artist);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            layoutParams.horizontalMargin = 0;
            layoutParams.verticalMargin = 0;
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }


    private void loadData() {
        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("recyclerName"));
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