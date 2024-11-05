package com.example.backend.backend.service;

import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.entity.User;

public interface UserService {
    User registerUser(UserDTO userDTO);
    User getUserByEmail(String email);
}