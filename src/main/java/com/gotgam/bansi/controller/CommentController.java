package com.gotgam.bansi.controller;

import com.gotgam.bansi.DTO.CommentDTO;
import com.gotgam.bansi.DTO.CommentDTO.CommentRequest;
import com.gotgam.bansi.DTO.CommentDTO.CommentResponse;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.service.CommentService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final PieceRepository pieceRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @RequestMapping(value="/piece/{pieceId}/", method=RequestMethod.POST)
    public CommentResponse createCommnet(@RequestHeader HttpHeaders headers, @PathVariable Long pieceId, @RequestBody CommentRequest commentReq) {
        String token = headers.getFirst("Authorization").substring(7);
        String kakaoId = jwtUtil.getUsernameFromToken(token);
        User user = userService.getUserFromId(kakaoId);
        Piece piece = pieceRepository.getById(pieceId);
        CommentDTO commentDTO =  commentService.createCommnet(user, piece, commentReq.getContent());
        return new CommentResponse("S00", "댓글 생성 성공", commentDTO);
    }
    
}
