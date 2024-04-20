package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.api.Services.ArtistService;
import com.group2.api.Services.ProductService;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ArtistInfoScreen extends AppCompatActivity {

    ActivityArtistInfoScreenBinding binding;

    ArrayList<Product> listArtistProduct = new ArrayList<>();

    MiniProductCardRecyclerAdapter artistProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityArtistInfoScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpRecycleView();
        setBackButton();
        getData();
    }

    private void setBackButton() {
        binding.imvArtistDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManagerNewProduct = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ItemOffsetHorizontalRecycler itemOffsetHorizontalRecycler = new ItemOffsetHorizontalRecycler(getBaseContext(), R.dimen.item_offset);
        binding.rccProductOfArtist.addItemDecoration(itemOffsetHorizontalRecycler);
        binding.rccProductOfArtist.setLayoutManager(layoutManagerNewProduct);
        binding.rccProductOfArtist.setHasFixedSize(true);

        artistProductAdapter = new MiniProductCardRecyclerAdapter(this, listArtistProduct);
        binding.rccProductOfArtist.setAdapter(artistProductAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        String artistCode = intent.getStringExtra("artistCode");
        CompletableFuture<Artist> futureArtist = ArtistService.instance.getArtistDetail(artistCode);
        CompletableFuture<ArrayList<Product>> futureProduct = ProductService.instance.getListProduct(1, "all", "asc", 10, 0, artistCode);
        futureArtist.thenAccept(artist -> {
            binding.txtArtistName.setText(artist.getArtistName());
            binding.txtArtistDescription.setText(artist.getArtistDescription());
             Picasso
                     .get()
                     .load(artist.getArtistAvatar())
                     .placeholder(R.drawable.placeholder_image)
                     .error(R.drawable.error_image)
                     .fit().centerInside()
                     .into(binding.imvArtistAvatar);
            binding.txtArtistYearDebut.setText(artist.getArtistYearDebut());

        });

        futureProduct.thenAccept(products -> {
            listArtistProduct.addAll(products);
            artistProductAdapter.notifyDataSetChanged();
        });

        try {
            futureArtist.get();
            futureProduct.get();
        } catch (Exception e) {
            Log.d("ArtistInfoScreen", "getData: " + e.getMessage());
        }
    }
}