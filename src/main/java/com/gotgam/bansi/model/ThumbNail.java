package com.gotgam.bansi.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThumbNail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @OneToOne
    private Piece piece;
    @ManyToOne
    private User user;
    @Column(columnDefinition = "LONGTEXT")
    private String encoded;

    @ManyToMany
    private Set<Keyword> keywords;
    @ManyToMany
    private Set<OptionalKeyword> opKeywords;
    @ManyToMany
    private Set<WhoKeyword> whoKeywords;
    @ManyToOne(optional = true)
    private PlaceKeyword placeKeyword;

    public ThumbNail(Piece piece, User user, String encoded){
        this.piece = piece;
        this.user = user;
        this.encoded = encoded;
    }
    
    public ThumbNail(Piece piece, User user, String encoded, Set<Keyword> keywords, Set<OptionalKeyword> opKeywords, Set<WhoKeyword> whoKeywords, PlaceKeyword placeKeyword){
        this.piece = piece;
        this.user = user;
        this.encoded = encoded;
        this.keywords = keywords;
        this.opKeywords = opKeywords;
        this.whoKeywords = whoKeywords;
        this.placeKeyword = placeKeyword;
    }
}
