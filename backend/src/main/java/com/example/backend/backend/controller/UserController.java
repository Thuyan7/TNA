package com.example.backend.backend.controller;


import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentReponsitory commentReponsitory;
    private final PostService postService;

    @Autowired
    public UserController( UserRepository userRepository, PostRepository postRepository, CommentReponsitory commentReponsitory, PostService postService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentReponsitory = commentReponsitory;
        this.postService = postService;

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

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            List<Post> posts = postRepository.findByUserId(user.getId());
            List<Comment> comments = commentReponsitory.findByUserId(user.getId());
            model.addAttribute("comments", comments);
            model.addAttribute("posts", posts);
            model.addAttribute("user", user);
        }
        return "profile";
    }

    @PostMapping("/profile/updateProfile")
    public String updateProfile(@ModelAttribute("user")UserDTO userDTO, Model model,Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            user.setFullName(userDTO.getFullName());
            user.setPhone(userDTO.getPhone());
            userRepository.save(user);
            model.addAttribute("user", user);
        }
        return "redirect:/user/profile";
    }

    @DeleteMapping("/profile/deletePost/{id}")
    public String deletePost(@PathVariable("id") int id ){
        postService.deletePost(id);
        return "redirect:/user/profile";
    }

    @DeleteMapping("/profile/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") int id){
        commentReponsitory.deleteById(id);
        return "redirect:/user/profile";
    }
}

