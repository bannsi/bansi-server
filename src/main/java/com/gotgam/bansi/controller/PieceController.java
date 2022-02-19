package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.PieceDTO.ListPieceResponse;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceResponse;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.util.JwtUtil;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/pieces/v1")
public class PieceController {
    @Autowired
    private PieceService pieceService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ListPieceResponse> getPiecesByUserId(@RequestHeader HttpHeaders headers){
        String token = headers.getFirst("Authorization").substring(7);
        log.info(token);
        String kakaoId = jwtUtil.getUsernameFromToken(token);
        List<Piece> pieces = pieceService.findPieceByUserId(kakaoId);
        
        return ResponseEntity.ok().body(new ListPieceResponse("S00", "message", pieces));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<PieceResponse> savePiece(@RequestHeader HttpHeaders headers, @ModelAttribute PieceRequest pieceRequest){
        String token = headers.getFirst("Authorization").substring(7);
        String kakaoId = jwtUtil.getUsernameFromToken(token);
        Piece piece = pieceService.savePiece(pieceRequest, kakaoId);
        return ResponseEntity.ok().body(new PieceResponse("S00", "saved", piece));
    }
    
    @RequestMapping(value="/{pieceId}/", method=RequestMethod.PUT)
    public ResponseEntity<PieceResponse> updatePiece(@PathVariable Long pieceId, @RequestBody Piece piece) {
        try {
            pieceService.updatePiece(pieceId, piece);    
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(new PieceResponse("E00", e.getMessage(), null));
        }
        return ResponseEntity.ok().body(new PieceResponse("S00", "piece updated", piece));
    }
}
