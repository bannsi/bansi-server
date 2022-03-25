package com.gotgam.bansi.controller;

import java.util.List;

import javax.validation.Valid;

import com.gotgam.bansi.DAO.ThumbNail;
import com.gotgam.bansi.DTO.PieceDTO.ListPieceResponse;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceResponse;
import com.gotgam.bansi.DTO.PieceLikeDTO.PieceLikeResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.DTO.ThumbnailDTO.ListThumbnailResponse;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.service.PieceLikeService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/pieces/v1")
public class PieceController {
    private final JwtUtil jwtUtil;
    private final PieceService pieceService;
    private final PieceLikeService pieceLikeService;
    private final UserService userService;

    public PieceController(JwtUtil jwtUtil, PieceService pieceService, PieceLikeService pieceLikeService, UserService userService){
        this.jwtUtil = jwtUtil;
        this.pieceService = pieceService;
        this.pieceLikeService = pieceLikeService;
        this.userService = userService;
    }
    
    @RequestMapping(value = "/user/{kakaoId}", method = RequestMethod.GET)
    public ResponseEntity<ListThumbnailResponse> getPiecesByUserId(@PathVariable String kakaoId){
        List<ThumbNail> thumbnails = pieceService.findThumbnails(kakaoId);
        return ResponseEntity.ok().body(new ListThumbnailResponse("S00", "message", thumbnails));
    }

    @RequestMapping(value="/{pieceId}", method=RequestMethod.GET)
    public ResponseEntity<PieceResponse> getPiece(@PathVariable Long pieceId) {
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        return ResponseEntity.ok().body(new PieceResponse("S00", "piece", piece));
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<PieceResponse> savePiece(@RequestHeader HttpHeaders headers, @Valid @RequestBody PieceRequest pieceRequest){
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        Piece piece = pieceService.savePiece(pieceRequest, kakaoId);
        return ResponseEntity.ok().body(new PieceResponse("S00", "saved", piece));
    }
    
    @RequestMapping(value="/{pieceId}/", method=RequestMethod.PUT)
    public ResponseEntity<PieceResponse> updatePiece(@PathVariable Long pieceId, @Valid @RequestBody Piece piece) {
        pieceService.updatePiece(pieceId, piece);
        return ResponseEntity.ok().body(new PieceResponse("S00", "piece updated", piece));
    }

    @RequestMapping(value = "/like/{pieceId}/", method = RequestMethod.POST)
    public ResponseEntity<PieceLikeResponse> likePiece(@PathVariable Long pieceId, @RequestHeader HttpHeaders headers){
        String userId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        Long likeCount = pieceService.likePiece(pieceId, userId);
        return ResponseEntity.ok().body(new PieceLikeResponse("S00", "like success", likeCount));
    }

    @RequestMapping(value="/dislike/{pieceId}/", method=RequestMethod.POST)
    public ResponseEntity<PieceLikeResponse> dislikePiece(@PathVariable Long pieceId, @RequestHeader HttpHeaders headers) {    
        String userId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        Long likeCount = pieceService.likePiece(pieceId, userId);
        return ResponseEntity.ok().body(new PieceLikeResponse("S00", "dislike success", likeCount));
    }
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<ListThumbnailResponse> getRandomPieces() {
        List<ThumbNail> thumbnails = pieceService.findRandomPieces();
        return ResponseEntity.ok().body(new ListThumbnailResponse("S00", "random thumbnails", thumbnails));
    }
    
    @RequestMapping(value="/{pieceId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deletePiece(@PathVariable Long pieceId) {
        pieceService.deletePiece(pieceId);
        return ResponseEntity.ok(new ResponseDTO("S00", "piece is deleted"));
    }

    @RequestMapping(value = "/place/{placeName}", method = RequestMethod.GET)
    public ResponseEntity<ListPieceResponse> filterByPlace(@PathVariable String placeName){
        List<Piece> pieces = pieceService.findByPlace(placeName);
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "filtered by place", pieces));
    }

    @GetMapping(value="/who/{whoId}")
    public ResponseEntity<ListPieceResponse> filterByWho(@PathVariable Long whoId) {
        List<Piece> pieces = pieceService.findByWho(whoId);
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "filtered by who", pieces));
    }

    @GetMapping(value="/keyword/{keywordId}")
    public ResponseEntity<ListPieceResponse> filterByKeyword(@PathVariable Long keywordId) {
        List<Piece> pieces = pieceService.findByKeyword(keywordId);
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "filtered by keyword", pieces));
    }    
}
