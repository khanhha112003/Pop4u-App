package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.ProductImgAdapter;
import com.group2.api.Services.ProductService;
import com.google.android.material.snackbar.Snackbar;
import com.group2.model.Product;
import com.group2.pop4u_app.Activity.MainActivity;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductDetailScreenBinding;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ProductDetailScreen extends AppCompatActivity {

    ActivityProductDetailScreenBinding binding;
    private ProductImgAdapter productImgAdapter;
    MiniProductCardRecyclerAdapter artistProductAdapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    Product product;
    Dialog optionDialog;
    int currentAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolbar();
        setArtistCardClick();
        setUpProductImage();
        bindingBackButton();
        addEvents();
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.tbrProductDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        final int[] previousScrollY = {0};

        binding.nsvProductDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > previousScrollY[0]) {
                binding.tbrProductDetail.setVisibility(View.VISIBLE);
                binding.imvProductDetailBack.setVisibility(View.GONE);
            } else if (scrollY == 0) {
                binding.tbrProductDetail.setVisibility(View.GONE);
                binding.imvProductDetailBack.setVisibility(View.VISIBLE);
            }
            previousScrollY[0] = scrollY;
        });
    }

    private void bindingBackButton() {
        binding.imvProductDetailBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void addEvents() {
        binding.crdArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailScreen.this, ArtistInfoScreen.class);
                intent.putExtra("artistName", product.getProductArtistName());
                startActivity(intent);
            }
        });
        binding.btnAddToFavProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean favoriteState = binding.btnAddToFavProduct.isSelected();
                binding.btnAddToFavProduct.setSelected(!favoriteState);
                if (favoriteState) {
                    Snackbar.make(binding.ctnSnackBar, R.string.delete_favorite_noti, Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else {
                    Snackbar.make(binding.ctnSnackBar, R.string.add_favorite_noti, Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
            }
        });

        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionDialog();
                Button btnAction = optionDialog.findViewById(R.id.btnAction);
                btnAction.setText(R.string.buy_now);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionDialog();
                Button btnAction = optionDialog.findViewById(R.id.btnAction);
                btnAction.setText(R.string.add_to_cart);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(binding.ctnSnackBar, "Bạn đã thêm " + currentAmount + " sản phẩm vào giỏ hàng.", Snackbar.LENGTH_LONG).setAction(R.string.view_cart, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ProductDetailScreen.this, MainActivity.class);

                            }
                        }).show();
                        optionDialog.dismiss();
                    }
                });
            }
        });
    }

    private void openOptionDialog() {
        optionDialog = new Dialog(ProductDetailScreen.this);
        optionDialog.setContentView(R.layout.product_option_dialog);
        TextView txtAmount = optionDialog.findViewById(R.id.txtAmount);
        currentAmount = Integer.parseInt(txtAmount.getText().toString());
        optionDialog.findViewById(R.id.btnUpAmount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAmount = Integer.parseInt(txtAmount.getText().toString());
                currentAmount = currentAmount + 1;
                txtAmount.setText(String.valueOf(currentAmount));
            }
        });

        optionDialog.findViewById(R.id.btnDownAmount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAmount = Integer.parseInt(txtAmount.getText().toString());
                if (currentAmount > 1) {
                    currentAmount = currentAmount - 1;
                    txtAmount.setText(String.valueOf(currentAmount));
                }
            }
        });

        optionDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionDialog.dismiss();
            }
        });

        optionDialog.getWindow().setGravity(Gravity.BOTTOM);
        optionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.show();
    }

    private void setUpProductImage() {
        ViewPager viewPagerProductImages = findViewById(R.id.imvProductImage);
        productImgAdapter = new ProductImgAdapter(this);
        viewPagerProductImages.setAdapter(productImgAdapter);
        updateIndicator(0, productImgAdapter.getCount());

        artistProductAdapter = new MiniProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductRelevant.setAdapter(artistProductAdapter);
        LinearLayoutManager layoutManagerNewProduct = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ItemOffsetHorizontalRecycler itemOffsetHorizontalRecycler = new ItemOffsetHorizontalRecycler(this, R.dimen.item_offset);
        binding.rccProductRelevant.addItemDecoration(itemOffsetHorizontalRecycler);
        binding.rccProductRelevant.setLayoutManager(layoutManagerNewProduct);

        viewPagerProductImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed
            }

            @Override
            public void onPageSelected(int position) {
                // Update indicator text
                updateIndicator(position, productImgAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed
            }
        });

        artistProductAdapter.setOnClickListener(new MiniProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                openProduct(product);
            }
        });
    }

    private void updateIndicator(int position, int total) {
        String indicatorText = (position + 1) + "/" + total;
        binding.txtIndicator.setText(indicatorText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void setArtistCardClick() {
        binding.crdArtistOfProduct.setOnClickListener(v -> {
            String artistCode = getIntent().getStringExtra("artistCode");
            Intent intent = new Intent(this, ArtistInfoScreen.class);
            intent.putExtra("artistCode", artistCode);
            startActivity(intent);
        });
    }

    private void loadData() {
        // Load data from server
        String productCode = getIntent().getStringExtra("productCode");
        String artistCode = getIntent().getStringExtra("artistCode");
        CompletableFuture<Product> future = ProductService.instance.getProduct(productCode);
        future.thenAccept(product -> {
            // Update UI with product data
            if (product.getProductComparingPrice() != 0) {
                binding.txtProductDetailPrice.setText(String.format("%s đ", product.getProductComparingPrice()));
                binding.txtProductDetailComparingPrice.setText(String.format("%s đ", product.getProductPrice()));
            } else {
                binding.txtProductDetailPrice.setText(String.format("%s đ", product.getProductPrice()));
                binding.txtProductDetailComparingPrice.setVisibility(View.GONE);
            }
            binding.txtProductDetailName.setText(product.getProductName());
            binding.txtProductDetailArtist.setText(product.getProductArtistName());
            binding.txtProductDetailDescription.setText(product.getProductDescription());
            binding.txtProductDetailRate.setText(String.format("%s", product.getProductRating()));
            binding.txtProductDetailSoldAmount.setText(String.format("%s", product.getProductSoldAmount()));
            productImgAdapter.setImagesUrl(product.getListProductPhoto());
            productImgAdapter.notifyDataSetChanged();
        });

        CompletableFuture<ArrayList<Product>> futureRelated = ProductService.instance.getListProduct(null, "related", null, null, 0, artistCode);
        futureRelated.thenAccept(productsResponse -> {
            productArrayList.clear();
            productArrayList.addAll(productsResponse);
            artistProductAdapter.notifyDataSetChanged();
        });

        try {
            future.get();
        } catch (Exception e) {
            Log.d("ProductListCategory", e.getMessage());
        }

        try {
            future.get();
            futureRelated.get();
        } catch (Exception e) {
            Log.d("ProductDetailScreen", "Error loading product data", e);
        }
    }

    private void openProduct(Product product) {
        Intent intent = new Intent(this , ProductDetailScreen.class);
        intent.putExtra("productCode", product.getProductCode());
        intent.putExtra("artistCode", product.getArtistCode());
        startActivity(intent);
    }
}