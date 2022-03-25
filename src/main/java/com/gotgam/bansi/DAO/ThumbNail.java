package com.gotgam.bansi.DAO;

import com.gotgam.bansi.model.Piece;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface ThumbNail {
    Long getPieceId();
    String getUserId();
    String getEncoded();
}
