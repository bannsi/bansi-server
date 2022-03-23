package com.gotgam.bansi.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchivePiece {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Piece piece;
    @ManyToOne
    private ArchiveFolder folder;

    public ArchivePiece(Piece piece, ArchiveFolder folder){
        this.piece = piece;
        this.folder = folder;
    }
}
