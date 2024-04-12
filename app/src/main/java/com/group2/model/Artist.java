package com.group2.model;

public class Artist {
    String artistCode;
    String artistAvatar;
    String artistName;
    String artistDescription;
    Boolean isHot;
    String artistLogo;
    int artistYearDebut;

    public Artist(  String artistCode,
                    String artistAvatar,
                    String artistName,
                    String artistDescription,
                    Boolean isHot,
                    String artistLogo,
                    int artistYearDebut) {
        this.artistCode = artistCode;
        this.artistAvatar = artistAvatar;
        this.artistName = artistName;
        this.artistDescription = artistDescription;
        this.isHot = isHot;
        this.artistLogo = artistLogo;
        this.artistYearDebut = artistYearDebut;
    }

    public String getArtistCode() {
        return artistCode;
    }

    public String getArtistAvatar() {
        return artistAvatar;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistDescription() { return artistDescription; }

    public Boolean getIsHot() {
        return isHot;
    }

    public String getArtistLogo() {
        return artistLogo;
    }

    public int getArtistYearDebut() {
        return artistYearDebut;
    }

}

