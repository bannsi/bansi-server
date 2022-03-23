package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.ArchiveFolder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ArchiveFolderDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ArchiveFolderRequest{
        private String name;
    }
    public static class ArchiveFolderResponse extends ResponseDTO {
        private ArchiveFolder body;
        public ArchiveFolderResponse(String code, String message, ArchiveFolder body){
            super(code, message);
            this.body = body;
        }
    }
    public static class ListArchiveFolderResponse extends ResponseDTO {
        private List<ArchiveFolder> body;
        public ListArchiveFolderResponse(String code, String message, List<ArchiveFolder> body){
            super(code, message);
            this.body = body;
        }
    }
}
