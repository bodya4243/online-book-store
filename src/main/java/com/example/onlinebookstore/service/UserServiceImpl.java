package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserRequestDto;
import com.example.onlinebookstore.exception.RegistrationException;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto registerUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't find register user");
        }
        User user = userMapper.toModel(userRequestDto);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
