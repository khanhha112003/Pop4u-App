package com.group2.pop4u_app.HomeScreen;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentProductOfWeekBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductOfWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductOfWeekFragment extends Fragment {

    FragmentProductOfWeekBinding binding;
    Product productOfWeek;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductOfWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductOfWeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductOfWeekFragment newInstance(String param1, String param2) {
        ProductOfWeekFragment fragment = new ProductOfWeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductOfWeekBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setProductOfWeek();
        addEvents();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setProductOfWeek() {
        CompletableFuture<Product> future = ProductService.instance.getProduct("PIU1001");
        future.thenAccept(product -> {
            String bannerLink = product.getBannerPhoto();
            Picasso.get()
                    .load(bannerLink)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .fit().centerCrop()
                    .into(binding.imvProductImage);
            Picasso.get()
                    .load(bannerLink)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .fit().centerCrop()
                    .into(binding.imvBackGroundCard);
            DecimalFormat df = new DecimalFormat("#,###");
            binding.txtProductPrice.setText(df.format(product.getProductPrice()) + "₫");
            binding.txtProductName.setText(product.getProductName());
            binding.txtProductArtist.setText(product.getProductArtistName());
            binding.txtSalePercent.setText(product.getProductSalePercent() + "%");
            this.productOfWeek = product;
        }).exceptionally(e -> {
            Log.d("ProductOfWeek", "loadData: " + e.getMessage());
            return null;
        });
    }

    private void addEvents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.imvBackGroundCard.setRenderEffect(RenderEffect.createBlurEffect(150.0f, 150.0f, Shader.TileMode.CLAMP));
        }
        binding.crdProductOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductDetailScreen.class);
                intent.putExtra("productCode", productOfWeek.getProductCode());
                intent.putExtra("artistCode", productOfWeek.getArtistCode());
                startActivity(intent);
            }
        });
    }
}