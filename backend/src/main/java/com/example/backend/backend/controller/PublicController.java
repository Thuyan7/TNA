package com.example.backend.backend.controller;

import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.Comment;
import com.example.backend.backend.entity.Post;
import com.example.backend.backend.repository.CommentReponsitory;
import com.example.backend.backend.repository.PostRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class PublicController {
    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentReponsitory commentReponsitory;
    private final UserRepository userRepository;

    @Autowired
    public PublicController(CommentReponsitory commentReponsitory, PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.commentReponsitory = commentReponsitory;
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("/introduce")
    public String introducePage(Model model, Principal principal) {
        if(principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            String role = userRepository.findByEmail(email).getRole().getName();
            model.addAttribute("name", name);
            model.addAttribute("role", role);
        }
        return "introduce";
    }

    @GetMapping("/contact")
    public String contactPage(Model model, Principal principal) {
        if(principal != null) {
            String email = principal.getName();
            String name = userRepository.findByEmail(email).getFullName();
            String role = userRepository.findByEmail(email).getRole().getName();
            model.addAttribute("name", name);
            model.addAttribute("role", role);
        }
        return "contact";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        List<Post> posts = postRepository.findByApprovedTrue();
        List<Comment> comments = commentReponsitory.findByApprovedTrue();
        model.addAttribute("comments", comments);
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/user";
        }
        model.addAttribute("user", new UserDTO());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO, Model model){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!userDTO.getEmail().matches(emailRegex)) {
            model.addAttribute("error1", "Email không hợp lệ!");
            return "register";
        }

        String phoneRegex = "^0[1-9][0-9]{8,9}$";
        if (!userDTO.getPhone().matches(phoneRegex)) {
            model.addAttribute("error2", "Số điện thoại không hợp lệ!");
            return "register";
        }
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error3", "Mật khẩu không khớp.Vui lòng nhập lại!");
            return "register";
        }
        userService.registerUser(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        return "login";
    }


    @GetMapping("/redirectAfterLogin")
    public String redirectAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/user/home";
        }
    }
}
