package com.example.backend.backend.controller;


import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.repository.AmenityReponsitory;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.CommentService;
import com.example.backend.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    private final PostService postService;
    private final AmenityReponsitory amenityReponsitory;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentReponsitory commentReponsitory;

    @Autowired
    public UserController(PostService postService, AmenityReponsitory amenityReponsitory, UserRepository userRepository, PostRepository postRepository, CommentReponsitory commentReponsitory) {
        this.postService = postService;
        this.amenityReponsitory = amenityReponsitory;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentReponsitory = commentReponsitory;
    }

    @GetMapping("/home")
    public String userPage(Model model , Principal principal) {
        if(principal != null){
        String email = principal.getName();
        List<Post> posts = postRepository.findByApprovedTrue();
        List<Comment> comments = commentReponsitory.findByApprovedTrue();
        model.addAttribute("comments", comments);
        model.addAttribute("posts", posts);
        model.addAttribute("email", email);
    }
        return "user-home";
    }
}
