package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserRequestDto;
import com.example.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserDto registerUser(UserRequestDto userRequestDto) throws RegistrationException;
}
