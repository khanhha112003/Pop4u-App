package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.Artist;

/*
{
      "artist_code": "BT01",
      "artist_name": "BTS",
      "artist_description": "BTS, còn được gọi là Bangtan Boys, là một nhóm nhạc nam Hàn Quốc được thành lập bởi Big Hit Music vào năm 2010 và ra mắt vào năm 2013. Nhóm bao gồm bảy thành viên: RM, Jin, Suga, J-Hope, Jimin, V và Jungkook. BTS được biết đến với âm nhạc của họ, bao gồm nhạc pop, hip hop, R&B và EDM. Họ cũng được biết đến với vũ đạo của họ, lời bài hát của họ và sự tham gia của họ vào các vấn đề xã hội. BTS đã trở thành một trong những nhóm nhạc thành công nhất thế giới. Họ đã bán được hơn 320 triệu album trên toàn thế giới và đã giành được nhiều giải thưởng, bao gồm một giải Grammy và hai giải American Music Awards.",
      "is_hot": true,
      "artist_logo": "https://cdn-contents.weverseshop.io/public/shop/88b0601c7d5a06f90bfec4b0dfe92dbf.jpg?q=95&w=512",
      "artist_avatar": "https://media-cdn-v2.laodong.vn/storage/newsportal/2022/11/24/1120309/Bts.jpg"
    }
 */
public class ArtistDAO {
    @JsonProperty("artist_name")
    private String artistName;

    // generate all properties
    @JsonProperty("artist_code")
    private String artistCode;

    @JsonProperty("artist_description")
    private String artistDescription;

    @JsonProperty("is_hot")
    private boolean isHot;

    @JsonProperty("artist_logo")
    private String artistLogo;

    @JsonProperty("artist_avatar")
    private String artistAvatar;

    public Artist asArtist() {
        return new Artist(artistCode, artistAvatar, artistName, artistDescription, isHot, artistLogo, 2020);
    }
}
