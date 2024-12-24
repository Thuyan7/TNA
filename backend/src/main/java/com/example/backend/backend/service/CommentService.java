package com.example.backend.backend.service;

import com.example.backend.backend.dto.CommentDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import org.springframework.stereotype.Service;


public interface CommentService {
    Comment save(CommentDTO commentDTO, Post post, User user);
    Comment updateApprovedComment(CommentDTO commentDTO);
}
