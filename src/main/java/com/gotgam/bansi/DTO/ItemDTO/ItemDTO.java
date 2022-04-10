package com.gotgam.bansi.DTO.ItemDTO;

import java.time.LocalDate;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String content; 
    private ThumbNailDTO thumbnail;
    private Integer orderNum;
    private LocalDate date;

    public ItemDTO(Item item){
        this.id = item.getId();
        this.content = item.getContent();
        this.thumbnail = new ThumbNailDTO(item.getThumbNail());
        this.orderNum = item.getOrderNum();
        this.date = item.getDate();
    }
}
