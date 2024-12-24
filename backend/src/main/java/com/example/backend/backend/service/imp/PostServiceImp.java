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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@Transactional
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final AmenityReponsitory amenityReponsitory;
    private final ImageRepository imageRepository;
    private final CommentReponsitory commentReponsitory;
    private final LocationRepository locationRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository, AmenityReponsitory amenityReponsitory, ImageRepository imageRepository, CommentReponsitory commentReponsitory, LocationRepository locationRepository) {
        this.postRepository = postRepository;
        this.amenityReponsitory = amenityReponsitory;
        this.imageRepository = imageRepository;
        this.commentReponsitory = commentReponsitory;
        this.locationRepository = locationRepository;
    }

    @Value("${file.upload-dir:D:/backend/backend/src/main/resources/static/image}")
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

    @Override
    public List<Post> searchPost(String city, String district, String ward, String priceRange) {
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] prices = priceRange.split("-");
            minPrice = new BigDecimal(prices[0]);
            if (!"0".equals(prices[1])) {
                maxPrice = new BigDecimal(prices[1]);
            }
        }
        return postRepository.searchPosts(city, district, ward, minPrice, maxPrice);
    }

    @Override
    public Post updatePost(PostDTO postDTO, List<MultipartFile> images) {

        Post existingPost = postRepository.findById(postDTO.getId());
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setPrice(postDTO.getPrice());
        existingPost.setDescription(postDTO.getDescription());

        Location location = existingPost.getLocation();
        if (location == null) {
            location = new Location();
        }
        location.setAddress(postDTO.getFullAddress());
        existingPost.setLocation(location);

        if (images != null && !images.isEmpty()) {
            List<Image> newImageList = new ArrayList<>();
            Path uploadPath = Paths.get(uploadDir);

            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        Image image = new Image();
                        image.setUrl(fileName);
                        image.setPost(existingPost);
                        newImageList.add(image);
                        imageRepository.save(image);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            Set<Image> currentImages = existingPost.getListImages();
            currentImages.addAll(newImageList);
            existingPost.setListImages(currentImages);

        } else {

        }

        List<Integer> amenityIds = postDTO.getAmenities();
        List<Amenity> selectedAmenities = amenityReponsitory.findAllById(amenityIds);
        existingPost.setListAmenities(new HashSet<>(selectedAmenities));
        return postRepository.save(existingPost);
    }


}
