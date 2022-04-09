package com.gotgam.bansi.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ArchiveLinkDTO.ArchiveLinkRequest;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderDTO;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderDTO.ArchiveFolderRequest;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderResponse;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ListArchiveFolderResponse;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.service.ArchiveFolderService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<ArchiveFolderDTO> folderDtos = folders.stream().map(folder -> new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks()
        )).collect(Collectors.toList());
        return ResponseEntity.ok().body(new ListArchiveFolderResponse("S00", "list my archives", folderDtos));
    }

    @RequestMapping(value="/folder/", method=RequestMethod.POST)
    public ResponseEntity<ArchiveFolderResponse> createFolder(@RequestHeader HttpHeaders headers, @RequestBody ArchiveFolderRequest folderDto) {
        String userId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        // TODO: service와 controller는 DTO를 사용해서 통신해야함
        ArchiveFolder folder = folderService.createFolder(userId, folderDto.getName());
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더 생성 성공", archievFolderDto));
    }
    
    @RequestMapping(value="/folder/{folderId}/", method=RequestMethod.PUT)
    public ResponseEntity<ArchiveFolderResponse> updateFolder(@PathVariable Long folderId, @RequestBody ArchiveFolderRequest folderDto) {
        ArchiveFolder folder = folderService.updateFolder(folderId, folderDto.getName());
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더 업데이트 성공", archievFolderDto));
    }

    @RequestMapping(value="/folder/{folderId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok().body(new ResponseDTO("S00", "폴더 삭제 성공"));
    }
    
    @RequestMapping(value="/folder/{folderId}", method=RequestMethod.GET)
    public ResponseEntity<ArchiveFolderResponse> getFolder(@PathVariable Long folderId) {
        ArchiveFolder folder = folderService.getFolder(folderId); 
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "폴더 가져오기 성공", archievFolderDto));
    }
    
    @RequestMapping(value="/folder/{folderId}/piece/{pieceId}/", method=RequestMethod.POST)
    public ResponseEntity<ArchiveFolderResponse> addPieceToFolder(@PathVariable Long folderId, @PathVariable Long pieceId) {
        ArchiveFolder folder = folderService.addPieceToFolder(pieceId, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에 조각 담기 성공", archievFolderDto));
    }

    @RequestMapping(value="/folder/{folderId}/piece/{pieceId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ArchiveFolderResponse> removePieceFromFolder(@PathVariable Long folderId, @PathVariable Long pieceId) {
        ArchiveFolder folder = folderService.deletePieceFromFolder(pieceId, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에서 조각 삭제 성공", archievFolderDto));
    }
    
    @RequestMapping(value="/folder/{folderId}/collection/{collectionId}/", method=RequestMethod.POST)
    public ResponseEntity<ArchiveFolderResponse> addCollectionToFolder(@PathVariable Long folderId, @PathVariable Long collectionId) {
        ArchiveFolder folder = folderService.addCollectionToFolder(collectionId, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에 조각 모음 담기 성공", archievFolderDto));
    }

    @RequestMapping(value="/folder/{folderId}/collection/{collectionId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ArchiveFolderResponse> removeCollectionFromFolder(@PathVariable Long folderId, @PathVariable Long collectionId) {
        ArchiveFolder folder = folderService.deleteCollectionFromFolder(collectionId, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에서 조각 모음 삭제 성공", archievFolderDto));
    }
    @RequestMapping(value="/folder/{folderId}/link/", method=RequestMethod.POST)
    public ResponseEntity<ArchiveFolderResponse> addLinkToFolder(@PathVariable Long folderId, @RequestBody ArchiveLinkRequest linkDto) {
        ArchiveFolder folder = folderService.addLinkToFolder(linkDto, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에 링크 담기 성공", archievFolderDto));
    }

    @RequestMapping(value="/folder/{folderId}/link/{linkId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ArchiveFolderResponse> removeLinkFromFolder(@PathVariable Long folderId, @PathVariable Long linkId) {
        ArchiveFolder folder = folderService.deleteLinkFromFolder(linkId, folderId);
        ArchiveFolderDTO archievFolderDto = new ArchiveFolderDTO(
            folder.getId(), 
            folder.getName(), 
            folder.getThumbNails(), 
            folder.getCollections(), 
            folder.getLinks());
        return ResponseEntity.ok().body(new ArchiveFolderResponse("S00", "아카이브 폴더에서 조각 삭제 성공", archievFolderDto));
    }

    @GetMapping(value="/test")
    public String getMethodName() {
        return "test";
    }   
}
