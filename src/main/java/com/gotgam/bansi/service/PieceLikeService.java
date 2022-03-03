package com.gotgam.bansi.service;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

public interface PieceLikeService {
    Long createPieceLike(Piece piece, User user);   
    Long deletePieceLike(Piece piece, User user);
}
