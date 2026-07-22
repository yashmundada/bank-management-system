package com.bank.customerservice.service;

import com.bank.customerservice.dto.LoginRequest;
import com.bank.customerservice.dto.RegisterRequest;

public interface UserService {

    String register(RegisterRequest request);

    String login(LoginRequest request);
}