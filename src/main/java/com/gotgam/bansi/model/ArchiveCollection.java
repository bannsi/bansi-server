package com.gotgam.bansi.model;

import javax.persistence.Entity;
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
@Entity
public class ArchiveCollection {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private PieceCollection collection;
    @ManyToOne
    private ArchiveFolder folder;
    
    public ArchiveCollection(PieceCollection collection, ArchiveFolder folder){
        this.collection = collection;
        this.folder = folder;
    }
}
