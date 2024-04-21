package com.group2.pop4u_app.SearchScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.api.Services.ArtistService;
import com.group2.model.Artist;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SearchDashboardFragment extends Fragment implements SearchView.OnQueryTextListener  {

    FragmentSearchDashboardBinding binding;
    ArrayList<Artist> featuredArtistArrayList;
    ArtistHorizontalListAdapter featuredArtistAdapter;
    Boolean isNotTypingSearch;
    public SearchDashboardFragment() {}

    public static SearchDashboardFragment newInstance() {
        SearchDashboardFragment fragment = new SearchDashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchDashboardBinding.inflate(inflater,container,false);
        setSearchBar();
        initData();
        loadingData();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        setTabHandler();
        super.onStart();
    }

    private void setTabHandler() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) {
            return;
        }
        getView().findViewById(binding.searchDashboardCategoryCardAll.getId())
                .setOnClickListener(view ->mainActivity.replaceFragment(new CategoryProductFragment("all")));

        getView().findViewById(binding.searchDashboardCategoryCardAlbum.getId())
                .setOnClickListener(view -> mainActivity.replaceFragment(new CategoryProductFragment("album")));

        getView().findViewById(binding.searchDashboardCategoryCardMerch.getId())
                .setOnClickListener(view -> mainActivity.replaceFragment(new CategoryProductFragment("merch")));

        getView().findViewById(binding.searchDashboardCategoryCardPhotobook.getId())
                .setOnClickListener(view -> mainActivity.replaceFragment(new CategoryProductFragment("photo")));

        getView().findViewById(binding.searchDashboardCategoryCardVinyl.getId())
                .setOnClickListener(view -> mainActivity.replaceFragment(new CategoryProductFragment("vinyl")));

        getView().findViewById(binding.searchDashboardCategoryCardLightstick.getId())
                .setOnClickListener(view -> mainActivity.replaceFragment(new CategoryProductFragment("lightstick")));
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.svQuerySearchBox.setQuery("", false);
        binding.svQuerySearchBox.clearFocus();
        isNotTypingSearch = true;
    }


   private void initData() {
       LinearLayoutManager layoutManagerFeaturedArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       ItemOffsetHorizontalRecycler itemOffsetHorizontalRecycler = new ItemOffsetHorizontalRecycler(getContext(), R.dimen.item_offset);
       binding.rccHotArtist.setLayoutManager(layoutManagerFeaturedArtist);
       binding.rccHotArtist.addItemDecoration(itemOffsetHorizontalRecycler);
       binding.rccHotArtist.setHasFixedSize(true);

       // Tạo danh sách các nghệ sĩ đặc sắc
       featuredArtistArrayList = new ArrayList<>();

       // Khởi tạo và thiết lập adapter
       featuredArtistAdapter = new ArtistHorizontalListAdapter(requireActivity(), featuredArtistArrayList);
       binding.rccHotArtist.setAdapter(featuredArtistAdapter);
   }

    private void loadingData() {
        CompletableFuture<ArrayList<Artist>> future = ArtistService.instance.getListArtist(1, 4, "hot");
        future.thenAccept(artistList -> {
            featuredArtistArrayList.addAll(artistList);
            featuredArtistAdapter.notifyDataSetChanged();
        });
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SearchDashboardFragment", "loadingData: " + e.getMessage());
        }
    }

    private void setSearchBar() {
        binding.svQuerySearchBox.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        if (!Objects.equals(s, "") && isNotTypingSearch) {
            isNotTypingSearch = false;
            Intent myIntent = new Intent(getActivity(), QuerySearchActivity.class);
            myIntent.putExtra("start_character", s);
            startActivity(myIntent);
        } else {
            return false;
        }
        return true;
    }
}