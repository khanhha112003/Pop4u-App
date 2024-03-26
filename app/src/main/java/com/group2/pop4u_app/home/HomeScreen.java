package com.group2.pop4u_app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.R;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    com.group2.pop4u_app.databinding.ActivityHomeScreenBinding binding;
    ArtistHorizontalListAdapter artistHorizontalListAdapter;

    MiniProductCardRecyclerAdapter miniProductCardRecyclerAdapter;
    ArrayList<Artist> artistArrayList;

    ArrayList<Product> productArrayList;
    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.group2.pop4u_app.databinding.ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        loadRecommendProduct();
        setToolbar();
    }

    private void setToolbar() {
        Toolbar topMainToolBar = binding.tlbTopMainToolbar;
        setSupportActionBar(topMainToolBar);
    }

    private void loadRecommendProduct() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.rccRecommendedProduct.setLayoutManager(gridLayoutManager);
        binding.rccRecommendedProduct.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rccNewReleasedProduct.setLayoutManager(layoutManager);
        binding.rccNewReleasedProduct.setHasFixedSize(true);

        productArrayList = new ArrayList<>();
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000.0, 0.0, 20, 5.5, 50, 30, 30, "ABC"));
        bigProductCardRecyclerAdapter = new BigProductCardRecyclerAdapter(this, productArrayList);
        binding.rccRecommendedProduct.setAdapter(bigProductCardRecyclerAdapter);

        miniProductCardRecyclerAdapter = new MiniProductCardRecyclerAdapter(this, productArrayList);
        binding.rccNewReleasedProduct.setAdapter(miniProductCardRecyclerAdapter);
    }

    private void addEvents() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rccHotArtist.setLayoutManager(layoutManager);
        binding.rccHotArtist.setHasFixedSize(true);


        artistArrayList = new ArrayList<>();
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        artistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));

        artistHorizontalListAdapter = new ArtistHorizontalListAdapter(this, artistArrayList);


        binding.rccHotArtist.setAdapter(artistHorizontalListAdapter);
        binding.rccHotArtist.setNestedScrollingEnabled(false);
    }
}