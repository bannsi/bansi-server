package com.gotgam.bansi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderDTO;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.respository.ArchiveFolderRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArchiveSerivceTest {
    @Autowired private ArchiveFolderService folderService;
    @Autowired private ArchiveFolderRepository folderRepository;
    @Autowired private PieceService pieceService;
    @Autowired private PieceRepository pieceRepository;

    @Autowired private JwtUtil jwtUtil;

    @Value("${test.token}")
    private String testToken;
    
    @Test
    @DisplayName("아카이브 생성 테스트")
    public void createArchiveTest(){
        String username = jwtUtil.getUsernameFromTokenStr(testToken);

        ArchiveFolderDTO folder = folderService.createFolder(username, "test folder");
        
        assertEquals(folder.getName(), "test folder");
    }

    @Test
    @DisplayName("아카이브 변경 테스트")
    public void updateArchiveTest(){
        String username = jwtUtil.getUsernameFromTokenStr(testToken);
        ArchiveFolderDTO folder = folderService.createFolder(username, "test folder");
        Piece piece = pieceRepository.findAll().get(0);
        
        folder = folderService.addPieceToFolder(piece.getPieceId(), folder.getId());
        assertEquals(folder.getThumbNails().size(), 1);

        folder = folderService.deletePieceFromFolder(piece.getPieceId(), folder.getId());
        log.info("folder pieces size: " + String.valueOf(folder.getThumbNails().size()));
        ArchiveFolder testFolder = folderRepository.findById(folder.getId()).orElseThrow(() -> new NotFoundException("잘못된 아카이브 아이디"));
        log.info("test folder pieces size: " + String.valueOf(testFolder.getThumbNails().size()));
        assertEquals(folder.getThumbNails().size(), testFolder.getThumbNails().size());
    }   
}
