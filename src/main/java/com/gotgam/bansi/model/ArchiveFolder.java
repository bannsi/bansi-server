package com.gotgam.bansi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArchiveFolder {
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Piece> pieces; 
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PieceCollection> collections;

    public ArchiveFolder(User user, String name){
        this.user = user;
        this.name = name;
        this.pieces = new HashSet<>();
        this.collections = new HashSet<>();
    }
}
