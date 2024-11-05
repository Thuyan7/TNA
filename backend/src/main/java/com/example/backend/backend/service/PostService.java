package com.example.backend.backend.service;

import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.entity.Amenity;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface PostService {
    Post savePost(PostDTO postDTO, List<MultipartFile> images, User user);
    List<Amenity> getAllAmenities();
    Post updateApprovedPost(PostDTO postDTO);
    void deletePost(int postId);
    List<Post> getRandomPost(int count);
}
