package com.group2.pop4u_app.SearchScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.group2.adapter.ArtistHorizontalListAdapter;
import com.group2.model.Artist;
import com.group2.pop4u_app.Activity.MainActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetHorizontalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentSearchDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchDashboardFragment extends Fragment implements SearchView.OnQueryTextListener  {

    FragmentSearchDashboardBinding binding;
    ArrayList<Artist> featuredArtistArrayList;
    ArtistHorizontalListAdapter featuredArtistAdapter;
    Boolean isNotTypingSearch;

    ArrayList<Artist> artistHorizontalList;
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
        setToolbar();
       //setListArtist();
        setSearchBar();
        initData();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        setTabHandler();
        super.onStart();
    }

    private void setTabHandler() {
        getView().findViewById(binding.searchDashboardCategoryCardAll.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new AllProductFragment()));

        getView().findViewById(binding.searchDashboardCategoryCardAlbum.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new AlbumFragment()));

        getView().findViewById(binding.searchDashboardCategoryCardMerch.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new MerchFragment()));

        getView().findViewById(binding.searchDashboardCategoryCardPhotobook.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new PhotobookFragment()));

        getView().findViewById(binding.searchDashboardCategoryCardVinyl.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new VinylFragment()));

        getView().findViewById(binding.searchDashboardCategoryCardLightstick.getId())
                .setOnClickListener(view -> ((MainActivity) getContext()).replaceFragment(new LightstickFragment()));
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.svQuerySearchBox.setQuery("", false);
        binding.svQuerySearchBox.clearFocus();
        isNotTypingSearch = true;
    }

    private void setToolbar() {
        binding.tbToolbarTitle.setTitleTextAppearance(getActivity(), R.style.SearchScreenToolbarTitle);
    }

   /** private void setListArtist() {
        List<HashMap<String, String>> testData = new ArrayList<>();
        // Now you can add HashMaps to the list as needed
        HashMap<String, String> data1 = new HashMap<>();
        data1.put("artist_name", "value1");
        data1.put("artist_img_url", "value2");
        testData.add(data1);

        HashMap<String, String> data2 = new HashMap<>();
        data2.put("artist_name", "value3");
        data2.put("artist_img_url", "value4");
        testData.add(data2);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        artistHorizontalListAdapter = new ArtistHorizontalListAdapter(getActivity(), artistHorizontalList);
        binding.rvListArtist.setAdapter(artistHorizontalListAdapter);
        binding.rvListArtist.setLayoutManager(llm);
    }
**/
   private void initData() {
       LinearLayoutManager layoutManagerFeaturedArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       ItemOffsetHorizontalRecycler itemOffsetHorizontalRecycler = new ItemOffsetHorizontalRecycler(getContext(), R.dimen.item_offset);
       binding.rccHotArtist.setLayoutManager(layoutManagerFeaturedArtist);
       binding.rccHotArtist.addItemDecoration(itemOffsetHorizontalRecycler);
       binding.rccHotArtist.setHasFixedSize(true);

       // Tạo danh sách các nghệ sĩ đặc sắc
       featuredArtistArrayList = new ArrayList<>();
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));
       featuredArtistArrayList.add(new Artist(1, R.drawable.img, "BIGBANG", "ABC", 2011));


       // Khởi tạo và thiết lập adapter
       featuredArtistAdapter = new ArtistHorizontalListAdapter(requireActivity(), featuredArtistArrayList);
       binding.rccHotArtist.setAdapter(featuredArtistAdapter);
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