package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.ProductImgAdapter;
import com.group2.api.Services.ArtistService;
import com.group2.api.Services.ProductService;
import com.google.android.material.snackbar.Snackbar;
import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.local.LoginManagerTemp;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.LoginScreen.LoginPage;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.PaymentScreen.Payment;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductDetailScreenBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ProductDetailScreen extends AppCompatActivity {

    ActivityProductDetailScreenBinding binding;
    private ProductImgAdapter productImgAdapter;
    BigProductCardRecyclerAdapter artistProductAdapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    Product product, thisProduct;
    Dialog optionDialog;
    int currentAmount;
    OrderDatabaseHelper databaseHelper;
    BadgeDrawable badge;
    Vibrator vibrator;
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.tbrProductDetail, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            int paddingTop = systemInsets.top;
            v.setPadding( 0, paddingTop, 0, 0);
            return insets;
        });

        setUpToolbar();
        createDB();
        setArtistCardClick();
        setUpProductImage();
        addEvents();
        addCartBadge();

    }

    private void addCartBadge() {
        badge = BadgeDrawable.create(ProductDetailScreen.this);
        badge.setVisible(true);
        badge.setNumber(databaseHelper.numOfRows());
        Toolbar tbrProductDetail = binding.tbrProductDetail;
        BadgeUtils.attachBadgeDrawable(badge, tbrProductDetail, R.id.mnOpenCart);
    }

    private void setUpToolbar() {
        final int[] previousScrollY = {0};
        binding.nsvProductDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
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

    private void addEvents() {
        binding.txtExpectedDate.append(" " + calExpectedDate());
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
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
                if (favoriteState) {
                    Snackbar.make(binding.ctnSnackBar, R.string.delete_favorite_noti, Snackbar.LENGTH_LONG).setAction(R.string.action_bar_favorite_action, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProductDetailScreen.this, FavoriteListActivity.class);
                            startActivity(intent);
                        };
                    }).show();
                } else {
                    Snackbar.make(binding.ctnSnackBar, R.string.add_favorite_noti, Snackbar.LENGTH_LONG).setAction(R.string.action_bar_favorite_action, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProductDetailScreen.this, FavoriteListActivity.class);
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });

        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginManagerTemp.isLogin == false) {
                    Toast.makeText(ProductDetailScreen.this, R.string.request_to_sign_in, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProductDetailScreen.this, LoginPage.class);
                    startActivity(intent);
                } else {
                    openOptionDialog();
                    Button btnAction = optionDialog.findViewById(R.id.btnAction);
                    btnAction.setText(R.string.buy_now);
                    btnAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProductDetailScreen.this, Payment.class);
                            intent.putExtra("productCode", product.getProductCode());
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginManagerTemp.isLogin == false) {
                    Toast.makeText(ProductDetailScreen.this, R.string.request_to_sign_in, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProductDetailScreen.this, LoginPage.class);
                    startActivity(intent);
                } else {
                    openOptionDialog();
                    Button btnAction = optionDialog.findViewById(R.id.btnAction);
                    btnAction.setText(R.string.add_to_cart);
                    btnAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int rowsUpdated = databaseHelper.addMoreQuantity(product, currentAmount);
                            if (rowsUpdated == 0) {
                                databaseHelper.insertData(product, currentAmount);
                            }
                            badge.setNumber(databaseHelper.numOfRows());
                            Snackbar.make(binding.ctnSnackBar, "Bạn đã thêm " + currentAmount + " sản phẩm vào giỏ hàng.", Snackbar.LENGTH_LONG).setAction(R.string.view_cart, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ProductDetailScreen.this, CartActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                            optionDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private String calExpectedDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.DATE, 2);
        Date twoDaysFromNow = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MM-yy", Locale.getDefault());
        String formattedDate = sdf.format(twoDaysFromNow);

        return formattedDate;
    }

    private void createDB() {
        databaseHelper = new OrderDatabaseHelper(ProductDetailScreen.this);
    }

    private void openOptionDialog() {
        optionDialog = new Dialog(ProductDetailScreen.this);
        optionDialog.setContentView(R.layout.product_option_dialog);
        ImageView imvProductImage = optionDialog.findViewById(R.id.imvProductImage);
        Picasso.get()
                .load(product.getBannerPhoto())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .fit().centerCrop()
                .into(imvProductImage);
        TextView txtPrice = optionDialog.findViewById(R.id.txtProductPrice);
        TextView txtComparingPrice = optionDialog.findViewById(R.id.txtComparingPrice);
        DecimalFormat df = new DecimalFormat("#,###");
        txtPrice.setText(df.format(product.getProductPrice()) + "₫");
        txtComparingPrice.setText(df.format(product.getProductComparingPrice()) + "₫");
        TextView txtSalePercent = optionDialog.findViewById(R.id.txtSalePercent);
        txtSalePercent.setText(product.getProductSalePercent() + "%");
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

        artistProductAdapter = new BigProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductRelevant.setAdapter(artistProductAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductDetailScreen.this, 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(ProductDetailScreen.this, R.dimen.item_offset);
        binding.rccProductRelevant.addItemDecoration(itemDecoration);
        binding.rccProductRelevant.setLayoutManager(gridLayoutManager);
        binding.rccProductRelevant.setNestedScrollingEnabled(false);

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

        artistProductAdapter.setOnClickListener(new BigProductCardRecyclerAdapter.OnClickListener() {
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
            if (LoginManagerTemp.isLogin) {
                Intent intent = new Intent(ProductDetailScreen.this, CartActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ProductDetailScreen.this, LoginPage.class);
                startActivity(intent);
            }
            return true;
        } else if (item.getItemId() == R.id.mnShareProduct) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList.add("ABC");
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Xem ngay sản phẩm " + product.getProductName() +
                            " tại Pop4u với mức giá siêu hời chỉ " + String.valueOf(product.getProductPrice()) +
                            "₫, giảm đến " + String.valueOf(product.getProductSalePercent()) +
                            "%.\n\nFreeship cho đơn hàng từ 500K, giao ngay chỉ trong 2 ngày.\n\nCùng nhiều quà tặng hấp dẫn đang chờ đón bạn.\n\n" +
                            product.getBannerPhoto()
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
            this.product = product;
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
            binding.txtProductDetailSoldAmount.append(String.format("%s", product.getProductSoldAmount()));
            productImgAdapter.setImagesUrl(product.getListProductPhoto());
            productImgAdapter.notifyDataSetChanged();
        });

        CompletableFuture<Artist> futureArtist = ArtistService.instance.getArtistDetail(artistCode);
        futureArtist.thenAccept(artist -> {
            Picasso.get()
                    .load(artist.getArtistAvatar())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .fit().centerCrop()
                    .into(binding.imvArtistAvatar);
            binding.txtArtistYearDebut.append(" " + String.valueOf(artist.getArtistYearDebut()));
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