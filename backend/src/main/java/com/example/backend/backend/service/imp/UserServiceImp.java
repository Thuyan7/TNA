package com.example.backend.backend.service.imp;

import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.Role;
import com.example.backend.backend.entity.User;
import com.example.backend.backend.repository.RoleRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findById(1).orElseThrow(()->new RuntimeException("Role not found"));

        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
