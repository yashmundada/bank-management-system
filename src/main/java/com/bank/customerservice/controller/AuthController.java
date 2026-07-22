package com.bank.customerservice.controller;

import com.bank.customerservice.dto.ApiResponse;
import com.bank.customerservice.dto.LoginRequest;
import com.bank.customerservice.dto.RegisterRequest;
import com.bank.customerservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterRequest request) {

        String message = userService.register(request);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                message,
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



@PostMapping("/login")
public ResponseEntity<ApiResponse<String>> login(
        @Valid @RequestBody LoginRequest request) {

    String token = userService.login(request);

    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Login Successful",
            token
    );

    return ResponseEntity.ok(response);
}
}