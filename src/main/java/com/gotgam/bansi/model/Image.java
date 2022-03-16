package com.gotgam.bansi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "LONGTEXT")
    private String encoded;
    @Column(nullable = false)
    private Boolean thumbnail = false;
    @ManyToOne
    @JoinColumn(name = "piece_id")
    private Piece piece;

    public Image(String encoded, Boolean thumbnail){
        this.encoded = encoded;
        this.thumbnail = thumbnail;
    }
}
