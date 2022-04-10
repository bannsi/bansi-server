package com.gotgam.bansi.DTO.ItemDTO;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String content;
        @Positive
        private Long pieceId;
        @NotNull
        private Integer orederNum;
        private LocalDate date;   
}
