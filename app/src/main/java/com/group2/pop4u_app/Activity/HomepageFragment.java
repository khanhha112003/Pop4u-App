package com.group2.pop4u_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BannerAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.SaleProductCardRecyclerAdapter;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentHomepageBinding;
import com.group2.pop4u_app.home.AllArtist;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.home.ProductListCategory;

import java.util.Timer;
import java.util.TimerTask;
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
    ViewPager mSliceViewpager;
    BannerAdapter bannerAdapter;
    LinearLayout mDotLayout;
    int currentPage = 0;
    TextView[] dots;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Timer timer;

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
        setupBanner();
    }
    private static final long DELAY_MS = 0;
    private static final long PERIOD_MS = 5000;

    private void setupBanner() {
        mSliceViewpager = binding.imsHomeBanner; // Initialize mSliceViewpager using binding
        mDotLayout = binding.BannerIndicators;
        bannerAdapter = new BannerAdapter(requireContext());
        mSliceViewpager.setAdapter(bannerAdapter);
        mSliceViewpager.addOnPageChangeListener(viewListener);
        setUpIndicator(0);
        startAutoSwipe();
    }
    private void startAutoSwipe() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == bannerAdapter.getCount() - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                mSliceViewpager.setCurrentItem(currentPage, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
    public void setUpIndicator(int position) {
        dots = new TextView[bannerAdapter.getCount()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < bannerAdapter.getCount(); i++) {
            dots[i] = new TextView(requireContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.md_theme_inversePrimary_mediumContrast, requireContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.md_theme_onTertiaryFixedVariant, requireContext().getTheme()));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        public void onPageSelected(int position) {
            setUpIndicator(position);
            mDotLayout.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void addItemClickEvents() {
        saleProductAdapter.setOnClickListener(new SaleProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                openProduct(product);
            }
        });

        rcmProductAdapter.setOnClickListener(new BigProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                openProduct(product);
            }
        });

        featuredArtistAdapter.setOnClickListener(new ArtistHorizontalListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Artist artist) {
                openArtist(artist);
            }
        });

        newProductAdapter.setOnClickListener(new MiniProductCardRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                openProduct(product);
            }
        });
    }

    private void openProduct(Product product) {
        Intent intent = new Intent(requireActivity(), ProductDetailScreen.class);
        intent.putExtra("productID", product.getProductID());
        startActivity(intent);
    }

    private void openArtist(Artist artist) {
        Intent intent = new Intent(requireActivity(), ArtistInfoScreen.class);
        intent.putExtra("artistID", artist.getArtistID());
        startActivity(intent);
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