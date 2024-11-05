package com.example.backend.backend.repository;

import com.example.backend.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReponsitory extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostIdAndApprovedTrue(int postId);
    List<Comment> findByApprovedTrue();
    List<Comment> deleteByPostId(int postId);
}
