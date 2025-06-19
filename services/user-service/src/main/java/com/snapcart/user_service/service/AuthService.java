package com.snapcart.user_service.service;

import com.snapcart.user_service.dto.LoginRequest;
import com.snapcart.user_service.dto.LoginResponse;
import com.snapcart.user_service.dto.RegisterRequest;
import com.snapcart.user_service.dto.response.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginRequest request, HttpServletResponse response);
    void register(RegisterRequest request);
}
