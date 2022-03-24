package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderRequest;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderResponse;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ListArchiveFolderResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.service.ArchiveFolderService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;






@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/archive")
public class ArchiveController {
    private final ArchiveFolderService folderService;
    private final JwtUtil jwtUtil;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ListArchiveFolderResponse> listArchives(@PathVariable String userId){
        List<ArchiveFolder> folders = folderService.listFolder(userId);
        return ResponseEntity.ok().body(new ListArchiveFolderResponse("S00", "list my archives", folders));
    }

    @RequestMapping(value="/folder/", method=RequestMethod.POST)
    public ResponseEntity<ArchiveFolderResponse> createFolder(@RequestHeader HttpHeaders headers, @RequestBody ArchiveFolderRequest folderDto) {
        String userId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        ArchiveFolder folder = folderService.createFolder(userId, folderDto.getName());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더 생성 성공", folder));
    }
    
    @RequestMapping(value="/folder/{folderId}/", method=RequestMethod.PUT)
    public ResponseEntity<ArchiveFolderResponse> updateFolder(@PathVariable Long folderId, @RequestBody ArchiveFolderRequest folderDto) {
        ArchiveFolder folder = folderService.updateFolder(folderId, folderDto.getName());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더 업데이트 성공", folder));
    }

    @RequestMapping(value="/folder/{folderId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok().body(new ResponseDTO("S00", "폴더 삭제 성공"));
    }
    
    @RequestMapping(value="/folder/{folderId}", method=RequestMethod.GET)
    public ResponseEntity<ArchiveFolderResponse> getFolder(@PathVariable Long folderId) {
        ArchiveFolder folder = folderService.getFolder(folderId); 
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "폴더 가져오기 성공", folder));
    }

    
}
