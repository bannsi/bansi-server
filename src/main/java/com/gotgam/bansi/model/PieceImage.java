package com.gotgam.bansi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="piece_images")
@Table(name = "piece_images")
public class PieceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="image_id", nullable = false)
    private Long imageId;
    @ManyToOne
    private Piece piece;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public PieceImage(Piece piece, String imageUrl){
        this.piece = piece;
        this.imageUrl = imageUrl;
    }

    public PieceImage withPiece(Piece piece){
        this.setPiece(piece);
        return this;
    }

    public PieceImage withImageUrl(String imageUrl){
        this.setImageUrl(imageUrl);
        return this;
    }
}
