package com.group2.pop4u_app.home;

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

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentProductOfWeekBinding;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
    }

    private void addEvents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.imvBackGroundCard.setRenderEffect(RenderEffect.createBlurEffect(150.0f, 150.0f, Shader.TileMode.CLAMP));
        }
    }
}