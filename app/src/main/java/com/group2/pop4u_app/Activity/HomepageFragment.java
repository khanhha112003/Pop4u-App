package com.group2.pop4u_app.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.SaleProductCardRecyclerAdapter;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentHomepageBinding;
import com.group2.pop4u_app.home.AllArtist;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.home.ProductListCategory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageFragment extends Fragment {

    FragmentHomepageBinding binding;
    MiniProductCardRecyclerAdapter newProductAdapter;
    BigProductCardRecyclerAdapter rcmProductAdapter;
    SaleProductCardRecyclerAdapter saleProductAdapter;
    ArrayList<Product> saleProductArrayList, newReleasedProductArrayList, rcmProductArrayList;
    ArtistHorizontalListAdapter artistHorizontalListAdapter;
    ArrayList<Artist> featuredArtistArrayList;
    ArtistHorizontalListAdapter featuredArtistAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomepageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomepageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
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
        binding = FragmentHomepageBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        addViewAllButtonEvent();
        addItemClickEvents();
    }

    private void addItemClickEvents() {
//        artistHorizontalListAdapter.setOnClickListener(new ArtistHorizontalListAdapter.ArtistHorOnClickListener() {
//            @Override
//            public void artistHorOnClick(int position, Artist artist) {
//                Intent intent = new Intent(getActivity().getBaseContext(), ArtistInfoScreen.class);
//                intent.putExtra("artistID", "Sinh nawm 102897423");
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void addViewAllButtonEvent() {
        binding.btnViewNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductListCategory.class);
                intent.putExtra("recyclerID", "newProduct");
                startActivity(intent);
            }
        });

        binding.btnViewAllHotArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AllArtist.class);
                intent.putExtra("recyclerID", "hotArtist");
                startActivity(intent);
            }
        });

        binding.btnViewAllSaleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductListCategory.class);
                intent.putExtra("recyclerID", "saleProduct");
                startActivity(intent);
            }
        });
    }

    private void initData() {
        rcmProductArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rccRecommendedProduct.setLayoutManager(gridLayoutManager);
        binding.rccRecommendedProduct.setHasFixedSize(true);

        rcmProductArrayList = new ArrayList<>();
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 0, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 0, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 0, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));
        rcmProductArrayList.add(new Product(1, "BAI HAT ABCD CUA NGHE SI A", R.drawable.img,"BLACKPINK", "Bán chạy", 350000, 0, 20, 5.5, 50, 30, 30, "ABC"));

        rcmProductAdapter = new BigProductCardRecyclerAdapter(requireActivity(), rcmProductArrayList);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        binding.rccRecommendedProduct.addItemDecoration(itemDecoration);
        binding.rccRecommendedProduct.setAdapter(rcmProductAdapter);
        binding.rccRecommendedProduct.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManagerNewProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ItemOffsetHorizontalRecycler itemOffsetHorizontalRecycler = new ItemOffsetHorizontalRecycler(getContext(), R.dimen.item_offset);
        binding.rccNewReleasedProduct.addItemDecoration(itemOffsetHorizontalRecycler);
        binding.rccNewReleasedProduct.setLayoutManager(layoutManagerNewProduct);
        binding.rccNewReleasedProduct.setHasFixedSize(true);
        newReleasedProductArrayList = rcmProductArrayList;
        newProductAdapter = new MiniProductCardRecyclerAdapter(requireActivity(), newReleasedProductArrayList);
        binding.rccNewReleasedProduct.setAdapter(newProductAdapter);

        LinearLayoutManager layoutManagerSaleProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rccSaleProducts.addItemDecoration(itemOffsetHorizontalRecycler);
        binding.rccSaleProducts.setLayoutManager(layoutManagerSaleProduct);
        binding.rccSaleProducts.setHasFixedSize(true);
        saleProductArrayList = rcmProductArrayList;
        saleProductAdapter = new SaleProductCardRecyclerAdapter(requireActivity(), saleProductArrayList);
        binding.rccSaleProducts.setAdapter(saleProductAdapter);

        LinearLayoutManager layoutManagerFeaturedArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rccHotArtist.setLayoutManager(layoutManagerFeaturedArtist);
        binding.rccHotArtist.addItemDecoration(itemOffsetHorizontalRecycler);
        binding.rccHotArtist.setHasFixedSize(true);
        featuredArtistArrayList = new ArrayList<>();
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
        featuredArtistAdapter = new ArtistHorizontalListAdapter(requireActivity(), featuredArtistArrayList);
        binding.rccHotArtist.setAdapter(featuredArtistAdapter);


    }
}