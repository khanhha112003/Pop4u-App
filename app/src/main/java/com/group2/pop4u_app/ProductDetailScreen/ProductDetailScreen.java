package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.group2.model.Product;
import com.group2.pop4u_app.Activity.MainActivity;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.adapter.ProductImgAdapter;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductDetailScreenBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductDetailScreen extends AppCompatActivity {

    ActivityProductDetailScreenBinding binding;
    private ViewPager viewPagerProductImages;
    private ProductImgAdapter adapter;

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
        loadProduct();
        setUpProductImage();
        addEvents();
    }

    private void loadProduct() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("ABC");
        Product product = new Product("VFN", "Cowboy Carter Album", stringArrayList, "Beyonce", "BAN CHAY", 680000, 690000, 10, 4.5, 56, 12, 34, "Phần tiếp theo của Renaissance là một album nhạc đồng quê mạnh mẽ và đầy tham vọng được xây dựng theo khuôn mẫu độc nhất của Beyoncé. Cô khẳng định vị trí xứng đáng của mình trong thể loại này mà chỉ một ngôi sao nhạc pop với tài năng và tầm ảnh hưởng đáng kinh ngạc của cô mới có thể làm được.");

        binding.txtProductName.setText(product.getProductName());
        binding.txtProductArtist.setText(product.getProductArtistName());
        binding.txtProductPrice.setText(String.valueOf(product.getProductPrice()));
        binding.txtComparingPrice.setText(String.valueOf(product.getProductComparingPrice()));
        binding.txtProductDescription.append(product.getProductDescription());

        binding.txtArtistYearDebut.append("");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("u, dd-MM-yyyy", Locale.getDefault());
        String expectedDate = dateFormat.format(calendar.getTime());
        binding.txtExpectedDate.append(" " + expectedDate );
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

        final int[] previousScrollY = {0};

        binding.nsvProductDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > previousScrollY[0]) {
                    Drawable actionBarBackground = getResources().getDrawable(R.color.md_theme_surfaceContainerLow);
                    getSupportActionBar().setBackgroundDrawable(actionBarBackground);
                } else if (scrollY == 0) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                previousScrollY[0] = scrollY;
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
                viewPagerProductImages = findViewById(R.id.imvProductImage);
        adapter = new ProductImgAdapter(this, getProductImages());
        viewPagerProductImages.setAdapter(adapter);

        updateIndicator(0, adapter.getCount());

        viewPagerProductImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Update indicator text
                updateIndicator(position, adapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
        } else if (item.getItemId() == R.id.mnFilterProduct) {
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Integer> getProductImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img);
        images.add(R.drawable.img_1);
        images.add(R.drawable.img_2);
        return images;
    }

    private void updateIndicator(int position, int total) {
        String indicatorText = (position + 1) + "/" + total;
        binding.txtIndicator.setText(indicatorText);
    }
}