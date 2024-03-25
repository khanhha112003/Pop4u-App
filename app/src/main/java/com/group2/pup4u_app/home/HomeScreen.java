package com.group2.pup4u_app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.GridLayout;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityHomeScreenBinding;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    ActivityHomeScreenBinding binding;
    ArtistHorizontalListAdapter artistHorizontalListAdapter;
    ArrayList<Artist> artistArrayList;

    ArrayList<Product> productArrayList;
    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        loadRecommendProduct();
    }

    private void loadRecommendProduct() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.rccRecommendedProduct.setLayoutManager(gridLayoutManager);
        binding.rccRecommendedProduct.setHasFixedSize(true);

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

        artistHorizontalListAdapter = new ArtistHorizontalListAdapter(HomeScreen.this, artistArrayList);


        binding.rccHotArtist.setAdapter(artistHorizontalListAdapter);
    }
}