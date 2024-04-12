package com.group2.pop4u_app.SearchScreen;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentMerchBinding;
import com.group2.pop4u_app.databinding.FragmentPhotobookBinding;

import java.util.ArrayList;

public class PhotobookFragment extends Fragment {
    FragmentPhotobookBinding binding;
    ProductGridviewAdapter adapter;

    public PhotobookFragment() {
    }


    public static PhotobookFragment newInstance(String param1, String param2) {
        PhotobookFragment fragment = new PhotobookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private void setGridViewAdapter(Context context) {
        // Required empty public constructor
        ArrayList<Product> productArrayList = new ArrayList<Product>();
//        productArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));

        adapter = new ProductGridviewAdapter(context, productArrayList);
        binding.gvPhotobook.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotobookBinding.inflate(inflater,container,false);
        setGridViewAdapter(getContext());
        return binding.getRoot();
    }
}