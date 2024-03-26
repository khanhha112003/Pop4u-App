package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding;

public class ArtistInfoScreen extends AppCompatActivity {

    ActivityArtistInfoScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistInfoScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}