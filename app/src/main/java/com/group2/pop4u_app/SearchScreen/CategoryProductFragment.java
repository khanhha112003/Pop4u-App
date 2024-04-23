package com.group2.pop4u_app.SearchScreen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.databinding.FragmentCategoryProductBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CategoryProductFragment extends Fragment {
    FragmentCategoryProductBinding binding;
    ProductGridviewAdapter adapter;
    private static final String CAT_TYPE = "category_type";
    String category_type = "all"; // "all", "lightstick", "photobook", "vinyl", "merchant"

    String param_type = "all";
    ArrayList<Product> productArrayList = new ArrayList<Product>();

    public CategoryProductFragment(String typeCategory) {
        this.category_type = typeCategory;
    }


    public static CategoryProductFragment newInstance(String category_type) {
        CategoryProductFragment fragment = new CategoryProductFragment(category_type);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category_type = getArguments().getString(CAT_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryProductBinding.inflate(inflater,container,false);
        setGridViewAdapter(getContext());
        setScreenTitle();
        setBackButton();
        loadCategoryViewData();
        return binding.getRoot();
    }

    private void setScreenTitle() {
        if (category_type == "album") {
            param_type = "Album";
            binding.tvProductSearchCategoryName.setText("Album");
        } else if (category_type == "lightstick") {
            param_type = "Lightstick";
            binding.tvProductSearchCategoryName.setText("Lightstick");
        } else if (category_type == "photobook") {
            param_type = "Photobook";
            binding.tvProductSearchCategoryName.setText("Photobook");
        } else if (category_type == "vinyl") {
            param_type = "Vinyl";
            binding.tvProductSearchCategoryName.setText("Vinyl");
        } else if (category_type == "merch") {
            param_type = "Merch";
            binding.tvProductSearchCategoryName.setText("Merch");
        } else {
            param_type = null;
            binding.tvProductSearchCategoryName.setText("Tất cả sản phẩm");
        }
    }

    private void setGridViewAdapter(Context context) {
        // Required empty public constructor
        adapter = new ProductGridviewAdapter(context, productArrayList);
        binding.gvAllProduct.setAdapter(adapter);
    }

    private void loadCategoryViewData() {
        CompletableFuture<ArrayList<Product>> future = ProductService.instance.getProductByCategory(param_type, null, null, null, null);
        future.thenAccept(products -> {
            productArrayList.addAll(products);
            adapter.notifyDataSetChanged();
        });

        try {
            future.get();
        } catch (Exception e) {
            Log.d("CategoryProductFragment", "Error: " + e.getMessage());
        }
    }

    private void setBackButton() {
        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new SearchDashboardFragment());
            }
        });
    }
}