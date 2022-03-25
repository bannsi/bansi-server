package com.gotgam.bansi.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThumbNail {
    private Long pieceId;
    private String kakaoUserId;
    private String encoded;
}
