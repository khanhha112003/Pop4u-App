package com.group2.pop4u_app.ArtistInfoScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityArtistInfoScreenBinding;

public class ArtistInfoScreen extends AppCompatActivity {

    ActivityArtistInfoScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityArtistInfoScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        String artistDes = intent.getStringExtra("artistID");
        binding.txtArtistDescription.setText(artistDes);
    }
}