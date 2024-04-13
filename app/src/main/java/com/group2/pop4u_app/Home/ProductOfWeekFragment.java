package com.group2.pop4u_app.Home;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.model.Product;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentProductOfWeekBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductOfWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductOfWeekFragment extends Fragment {

    FragmentProductOfWeekBinding binding;

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
        // Inflate the layout for this fragment
        binding = FragmentProductOfWeekBinding.inflate(inflater, container, false);
        addEvents();
        setProductOfWeek();
        return binding.getRoot();
    }

    private void setProductOfWeek() {
        ArrayList<String>  stringArrayList = new ArrayList<>();
        stringArrayList.add("abd");
        Product product = new Product("gegbrh", "Cowboy Carter Album", stringArrayList, "Beyonce", "BAN CHAY", 680000, 690000, 10, 4.5, 56, 12, 34, "Phần tiếp theo của Renaissance là một album nhạc đồng quê mạnh mẽ và đầy tham vọng được xây dựng theo khuôn mẫu độc nhất của Beyoncé. Cô khẳng định vị trí xứng đáng của mình trong thể loại này mà chỉ một ngôi sao nhạc pop với tài năng và tầm ảnh hưởng đáng kinh ngạc của cô mới có thể làm được.");

//        binding.imvBackGroundCard.setImageResource(product.getProductImage1());
//        binding.imvProductImage.setImageResource(product.getProductImage1());
        binding.txtProductName.setText(product.getProductName());
        binding.txtProductArtist.setText(product.getProductArtistName());
        binding.txtProductPrice.setText(String.valueOf(product.getProductPrice()));
    }

    private void addEvents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.imvBackGroundCard.setRenderEffect(RenderEffect.createBlurEffect(150.0f, 150.0f, Shader.TileMode.CLAMP));
        }

        binding.crdProductOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductDetailScreen.class);
                intent.putExtra("productID", "ABCD");
                startActivity(intent);
            }
        });
    }
}