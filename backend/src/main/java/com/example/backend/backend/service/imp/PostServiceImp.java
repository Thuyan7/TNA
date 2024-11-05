package com.example.backend.backend.service.imp;

import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.entity.*;
import com.example.backend.backend.repository.*;
import com.example.backend.backend.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final AmenityReponsitory amenityReponsitory;
    private final ImageRepository imageRepository;
    private final CommentReponsitory commentReponsitory;

    @Autowired
    public PostServiceImp(PostRepository postRepository, AmenityReponsitory amenityReponsitory, ImageRepository imageRepository, CommentReponsitory commentReponsitory) {
        this.postRepository = postRepository;
        this.amenityReponsitory = amenityReponsitory;
        this.imageRepository = imageRepository;
        this.commentReponsitory = commentReponsitory;
    }

    @Value("${file.upload-dir:C:/default/upload/directory}")
    private String uploadDir;



    @Override
    public Post savePost(PostDTO postDTO, List<MultipartFile> images, User user) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setPrice(postDTO.getPrice());
        post.setDescription(postDTO.getDescription());

        Location location = new Location();
        location.setAddress(postDTO.getStreetAddress() + ", " + postDTO.getWard() + ", " + postDTO.getDistrict() + ", " + postDTO.getCity());
        post.setLocation(location);

        post.setUser(user);
        postRepository.save(post);

        List<Image> imageList = new ArrayList<>();
        Path uploadPath = Paths.get(uploadDir);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (images != null) {
                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        Image image = new Image();
                        image.setUrl(fileName);
                        image.setPost(post);
                        imageList.add(image);

                        imageRepository.save(image);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        post.setListImages(new HashSet<>(imageList));

        List<Integer> amenityIds = postDTO.getAmenities();
        List<Amenity> selectedAmenities = amenityReponsitory.findAllById(amenityIds);
        post.setListAmenities(new HashSet<>(selectedAmenities));

        return postRepository.save(post);
    }


    @Override
    public List<Amenity> getAllAmenities() {
        return List.of();
    }

    @Override
    public Post updateApprovedPost(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getId());
        if (post != null) {
            post.setApproved(postDTO.isApproved());
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public void deletePost(int postId) {
        commentReponsitory.deleteByPostId(postId);
        imageRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getRandomPost(int count) {
        List<Post> allPosts = postRepository.findByApprovedTrue();
        Random random = new Random();
        return allPosts.stream().sorted((a,b)-> random.nextInt(3)-1)
                .limit(count)
                .toList();
    }
}
