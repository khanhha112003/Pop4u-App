package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding;

import java.util.ArrayList;

public class ArtistInfoScreen extends AppCompatActivity {

    ActivityArtistInfoScreenBinding binding;

    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrArtistInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(binding.getRoot());
        getData();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {
        binding.nsvArtistInfo.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                getSupportActionBar().setTitle("Lana Del Rey");
                binding.nsvArtistInfo.setBackgroundColor(R.color.md_theme_surfaceContainerLow);
            }
        });

        bigProductCardRecyclerAdapter.setOnClickListener(new BigProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailScreen.class);
                intent.putExtra("productID", product.getProductID());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        String artistDes = intent.getStringExtra("artistID");
        binding.txtArtistDescription.setText(artistDes);

        Artist artist = new Artist(12, R.drawable.img, "ABC", "ABC", 2021);
        binding.imvArtistAvatar.setImageResource(artist.getArtistAvatar());
        binding.txtArtistName.setText(artist.getArtistName());
        binding.txtArtistYearDebut.append(String.valueOf(artist.getArtistYearDebut()));
        binding.txtArtistDescription.setText(artist.getArtistDescription());

        productArrayList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        bigProductCardRecyclerAdapter = new BigProductCardRecyclerAdapter(ArtistInfoScreen.this, productArrayList);
        binding.rccProductOfArtist.setLayoutManager(gridLayoutManager);
        binding.rccProductOfArtist.addItemDecoration(itemOffsetDecoration);
    }
}