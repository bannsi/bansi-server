package com.gotgam.bansi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.OptionalKeywordRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PieceServiceTest {
    private PieceService pieceService;
    @Autowired private PieceRepository pieceRepository;
    @Autowired private KeywordRepository keywordRepository;
    @Autowired private KeywordService keywordService;
    @Autowired private WhoKeywordRepository whoKeywordRepository;
    @Autowired private WhoKeywordService whoKeywordService;
    @Autowired private OptionalKeywordRepository opKeywordRepository;
    @Autowired private OptionalKeywordService optionalKeywordService;
    @Autowired private UserService userService;
    @Autowired private ImageService imageService;
    @Autowired private PlaceKeywordService placeKeywordService;
    @Autowired private PieceLikeService likeService;
    @Autowired private JwtUtil jwtUtil;

    @Value("${test.token}")
    private String testToken;
    
    @BeforeEach
    public void setUp(){
        this.pieceService = new PieceServiceImpl(
            pieceRepository,
            keywordRepository,
            whoKeywordRepository,
            opKeywordRepository,
            userService,
            imageService,
            placeKeywordService,
            likeService);
    }

    @Test
    @DisplayName("조각 생성")
    public void pieceCreateTest(){
        List<Long> keywords = new ArrayList<>();
        keywords.add(keywordService.createKeyword("test keyword1").getId());
        keywords.add(keywordService.createKeyword("test keyword2").getId());
        List<Long> opKeywords = new ArrayList<>();
        opKeywords.add(optionalKeywordService.createOpKeyword(new OptionalKeywordRequest("test opkey1")).getId());
        List<Long> whos = new ArrayList<>();
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who1")).getId());
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who2")).getId());
        
        String keywordReq = keywords.toString();
        keywordReq = keywordReq.substring(1, keywordReq.length()-1);
        String opKeywordReq = opKeywords.toString();
        opKeywordReq = opKeywordReq.substring(1, opKeywordReq.length()-1);
        String whoKeywordReq = whos.toString();
        whoKeywordReq = whoKeywordReq.substring(1, whoKeywordReq.length()-1);
        List<ImageRequest> images = new ArrayList<>();
        images.add(new ImageRequest("test", true));
        images.add(new ImageRequest("test2", false));
        
        PieceRequest pieceDto = new PieceRequest(
            LocalDate.now(),
            "test content",
            2.3333,
            3.4444,
            "test address",
            "test addresDetail",
            "www.naver.com",
            images,
            keywords,
            opKeywords,
            whos,
            "busan"
        );
    }
    
    @Test
    public void ThumbNailTest(){
        String userId = jwtUtil.getUsernameFromTokenStr(testToken);
        pieceService.findThumbnails(userId);
    }
}
