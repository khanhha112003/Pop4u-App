package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;
import com.group2.api.Services.AuthorService;
import com.group2.api.Services.ProductService;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ArtistInfoScreen extends AppCompatActivity {

    ActivityArtistInfoScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityArtistInfoScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        String artistCode = intent.getStringExtra("artistCode");
        CompletableFuture<Artist> futureArtist = AuthorService.instance.getArtistDetail(artistCode);
        CompletableFuture<ProductResponseDAO> futureProduct = ProductService.instance.getListProduct(1, "all", "desc", 10, 0, artistCode);
        futureArtist.thenAccept(artist -> {
            binding.txtArtistName.setText(artist.getArtistName());
            binding.txtArtistDescription.setText(artist.getArtistDescription());
//            binding.imvArtistAvatar
            binding.txtArtistYearDebut.setText(artist.getArtistYearDebut());

        });

        futureProduct.thenAccept(productResponse -> {
            ArrayList<ProductDAO> product = new ArrayList<>(productResponse.getProductList());
            ArrayList<Product> products = new ArrayList<>();
            for (ProductDAO productDAO : product) {
                products.add(productDAO.asProduct());
            }
        });
        try {
            futureArtist.get();
            futureProduct.get();
        } catch (Exception e) {
            Log.d("ArtistInfoScreen", "getData: " + e.getMessage());
        }
    }
}