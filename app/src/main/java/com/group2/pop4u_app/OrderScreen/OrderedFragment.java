package com.group2.pop4u_app.OrderScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentOrderedBinding;


public class OrderedFragment extends Fragment {

    FragmentOrderedBinding binding;
    public OrderedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordered, container, false);
    }
}