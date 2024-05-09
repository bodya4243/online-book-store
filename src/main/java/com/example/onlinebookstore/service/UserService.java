package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserRequestDto;

public interface UserService {
    UserDto registerUser(UserRequestDto userRequestDto);
}
