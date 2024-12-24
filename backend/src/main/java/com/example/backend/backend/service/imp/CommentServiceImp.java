package com.example.backend.backend.service.imp;

import com.example.backend.backend.dto.CommentDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentReponsitory commentReponsitory;

    @Autowired
    public CommentServiceImp(CommentReponsitory commentReponsitory) {
        this.commentReponsitory = commentReponsitory;
    }


    @Override
    public Comment save(CommentDTO commentDTO, Post post, User user) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setRating(commentDTO.getRating());
        comment.setPost(post);
        comment.setUser(user);
        return commentReponsitory.save(comment);
    }

    @Override
    public Comment updateApprovedComment(CommentDTO commentDTO) {
        Comment comment = commentReponsitory.findById(commentDTO.getId()).orElse(null);
        if(comment != null) {
            comment.setApproved(commentDTO.isApproved());
            return commentReponsitory.save(comment);
        }
        return null;
    }
}
