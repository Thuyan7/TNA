package com.example.backend.backend.controller;

import com.example.backend.backend.dto.CommentDTO;
import com.example.backend.backend.dto.PostDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.CommentService;
import com.example.backend.backend.service.PostService;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final  PostRepository postRepository;
    private final  PostService postService;
    private final UserRepository userRepository;
    private final CommentReponsitory commentReponsitory;
    private final CommentService commentService;

    public AdminController(PostRepository postRepository, PostService postService, UserRepository userRepository, CommentReponsitory commentReponsitory, CommentService commentService) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.commentReponsitory = commentReponsitory;
        this.commentService = commentService;
    }

    @GetMapping("/home")
    public String adminPage(Model model,Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            List<Post> posts = postRepository.findByApprovedTrue();
            List<Comment> comments = commentReponsitory.findByApprovedTrue();
            model.addAttribute("name", name);
            model.addAttribute("comments", comments);
            model.addAttribute("posts", posts);
        }
        return "admin-home";
    }

    @GetMapping("/user-management")
    public String userManager(Model model,Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            List<User> users = userRepository.findAll();
            model.addAttribute("name", name);
            model.addAttribute("users", users);
        }
        return "user-management";
    }

    @DeleteMapping("/user-management/{id}")
    public String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return "redirect:/admin/user-management";
    }


    @GetMapping("/comment-management")
    public String commentManagerPage(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            List<Comment> comments = commentReponsitory.findAll();
            model.addAttribute("name", name);
            model.addAttribute("comments", comments);
        }
        return "comment-management";
    }

    @PostMapping("/comment-management/updateStatus")
    public String updateComment(@RequestParam ("commentId") int commentId, @RequestParam("approved") boolean approved, Model model){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentId);
        commentDTO.setApproved(approved);
        commentService.updateApprovedComment(commentDTO);
        return "redirect:/admin/comment-management";
    }

    @DeleteMapping("/comment-management/{id}")
    public String deleteComment(@PathVariable int id){
        commentReponsitory.deleteById(id);
        return "redirect:/admin/comment-management";
    }

    @GetMapping("/post-management")
    public String postManagerPage(Model model,Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            List<Post> posts = postRepository.findAll();
            Map<Integer, Double> averageRating = new HashMap<>();
            for(Post post : posts){
                Double avgRating = commentReponsitory.findAverageRating(post.getId());
                averageRating.put(post.getId(), avgRating != null ? avgRating : 0.0);
            }
            model.addAttribute("name", name);
            model.addAttribute("posts", posts);
            model.addAttribute("averageRating", averageRating);
        }
        return "post-management";
    }

    @PostMapping("/post-management/updateStatus")
    public String updatePost(@RequestParam("postId") int postId, @RequestParam("approved") boolean approved, Model model) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setApproved(approved);
        postService.updateApprovedPost(postDTO);
        return "redirect:/admin/post-management";
    }

    @DeleteMapping("/post-management/{id}")
    public String deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return "redirect:/admin/post-management";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal){
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            long totalPost = postRepository.count();
            long totalPostApprovedTrue = postRepository.countByApprovedTrue();
            long totalPostApprovedFalse = postRepository.countByApprovedFalse();
            long totalUser = userRepository.count();
            long totalComment = commentReponsitory.count();
            long totalCommentApprovedTrue = commentReponsitory.countByApprovedTrue();
            long totalCommentApprovedFalse = commentReponsitory.countByApprovedFalse();
            model.addAttribute("user", user);
            model.addAttribute("totalPost", totalPost);
            model.addAttribute("totalPostApprovedTrue", totalPostApprovedTrue);
            model.addAttribute("totalPostApprovedFalse", totalPostApprovedFalse);
            model.addAttribute("totalUser", totalUser);
            model.addAttribute("totalComment", totalComment);
            model.addAttribute("totalCommentApprovedTrue", totalCommentApprovedTrue);
            model.addAttribute("totalCommentApprovedFalse", totalCommentApprovedFalse);
        }
        return "admin-profile";
    }

    @PostMapping("/profile/updateProfile")
    public String updateProfile(@ModelAttribute("user") UserDTO userDTO, Model model, Principal principal){
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            user.setFullName(userDTO.getFullName());
            user.setPhone(userDTO.getPhone());
            userRepository.save(user);
            model.addAttribute("user", user);
        }
        return "redirect:/admin/profile";
    }

}
