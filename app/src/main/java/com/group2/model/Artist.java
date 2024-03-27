package com.group2.model;

public class Artist {
    int artistID;
    int artistAvatar;
    String artistName;
    String artistDescription;
    int artistYearDebut;

    public Artist(int artistID, int artistAvatar, String artistName, String artistDescription, int artistYearDebut) {
        this.artistID = artistID;
        this.artistAvatar = artistAvatar;
        this.artistName = artistName;
        this.artistDescription = artistDescription;
        this.artistYearDebut = artistYearDebut;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getArtistAvatar() {
        return artistAvatar;
    }

    public void setArtistAvatar(int artistAvatar) {
        this.artistAvatar = artistAvatar;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistDescription() {
        return artistDescription;
    }

    public void setArtistDescription(String artistDescription) {
        this.artistDescription = artistDescription;
    }

    public int getArtistYearDebut() {
        return artistYearDebut;
    }

    public void setArtistYearDebut(int artistYearDebut) {
        this.artistYearDebut = artistYearDebut;
    }
}

