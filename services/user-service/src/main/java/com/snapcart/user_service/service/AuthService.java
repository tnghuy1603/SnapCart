package com.snapcart.user_service.service;

import com.snapcart.user_service.dto.LoginRequest;
import com.snapcart.user_service.dto.request.RegisterRequest;
import com.snapcart.user_service.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    UserResponse login(LoginRequest request, HttpServletResponse response);
    void register(RegisterRequest request);
}
