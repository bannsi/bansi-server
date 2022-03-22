package com.gotgam.bansi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PieceThumbnail{
        private Long pieceId;
        private String thumbnail;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse{
        private String image;
        private Boolean thumbnail;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ImageRequest{
        private String image;
        private Boolean thumbnail;
    }
}
