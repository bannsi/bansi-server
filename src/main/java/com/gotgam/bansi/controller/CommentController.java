package com.gotgam.bansi.controller;

import com.gotgam.bansi.DTO.CommentDTO;
import com.gotgam.bansi.DTO.CommentDTO.CommentRequest;
import com.gotgam.bansi.DTO.CommentDTO.CommentResponse;
import com.gotgam.bansi.DTO.CommentDTO.PageCommentResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
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
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        CommentDTO commentDTO =  commentService.createCommnet(kakaoId, pieceId, commentReq);
        return new CommentResponse("S00", "댓글 생성 성공", commentDTO);
    }

    @RequestMapping(value = "/piece/{pieceId}", method = RequestMethod.GET)
    public PageCommentResponse listComment(@PathVariable Long pieceId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<CommentDTO> pages =  commentService.findAllByPiece(pieceId, PageRequest.of(page, size));
        return new PageCommentResponse("S00", "댓글 참조 성공", pages);
    }

    @RequestMapping(value="/{commentId}/", method=RequestMethod.PUT)
    public CommentResponse updateComment(@RequestHeader HttpHeaders headers, @PathVariable Long commentId, @RequestBody CommentRequest commentReq) {
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        CommentDTO commentDTO = commentService.updateCommnet(kakaoId, commentId, commentReq);
        return new CommentResponse("S00", "댓글 수정 성공", commentDTO);
    }
    
    @RequestMapping(value="/commentId/", method=RequestMethod.DELETE)
    public ResponseDTO deleteComment(@RequestHeader HttpHeaders headers, @PathVariable Long commentId) {
        String kakaoId = jwtUtil.getUsernameFromTokenStr(headers.getFirst("Authorization"));
        commentService.deleteComment(kakaoId, commentId);
        return new ResponseDTO("S00", "댓글 삭제 성공");
    }
    
}
