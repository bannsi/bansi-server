package com.gotgam.bansi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="piece_likes")
public class PieceLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="piece_like_id", nullable = false)
    private Long pieceLikeId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Piece piece;
}