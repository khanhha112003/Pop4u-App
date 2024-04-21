package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.ProductImgAdapter;
import com.group2.api.Services.ProductService;
import com.google.android.material.snackbar.Snackbar;
import com.group2.model.Product;
import com.group2.pop4u_app.MainActivity;
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
        setSupportActionBar(binding.tbrProductDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setContentView(binding.getRoot());


        ViewCompat.setOnApplyWindowInsetsListener(binding.ctrProductButton, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            return WindowInsetsCompat.CONSUMED;
        });

        setUpToolbar();
        setArtistCardClick();
        setUpProductImage();
        bindingBackButton();
        addEvents();
    }

    private void setUpToolbar() {
        final int[] previousScrollY = {0};
        binding.nsvProductDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > previousScrollY[0]) {
                int color = getResources().getColor(R.color.md_theme_surfaceContainerLow);
                Drawable drawable = new ColorDrawable(color);
                getSupportActionBar().setBackgroundDrawable(drawable);
//                binding.tbrProductDetail.setVisibility(View.VISIBLE);
//                binding.imvProductDetailBack.setVisibility(View.GONE);
            } else if (scrollY == 0) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                binding.tbrProductDetail.setVisibility(View.GONE);
//                binding.imvProductDetailBack.setVisibility(View.VISIBLE);
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
            Intent intent = new Intent(ProductDetailScreen.this, CartActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.mnShareProduct) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList.add("ABC");
            Product product = new Product("VFN", "Cowboy Carter Album", stringArrayList, "Beyonce", "ABC", "BAN CHAY", 680000, 690000, 10, 4.5, 56, 12, 34, "Phần tiếp theo của Renaissance là một album nhạc đồng quê mạnh mẽ và đầy tham vọng được xây dựng theo khuôn mẫu độc nhất của Beyoncé. Cô khẳng định vị trí xứng đáng của mình trong thể loại này mà chỉ một ngôi sao nhạc pop với tài năng và tầm ảnh hưởng đáng kinh ngạc của cô mới có thể làm được.");
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Xem ngay sản phẩm " + product.getProductName() +
                            " tại Pop4u với mức giá siêu hời chỉ " + String.valueOf(product.getProductPrice()) +
                            "₫, giảm đến " + String.valueOf(product.getProductSalePercent()) +
                            "%\n\nFreeship cho đơn hàng từ 500K, giao ngay chỉ trong 2 ngày.\n\nCùng nhiều quà tặng hấp dẫn đang chờ đón bạn."
            );
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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