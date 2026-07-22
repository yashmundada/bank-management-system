package com.bank.customerservice.service.impl;

import com.bank.customerservice.dto.LoginRequest;
import com.bank.customerservice.dto.RegisterRequest;
import com.bank.customerservice.entity.User;
import com.bank.customerservice.enums.Role;
import com.bank.customerservice.exception.InvalidCredentialsException;
import com.bank.customerservice.jwt.JwtService;
import com.bank.customerservice.repository.UserRepository;
import com.bank.customerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return jwtService.generateToken(user.getUsername());
    }
}