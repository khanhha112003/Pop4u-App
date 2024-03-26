package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.SearchScreenActivitySearchDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchDashboardActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchScreenActivitySearchDashboardBinding binding;

    ListArtistAdapter artistsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchScreenActivitySearchDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setListArtist();
        setSearchBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.svQuerySearchBox.setQuery("", false);
        binding.svQuerySearchBox.clearFocus();
    }

    private void setToolbar() {
        binding.tbToolbarTitle.setTitleTextAppearance(this, R.style.SearchScreenToolbarTitle);
    }

    private void setListArtist() {
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

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        artistsAdapter = new ListArtistAdapter(this, testData);
        binding.rvListArtist.setAdapter(artistsAdapter);
        binding.rvListArtist.setLayoutManager(llm);
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
        if (!Objects.equals(s, "")) {
            Intent myIntent = new Intent(getApplicationContext(), QuerySearchActivity.class);
            myIntent.putExtra("start_character", s);
            startActivity(myIntent);
        } else {
            return false;
        }
        return true;
    }
}