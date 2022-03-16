package com.gotgam.bansi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "piece_collections")
public class PieceCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "collection_id", nullable = false)
    private Long collectionId;
    @ManyToOne
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "cover_image")
    private String coverImage;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @OneToMany
    List<Item> items;
    @ManyToOne
    private PlaceKeyword place;
}
