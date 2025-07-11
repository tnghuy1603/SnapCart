package com.snapcart.user_service.controller;

import com.snapcart.user_service.dto.LoginRequest;
import com.snapcart.user_service.dto.request.RegisterRequest;
import com.snapcart.user_service.dto.response.SnapCartResponse;
import com.snapcart.user_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        return SnapCartResponse.successResponse(authService.login(request, response), "Login successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return SnapCartResponse.successResponse("Register successful");
    }
}
