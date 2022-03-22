package com.gotgam.bansi.model;

import java.util.Date;

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
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "content", nullable = true)
    private String content;
    @ManyToOne
    private Piece piece;
    @Column(name = "order_num", nullable = false)
    private Integer orderNum;
    @Column(name = "date", nullable = false)
    private Date date;

    public Item(String content, Piece piece, Integer orderNum, Date date){
        this.content = content;
        this.piece = piece;
        this.orderNum = orderNum;
        this.date = date;
    }
}
