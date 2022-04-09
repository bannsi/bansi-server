package com.gotgam.bansi.DTO.ArchiveFolderDTO;

import java.util.List;

import com.gotgam.bansi.DTO.ResponseDTO;

import lombok.Getter;

@Getter
public class ListArchiveFolderResponse extends ResponseDTO{
    private List<ArchiveFolderDTO> body;
        public ListArchiveFolderResponse(String code, String message, List<ArchiveFolderDTO> body){
            super(code, message);
            this.body = body;
        }    
}
