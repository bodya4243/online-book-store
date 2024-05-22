package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserRequestDto;
import com.example.onlinebookstore.exception.RegistrationException;
import com.example.onlinebookstore.model.User;

public interface UserService {
    UserDto registerUser(UserRequestDto userRequestDto) throws RegistrationException;

    User findByUsername(String username);
}
