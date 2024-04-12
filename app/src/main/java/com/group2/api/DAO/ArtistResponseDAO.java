package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

public class ArtistResponseDAO {
    @JsonProperty("total")
    private int total;

    @JsonProperty("list_artist")
    private Collection<ArtistDAO> artistList;

    public ArrayList<ArtistDAO> getArtistList() {
        return new ArrayList<>(artistList);
    }
}
