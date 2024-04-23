package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.api.Services.ArtistService;
import com.group2.api.Services.ProductService;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.ProductDetailScreen.CartActivity;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
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
        setSupportActionBar(binding.tbrArtistInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        ViewCompat.setOnApplyWindowInsetsListener(binding.nsvArtistInfo, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            return WindowInsetsCompat.CONSUMED;
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.tbrArtistInfo, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            int paddingTop = systemInsets.top;
            v.setPadding( 0, paddingTop, 0, 0);
            return insets;
        });
        setToolbarBehavior();
        setContentView(binding.getRoot());
        setUpRecycleView();
        getData();
        addEvents();
    }


    private void addEvents() {
        final int[] viewMoreState = {0};
        binding.btnViewMoreDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewMoreState[0] == 0) {
                    binding.txtArtistDescription.setMaxLines(Integer.MAX_VALUE);
                    binding.btnViewMoreDes.setText("Thu gọn");
                    viewMoreState[0] = 1;
                } else {
                    binding.txtArtistDescription.setMaxLines(3);
                    binding.btnViewMoreDes.setText("Xem thêm");
                    viewMoreState[0] = 0;
                }
            }
        });
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
        } else if (item.getItemId() == R.id.mnOpenCart) {
            this.finish();
            Intent intent = new Intent(ArtistInfoScreen.this, CartActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.mnShareProduct) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Khám phá các sản phẩm nghệ sĩ " + binding.txtArtistName.getText().toString() +
                            " tại Pop4u với mức giá siêu hời và ưu đãi vô hạn ngay.");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setToolbarBehavior() {
        final int[] previousScrollY = {0};
        binding.nsvArtistInfo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > previousScrollY[0]) {
                int color = getResources().getColor(R.color.md_theme_surfaceContainerLow);
                Drawable drawable = new ColorDrawable(color);
                getSupportActionBar().setBackgroundDrawable(drawable);
            } else if (scrollY == 0) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            previousScrollY[0] = scrollY;
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