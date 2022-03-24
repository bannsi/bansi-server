package com.gotgam.bansi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="piece_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_kakao_id", "piece_piece_id"}))
public class PieceLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pieceLikeId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Piece piece;

    public PieceLike(User user, Piece piece){
        this.user = user;
        this.piece = piece;
    }
}