package com.example.backend.backend.controller;

import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.entity.*;
import com.example.backend.backend.repository.AmenityReponsitory;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.LocationService;
import com.example.backend.backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final AmenityReponsitory amenityReponsitory;
    private final UserRepository userRepository;
    private final CommentReponsitory commentReponsitory;
    private final PostRepository postRepository;
    private final LocationService locationService;

    @Autowired
    public PostController(PostService postService,AmenityReponsitory amenityReponsitory, UserRepository userRepository,CommentReponsitory commentReponsitory,PostRepository postRepository,LocationService locationService) {
        this.postService = postService;
        this.amenityReponsitory = amenityReponsitory;
        this.userRepository = userRepository;
        this.commentReponsitory = commentReponsitory;
        this.postRepository = postRepository;
        this.locationService = locationService;
    }

    @GetMapping("/create_post")
    public String showCreatePostPage(Model model , Principal principal){
        if(principal != null){
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            model.addAttribute("name", name);
            List<Amenity> amenities = amenityReponsitory.findAll();
            model.addAttribute("amenities", amenities);
        }
        return "create-post";
    }

    @PostMapping("/create_post")
    public String createPost( @Valid @ModelAttribute PostDTO postDTO,
                             @RequestParam("images") List<MultipartFile> images, Principal principal, Model model, BindingResult result) {
        User user = userRepository.findByEmail(principal.getName());

        if (result.hasErrors()) {
            return "create-post";
        }
        postService.savePost(postDTO, images, user);
        return "redirect:/post";
    }

    @GetMapping
    public String postPage(Model model, Principal principal) {
        if(principal != null){
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            String role = userRepository.findByEmail(email).getRole().getName();
            model.addAttribute("name", name);
            model.addAttribute("role", role);
        }
        List<Post> posts = postRepository.findByApprovedTrue();
        model.addAttribute("cities",locationService.getAllCities());
        model.addAttribute("districts",locationService.getAllDistricts());
        model.addAttribute("wards",locationService.getAllWards());
        model.addAttribute("posts", posts);
        return "post";
    }


    @GetMapping("/search")
    public String searchPosts(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String priceRange,
            Model model) {
        List<Post> posts = postService.searchPost(city, district, ward, priceRange);
        model.addAttribute("cities",locationService.getAllCities());
        model.addAttribute("districts",locationService.getAllDistricts());
        model.addAttribute("wards",locationService.getAllWards());
        model.addAttribute("posts", posts);
        return "post";
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable int id, Model model,Principal principal){
        if(principal != null){
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            String role = userRepository.findByEmail(email).getRole().getName();
            Post post = postRepository.findById(id);
            List<Post> posts = postService.getRandomPost(3);
            List<Comment> comments = commentReponsitory.findByPostIdAndApprovedTrue(id);
            model.addAttribute("role",role);
            model.addAttribute("post", post);
            model.addAttribute("posts", posts);
            model.addAttribute("comments", comments);
            model.addAttribute("name", name);
        }
        return "postdetail";
    }
}
