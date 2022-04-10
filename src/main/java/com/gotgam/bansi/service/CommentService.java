package com.gotgam.bansi.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.CommentDTO;
import com.gotgam.bansi.model.Comment;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.CommentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentDTO createCommnet(User user, Piece piece, String content){
        Comment comment = new Comment(user, piece, content);
        return new CommentDTO(commentRepository.save(comment));
    }

    public void deleteCommnet(Long commentId){
        commentRepository.deleteById(commentId);
    }

    public CommentDTO updateCommnet(Long commentId, String content){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("잘못된 댓글 아이디"));
        comment.setContent(content);
        return new CommentDTO(comment);
    }

    public List<CommentDTO> findAllByPiece(Piece piece){
        List<Comment> comments = commentRepository.findAllByPiece(piece);
        return comments.stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
    }
}
