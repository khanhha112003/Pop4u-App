package com.group2.pop4u_app.HomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.adapter.BannerAdapter;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.SaleProductCardRecyclerAdapter;
import com.group2.api.Services.ArtistService;
import com.group2.api.Services.ProductService;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentHomepageBinding;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class HomepageFragment extends Fragment {

    FragmentHomepageBinding binding;
    MiniProductCardRecyclerAdapter newProductAdapter;
    BigProductCardRecyclerAdapter rcmProductAdapter;
    SaleProductCardRecyclerAdapter saleProductAdapter;
    ArrayList<Product> saleProductArrayList, newReleasedProductArrayList, rcmProductArrayList;
    ArrayList<Artist> featuredArtistArrayList;
    ArtistHorizontalListAdapter featuredArtistAdapter;
    ViewPager mSliceViewpager;
    BannerAdapter bannerAdapter;
    LinearLayout mDotLayout;
    int currentPage = 0;
    TextView[] dots;

    private Timer timer;

    public HomepageFragment() {
        // Required empty public constructor
    }
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        loadProductOfWeek();
        setupBanner();
        loadData();
    }

    private void loadData() {
        CompletableFuture<ArrayList<Product>> newProductFuture = ProductService.instance.getListProduct(1, "new", "asc", 10, 0, "");
        CompletableFuture<ArrayList<Product>> saleProductFuture = ProductService.instance.getListProduct(1, "sale", "desc", 10, 0, "");
        CompletableFuture<ArrayList<Artist>> featuredArtistFuture = ArtistService.instance.getListArtist(1, 4, "hot");
        newProductFuture.thenAccept(products -> {
            newReleasedProductArrayList.clear();
            newReleasedProductArrayList.addAll(products);
            newProductAdapter.notifyDataSetChanged();
        });

        saleProductFuture.thenAccept(products -> {
            saleProductArrayList.clear();
            saleProductArrayList.addAll(products);
            saleProductAdapter.notifyDataSetChanged();
        });

        featuredArtistFuture.thenAccept(artists -> {
            featuredArtistArrayList.clear();
            featuredArtistArrayList.addAll(artists);
            featuredArtistAdapter.notifyDataSetChanged();
        });

        try {
            newProductFuture.get();
            saleProductFuture.get();
            featuredArtistFuture.get();
        } catch (Exception e) {
            Log.d("HomepageFragment", "loadData: " + e.getMessage());
        }
    }

    private void loadProductOfWeek() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgProductOfWeek, new ProductOfWeekFragment());
        fragmentTransaction.commit();
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
            dots[i] = new TextView(getContext());
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
        intent.putExtra("productCode", product.getProductCode());
        intent.putExtra("artistCode", product.getArtistCode());
        startActivity(intent);
    }

    private void openArtist(Artist artist) {
        Intent intent = new Intent(requireActivity(), ArtistInfoScreen.class);
        intent.putExtra("artistCode", artist.getArtistCode());
        startActivity(intent);
    }

    private void addViewAllButtonEvent() {
        binding.btnViewNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductListCategory.class);
                intent.putExtra("recyclerID", "newProduct");
                intent.putExtra("recyclerName", "Mới ra mắt");
                startActivity(intent);
            }
        });

        binding.btnViewAllHotArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AllArtist.class);
                intent.putExtra("recyclerID", "hotArtist");
                intent.putExtra("recyclerName", "Nghệ sĩ nổi bật");
                startActivity(intent);
            }
        });

        binding.btnViewAllSaleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ProductListCategory.class);
                intent.putExtra("recyclerID", "saleProduct");
                intent.putExtra("recyclerName", "Sale");
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
        featuredArtistAdapter = new ArtistHorizontalListAdapter(requireActivity(), featuredArtistArrayList);
        binding.rccHotArtist.setAdapter(featuredArtistAdapter);

    }
}