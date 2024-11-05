package com.example.backend.backend.controller;

import com.example.backend.backend.dto.CommentDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.CommentService;
import com.example.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private  CommentService commentService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PostRepository postRepository;

    @PostMapping("/create-comment")
    public String createComment(@ModelAttribute CommentDTO commentDTO, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Post post = postRepository.findById(commentDTO.getPostId());
        commentService.save(commentDTO, post, user);
        return "redirect:/post/detail/" + post.getId();
    }

}
