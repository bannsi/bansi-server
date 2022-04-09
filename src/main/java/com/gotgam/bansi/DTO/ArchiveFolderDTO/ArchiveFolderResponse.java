package com.gotgam.bansi.DTO.ArchiveFolderDTO;

import com.gotgam.bansi.DTO.ResponseDTO;

import lombok.Getter;

@Getter
public class ArchiveFolderResponse extends ResponseDTO{
    private ArchiveFolderDTO body;
        public ArchiveFolderResponse(String code, String message, ArchiveFolderDTO body){
            super(code, message);
            this.body = body;
        }
}
