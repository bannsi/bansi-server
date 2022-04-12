package com.gotgam.bansi.controller;

import com.gotgam.bansi.DTO.CommentDTO;
import com.gotgam.bansi.DTO.CommentDTO.CommentRequest;
import com.gotgam.bansi.DTO.CommentDTO.CommentResponse;
import com.gotgam.bansi.model.Comment;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.service.CommentService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final PieceService pieceService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @RequestMapping(value="/piece/{pieceId}/", method=RequestMethod.POST)
    public CommentResponse createCommnet(@RequestHeader HttpHeaders headers, @PathVariable Long pieceId, @RequestBody CommentRequest commentReq) {
        System.out.println(commentReq.getComment());
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        User user = userService.getUserFromId(kakaoId);
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        CommentDTO commentDTO =  commentService.createCommnet(user, piece, commentReq.getComment());
        return new CommentResponse("S00", "댓글 생성 성공", commentDTO);
    }


    @RequestMapping(value = "/piece/{pieceId}")
    public Page<Comment> listComment(@PathVariable Long pieceId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return commentService.findAllByPiece(pieceId, PageRequest.of(page, size));
    }
}
