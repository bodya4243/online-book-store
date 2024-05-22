package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserRequestDto;
import com.example.onlinebookstore.exception.RegistrationException;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.model.Role;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.RoleRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserDto registerUser(UserRequestDto userRequestDto) throws RegistrationException {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't find register user");
        }
        User user = userMapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Collections.singleton(defaultRole));
        User savedUser = userRepository.save(user);
        shoppingCartRepository.save(new ShoppingCart(user));
        return userMapper.toDto(savedUser);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
