package com.group2.api.Services;

import com.group2.api.DAO.ArtistDAO;
import com.group2.api.DAO.ArtistResponseDAO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IArtistService {
    @GET("/api/artist/artist_list")
    Call<ArrayList<ArtistDAO>> searchArtist(
            @Query("alphabet") String query
    );

    @GET("/api/artist/artist_list")
    Call<ArtistResponseDAO> getArtistList(
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    @GET("/api/artist/artist_detail")
    Call<ArtistDAO> getArtistDetail(
            @Query("artist_code") String artistCode
    );
}
