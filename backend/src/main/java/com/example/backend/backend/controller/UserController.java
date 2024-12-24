package com.example.backend.backend.controller;


import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.*;
import com.example.backend.backend.repository.*;
import com.example.backend.backend.service.PostService;
import com.example.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentReponsitory commentReponsitory;
    private final AmenityReponsitory amenityReponsitory;
    private final LocationRepository locationRepository;
    private final PostService postService;


    @Autowired
    public UserController( UserRepository userRepository, PostRepository postRepository, CommentReponsitory commentReponsitory, PostService postService, AmenityReponsitory amenityReponsitory, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentReponsitory = commentReponsitory;
        this.postService = postService;
        this.amenityReponsitory = amenityReponsitory;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/home")
    public String userPage(Model model , Principal principal) {
        if(principal != null){
        String email = principal.getName();
        String name = userRepository.findByEmail(email).getFullName();
            List<Post> posts = postRepository.findByApprovedTrue();
            List<Comment> comments = commentReponsitory.findByApprovedTrue();
            model.addAttribute("comments", comments);
            model.addAttribute("posts", posts);
            model.addAttribute("name", name);
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

    @GetMapping("/profile/updatePost/{id}")
    public String showUpdatePostForm(@PathVariable("id") int id, Model model,Principal principal){
        if(principal != null){
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user",user);
        }
        Post post = postRepository.findById(id);
        List<Amenity> amenities = amenityReponsitory.findAll();
        model.addAttribute("amenities", amenities);
        model.addAttribute("post", post);
        return "update-post";
    }

    @PostMapping("/profile/updatePost")
    public String updatePost( @ModelAttribute("post") PostDTO postDTO, @RequestParam("images") List<MultipartFile> images) {
            postService.updatePost(postDTO, images);
            return "redirect:/user/profile";

        }

    }



