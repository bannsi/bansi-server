package com.gotgam.bansi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    @OneToMany
    private List<ArchivePiece> pieces; 
    @OneToMany
    private List<ArchiveCollection> collections;

    public ArchiveFolder(User user, String name){
        this.user = user;
        this.name = name;
        this.pieces = new ArrayList<>();
        this.collections = new ArrayList<>();
    }
}
