package com.group2.api.Services;

import com.group2.api.DAO.ArtistDAO;
import com.group2.api.DAO.ArtistResponseDAO;
import com.group2.model.Artist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class AuthorService {
    public static final AuthorService instance = new AuthorService();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CompletableFuture<ArrayList<Artist>> getListArtist(Integer page, Integer limit) {
        CompletableFuture<ArrayList<Artist>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IArtistService service = ServiceGenerator.createService(IArtistService.class);
            Call<ArtistResponseDAO> call = service.getArtistList(page, limit);

            try {
                Response<ArtistResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ArtistResponseDAO artistResponse = response.body();
                    if (artistResponse == null) {
                        future.completeExceptionally(new NullPointerException("Artist not found")); // Complete the future exceptionally if artist is null
                        return;
                    }
                    ArrayList<ArtistDAO> artistDAOList = artistResponse.getArtistList();
                    ArrayList<Artist> artistList = new ArrayList<>();
                    for (ArtistDAO artistDAO : artistDAOList) {
                        artistList.add(artistDAO.asArtist());
                    }
                    future.complete(artistList); // Complete the future with the ArtistResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Artist Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }


    public CompletableFuture<ArrayList<Artist>> searchArtist(String query) {
        CompletableFuture<ArrayList<Artist>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IArtistService service = ServiceGenerator.createService(IArtistService.class);
            Call<ArrayList<ArtistDAO>> call = service.searchArtist(query);

            try {
                Response<ArrayList<ArtistDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<ArtistDAO> artistResponse = response.body();
                    if (artistResponse == null) {
                        future.completeExceptionally(new NullPointerException("Artist not found")); // Complete the future exceptionally if artist is null
                        return;
                    }
                    ArrayList<Artist> artistList = new ArrayList<>();
                    for (ArtistDAO artistDAO : artistResponse) {
                        artistList.add(artistDAO.asArtist());
                    }
                    future.complete(artistList); // Complete the future with the ArtistResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Artist Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }

    public CompletableFuture<Artist> getArtistDetail(String artistCode) {
        CompletableFuture<Artist> future = new CompletableFuture<>();
        executor.execute(() -> {
            IArtistService service = ServiceGenerator.createService(IArtistService.class);
            Call<ArtistDAO> call = service.getArtistDetail(artistCode);

            try {
                Response<ArtistDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ArtistDAO artistDAO = response.body();
                    if (artistDAO != null) {
                        future.complete(artistDAO.asArtist()); // Complete the future with the ArtistDAO object
                    } else {
                        future.completeExceptionally(new NullPointerException("Artist not found")); // Complete the future exceptionally if artist is null
                    }
                } else {
                    future.completeExceptionally(new Exception("Artist Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }
}
