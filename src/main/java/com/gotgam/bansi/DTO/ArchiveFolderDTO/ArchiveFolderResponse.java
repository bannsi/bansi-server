package com.gotgam.bansi.DTO.ArchiveFolderDTO;

import com.gotgam.bansi.DTO.ResponseDTO;

public class ArchiveFolderResponse extends ResponseDTO{
    private ArchiveFolderDTO body;
        public ArchiveFolderResponse(String code, String message, ArchiveFolderDTO body){
            super(code, message);
            this.body = body;
        }
}
