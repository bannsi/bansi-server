package com.gotgam.bansi.DTO;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO {
    List<Long> keywordIds;
    List<Long> whoIds;
    List<String> placeNames;
}
