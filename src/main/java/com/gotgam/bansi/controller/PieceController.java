package com.gotgam.bansi.controller;

import java.util.List;

import javax.validation.Valid;

import com.gotgam.bansi.DTO.PieceDTO.ListPieceResponse;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceResponse;
import com.gotgam.bansi.DTO.PieceLikeDTO.PieceLikeResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.service.PieceLikeService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    private JwtUtil jwtUtil;
    private PieceService pieceService;
    private PieceLikeService pieceLikeService;
    private UserService userService;

    public PieceController(JwtUtil jwtUtil, PieceService pieceService, PieceLikeService pieceLikeService, UserService userService){
        this.jwtUtil = jwtUtil;
        this.pieceService = pieceService;
        this.pieceLikeService = pieceLikeService;
        this.userService = userService;
    }
    
    @RequestMapping(value = "/{kakaoId}", method = RequestMethod.GET)
    public ResponseEntity<ListPieceResponse> getPiecesByUserId(@PathVariable String kakaoId){
        List<Piece> pieces = pieceService.findPieceByUserId(kakaoId);
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "message", pieces));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<PieceResponse> savePiece(@RequestHeader HttpHeaders headers, @Valid @RequestBody PieceRequest pieceRequest){
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        log.info(pieceRequest.getAddress());
        log.info("keyword");
        log.info(pieceRequest.getKeywords().toString());
        log.info("who keyword");
        log.info(pieceRequest.getWhos().toString());
        Piece piece = pieceService.savePiece(pieceRequest, kakaoId);
        return ResponseEntity.ok().body(new PieceResponse("S00", "saved", piece));
    }
    
    @RequestMapping(value="/{pieceId}/", method=RequestMethod.PUT)
    public ResponseEntity<PieceResponse> updatePiece(@PathVariable Long pieceId, @RequestBody Piece piece) {
        pieceService.updatePiece(pieceId, piece);
        return ResponseEntity.ok().body(new PieceResponse("S00", "piece updated", piece));
    }

    @RequestMapping(value = "/like/{pieceId}/", method = RequestMethod.POST)
    public ResponseEntity<PieceLikeResponse> likePiece(@PathVariable Long pieceId, @RequestHeader HttpHeaders headers){
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        User user = userService.getUserFromId(kakaoId);
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        Long likeCount = pieceLikeService.createPieceLike(piece, user);
        return ResponseEntity.ok().body(new PieceLikeResponse("S00", "like success", likeCount));
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<ListPieceResponse> getRandomPieces() {
        List<Piece> pieces = pieceService.findRandomPieces();
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "message", pieces));
    }

    @RequestMapping(value="/{pieceId}/", method=RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deletePiece(@PathVariable Long pieceId) {
        pieceService.deletePiece(pieceId);
        return ResponseEntity.ok(new ResponseDTO("S00", "piece is deleted"));
    }
    
}
