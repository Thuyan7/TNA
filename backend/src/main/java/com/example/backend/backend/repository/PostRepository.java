package com.example.backend.backend.repository;


import com.example.backend.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{
    Post findByTitle(String title);
    List<Post> findByApprovedTrue();
    Post findById(int id);
    Post deleteById(int id);
}
