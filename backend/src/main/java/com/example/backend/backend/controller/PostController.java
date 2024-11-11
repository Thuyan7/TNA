package com.example.backend.backend.controller;

import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.entity.Amenity;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.AmenityReponsitory;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    public PostController(PostService postService,AmenityReponsitory amenityReponsitory, UserRepository userRepository,CommentReponsitory commentReponsitory,PostRepository postRepository){
        this.postService = postService;
        this.amenityReponsitory = amenityReponsitory;
        this.userRepository = userRepository;
        this.commentReponsitory = commentReponsitory;
        this.postRepository = postRepository;
    }

    @GetMapping("/create_post")
    public String showCreatePostPage(Model model , Principal principal){
        if(principal != null){
            String email = principal.getName();
            model.addAttribute("email", email);
            List<Amenity> amenities = amenityReponsitory.findAll();
            model.addAttribute("amenities", amenities);
        }
        return "create-post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute PostDTO postDTO,
                             @RequestParam("images") List<MultipartFile> images, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        postService.savePost(postDTO, images, user);
        return "redirect:/post";
    }

    @GetMapping
    public String postPage(Model model, Principal principal) {
        if(principal != null){
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        List<Post> posts = postRepository.findByApprovedTrue();
        model.addAttribute("posts", posts);
        return "post";
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable int id, Model model,Principal principal){
        if(principal != null){
            String email = principal.getName();
            Post post = postRepository.findById(id);
            List<Post> posts = postService.getRandomPost(3);
            List<Comment> comments = commentReponsitory.findByPostIdAndApprovedTrue(id);
            model.addAttribute("post", post);
            model.addAttribute("posts", posts);
            model.addAttribute("comments", comments);
            model.addAttribute("email", email);
        }
        return "postdetail";
    }
}
