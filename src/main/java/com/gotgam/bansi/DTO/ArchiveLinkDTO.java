package com.gotgam.bansi.DTO;

import java.net.URL;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ArchiveLinkDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArchiveLinkRequest{
        @NotBlank
        private URL url;
        private Integer time;
    }
}
