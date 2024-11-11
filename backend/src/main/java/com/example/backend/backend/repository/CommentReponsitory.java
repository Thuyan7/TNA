package com.example.backend.backend.repository;

import com.example.backend.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentReponsitory extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostIdAndApprovedTrue(int postId);
    List<Comment> findByApprovedTrue();
    List<Comment> deleteByPostId(int postId);
    List<Comment> findByUserId(int userId);

    @Query("SELECT AVG(c.rating) FROM Comment c WHERE c.post.id = :postId AND c.approved = true")
    Double findAverageRating(@Param("postId") int postId);
}
